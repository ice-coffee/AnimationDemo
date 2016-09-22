package com.example.animation.advanced;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by mzp on 2016/9/21.
 */
public class MyAnimView extends View
{
    /*圆形半径*/
    private static final float RADIUS = 50f;

    private Point currentPoint;

    /*画笔*/
    private Paint mPaint;

    /*定义控件color属性*/
    private String color;

    public MyAnimView(Context context)
    {
        super(context);
        init();
    }

    public MyAnimView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public MyAnimView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化画笔并设置为蓝色
     */
    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public String getColor()
    {
        return color;
    }

    /**
     * 通过改变画笔颜色立即刷新视图,到达动态修改控件颜色的目的
     * @param color
     */
    public void setColor(String color)
    {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (null == currentPoint)
        {
            currentPoint = new Point(RADIUS, RADIUS);
            startAnimation();
        }
        drawCircle(canvas);
    }

    /**
     * 根据Point坐标和RADIUS半径画圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas)
    {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    /**
     * 开始动画
     */
    private void startAnimation()
    {
        //Point动画的开始坐标与结束坐标
        Point startPoint = new Point(getWidth()/2, RADIUS);
        Point endPoint = new Point(getWidth()/2, getHeight()-RADIUS);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                //更新当前Point的位置
                currentPoint = (Point) animation.getAnimatedValue();

                //刷新屏幕, 回调onDraw()
                invalidate();
            }
        });


        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.setDuration(3000);

        //修改补间器
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }
}
