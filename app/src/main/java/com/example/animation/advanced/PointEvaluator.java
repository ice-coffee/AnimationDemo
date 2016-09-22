package com.example.animation.advanced;

import android.animation.TypeEvaluator;

/**
 * Created by mzp on 2016/9/21.
 */
public class PointEvaluator implements TypeEvaluator
{
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue)
    {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        Point point = new Point(x, y);
        return point;
    }
}
