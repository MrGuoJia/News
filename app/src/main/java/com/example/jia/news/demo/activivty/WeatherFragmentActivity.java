package com.example.jia.news.demo.activivty;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jia.news.R;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.JI_SU_WeatherData;
import data.TemperatureData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFragmentActivity extends Fragment {
    public final static String JI_SU_WEATHER="http://api.jisuapi.com/weather/query?appkey=d9b1f99636c313ae&city=广州";//极速数据的获取请求
    private List<JI_SU_WeatherData.ResultBean.DailyBean> weatherList=new ArrayList<JI_SU_WeatherData.ResultBean.DailyBean>();//用来获取极速数据
    private List<JI_SU_WeatherData.ResultBean.IndexBean> addviceList=new ArrayList<>();
    private List<TemperatureData> weatherDataList=new ArrayList<>();//用来存取得的数据
    private Handler mHandler=new Handler();
    private static final int MSG_GET_NEWS = 1009;

    private TextView  tv_date;
    private TextView  tv_week;
    private TextView  tv_lowTemperature;
    private TextView  tv_highTemperature;
    private TextView  tv_windPower;
    private TextView  tv_windDirection;
    private TextView  tv_advice;
    private String today="a";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListFromJuHe();
        initHandler();//实现加载数据,刷新

    }

    private void initHandler() {
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what==MSG_GET_NEWS){
                    Gson gson=new Gson();
                    JI_SU_WeatherData data=gson.fromJson(today,JI_SU_WeatherData.class);
                    weatherList= data.getResult().getDaily();
                    addviceList=data.getResult().getIndex();
                    for(int a=0;a<weatherList.size();a++){
                        TemperatureData myData=new TemperatureData();
                        myData.setDate(weatherList.get(a).getDate());
                        myData.setWeek(weatherList.get(a).getWeek());
                        myData.setWeather(weatherList.get(a).getDay().getWeather());
                        myData.setTemphigh(weatherList.get(a).getDay().getTemphigh());
                        myData.setTemplow(weatherList.get(a).getNight().getTemplow());
                        myData.setWinddirect(weatherList.get(a).getDay().getWinddirect());
                        myData.setWindpower(weatherList.get(a).getDay().getWindpower());

                        weatherDataList.add(myData);
                    }
                    TemperatureData nowDay=weatherDataList.get(0);
                    String date=nowDay.getDate() ;
                    String week=nowDay.getWeek() ;               String weather=nowDay.getWeather() ;
                    String tempHigh=nowDay.getTemphigh() ;               String tempLow=nowDay.getTemplow() ;
                    String winddirect=nowDay.getWinddirect() ;               String windpower=nowDay.getWindpower() ;
                    String advice=addviceList.get(0).getDetail();

                    tv_date.setText(date);tv_week.setText(week);
                    tv_lowTemperature.setText(tempLow);tv_highTemperature.setText(tempHigh);
                    tv_windPower.setText(windpower); tv_windDirection.setText(winddirect);
                    tv_advice.setText(advice);

                    return true;

                }
                return false;
            }
        });

    }




    private void initListFromJuHe() {

      new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  OkHttpClient client=new OkHttpClient();
                  Request request=new Request.Builder().url(JI_SU_WEATHER).build();
                  Response response=client.newCall(request).execute();
                  String data=response.body().string();
                  today=data;
                  mHandler.sendEmptyMessage(MSG_GET_NEWS);
              } catch (IOException e) {
                  e.printStackTrace();
              }

          }
      }).start();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_weather,container,false);
      ;
        tv_date= (TextView) view.findViewById(R.id.tv_date);
        tv_week =(TextView) view.findViewById(R.id.tv_week);
        tv_lowTemperature =(TextView) view.findViewById(R.id.tv_lowTemperature);
        tv_highTemperature =(TextView) view.findViewById(R.id.tv_highTemperature);
        tv_windPower =(TextView) view.findViewById(R.id.tv_windPower);
        tv_windDirection =(TextView) view.findViewById(R.id.tv_windDirection);
        tv_advice =(TextView) view.findViewById(R.id.tv_advice);
        return view;
    }
}
