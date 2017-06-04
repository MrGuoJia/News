package com.example.jia.news.demo.activivty;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import data.InternetNewsData;
import constant.JU_HE_REQUEST;
import com.example.jia.news.demo.listener.MyOnItemClickListener;
import com.example.jia.news.demo.adapter.MessageAdapter;
import com.example.jia.news.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PageFragmentActivity extends Fragment {
    private static final int IS_TIME_TO_REFRESH = 1314;
    private List<InternetNewsData.ResultBean.DataBean> newMsg= new ArrayList<>();
    private MessageAdapter messageAdapter;

    private static final int MSG_GET_NEWS = 1001;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String request_url= JU_HE_REQUEST.TOP_TITTLE;//默认类型是TOP,设get and set，方便去主界面设置请求类型
    private android.os.Handler mHandler=new Handler();
    private Handler refresh=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initHandler();
        getInternetData();//从网络获取数据，并存到newMsg

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        final View view=inflater.inflate(R.layout.activity_page_fragment,container,false);
        RecyclerView fragment_recyclerView= (RecyclerView) view.findViewById(R.id.fragment_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        messageAdapter=new MessageAdapter(newMsg);
        fragment_recyclerView.setAdapter(messageAdapter);
        fragment_recyclerView.setLayoutManager(layoutManager);

        OnclickItem();//点击实现在内置浏览器浏览该链接新闻
        freshActivity(view);//实现界面刷新
        return view;

    }

    private void freshActivity(View view) {
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message message=new Message();
                            message.arg1=IS_TIME_TO_REFRESH;
                            refresh.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }

                    }

                }).start();
            }
        });


    }

    private void OnclickItem() {

        messageAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(final View view, int position) {
                String url= newMsg.get(position).getUrl();//该标题的链接路径
                Intent newsIntent=new Intent();
                newsIntent.putExtra("tittle",newMsg.get(position).getTitle());//将标题传过去，以便收藏
                newsIntent.putExtra("imgUrl",newMsg.get(position).getThumbnail_pic_s());//将图片传过去，以便收藏
                newsIntent.putExtra("url",url);//将该标题详细地址信息传过去
                newsIntent.setClass(view.getContext(), DetailNewsActivity.class);
                //点击后传递到新界面，进行评论，收藏，分享
                startActivity(newsIntent);

            }
        });
    }


    private void initHandler() {
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what==MSG_GET_NEWS){
                     messageAdapter.changDate(newMsg);
                    return true;
                }
                return false;
            }
        });

        refresh=new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.arg1==IS_TIME_TO_REFRESH){
                    getInternetData();
                    messageAdapter.changDate(newMsg);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
    private void getInternetData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();//创建okHttpClient对象

         Request request = new Request.Builder() //创建一个Request
                .url(getRequest_url())
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback()//请求加入调度
        {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("AAA", "GET DATA FAILED");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson g=new Gson();
                InternetNewsData myData= g.fromJson(response.body().string(),InternetNewsData.class);
                newMsg=myData.getResult().getData();
                mHandler.sendEmptyMessage(MSG_GET_NEWS);
            }
        });
    }
    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }


}
