package com.example.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.animation.advanced.AdvancedActivity;
import com.example.animation.paimary.PrimaryActivity;

/**
 * Created by mzp on 2016/9/13.
 */
public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void primaryUsage(View view)
    {
        startActivity(new Intent(this, PrimaryActivity.class));
    }

    public void advancedUsage(View view)
    {
        startActivity(new Intent(this, AdvancedActivity.class));
    }
}
