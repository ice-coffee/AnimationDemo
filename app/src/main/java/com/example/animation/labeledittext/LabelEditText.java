package com.example.animation.labeledittext;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import com.example.animation.R;

/**
 * Created by mzp on 2016/11/18.
 */

public class LabelEditText extends EditText
{
    //标签画笔
    private TextPaint labelPaint;
    //标签颜色
    private int labelColor;
    //标签字号
    private int labelSize;
    //标签内容
    private String labelText;
    //标签高度
    private float labelHeight;
    //标签动画
    private ValueAnimator labelAnim;
    //标签动画移动比例
    private float yFraction;
    //标签透明度
    private float animValue;
    //标签动画是否已显示
    private boolean isShow = false;

    //下划线画笔
    private Paint linePaint;

    public LabelEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public LabelEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs)
    {
        // 设置edittext的背景为空，主要为了隐藏自带的下划线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            setBackground(null);
        } else
        {
            setBackgroundDrawable(null);
        }

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LabelEditText);
        labelColor = array.getColor(R.styleable.LabelEditText_labelColor, 0xee18b4ed);
        labelText = array.getString(R.styleable.LabelEditText_labelText);
        labelPaint = new TextPaint();
        labelPaint.setColor(labelColor);
        labelPaint.setTextSize(spToPix(14f));

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(dpToPix(0.35f));
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        labelHeight = getTextHeight(labelPaint);

        correctPadding();

        labelAnim = ValueAnimator.ofFloat(0, 255);
        labelAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                animValue = (float) animation.getAnimatedValue();
                yFraction = 1.5f - (animValue / 255) * 0.5f;
                invalidate();
            }
        });

        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 0 && isShow && isFocused())
                {
                    labelAnim.reverse();
                    isShow = false;
                }

                if (s.length() != 0 && !isShow && isFocused())
                {
                    labelAnim.start();
                    isShow = true;
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // 获取下划线的起点高度
        int lineStartY = getScrollY() + getHeight() - getPaddingBottom() + dpToPix(5);

        // 设置标签的透明度
        labelPaint.setAlpha((int) animValue);
        canvas.drawText(labelText, getScaleX(), (getScaleY() + labelHeight) * yFraction, labelPaint);
        canvas.drawLine(getScaleX(), lineStartY, getScaleX() + getWidth() - getPaddingRight(), lineStartY + dpToPix(0.8f), linePaint);

        super.onDraw(canvas);
    }

    // 获取TextPaint代表的高度
    private float getTextHeight(Paint paint)
    {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.bottom + (-fontMetrics.top);
    }

    //设定edittext新的填充距离
    private void correctPadding()
    {
        super.setPadding(0, getPaddingTop() + (int) labelHeight, getPaddingRight(), getPaddingBottom() + dpToPix(5));
    }

    // sp to pix
    private float spToPix(float i)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i, getContext().getResources().getDisplayMetrics());
    }

    // dp to px
    private int dpToPix(float i)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getContext().getResources().getDisplayMetrics());
    }
}
