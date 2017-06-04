package data;

/**
 * Created by jia on 2017/6/3.
 */

public class TemperaturePoint {//记录温度点的坐标
    private   int x;
    private  int lowy;
    private  int highy;
    private String highTemperature;
    private String lowTemperature;
    private String week;

    public String getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getLowy() {
        return lowy;
    }

    public void setLowy(int lowy) {
        this.lowy = lowy;
    }

    public int getHighy() {
        return highy;
    }

    public void setHighy(int highy) {
        this.highy = highy;
    }

    public TemperaturePoint(int x, int lowy, int highy,String highTemperature,String lowTemperature,String week){
        this.x=x;
        this.lowy=lowy;
        this.highy=highy;
        this.highTemperature=highTemperature;
        this.lowTemperature=lowTemperature;
        this.week=week;
    }
}
