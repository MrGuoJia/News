package com.example.jia.news.Activity;

import java.io.Serializable;

/**
 * Created by jia on 2017/5/20.
 */

public class CommentsData implements Serializable {
    private  String tittle;
    private String time;
    private String commentsResult;
    public CommentsData(){}
    public CommentsData(String tittle,String time, String commentsResult){
        this.tittle=tittle;
        this.time=time;
        this.commentsResult=commentsResult;
    }
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentsResult() {
        return commentsResult;
    }

    public void setCommentsResult(String commentsResult) {
        this.commentsResult = commentsResult;
    }
}
