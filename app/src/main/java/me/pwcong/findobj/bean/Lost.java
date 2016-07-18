package me.pwcong.findobj.bean;

import cn.bmob.v3.BmobObject;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.conf.Constants;

/**
 * Created by pwcong on 2016/7/17.
 */
public class Lost extends BaseObject {
    @Override
    public String getFlag() {
        return Constants.FLAG_LOST;
    }
}
