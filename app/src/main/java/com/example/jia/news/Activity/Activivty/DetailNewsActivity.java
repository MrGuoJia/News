package com.example.jia.news.Activity.Activivty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jia.news.Activity.CollectionData;
import com.example.jia.news.Activity.CommentsData;
import com.example.jia.news.R;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DetailNewsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edt_speak;
    private Button btn_send;
    private ImageView img_collection;
    private com.tencent.smtt.sdk.WebView webView;
    private WebViewClient mClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    };
    private String comments;//用来收集对当前新闻的评论
    private String tittle;//用来实现对该标题的收藏，评论
    private ArrayList<CommentsData> mCommentsData=new ArrayList<>();//这个是实现存储评论的列表，用于传递到评论界面
    private ArrayList<CollectionData>mCollectionDataList=new ArrayList<>();//这个是实现存储收藏的列表,用于传递到收藏界面
    private Intent collectionIntent=new Intent();//用于传递收藏的值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        Toast.makeText(DetailNewsActivity.this,"等待加载...",Toast.LENGTH_SHORT).show();
        initToolBar();//自定义标题栏,并实现滑动子菜单
        initViews();
        getNewsMessage();

    }

    private void initViews() {
        edt_speak= (EditText) findViewById(R.id.edt_speak);
        btn_send= (Button) findViewById(R.id.btn_send);
        img_collection= (ImageView) findViewById(R.id.img_collection);

        btn_send.setOnClickListener(new View.OnClickListener() { //发表评论
            @Override
            public void onClick(View v) {
                String speak=edt_speak.getText().toString();
                if(speak.length()==0){
                    Toast.makeText(DetailNewsActivity.this,"请输入评论",Toast.LENGTH_SHORT).show();
                    return;
                }
                comments=speak;//评论的内容
                Date now=new Date();
                SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");//将时间改为字符格式
                String   date   =   sDateFormat.format(now);//获取当前时间的字符串形式
                CommentsData user1=new CommentsData(tittle,date,speak);
                mCommentsData.add(user1);//将发送的统统存到arraylist中,在菜单栏那点击查看评论时，将数据传过去
                Toast.makeText(DetailNewsActivity.this,"发表成功，可去‘我的评论’查看 ",Toast.LENGTH_SHORT).show();
                edt_speak.setText("");//令编辑框在写完点击发送后，清空编辑框
            }
        });


        img_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(DetailNewsActivity.this);
                builder.setMessage("确认要收藏此新闻吗？");
                builder.setTitle("我的收藏");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CollectionData mCollectionData=new CollectionData();
                        collectionIntent=getIntent();
                        mCollectionData.setTittle(collectionIntent.getStringExtra("tittle"));
                        mCollectionData.setImg_url(collectionIntent.getStringExtra("imgUrl"));
                        mCollectionDataList.add(mCollectionData);
                        Toast.makeText(DetailNewsActivity.this,"收藏成功，可去‘我的收藏’查看 ",Toast.LENGTH_SHORT).show();
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

    private void initToolBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getNewsMessage() {//将内容用X5内核显示出来
        Intent getUrl=getIntent();
        String url=getUrl.getStringExtra("url");
        tittle=getUrl.getStringExtra("tittle");
        webView = (com.tencent.smtt.sdk.WebView)findViewById(R.id.webView);
        webView.setWebViewClient(mClient);
        webView.loadUrl(url);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           case R.id.item_collection:
               //将收藏的此网页，显示到新的RecyclerView中，做成属于自己的收藏,没做数据库...一关闭再开数据将不存在
               collectionIntent.setClass(DetailNewsActivity.this,CollectionActivity.class);
               collectionIntent.putExtra("mCollectionDataList",mCollectionDataList);
               startActivity(collectionIntent);


            break ;
            case R.id.item_speak:
                //将写的评论全部显示到新的界面列表中去,当然没做数据库...一关闭再开数据将不存在
                Intent send_comments=new Intent();
                send_comments.putExtra("mCommentsData",mCommentsData);
                send_comments.setClass(DetailNewsActivity.this,MyCommentsActivity.class);
                startActivity(send_comments);
               // finish();
                break ;
            case R.id.item_return:
                //返回主界面
                startActivity(new Intent(DetailNewsActivity.this,MainActivity.class));
                break ;
             default:
                break;
        }
        return true;
    }
}
