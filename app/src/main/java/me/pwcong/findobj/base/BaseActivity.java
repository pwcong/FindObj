package me.pwcong.findobj.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import me.pwcong.findobj.conf.Constants;

/**
 * Created by pwcong on 2016/7/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, Constants.APPLICATOIN_ID);
        setContentView(setView());
        ButterKnife.bind(this);

        initVariable();

    }

    protected abstract int setView();

    protected abstract void initVariable();




}
