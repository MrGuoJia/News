package com.example.jia.news.Activity.Activivty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.jia.news.R;

public class GuideActivity extends AppCompatActivity {
    private TextView tv_use;
    private ViewFlipper guide_flipper;
    private CustomGestureDetectorListener mDetectorListener;//监听手势
    private GestureDetector mGestureDetector;//滑动手势
    @Override
    protected void onCreate(Bundle savedInstanceState) { //第一次启动才显示 第二次之后不再显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();

    }

    private void initViews() {
        guide_flipper= (ViewFlipper) findViewById(R.id.guide_flipper);
        mDetectorListener=new CustomGestureDetectorListener();
        mGestureDetector=new GestureDetector(GuideActivity.this, mDetectorListener);
        tv_use= (TextView) findViewById(R.id.txt_start);
        tv_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));//传递界面
                finish();
            }
        });
    }

    class CustomGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() > e2.getX()) { //如果初始触点的X坐标比最终触点的X坐标大表示向左滑动
                guide_flipper.showNext();
            }

            if (e1.getX() < e2.getX()) { //如果初始触点的X坐标比最终触点的X坐标小表示向右滑动
                guide_flipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
