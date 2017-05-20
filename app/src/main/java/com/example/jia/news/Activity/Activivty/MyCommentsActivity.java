package com.example.jia.news.Activity.Activivty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.jia.news.Activity.CommentsData;
import com.example.jia.news.Activity.adapter.CommentAdapter;
import com.example.jia.news.R;
import java.util.ArrayList;
/*
* 我的评论这个界面一返回，ArrayList<CommentsData> mCommentsData中数据将会被清空，因为没做数据库.....
*
* */

public class MyCommentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView_show_comments;
    private ArrayList<CommentsData> mCommentsData=new ArrayList<CommentsData>();
    private CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);
        initGetComments();
        initViews();
    }

    private void initGetComments() {
        Intent getCommentsResult=getIntent();
        mCommentsData= (ArrayList<CommentsData>) getCommentsResult.getSerializableExtra("mCommentsData");
    }

    private void initViews() {
        recyclerView_show_comments= (RecyclerView) findViewById(R.id.recyclerView_show_comments);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView_show_comments.setLayoutManager(layoutManager);
        commentAdapter=new CommentAdapter(mCommentsData);
        recyclerView_show_comments.setAdapter(commentAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  mCommentsData.clear();
        commentAdapter.notifyDataSetChanged();*/
    }
}
