package com.example.animation.alipaydial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.animation.R;

/**
 * Created by mzp on 2016/12/5.
 */

public class RoundIndicatorActivity extends AppCompatActivity
{
    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_indicator);

        roundIndicatorView = (RoundIndicatorView) findViewById(R.id.my_view);
        editText = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a =Integer.valueOf(editText.getText().toString());
                roundIndicatorView.setCurrentNumAnim(a);
            }
        });
    }
}
