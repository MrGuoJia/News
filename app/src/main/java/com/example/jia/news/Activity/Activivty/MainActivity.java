package com.example.jia.news.Activity.Activivty;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jia.news.Activity.InternetNewsData;
import com.example.jia.news.Activity.JU_HE_REQUEST;
import com.example.jia.news.Activity.adapter.MyFragmentPagerAdapter;
import com.example.jia.news.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter adapter;
    private ArrayList<String> title=new ArrayList<String>();//存新闻种类
    private ArrayList<PageFragmentActivity> fragments=new ArrayList<PageFragmentActivity>();//显示的新闻列表的多个小标题
    private List<InternetNewsData.ResultBean.DataBean> newMsg= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initList();//将标题的各种类型添加到list
        initViews();


    }


    private void initList() {
        //生成10个标题
        title.add("头条");title.add("社会");title.add("国内");title.add("国际");title.add("娱乐");
        title.add("体育");title.add("军事");title.add("科技");title.add("财经");title.add("时尚");

        //生成10个Fragment
        PageFragmentActivity top=new PageFragmentActivity();
        PageFragmentActivity social=new PageFragmentActivity(); PageFragmentActivity domestic=new PageFragmentActivity();
        PageFragmentActivity international=new PageFragmentActivity();  PageFragmentActivity entertainment=new PageFragmentActivity(); PageFragmentActivity sport=new PageFragmentActivity();
        PageFragmentActivity military=new PageFragmentActivity();  PageFragmentActivity science =new PageFragmentActivity(); PageFragmentActivity finance=new PageFragmentActivity();
        PageFragmentActivity fashion=new PageFragmentActivity();
        //设置聚合数据请求的新闻类型
        top.setRequest_url(JU_HE_REQUEST.TOP_TITTLE); social.setRequest_url(JU_HE_REQUEST.SHE_HUI);
        domestic.setRequest_url(JU_HE_REQUEST.GUO_NEI);international.setRequest_url(JU_HE_REQUEST.GUO_JI);
        entertainment.setRequest_url(JU_HE_REQUEST.YU_LE);sport.setRequest_url(JU_HE_REQUEST.TI_YU);
        military.setRequest_url(JU_HE_REQUEST.JUN_SHI);science.setRequest_url(JU_HE_REQUEST.KE_JI);
        finance.setRequest_url(JU_HE_REQUEST.CAI_JING);fashion.setRequest_url(JU_HE_REQUEST.SHI_SHANG);


        fragments.add(top);  fragments.add(social);  fragments.add(domestic);  fragments.add(international);  fragments.add(entertainment);
        fragments.add(sport);  fragments.add(military);  fragments.add(science);  fragments.add(finance);  fragments.add(fashion);
    }

    private void initViews() {
        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        adapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),title,fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }




}
