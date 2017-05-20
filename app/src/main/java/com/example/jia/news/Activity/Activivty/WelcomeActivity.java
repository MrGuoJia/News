package com.example.jia.news.Activity.Activivty;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jia.news.Activity.Util.SPUtil;
import com.example.jia.news.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        judgeIsFirst();
    }

    private void judgeIsFirst() {
        // 如果是第一次运行  就显示引导页
        if(SPUtil.getIsFirstRun(WelcomeActivity.this)) {
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
            SPUtil.setIsFristRun(WelcomeActivity.this, false);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        }
    }
}
