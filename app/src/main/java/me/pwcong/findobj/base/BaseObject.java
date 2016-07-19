package me.pwcong.findobj.base;

import cn.bmob.v3.BmobObject;

/**
 * Created by pwcong on 2016/7/18.
 */
public abstract class BaseObject extends BmobObject{

    String title;
    String describe;
    String phone;
    String userId;
    String flag;

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
