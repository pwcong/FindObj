package me.pwcong.findobj.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.adapter.FindMessageItemAdapter;
import me.pwcong.findobj.base.BaseFragment;
import me.pwcong.findobj.bean.FindMessage;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.listener.FindMessageItemListener;

/**
 * Created by pwcong on 2016/7/19.
 */
public class FindMessageFragment extends BaseFragment{

    List<FindMessage> findMessageList;

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;

    FindMessageItemListener mListener;

    public FindMessageFragment(){}

    public static FindMessageFragment newInstance(){
        return new FindMessageFragment();
    }

    @Override
    protected int setView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initVariable(View view) {

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        refreshLayout.setColorSchemeColors(Constants.TEAL_LIGHT,Constants.BLUE_LIGHT,Constants.RED_NORMAL,Constants.ORANGE_DEEP,Constants.GREEN_LIGHT);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });

        refreshData();

    }

    @Override
    public void refreshData() {

        findMessageList=new ArrayList<FindMessage>();
        BmobQuery<FindMessage> query=new BmobQuery<FindMessage>();
        query.addWhereEqualTo(Constants.USERID, MyApplication.getUser().getObjectId());
        query.order(MyApplication.getOrderType());
        query.setLimit(Constants.DATA_LIMIT);
        query.findObjects(new FindListener<FindMessage>() {
            @Override
            public void done(List<FindMessage> list, BmobException e) {
                if(null==e){
                    for (FindMessage findMessage:list){
                        findMessageList.add(findMessage);
                    }
                }
                else {
                    Toast.makeText(getActivity(),"寻物通知获取失败",Toast.LENGTH_SHORT).show();
                }

                recyclerView.setAdapter(new FindMessageItemAdapter(findMessageList,mListener));

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener= (FindMessageItemListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }


}
