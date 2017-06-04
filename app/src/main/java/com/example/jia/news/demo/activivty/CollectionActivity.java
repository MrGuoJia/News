package com.example.jia.news.demo.activivty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import data.CollectionData;

import com.example.jia.news.demo.listener.MyOnItemClickListener;
import com.example.jia.news.demo.listener.MyOnItemLongClickListener;
import com.example.jia.news.demo.adapter.CollectionAdapter;
import com.example.jia.news.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CollectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView_collection;
    private ArrayList<CollectionData> mCollection=new ArrayList<>();
    private CollectionAdapter mCollectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getAllCollectionMsg();//获取Bmob中存储的数据
        initViews();
        clickEvent();//实现点击访问，长按删除
    }

    private void clickEvent() {
        mCollectionAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
               Intent goToDetail=new Intent();
                goToDetail.putExtra("detail_msg",mCollection.get(position).getdetail_msg());
                goToDetail.putExtra("news_detail_tittle",mCollection.get(position).getTittle());
                goToDetail.putExtra("news_detail_imgUrl",mCollection.get(position).getImg_url());
                goToDetail.setClass(CollectionActivity.this,CollectionToInternetActivity.class);
                startActivity(goToDetail);
                finish();
            }
        });





       mCollectionAdapter.setOnItemLongClickListener(new MyOnItemLongClickListener() {//实现长按删除
           @Override
           public void OnItemLongClickListener(View view, final int position) {

               AlertDialog.Builder builder=new AlertDialog.Builder(CollectionActivity.this);
               builder.setMessage("确认要删除此评论吗？");
               builder.setTitle("删除评论");
               builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       CollectionData delete=new CollectionData();
                       delete.setObjectId( mCollection.get(position).getObjectId() );
                       delete.delete(new UpdateListener() {
                           @Override
                           public void done(BmobException e) {
                               if(e==null){
                                   mCollection.remove(position);//删除指定收藏
                                   mCollectionAdapter.dataChanged(mCollection);//重新指定列表，并通知数据刷新
                                   Toast.makeText(CollectionActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                               }else{
                                   Toast.makeText(CollectionActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
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
       }); //长按删除
    }

    private void getAllCollectionMsg() {
        BmobQuery<CollectionData> query=new BmobQuery<>();
        query.findObjects(new FindListener<CollectionData>() {
            @Override
            public void done(List<CollectionData> list, BmobException e) {
                Toast.makeText(CollectionActivity.this,"共有"+list.size()+"条收藏",Toast.LENGTH_SHORT).show();
                mCollection= (ArrayList<CollectionData>) list;
                mCollectionAdapter.dataChanged(mCollection);
            }
        });
    }


    private void initViews() {
        recyclerView_collection= (RecyclerView) findViewById(R.id.recyclerView_collection);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView_collection.setLayoutManager(layoutManager);
        mCollectionAdapter=new CollectionAdapter(mCollection);
        recyclerView_collection.setAdapter(mCollectionAdapter);

    }


}
