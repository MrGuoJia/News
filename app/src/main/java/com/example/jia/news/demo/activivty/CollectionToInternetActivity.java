package com.example.jia.news.demo.activivty;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.jia.news.R;
import data.CollectionData;
import data.CommentsData;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CollectionToInternetActivity extends AppCompatActivity {
    private Toolbar toolbar_visit;
    private EditText edt_speak_visit;
    private Button btn_send_visit;
    private ImageView img_collection_visit;
    private com.tencent.smtt.sdk.WebView webView_visit;
    private WebViewClient mClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    };
    private String tittle;
    private String imgUrl;
    private String msg_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_to_internet);
        initData();
        initToolBar();//自定义标题栏,并实现子菜单功能
        initViews();
        clickEvent();
        disPlayMsg();//将收藏的标题新闻用X5内核腾讯展示出来
    }

    private void disPlayMsg() {
        webView_visit= (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView_visit);
        webView_visit.setWebViewClient(mClient);
        webView_visit.loadUrl(msg_url);

    }
    private void initData() {
        Intent newsGet=getIntent();
        imgUrl= newsGet.getStringExtra("news_detail_imgUrl");
        tittle=newsGet.getStringExtra("news_detail_tittle");
        msg_url=newsGet.getStringExtra("detail_msg");
    }
    private void initToolBar() {
        toolbar_visit= (Toolbar) findViewById(R.id.toolbar_visit);
        setSupportActionBar(toolbar_visit);
    }



    private void initViews() {
        edt_speak_visit= (EditText) findViewById(R.id.edt_speak_visit);
        btn_send_visit= (Button) findViewById(R.id.btn_send_visit);
        img_collection_visit= (ImageView) findViewById(R.id.img_collection_visit);

    }
    private void clickEvent() {
        btn_send_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speak=edt_speak_visit.getText().toString();
                if(speak.length()==0){
                    Toast.makeText(CollectionToInternetActivity.this,"请输入评论",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date now=new Date();
                SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");//将时间改为字符格式
                String   date   =   sDateFormat.format(now);//获取当前时间的字符串形式
                CommentsData user1=new CommentsData(tittle,date,speak);
                user1.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(CollectionToInternetActivity.this,"发表成功，可去‘我的评论’查看 ",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CollectionToInternetActivity.this,"发表失败，请重新评论",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                edt_speak_visit.setText("");//令编辑框在写完点击发送后，清空编辑框
            }
        });

        img_collection_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CollectionToInternetActivity.this);
                builder.setMessage("确认要收藏此新闻吗？");
                builder.setTitle("我的收藏");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CollectionData mCollectionData=new CollectionData();

                        mCollectionData.setTittle(tittle);
                        mCollectionData.setImg_url(imgUrl);
                        mCollectionData.setdetail_msg(msg_url);
                        //  mCollectionDataList.add(mCollectionData);
                        mCollectionData.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Toast.makeText(CollectionToInternetActivity.this,"收藏成功，可去‘我的收藏’查看 ",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CollectionToInternetActivity.this,"收藏失败 ",Toast.LENGTH_SHORT).show();
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





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.visit_toolbar,menu);//做一个一模一样的评论收藏界面
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_collection_visit:
              startActivity(new Intent(CollectionToInternetActivity.this,CollectionActivity.class));
                finish();
                break ;
            case R.id.item_speak_visit:
                //将写的评论全部显示到新的界面列表中去
              /*  Intent send_comments=new Intent();*/
                 finish();
                startActivity(new Intent(CollectionToInternetActivity.this,MyCommentsActivity.class));
                break ;
            case R.id.item_return_visit:
                //返回主界面
                startActivity(new Intent(CollectionToInternetActivity.this,MainActivity.class));
                finish();
                break ;
            default:
                break;
        }
        return true;
    }
}
