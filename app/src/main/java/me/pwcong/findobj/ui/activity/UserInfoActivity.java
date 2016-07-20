package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.bean.UserInfo;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.utils.CheckUtil;

/**
 * Created by pwcong on 2016/7/19.
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_summary)
    TextView tv_summary;

    @Override
    protected int setView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initVariable() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToMainActivity();
            }
        });

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

    private void setUserInfoTextView(UserInfo userInfo){

        tv_name.setText(CheckUtil.checkStringNull(userInfo.getName(),""));
        tv_sex.setText(CheckUtil.checkStringNull(userInfo.getSex(),""));

        tv_email.setText(CheckUtil.checkStringNull(userInfo.getEmail(),""));
        tv_summary.setText(CheckUtil.checkStringNull(userInfo.getSummary(),""));
    }

    public void refreshData(){

        final String userId = getIntent().getExtras().getString(Constants.USERID);

        BmobQuery<UserInfo> query=new BmobQuery<UserInfo>();
        query.addWhereEqualTo(Constants.USERID,userId);
        query.order("-createdAt");
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if(null==e&&list.size()>0){

                    UserInfo userInfo=list.get(0);

                    setUserInfoTextView(userInfo);
                }
                else {
                    Toast.makeText(UserInfoActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(getIntent().getExtras().getBoolean(Constants.MYSELF)){
            getMenuInflater().inflate(R.menu.activity_userinfo_menu,menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_update){
            redirectToUpdateUserInfoActivity();
        }

        return super.onOptionsItemSelected(item);

    }

    private void redirectToUpdateUserInfoActivity(){

        startActivity(new Intent(UserInfoActivity.this,UpdateUserInfoActivity.class));
        finish();

    }

    private void redirectToMainActivity(){
        startActivity(new Intent(UserInfoActivity.this,MainActivity.class));
        finish();
    }

}
