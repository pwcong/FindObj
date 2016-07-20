package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.drakeet.materialdialog.MaterialDialog;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.base.BaseFragment;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.bean.Lost;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.ui.dialog.SimpleItemListDialog;
import me.pwcong.findobj.ui.fragment.FindSquareFragment;
import me.pwcong.findobj.ui.fragment.MyFindFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,FindSquareFragment.BaseObjectItemListener{

    List<BaseFragment> fragments=new ArrayList<BaseFragment>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    LinearLayout ll_userInfo;


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




    }

    private void initFragments(){
        fragments.add(FindSquareFragment.newInstance());
        fragments.add(MyFindFragment.newInstance());
    }

    private void initNavigationViewHeader(NavigationView navigationView){
        ll_userInfo= (LinearLayout) navigationView.getHeaderView(0);
        TextView textView= (TextView) ll_userInfo.findViewById(R.id.tv_username);
        textView.setText(MyApplication.getUser().getUsername());

        ll_userInfo.setOnClickListener(new View.OnClickListener() {
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_order_time_asc) {

            MyApplication.setOrderType(Constants.ORDER_BY_TIME_ASC);

            return true;
        }else if(id==R.id.action_order_time_desc){

            MyApplication.setOrderType(Constants.ORDER_BY_TIME_DESC);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find_square) {
            toolbar.setTitle(R.string.title_find_square);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(0)).commit();
            fab.setVisibility(FloatingActionButton.VISIBLE);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_find_mine) {
            toolbar.setTitle(R.string.title_find_mine);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(1)).commit();
            fab.setVisibility(FloatingActionButton.VISIBLE);
            drawer.closeDrawer(GravityCompat.START);
        } else if(id == R.id.nav_find_message){
            toolbar.setTitle(R.string.title_find_message);
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

            final SimpleItemListDialog dialog=new SimpleItemListDialog(MainActivity.this, getFindSquareDialogItemList(), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0:
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.USERID,baseObject.getUserId());
                            bundle.putBoolean(Constants.MYSELF,false);
                            redirectToUserInfoActivity(bundle);
                            break;
                        case 1:break;
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
            dialog.setMessage("是否删除这条寻物启事？");
            dialog.setPositiveButton("是", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Lost lost=new Lost();
                    lost.setObjectId(baseObject.getObjectId());
                    lost.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(null==e){
                                Toast.makeText(MainActivity.this,"已成功删除一条寻物启事",Toast.LENGTH_SHORT).show();
                                fragments.get(1).refreshData();
                            }
                            else {
                                Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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

    public List<String> getFindSquareDialogItemList(){
        List<String> list=new ArrayList<String>();
        list.add("查看寻物用户");
        list.add("告知寻物用户");
        return list;
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
