package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.bean.Lost;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.utils.CheckUtil;

/**
 * Created by pwcong on 2016/7/18.
 */
public class AddItemActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_describe)
    EditText et_describe;
    @BindView(R.id.et_phone)
    EditText et_phone;

    @Override
    protected int setView() {
        return R.layout.activity_additem;
    }

    @Override
    protected void initVariable() {

        toolbar.setTitle(R.string.title_find);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToMainActivity();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_additem_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_done){
            sendLostMessage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendLostMessage(){

        String title=et_title.getText().toString();
        String describe=et_describe.getText().toString();
        String phone=et_phone.getText().toString();

        if(CheckUtil.checkStringLength(title,Constants.TITLE_LENGTH_LIMIT)&&CheckUtil.checkStringLength(describe,Constants.DESCRIBE_LENGTH_LIMIT)&&!phone.equals("")){

            Lost lost=new Lost();
            lost.setTitle(title);
            lost.setDescribe(describe);
            lost.setPhone(phone);
            lost.setUserId(MyApplication.getUser().getObjectId());

            lost.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(null==e){
                        Toast.makeText(AddItemActivity.this,R.string.success_send_find,Toast.LENGTH_SHORT).show();
                        redirectToMainActivity();
                    }else {
                        Toast.makeText(AddItemActivity.this,R.string.error_send_find,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(AddItemActivity.this,R.string.error_send_find,Toast.LENGTH_SHORT).show();
        }



    }


    private void redirectToMainActivity(){
        startActivity(new Intent(AddItemActivity.this,MainActivity.class));
        finish();
    }
}
