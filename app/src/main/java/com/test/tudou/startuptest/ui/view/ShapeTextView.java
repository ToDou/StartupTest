package com.test.tudou.startuptest.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tudou on 15-3-21.
 */
public class ShapeTextView extends View {
    private static final int DEFAULT_HEIGHT_DP = 10;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.rgb(56, 43, 100);

    private float default_text_size;

    private Path mBackgroundPath = new Path();

    private Paint mBackgroundPaint;
    private Paint mTextPaint;

    private int mBackgroundColor;
    private int mTextColor;

    private String mTextContent = "你好";
    private float mTextStart;
    private float mTextEnd;
    private float mTextSize;

    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        initializePainters();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateBackground();
        canvas.drawPath(mBackgroundPath, mBackgroundPaint);

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
        mTextColor = DEFAULT_TEXT_COLOR;
        mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        default_text_size = sp2px(20);
        mTextSize = default_text_size;
    }

    private void initializePainters() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    private void calculateText() {
        float textWidth = mTextPaint.measureText(mTextContent);
        mTextStart = getWidth() / 2 - textWidth / 2;
        mTextEnd = getWidth() / 2 + textWidth / 2;
    }

    private void calculateBackground() {
        mBackgroundPath.reset();
        mBackgroundPath.setFillType(Path.FillType.EVEN_ODD);
        mBackgroundPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
        mBackgroundPath.moveTo(getWidth() - dp2px(DEFAULT_HEIGHT_DP), getHeight() / 2);
        mBackgroundPath.lineTo(getWidth(), getHeight() / 2 - dp2px(DEFAULT_HEIGHT_DP) );
        mBackgroundPath.lineTo(getWidth(), getHeight() / 2 + dp2px(DEFAULT_HEIGHT_DP) );
        mBackgroundPath.lineTo(getWidth() - dp2px(DEFAULT_HEIGHT_DP), getHeight() / 2);
    }

    public void reDraw() {
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(backgroundColor);
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
