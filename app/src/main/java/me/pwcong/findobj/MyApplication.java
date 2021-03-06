package me.pwcong.findobj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import cn.bmob.v3.BmobUser;
import me.pwcong.findobj.bean.User;
import me.pwcong.findobj.conf.Constants;

/**
 * Created by pwcong on 2016/7/15.
 */
public class MyApplication extends Application{

    static Context context;
    static User user;
    static String orderType= Constants.ORDER_BY_TIME_DESC;

    static int fragmentIdex;


    static boolean login;
    static Set<Activity> activityList=new HashSet<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initVariable();

    }


    private void initVariable(){

        User _user= BmobUser.getCurrentUser(User.class);


        if(null!=_user){
            user=_user;
            login=true;
        }
        else {
            user=null;
            login=false;
        }

    }


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        MyApplication.login = login;
    }

    public static String getOrderType() {
        return orderType;
    }

    public static void setOrderType(String orderType) {
        MyApplication.orderType = orderType;
    }


    public static int getFragmentIdex() {
        return fragmentIdex;
    }

    public static void setFragmentIdex(int fragmentIdex) {
        MyApplication.fragmentIdex = fragmentIdex;
    }

    public static void addActivity(Activity activity){

        if(!activityList.contains(activity)){
            activityList.add(activity);
        }

    }

    public static void finishAllActivities(){
        for (Activity activity:activityList){
            if(null!=activity){
                activity.finish();
                activityList.remove(activity);
            }
        }
    }

}
