package com.example.jia.news.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.JI_SU_WeatherData;
import data.TemperatureData;
import data.TemperaturePoint;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by jia on 2017/6/1.
 */
public class WeatherView extends View {
    Paint paint = new Paint();
    Paint drawText=new Paint();
    private int radius = 10;//设置小圆点半径
    private  List<TemperatureData> weatherDataList=new ArrayList<>();
    private List<TemperaturePoint> pointList=new ArrayList<>();
    public final static String JI_SU_WEATHER="http://api.jisuapi.com/weather/query?appkey=d9b1f99636c313ae&city=广州";//极速数据的获取请求
    private List<JI_SU_WeatherData.ResultBean.DailyBean> weatherList=new ArrayList<JI_SU_WeatherData.ResultBean.DailyBean>();//用来获取极速数据
    private String today="a";
    public WeatherView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        initListFromJuHe();



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
                    parsWithJSON(today);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoints(canvas);//画温度点
        drawLint(canvas);//画温度曲线，和写出度数
        drawWeek(canvas);//将当天与星期几对应

    }

    private void drawWeek(Canvas canvas) {
        Paint drawTextPen=new Paint();
        drawTextPen.setTextAlign(Paint.Align.CENTER);
        drawTextPen.setTextSize(35);
        drawTextPen.setStyle(Paint.Style.FILL_AND_STROKE);
        drawTextPen.setAntiAlias(true);//抗锯齿
        drawTextPen.setDither(true);

        Paint drawLinePen=new Paint();
        drawLinePen.setColor(Color.WHITE);//画竖直线用
        drawLinePen.setStrokeWidth(1); //设置线宽
        int stopDrawLine=pointList.size()-1;//用来判断最后一个温度点不需画直线

        for(int i=0;i<pointList.size();i++){
            int x=70+(i*150);
            String week=pointList.get(i).getWeek();
            canvas.drawText(week,x,580,drawTextPen);
            if(i<stopDrawLine){
                canvas.drawLine(x+80,10,x+80,590,drawLinePen);
            }
        }
    }

    private void drawLint(Canvas canvas) {
        Path lowPath = new Path();
        Path highPath = new Path();
        for(int i=0;i<pointList.size();i++){
            TemperaturePoint p=pointList.get(i);
            if(i==0){
                lowPath.moveTo(p.getX(),p.getLowy());
                highPath.moveTo(p.getX(),p.getHighy());//第一个坐标连线要以moveTo
            }else {
                lowPath.lineTo(p.getX(),p.getLowy());
                highPath.lineTo(p.getX(),p.getHighy());
            }
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        canvas.drawPath(lowPath,paint);//将最低温度逐一连起来
        paint.setColor(Color.RED);
        canvas.drawPath(highPath, paint);//将最高温度逐一连起来
    }

    private void drawPoints(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满
        drawText.setTextSize(35);
        drawText.setStyle(Paint.Style.FILL_AND_STROKE);
        drawText.setAntiAlias(true);//抗锯齿
        drawText.setDither(true);
        drawText.setTextAlign(Paint.Align.CENTER);
        for(int i=0;i<pointList.size();i++){
            TemperaturePoint point = pointList.get(i);
            paint.setColor(Color.GREEN);
            canvas.drawCircle(point.getX(),point.getLowy(),radius,paint);//画出来的是最低温度的实心圆点
            //这是为了写出摄氏度而设置的坐标，距离温度点5
            int lowTemperatureX=point.getX();int lowTemperatureY=point.getLowy()-20;int highTemperatureY=point.getHighy()-20;
            canvas.drawText(point.getLowTemperature()+"℃",lowTemperatureX,lowTemperatureY,drawText);//写出每个点的度数
            paint.setColor(Color.RED);
            canvas.drawCircle(point.getX(),point.getHighy(),radius,paint);
            canvas.drawText(point.getHighTemperature()+"℃",lowTemperatureX,highTemperatureY,drawText);


        }
    }

    private void calculatePoint() {
        for(int i=0;i<weatherDataList.size();i++){
            int x=70+(i*150);//初始横坐标为50，间隔150
            int lowy= (40 - Integer.parseInt((weatherDataList.get(i).getTemplow()))) * 20;//设置当天最低的纵坐标高度,40减，算温度不超过40
            int highy= (40 - Integer.parseInt((weatherDataList.get(i).getTemphigh()))) * 20;
            String highTemperature=weatherDataList.get(i).getTemphigh();
            String lowTemperature=weatherDataList.get(i).getTemplow();
            String week=weatherDataList.get(i).getWeek();
            TemperaturePoint point=new TemperaturePoint(x,lowy,highy,highTemperature,lowTemperature,week);
            pointList.add(point);
        }
    }

    private void parsWithJSON(String today) {
        Gson gson=new Gson();
        JI_SU_WeatherData data=gson.fromJson(today,JI_SU_WeatherData.class);
        weatherList= data.getResult().getDaily();
        for(int a=0;a<weatherList.size();a++){
            TemperatureData myData=new TemperatureData();
            myData.setTemphigh(weatherList.get(a).getDay().getTemphigh());
            myData.setTemplow(weatherList.get(a).getNight().getTemplow());
            myData.setWeek(weatherList.get(a).getWeek());
            weatherDataList.add(myData);

        }
        calculatePoint();
    }
}
