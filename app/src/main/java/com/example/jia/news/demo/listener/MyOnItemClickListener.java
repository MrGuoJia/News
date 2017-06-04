package com.example.jia.news.demo.listener;

import android.view.View;

/**
 * Created by jia on 2017/5/17.
 * 实现新闻列表中子项目的点击事件,传递到新的界面，实现可以评论，可以收藏，可以分享
 */

public interface MyOnItemClickListener {
    void OnItemClickListener(View view, int position);
}
