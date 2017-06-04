package com.example.jia.news.demo.activivty;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import data.CommentsData;
import com.example.jia.news.demo.listener.MyOnItemLongClickListener;
import com.example.jia.news.demo.adapter.CommentAdapter;
import com.example.jia.news.R;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyCommentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView_show_comments;
    private ArrayList<CommentsData> mCommentsData=new ArrayList<CommentsData>();
    private CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);
        initViews();
        getAllCommentMsg();//将Bmob中存储的数据取出来放到到list中，使其展示出来
        longClickEvent();//长按删除事件


    }

    private void longClickEvent() {
        commentAdapter.setOnItemLongClickListener(new MyOnItemLongClickListener() {
            @Override
            public void OnItemLongClickListener(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MyCommentsActivity.this);
                builder.setMessage("确认要删除此评论吗？");
                builder.setTitle("删除评论");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommentsData delete=new CommentsData();
                       delete.setObjectId( mCommentsData.get(position).getObjectId() );
                        delete.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    mCommentsData.remove(position);
                                    commentAdapter.dataChanged(mCommentsData);
                                    Toast.makeText(MyCommentsActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MyCommentsActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void getAllCommentMsg() {
        BmobQuery<CommentsData> query=new BmobQuery<>();
        query.findObjects(new FindListener<CommentsData>() {
            @Override
            public void done(List<CommentsData> list, BmobException e) {
                Toast.makeText(MyCommentsActivity.this,"共有"+list.size()+"条评论",Toast.LENGTH_SHORT).show();
                mCommentsData= (ArrayList<CommentsData>) list;
                commentAdapter.dataChanged(mCommentsData);
            }
        });
    }



    private void initViews() {

        recyclerView_show_comments= (RecyclerView) findViewById(R.id.recyclerView_show_comments);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView_show_comments.setLayoutManager(layoutManager);
        commentAdapter=new CommentAdapter(mCommentsData);
        commentAdapter.dataChanged(mCommentsData);
        recyclerView_show_comments.setAdapter(commentAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  mCommentsData.clear();
        commentAdapter.notifyDataSetChanged();*/
    }
}
