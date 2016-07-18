package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.listener.OnListFragmentInteractionListener;
import me.pwcong.findobj.ui.fragment.FindSquareFragment;
import me.pwcong.findobj.ui.fragment.MyFindFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    List<Fragment> fragments=new ArrayList<Fragment>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


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

        initFragments();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragments.get(0)).commit();


    }

    private void initFragments(){
        fragments.add(FindSquareFragment.newInstance(new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(BaseObject baseObject) {
                Toast.makeText(MainActivity.this,baseObject.getTitle(),Toast.LENGTH_SHORT).show();
            }
        }));
        fragments.add(MyFindFragment.newInstance(new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(BaseObject baseObject) {
                Toast.makeText(MainActivity.this,baseObject.getTitle(),Toast.LENGTH_SHORT).show();
            }
        }));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order_time_asc) {
            return true;
        }else if(id==R.id.action_order_time_desc){
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
        } else if (id == R.id.nav_find_mine) {
            toolbar.setTitle(R.string.title_find_mine);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(1)).commit();
            fab.setVisibility(FloatingActionButton.VISIBLE);
        } else if(id == R.id.nav_find_message){
            toolbar.setTitle(R.string.title_find_message);
            fab.setVisibility(FloatingActionButton.GONE);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void redirectToAddItemActivity(){

        Intent intent=new Intent(MainActivity.this,AddItemActivity.class);
        startActivity(intent);

    }

}
