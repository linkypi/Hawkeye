package com.lynch.hawkeye.model;

import java.io.Serializable;

/**
 * Created by linxueqi on 2016/12/9 0009.
 */

public class LoginDto implements Serializable{

    private boolean loginSeccess;
    private String userName;

    public LoginDto(){}
    public LoginDto(boolean flag,String user){
        this.loginSeccess = flag;
        this.userName = user;
    }

    public void setUserName(String prop){
       this.userName = prop;
    }
    public String getUserName(){
       return userName ;
    }
    public void setLoginSeccess(boolean prop){
       this.loginSeccess = prop;
    }
    public boolean getLoginSeccess(){
       return loginSeccess ;
    }

    
}
