package me.pwcong.findobj.utils;

import android.util.Log;

/**
 * Created by pwcong on 2016/7/18.
 */
public abstract class CheckUtil {

    public static char[] illegalCharacters="~!@#$%^&*()_+`-=[]{}|:\"<>?".toCharArray();

    public static boolean checkStringLegal(String s){

        for (char c:illegalCharacters){
            if (s.indexOf(c)>0){
                return false;
            }
        }
        return true;
    }

    public static boolean checkStringLength(String s,int length){
        if (s.length()<length)
            return false;
        else
            return true;
    }

    public static boolean checkMail(String s){
        if(s.indexOf('@')<0)
            return false;
        else
            return true;
    }

    public static String checkStringNull(String s,String defaultString){
        if(s==null)
            return defaultString;
        else
            return s;
    }


    public static boolean checkStringNotNull(String s){
        if(s==null)
            return false;
        else
            return true;
    }





}
