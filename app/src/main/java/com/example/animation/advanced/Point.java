package com.example.animation.advanced;

/**
 * Created by mzp on 2016/9/21.
 */
public class Point
{
    private float x;
    private float y;

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getY()
    {
        return y;
    }

    public Point setY(float y)
    {
        this.y = y;
        return this;
    }

    public float getX()
    {
        return x;
    }

    public Point setX(float x)
    {
        this.x = x;
        return this;
    }
}
