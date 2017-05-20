package com.example.jia.news.Activity;

import java.io.Serializable;

/**
 * Created by jia on 2017/5/20.
 */

public class CollectionData implements Serializable {//实现Serializable，方便intent传递复杂的数据
    String tittle;
    String img_url;
    public CollectionData(){}
    public CollectionData(String tittle,String img_url){
        this.tittle=tittle;
        this.img_url=img_url;
    }
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
