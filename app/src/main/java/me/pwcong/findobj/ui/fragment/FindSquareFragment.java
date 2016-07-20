package me.pwcong.findobj.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.adapter.BaseObjectItemAdapter;
import me.pwcong.findobj.base.BaseFragment;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.bean.Lost;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.ui.activity.MainActivity;

/**
 * Created by pwcong on 2016/7/17.
 */
public class FindSquareFragment extends BaseFragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    private BaseObjectItemListener mListener;
    private List<Lost> lostList;

    public String orderType=Constants.ORDER_BY_TIME_DESC;

    public FindSquareFragment(){

    }


    public static FindSquareFragment newInstance(){
        return new FindSquareFragment();
    }


    @Override
    protected int setView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initVariable(View view) {

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.layout_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        swipeRefreshLayout.setColorSchemeColors(Constants.TEAL_LIGHT,Constants.BLUE_LIGHT,Constants.RED_NORMAL,Constants.ORANGE_DEEP,Constants.GREEN_LIGHT);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        refreshData();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener=(BaseObjectItemListener)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    @Override
    public void refreshData(){

        lostList=new ArrayList<Lost>();
        BmobQuery<Lost> query=new BmobQuery<Lost>();
        query.order(MyApplication.getOrderType());
        query.setLimit(Constants.DATA_LIMIT);
        query.findObjects(new FindListener<Lost>() {
            @Override
            public void done(List<Lost> list, BmobException e) {
                if(null==e){
                    for (Lost lost:list){
                        lost.setFlag(Constants.FLAG_FINDSQUARE);
                        lostList.add(lost);
                    }

                }else {
                    Toast.makeText(getActivity(),"查询失败",Toast.LENGTH_SHORT).show();
                }

                recyclerView.setAdapter(new BaseObjectItemAdapter(lostList,mListener));

            }
        });
    }

    public interface BaseObjectItemListener{
        void onBaseObjectItemInteraction(BaseObject baseObject);
    }



}
