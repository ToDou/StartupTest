package com.test.tudou.startuptest.ui.view.shapeview;

import android.content.Context;
import android.util.AttributeSet;

import com.test.tudou.startuptest.ui.view.shapeview.shader.BubbleShader;
import com.test.tudou.startuptest.ui.view.shapeview.shader.ShaderHelper;

/**
 * Created by tudou on 15-3-19.
 */
public class BubbleImageView extends ShaderImageView {

    public BubbleImageView(Context context) {
        super(context);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new BubbleShader();
    }
}