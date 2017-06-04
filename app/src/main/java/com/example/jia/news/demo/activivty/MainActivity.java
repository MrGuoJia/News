package com.example.jia.news.demo.activivty;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import data.InternetNewsData;
import constant.JU_HE_REQUEST;
import com.example.jia.news.demo.adapter.MyFragmentPagerAdapter;
import com.example.jia.news.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter adapter;
    private ArrayList<String> title=new ArrayList<String>();//存新闻种类
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();//显示的新闻列表的多个小标题
    private List<InternetNewsData.ResultBean.DataBean> newMsg= new ArrayList<>();
    private TextView tv_myTop;
    private TextView tv_myArmy;
    private TextView tv_myJoke;
    private TextView tv_myWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBmob();//初始化Bmob,用来存储收藏，评论
        initList();//将标题的各种类型添加到list
        initViews();


    }

    private void initBmob() {
        /*
        *libbmob.so、依赖jar包(okhttp、okio、rx、gson等)及自动更新组件所需要的资源文件
        * 原本添加过的 compile 'com.google.code.gson:gson:2.8.0'
        *  compile 'com.squareup.okhttp3:okhttp:3.8.0'
        *  要去掉，不然运行不了
        * */
        Bmob.initialize(this, "319f04276a9113cca07657bf733d869e");
    }


    private void initList() {
        //生成10个标题
        title.add("头条");title.add("社会");title.add("国内");title.add("国际");title.add("娱乐");
        title.add("体育");title.add("军事");title.add("科技");title.add("财经");title.add("时尚");
        title.add("天气");title.add("笑话");

        //生成12个Fragment
        PageFragmentActivity top=new PageFragmentActivity();
        PageFragmentActivity social=new PageFragmentActivity(); PageFragmentActivity domestic=new PageFragmentActivity();
        PageFragmentActivity international=new PageFragmentActivity();  PageFragmentActivity entertainment=new PageFragmentActivity(); PageFragmentActivity sport=new PageFragmentActivity();
        PageFragmentActivity military=new PageFragmentActivity();  PageFragmentActivity science =new PageFragmentActivity(); PageFragmentActivity finance=new PageFragmentActivity();
        PageFragmentActivity fashion=new PageFragmentActivity();
        JokeFragmentActivity joke=new JokeFragmentActivity();
        WeatherFragmentActivity weather=new WeatherFragmentActivity();
        //设置聚合数据请求的新闻类型
        top.setRequest_url(JU_HE_REQUEST.TOP_TITTLE); social.setRequest_url(JU_HE_REQUEST.SHE_HUI);
        domestic.setRequest_url(JU_HE_REQUEST.GUO_NEI);international.setRequest_url(JU_HE_REQUEST.GUO_JI);
        entertainment.setRequest_url(JU_HE_REQUEST.YU_LE);sport.setRequest_url(JU_HE_REQUEST.TI_YU);
        military.setRequest_url(JU_HE_REQUEST.JUN_SHI);science.setRequest_url(JU_HE_REQUEST.KE_JI);
        finance.setRequest_url(JU_HE_REQUEST.CAI_JING);fashion.setRequest_url(JU_HE_REQUEST.SHI_SHANG);


        fragments.add(top);  fragments.add(social);  fragments.add(domestic);  fragments.add(international);  fragments.add(entertainment);
        fragments.add(sport);  fragments.add(military);  fragments.add(science);  fragments.add(finance);  fragments.add(fashion);
         fragments.add(weather); fragments.add(joke);
    }

    private void initViews() {
        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        adapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),title,fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(12);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tv_myTop= (TextView) findViewById(R.id.tv_myTop);
        tv_myArmy= (TextView) findViewById(R.id.tv_myArmy);
        tv_myWeather= (TextView) findViewById(R.id.tv_myWeather);
        tv_myJoke= (TextView) findViewById(R.id.tv_myJoker);
        tv_myTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                tv_myTop.setTextColor(Color.BLACK);
                tv_myTop.setTextSize(25);
                tv_myArmy.setTextColor(Color.WHITE);
                tv_myArmy.setTextSize(20);
                tv_myWeather.setTextColor(Color.WHITE);
                tv_myWeather.setTextSize(20);
                tv_myJoke.setTextColor(Color.WHITE);
                tv_myJoke.setTextSize(20);

            }
        });
        tv_myArmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(6);
                tv_myArmy.setTextColor(Color.BLACK);
                tv_myArmy.setTextSize(25);
                tv_myTop.setTextColor(Color.WHITE);
                tv_myTop.setTextSize(20);
                tv_myWeather.setTextColor(Color.WHITE);
                tv_myWeather.setTextSize(20);
                tv_myJoke.setTextColor(Color.WHITE);
                tv_myJoke.setTextSize(20);

            }
        });
        tv_myWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(10);
                tv_myWeather.setTextColor(Color.BLACK);
                tv_myWeather.setTextSize(25);
                tv_myTop.setTextColor(Color.WHITE);
                tv_myTop.setTextSize(20);
                tv_myJoke.setTextColor(Color.WHITE);
                tv_myJoke.setTextSize(20);
                tv_myArmy.setTextColor(Color.WHITE);
                tv_myArmy.setTextSize(20);

            }
        });


        tv_myJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(11);
                tv_myJoke.setTextColor(Color.BLACK);
                tv_myJoke.setTextSize(25);
                tv_myTop.setTextColor(Color.WHITE);
                tv_myTop.setTextSize(20);
                tv_myWeather.setTextColor(Color.WHITE);
                tv_myWeather.setTextSize(20);
                tv_myArmy.setTextColor(Color.WHITE);
                tv_myArmy.setTextSize(20);
            }
        });

    }




}
