package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.bean.User;


/**
 * Created by pwcong on 2016/7/16.
 */
public class LoginActivity extends BaseActivity{

    @BindView(R.id.toolbar_login) Toolbar toolbar;
    @BindView(R.id.et_username) EditText et_username;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.btn_sign_in) Button btn_signIn;
    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.login_progress) ProgressBar progressBar_login;

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariable() {

        setSupportActionBar(toolbar);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar_login.setVisibility(ProgressBar.VISIBLE);

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(!username.equals("")&&!password.equals("")){
                    final User _user=new User();
                    _user.setUsername(username);
                    _user.setPassword(password);

                    _user.login(new SaveListener<User>() {


                        @Override
                        public void done(User user, BmobException e) {
                            if(e==null){
                                Toast.makeText(LoginActivity.this,R.string.success_login,Toast.LENGTH_SHORT).show();

                                MyApplication.setUser(user);
                                MyApplication.setLogin(true);

                                redirectToMainActivity();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,R.string.error_login,Toast.LENGTH_SHORT).show();


                            }

                            progressBar_login.setVisibility(ProgressBar.GONE);

                        }
                    });
                    
                }

                if(username.equals("")){
                    Toast.makeText(LoginActivity.this,R.string.error_empty_username,Toast.LENGTH_SHORT).show();
                    progressBar_login.setVisibility(ProgressBar.GONE);
                }

                if(password.equals("")){
                    Toast.makeText(LoginActivity.this,R.string.error_empty_password,Toast.LENGTH_SHORT).show();
                    progressBar_login.setVisibility(ProgressBar.GONE);

                }



            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToRegisterActivity();
            }
        });


    }

    private void redirectToRegisterActivity(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    private void redirectToMainActivity(){

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}

