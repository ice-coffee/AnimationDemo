package com.example.animation.paimary;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.animation.R;

/**
 * Created by mzp on 2016/9/21.
 */
public class PrimaryActivity extends Activity
{
    private static final String TAG = "property_animation";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
    }

    /**
     * 值过度
     * @param view
     */
    public void valueAnimation(View view)
    {
        ValueAnimator animator = ValueAnimator.ofInt(1, 4, 5, 8);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int currentValue = (int) animation.getAnimatedValue();
                Log.e(TAG, "current value is " + currentValue);
            }
        });

        animator.start();
    }

    /**
     * 淡入淡出
     * @param view
     */
    public void alphaAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 旋转
     * @param view
     */
    public void rotationAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 水平移动
     * @param view
     */
    public void translationXAnimation(View view)
    {
        float curTranslationX = view.getTranslationX();

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 垂直移动
     * @param view
     */
    public void translationYAnimation(View view)
    {
        float curTranslationY = view.getTranslationY();

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", curTranslationY, -500f, curTranslationY);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 前后移动
     * @param view
     */
    public void translationZAnimation(View view)
    {
//        float curTranslationZ = view.getTranslationZ();
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", curTranslationZ, 500f, curTranslationZ);
//        animator.setDuration(2000);
//        animator.start();
    }

    /**
     * X轴缩放
     * @param view
     */
    public void scaleXAnimation(View view)
    {
        float curScaleX = view.getScaleX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleX", curScaleX, 0f, curScaleX);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * Y轴缩放
     * @param view
     */
    public void scaleYAnimation(View view)
    {
        float curScaleY = view.getScaleY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", curScaleY, 0f, curScaleY);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 组合动画
     * 视图先移出屏幕再移入屏幕，然后开始旋转360度，旋转的同时进行淡入淡出操作
     * @param view
     */
    public void animationSet(View view)
    {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", 0f, -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
            }
        });
    }

    public void xmlValueAnimation(View view)
    {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_valueanim);
        animator.setTarget(view);
        animator.start();
    }

    public void xmlObjectAnimation(View view)
    {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_translate);
        animator.setTarget(view);
        animator.start();
    }

    public void xmlSetAnimation(View view)
    {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_setanim);
        animator.setTarget(view);
        animator.start();
    }
}
