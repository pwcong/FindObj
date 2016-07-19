package me.pwcong.findobj.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bmob.v3.Bmob;
import me.pwcong.findobj.conf.Constants;

/**
 * Created by pwcong on 2016/7/16.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bmob.initialize(getActivity(), Constants.APPLICATOIN_ID);

        View view = inflater.inflate(setView(), container, false);

        initVariable(view);


        return view;
    }


    protected abstract int setView();
    protected abstract void initVariable(View view);

    public abstract void refreshData();

}
