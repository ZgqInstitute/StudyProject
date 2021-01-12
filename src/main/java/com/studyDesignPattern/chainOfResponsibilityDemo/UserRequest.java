package com.studyDesignPattern.chainOfResponsibilityDemo;

public class UserRequest {
    private boolean isLogin;//是否登录 (节点1)
    private boolean isAuthority;//是否有权限 (节点2)
    private boolean isBusy;//网络是否繁忙 (节点3)

    public UserRequest(boolean isLogin, boolean isAuthority, boolean isBusy) {
        this.isLogin = isLogin;
        this.isAuthority = isAuthority;
        this.isBusy = isBusy;
    }

    public boolean isLogin(){
        return isLogin;
    }

    public boolean isAuthority(){
        return isAuthority;
    }

    public boolean isBusy(){
        return isBusy;
    }


}
