package com.lynch.hawkeye.model;

import java.io.Serializable;

/**
 * Created by linxueqi on 2016/11/29 0029.
 */

public class Dto implements Serializable {
    public  Dto(){}
    public  Dto(String title,String url){
        this.title = title;
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}
