package com.example.jia.news.demo.activivty;

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

import com.example.jia.news.R;
import com.example.jia.news.demo.adapter.JokeAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constant.JU_HE_REQUEST;
import data.JokeData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeFragmentActivity extends Fragment {
    private static final int IS_TIME_TO_REFRESH = 1112;
    private List<JokeData.ResultBean.DataBean> listJoke=new ArrayList<>();//用来存储从网络获取的笑话
    private JokeAdapter myJoke;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.os.Handler mHandler=new Handler();
    private Handler refresh=new Handler();
    private static final int MSG_GET_NEWS = 1111;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();//实现加载数据,刷新
        initList();//获取从聚合数据上的拿下来的内容
    }

    private void initHandler() {
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what==MSG_GET_NEWS){
                    myJoke.setDataChange(listJoke);
                    return true;
                }
                return false;
            }
        });
        refresh=new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.arg1==IS_TIME_TO_REFRESH){
                    initList();
                    myJoke.setDataChange(listJoke);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void initList() {
        OkHttpClient mOkHttpClient = new OkHttpClient();//创建okHttpClient对象

        Request request = new Request.Builder() //创建一个Request
                .url(JU_HE_REQUEST.JOKE)
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
                JokeData myData= g.fromJson(response.body().string(),JokeData.class);
                listJoke=myData.getResult().getData();
                mHandler.sendEmptyMessage(MSG_GET_NEWS);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_joke_fragment,container,false);
        RecyclerView joke_recyclerView= (RecyclerView) view.findViewById(R.id.fragment_recyclerView_joke);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        myJoke=new JokeAdapter(listJoke);
        joke_recyclerView.setLayoutManager(layoutManager);
        joke_recyclerView.setAdapter(myJoke);

        freshActivity(view);//实现界面刷新
        return view;

    }

    private void freshActivity(View view) {
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_joke);
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
}
