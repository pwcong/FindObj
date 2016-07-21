package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.bean.UserInfo;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.utils.CheckUtil;

/**
 * Created by pwcong on 2016/7/19.
 */
public class UpdateUserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.rg_sex)
    RadioGroup radioGroup;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_summary)
    EditText et_summary;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;


    final UserInfo userInfo=new UserInfo();

    @Override
    protected int setView() {
        return R.layout.activity_updateuserinfo;
    }

    @Override
    protected void initVariable() {
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToUserInfoActivity();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_updateuserinfo_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_done){

            updateUserInfo();

        }
        return super.onOptionsItemSelected(item);
    }


    public void refreshData(){

        BmobQuery<UserInfo> query=new BmobQuery<UserInfo>();
        query.addWhereEqualTo(Constants.USERID,MyApplication.getUser().getObjectId());
        query.order(Constants.ORDER_BY_TIME_DESC);
        query.setLimit(Constants.DATA_LIMIT);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if(null==e&&list.size()>0){
                    UserInfo _userInfo = list.get(0);

                    userInfo.setObjectId(_userInfo.getObjectId());

                    et_name.setText(CheckUtil.checkStringNull(_userInfo.getName(),""));
                    et_email.setText(CheckUtil.checkStringNull(_userInfo.getEmail(),""));
                    et_summary.setText(CheckUtil.checkStringNull(_userInfo.getSummary(),""));

                    if(CheckUtil.checkStringNotNull(_userInfo.getSex())){
                        if(_userInfo.getSex().equals("男")){
                            radioGroup.check(R.id.rb_male);
                        }
                        if(_userInfo.getSex().equals("女")){
                            radioGroup.check(R.id.rb_female);
                        }

                    }else {

                    }

                }
            }
        });
    }

    private void updateUserInfo(){

        userInfo.setName(CheckUtil.checkStringNull(et_name.getText().toString(),""));
        userInfo.setSummary(CheckUtil.checkStringNull(et_summary.getText().toString(),""));
        userInfo.setEmail(CheckUtil.checkStringNull(et_email.getText().toString(),""));
        if(radioGroup.getCheckedRadioButtonId()==R.id.rb_male){
            userInfo.setSex("男");
        }
        if(radioGroup.getCheckedRadioButtonId()==R.id.rb_female){
            userInfo.setSex("女");
        }

        userInfo.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(null==e){
                    redirectToUserInfoActivity();
                    Toast.makeText(UpdateUserInfoActivity.this,"用户信息已成功更新",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UpdateUserInfoActivity.this,"用户信息更新失败",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void redirectToUserInfoActivity(){

        Bundle bundle=new Bundle();
        bundle.putBoolean(Constants.MYSELF,true);
        bundle.putString(Constants.USERID, MyApplication.getUser().getObjectId());

        Intent intent=new Intent(UpdateUserInfoActivity.this,UserInfoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
