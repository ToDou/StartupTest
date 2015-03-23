package com.test.tudou.startuptest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.test.tudou.startuptest.R;

/**
 * Created by tudou on 15-3-21.
 */
public class ShapeTextView extends View {
    private static final int DEFAULT_HEIGHT_DP = 10;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.rgb(56, 43, 100);

    private float default_text_size;
    private float default_triangle_height;

    private Path mBackgroundPath = new Path();
    private Path mRipplePath = new Path();

    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    private Paint mRipplePaint;

    private float mTriangle_height;
    private boolean isShowTriangle;

    private int mBackgroundColor;
    private int mTextColor;

    private String mTextContent = "你好";
    private float mTextStart;
    private float mTextEnd;
    private float mTextSize;

    OnClickListener onClickListener;
    boolean clickAfterRipple = true;
    private float touchX = -1, touchY = -1;
    private float touchCircleRadius = -1;
    private int rippleSize = 3;
    private int mRippleColor;
    private float rippleSpeed = 12f;

    private boolean animation = false;

    private boolean isLastTouch = false;

    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShapeTextView,
                defStyleAttr, 0);

        mBackgroundColor = attributes.getColor(R.styleable.ShapeTextView_background_color, DEFAULT_BACKGROUND_COLOR);
        mTextSize = attributes.getDimension(R.styleable.ShapeTextView_text_size, default_text_size);
        mTextColor = attributes.getColor(R.styleable.ShapeTextView_text_color, DEFAULT_TEXT_COLOR);
        mTriangle_height = attributes.getDimension(R.styleable.ShapeTextView_triangle_height, default_triangle_height);

        initializePainters();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateBackground();
        canvas.drawPath(mBackgroundPath, mBackgroundPaint);
        drawRipple(canvas);
    }

    private void drawRipple(Canvas canvas) {
        if (touchCircleRadius > getHeight() / rippleSize)
            touchCircleRadius += rippleSpeed;
        if (touchCircleRadius >= getWidth()) {
            touchX = -1;
            touchY = -1;
            touchCircleRadius = getHeight() / rippleSize;
            if (onClickListener != null&& clickAfterRipple)
                onClickListener.onClick(this);
        }

        if (touchX != -1) {
            calculateRipple();
            canvas.drawPath(mRipplePath, mRipplePaint);
            drawText(canvas);
            invalidate();
        } else {
            drawText(canvas);
        }

        if (animation) {
            invalidate();
        }
    }

    private void drawText(Canvas canvas) {
        calculateText();
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        //计算文字baseline
        float textWidth = mTextPaint.measureText(mTextContent);
        float textBaseY = getHeight() - (getHeight() - fontHeight)/2 - fontMetrics.bottom;
        canvas.drawText(mTextContent, getWidth() / 2 - textWidth / 2, textBaseY, mTextPaint);
    }

    private void init() {
        default_text_size = sp2px(20);
        default_triangle_height = dp2px(DEFAULT_HEIGHT_DP);

        mTextColor = DEFAULT_TEXT_COLOR;
        mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        mTriangle_height = default_triangle_height;
        mTextSize = default_text_size;
    }

    private void initializePainters() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);

        mRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRipplePaint.setColor(makePressColor());

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    private void calculateText() {
        float textWidth = mTextPaint.measureText(mTextContent);
        mTextStart = getWidth() / 2 - textWidth / 2;
        mTextEnd = getWidth() / 2 + textWidth / 2;
    }

    private void calculateRipple() {
        mRipplePath.reset();
        mRipplePath.setFillType(Path.FillType.EVEN_ODD);
        mRipplePath.addCircle(touchX, touchY, touchCircleRadius, Path.Direction.CW);
    }



    private void calculateBackground() {
        mBackgroundPath.reset();
        mBackgroundPath.setFillType(Path.FillType.EVEN_ODD);
        mBackgroundPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
        if (isShowTriangle) {
            mBackgroundPath.moveTo(getWidth() - mTriangle_height, getHeight() / 2);
            mBackgroundPath.lineTo(getWidth(), getHeight() / 2 - mTriangle_height);
            mBackgroundPath.lineTo(getWidth(), getHeight() / 2 + mTriangle_height);
            mBackgroundPath.lineTo(getWidth() - mTriangle_height, getHeight() / 2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        if (isEnabled()) {
            isLastTouch = true;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchCircleRadius = getHeight() / rippleSize;
                touchX = event.getX();
                touchY = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                touchCircleRadius = getHeight() / rippleSize;
                touchX = event.getX();
                touchY = event.getY();
                if (!((event.getX() <= getWidth() && event.getX() >= 0) && (event
                        .getY() <= getHeight() && event.getY() >= 0))) {
                    isLastTouch = false;
                    touchX = -1;
                    touchY = -1;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if ((event.getX() <= getWidth() && event.getX() >= 0)
                        && (event.getY() <= getHeight() && event.getY() >= 0)) {
                    touchCircleRadius++;
                    if(!clickAfterRipple && onClickListener != null){
                        onClickListener.onClick(this);
                    }
                } else {
                    isLastTouch = false;
                    touchX = -1;
                    touchY = -1;
                }
            }else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                isLastTouch = false;
                touchX = -1;
                touchY = -1;
            }
        }
        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction,
                                  Rect previouslyFocusedRect) {
        if (!gainFocus) {
            touchX = -1;
            touchY = -1;
        }
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        animation = true;
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        animation = false;
    }

    protected int makePressColor() {
        int r = (mBackgroundColor >> 16) & 0xFF;
        int g = (mBackgroundColor >> 8) & 0xFF;
        int b = (mBackgroundColor >> 0) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.rgb(r, g, b);
    }

    public void showTriangle() {
        isShowTriangle = true;
        invalidate();
    }

    public void hideTriangle() {
        isShowTriangle = false;
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(backgroundColor);
        mRippleColor  = makePressColor();
        mRipplePaint.setColor(mRippleColor);
        invalidate();
    }

    public void setText(String text) {
        mTextContent = text;
        invalidate();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setTextSize(float size) {
        mTextSize = size;
        mTextPaint.setTextSize(size);
        invalidate();
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}
