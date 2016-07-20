package me.pwcong.findobj.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by pwcong on 2016/7/20.
 */
public class FindMessage extends BmobObject {

    String title;
    String reply;
    String userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "FindMessage{" +
                "title='" + title + '\'' +
                ", reply='" + reply + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
