package me.pwcong.findobj.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by pwcong on 2016/7/20.
 */
public class FindMessage extends BmobObject {

    String title;
    String reply;
    String userId;
    String sendId;

    @Override
    public String toString() {
        return "FindMessage{" +
                "title='" + title + '\'' +
                ", reply='" + reply + '\'' +
                ", userId='" + userId + '\'' +
                ", sendId='" + sendId + '\'' +
                '}';
    }

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

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
