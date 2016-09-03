package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.drakeet.materialdialog.MaterialDialog;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.base.BaseFragment;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.bean.FindMessage;
import me.pwcong.findobj.bean.Found;
import me.pwcong.findobj.bean.Lost;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.listener.BaseObjectItemListener;
import me.pwcong.findobj.listener.FindMessageItemListener;
import me.pwcong.findobj.ui.dialog.SimpleItemListDialog;
import me.pwcong.findobj.ui.dialog.SimpleMessageSendDialog;
import me.pwcong.findobj.ui.fragment.FindMessageFragment;
import me.pwcong.findobj.ui.fragment.FindSquareFragment;
import me.pwcong.findobj.ui.fragment.MyFindFragment;
import me.pwcong.findobj.utils.CheckUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,BaseObjectItemListener,FindMessageItemListener {

    List<BaseFragment> fragments=new ArrayList<BaseFragment>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    RelativeLayout rl_userInfo;


    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {

        toolbar.setTitle(R.string.title_find_square);
        setSupportActionBar(toolbar);

        fab.setVisibility(FloatingActionButton.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToAddItemActivity();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_find_square);

        initNavigationViewHeader(navigationView);

        initFragments();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragments.get(0)).commit();
        MyApplication.setFragmentIdex(0);


    }

    private void initFragments(){
        fragments.add(FindSquareFragment.newInstance());
        fragments.add(MyFindFragment.newInstance());
        fragments.add(FindMessageFragment.newInstance());
    }

    private void initNavigationViewHeader(NavigationView navigationView){
        rl_userInfo = (RelativeLayout) navigationView.getHeaderView(0);
        TextView textView= (TextView) rl_userInfo.findViewById(R.id.tv_username);
        textView.setText(MyApplication.getUser().getUsername());

        rl_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putBoolean(Constants.MYSELF,true);
                bundle.putString(Constants.USERID, MyApplication.getUser().getObjectId());
                redirectToUserInfoActivity(bundle);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_order_time_asc) {

            MyApplication.setOrderType(Constants.ORDER_BY_TIME_ASC);
            fragments.get(MyApplication.getFragmentIdex()).refreshData();

            return true;
        }else if(id==R.id.action_order_time_desc){

            MyApplication.setOrderType(Constants.ORDER_BY_TIME_DESC);
            fragments.get(MyApplication.getFragmentIdex()).refreshData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_find_square) {
            toolbar.setTitle(R.string.title_find_square);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(0)).commit();
            MyApplication.setFragmentIdex(0);
            fab.setVisibility(FloatingActionButton.VISIBLE);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_find_mine) {
            toolbar.setTitle(R.string.title_find_mine);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(1)).commit();
            MyApplication.setFragmentIdex(1);
            fab.setVisibility(FloatingActionButton.VISIBLE);
            drawer.closeDrawer(GravityCompat.START);
        } else if(id == R.id.nav_find_message){
            toolbar.setTitle(R.string.title_find_message);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragments.get(2)).commit();
            MyApplication.setFragmentIdex(2);
            fab.setVisibility(FloatingActionButton.GONE);
            drawer.closeDrawer(GravityCompat.START);
        } else if(id == R.id.nav_info){
            redirectToAboutActivity();
        } else if(id == R.id.nav_logout){

            final MaterialDialog dialog=new MaterialDialog(MainActivity.this);
            dialog.setMessage("是否注销用户？");
            dialog.setPositiveButton("是", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    redirectToLoginActivity();
                }
            });
            dialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();


        }

        return true;
    }



    @Override
    public void onBaseObjectItemInteraction(final BaseObject baseObject) {
        if(baseObject.getFlag().equals(Constants.FLAG_FINDSQUARE)){

            final SimpleItemListDialog dialog=new SimpleItemListDialog(MainActivity.this, getFindSquareDialogItemList());

            dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0:
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.USERID,baseObject.getUserId());
                            bundle.putBoolean(Constants.MYSELF,false);
                            redirectToUserInfoActivity(bundle);
                            break;
                        case 1:

                            final SimpleMessageSendDialog messageSendDialog=new SimpleMessageSendDialog(MainActivity.this,4,7);

                            messageSendDialog.setTitle("通知");
                            messageSendDialog.setPositiveButton("发送", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    sendFindMessage(baseObject, CheckUtil.checkStringNull(messageSendDialog.getEditText().getText().toString(),""));

                                    messageSendDialog.dismiss();
                                }
                            });
                            messageSendDialog.setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    messageSendDialog.dismiss();
                                }
                            });
                            messageSendDialog.show();
                            dialog.dismiss();

                            break;
                        default:break;
                    }
                }
            });

            dialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }


        if (baseObject.getFlag().equals(Constants.FLAG_MYFIND)){


            final MaterialDialog dialog=new MaterialDialog(MainActivity.this);
            dialog.setMessage("确认删除这条寻物启事？");
            dialog.setPositiveButton("是", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeLostItem(baseObject);
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }



    @Override
    public void onFindMessageItemInteraction(final FindMessage findMessage) {

        final SimpleItemListDialog simpleItemListDialog=new SimpleItemListDialog(MainActivity.this,getFindMessageDialogItemList());
        simpleItemListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        Bundle bundle=new Bundle();
                        bundle.putBoolean(Constants.MYSELF,false);
                        bundle.putString(Constants.USERID,findMessage.getSendId());
                        redirectToUserInfoActivity(bundle);

                        break;

                    case 1:

                        final MaterialDialog deleteDialog=new MaterialDialog(MainActivity.this);
                        deleteDialog.setMessage("确认删除这条通知？");
                        deleteDialog.setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                removeFindMessageItem(findMessage);
                                deleteDialog.dismiss();
                            }
                        });
                        deleteDialog.setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteDialog.dismiss();
                            }
                        });
                        deleteDialog.show();

                        simpleItemListDialog.dismiss();
                        break;

                    default:break;
                }

            }
        });
        simpleItemListDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleItemListDialog.dismiss();
            }
        });
        simpleItemListDialog.show();



    }



    public List<String> getFindSquareDialogItemList(){
        List<String> list=new ArrayList<String>();
        list.add("查看Ta的信息");
        list.add("我有线索，通知Ta");
        return list;
    }

    public List<String> getFindMessageDialogItemList(){
        List<String> list=new ArrayList<String>();
        list.add("查看Ta的信息");
        list.add("删除该通知");
        return list;
    }


    private void removeLostItem(final BaseObject baseObject){
        Lost lost=new Lost();
        lost.setObjectId(baseObject.getObjectId());
        lost.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(null==e){
                    Toast.makeText(MainActivity.this,"已成功删除一条寻物启事",Toast.LENGTH_SHORT).show();
                    convertLostToFound(baseObject);
                    fragments.get(1).refreshData();
                }
                else {
                    Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void convertLostToFound(BaseObject baseObject){
        Found found=new Found();
        found.setUserId(baseObject.getUserId());
        found.setTitle(baseObject.getTitle());
        found.setDescribe(baseObject.getDescribe());
        found.setPhone(baseObject.getPhone());
        found.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

    }


    private void sendFindMessage(BaseObject baseObject,String reply){

        FindMessage findMessage=new FindMessage();
        findMessage.setTitle(baseObject.getTitle());
        findMessage.setReply(reply);
        findMessage.setUserId(baseObject.getUserId());
        findMessage.setSendId(MyApplication.getUser().getObjectId());
        findMessage.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(null==e){
                    Toast.makeText(MainActivity.this,"通知发送成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"通知发送失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void removeFindMessageItem(FindMessage findMessage){
        findMessage.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(null==e){
                    Toast.makeText(MainActivity.this,"已成功删除一条通知",Toast.LENGTH_SHORT).show();
                    fragments.get(2).refreshData();
                }else {
                    Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirectToAddItemActivity(){

        Intent intent=new Intent(MainActivity.this,AddItemActivity.class);
        startActivity(intent);

    }

    private void redirectToUserInfoActivity(Bundle bundle){
        Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void redirectToAboutActivity(){
        startActivity(new Intent(MainActivity.this,AboutActivity.class));
    }
    private void redirectToLoginActivity(){
        MyApplication.setUser(null);
        MyApplication.setLogin(false);
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

}
