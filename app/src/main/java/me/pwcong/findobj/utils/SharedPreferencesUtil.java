package me.pwcong.findobj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.pwcong.findobj.conf.Constants;

/**
 * Created by pwcong on 2016/7/15.
 */
public abstract class SharedPreferencesUtil {

    public static String get(Context context, String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Constants.PRE_FILENAME,Context.MODE_PRIVATE);
        if(null!=sharedPreferences){
            return sharedPreferences.getString(key,null);
        }

        return null;
    }

    public static void put(Context context,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Constants.PRE_FILENAME,Context.MODE_PRIVATE);

        if(null!=sharedPreferences){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,value);
        }



    }

}
