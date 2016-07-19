package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;
import me.pwcong.findobj.bean.User;
import me.pwcong.findobj.bean.UserInfo;
import me.pwcong.findobj.conf.Constants;
import me.pwcong.findobj.utils.CheckUtil;

/**
 * Created by pwcong on 2016/7/15.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar_register) Toolbar toolbar;
    @BindView(R.id.register_progress) ProgressBar progressBar_register;
    @BindView(R.id.btn_submit) Button btn_submit;
    @BindView(R.id.btn_cancel) Button btn_cancel;
    @BindView(R.id.et_username) EditText et_username;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.btn_get_code) Button btn_get_code;
    @BindView(R.id.et_code) EditText et_code;
    @BindView(R.id.et_telephone) EditText et_telephone;


    @Override
    protected int setView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initVariable() {

        setSupportActionBar(toolbar);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToLoginActivity();
            }
        });

        getCheckCode();

        tryRegister();


    }

    private void tryRegister(){

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar_register.setVisibility(ProgressBar.VISIBLE);

                final String username=et_username.getText().toString();
                final String password=et_password.getText().toString();
                final String telephone=et_telephone.getText().toString();
                final String code=et_code.getText().toString();

                if(!username.equals("")&&!password.equals("")&&!telephone.equals("")
                        && !code.equals("")&& CheckUtil.checkStringLength(username,Constants.USERNAME_LENGTH_LIMIT)
                        &&CheckUtil.checkStringLength(password,Constants.USERNAME_LENGTH_LIMIT)){

                    final User _user=new User();
                    _user.setUsername(username);
                    _user.setPassword(password);
                    _user.setMobilePhoneNumber(telephone);
                    _user.setMobilePhoneNumberVerified(true);

                    BmobQuery<User> query=new BmobQuery<User>();
                    query.addWhereEqualTo(Constants.USERNAME,_user.getUsername());

                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(null==e&&list.size()<1) {

                                BmobSMS.verifySmsCode(telephone, code, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                        if(null==e){
                                            _user.signUp(new SaveListener<User>() {
                                                @Override
                                                public void done(User user, BmobException e) {
                                                    if(null==e){
                                                        Toast.makeText(RegisterActivity.this, R.string.success_register, Toast.LENGTH_SHORT).show();

                                                        MyApplication.setUser(user);
                                                        MyApplication.setLogin(true);

                                                        tryCreateUserInfo(user);
                                                        redirectToMainActivity();
                                                    }
                                                    else {
                                                        Toast.makeText(RegisterActivity.this,R.string.error_register,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(RegisterActivity.this,R.string.error_check_code,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }else {
                                Toast.makeText(RegisterActivity.this,R.string.error_exist_username,Toast.LENGTH_SHORT).show();
                            }
                            progressBar_register.setVisibility(ProgressBar.GONE);

                        }
                    });

                }

                if(username.equals("")){
                    Toast.makeText(RegisterActivity.this,R.string.error_empty_username,Toast.LENGTH_SHORT).show();
                }

                if(password.equals("")){
                    Toast.makeText(RegisterActivity.this,R.string.error_empty_password,Toast.LENGTH_SHORT).show();
                }

                if(telephone.equals("")){
                    Toast.makeText(RegisterActivity.this,R.string.error_empty_telephone,Toast.LENGTH_SHORT).show();
                }

                if(code.equals("")){
                    Toast.makeText(RegisterActivity.this,R.string.error_empty_code,Toast.LENGTH_SHORT).show();

                }
                if(!CheckUtil.checkStringLength(username,Constants.USERNAME_LENGTH_LIMIT)||!CheckUtil.checkStringLength(password,Constants.USERNAME_LENGTH_LIMIT)){
                    Toast.makeText(RegisterActivity.this,R.string.error_length_username_password,Toast.LENGTH_SHORT).show();
                }
                progressBar_register.setVisibility(ProgressBar.GONE);

            }
        });
    }

    private void tryCreateUserInfo(User user){
        final UserInfo userInfo=new UserInfo();
        userInfo.setUserId(user.getObjectId());
        userInfo.setName("新用户");
        userInfo.setSummary("此人好懒，什么都没留下。");
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(null==e){
                    Toast.makeText(RegisterActivity.this,R.string.success_create_user_info,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this,R.string.error_create_user_info,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public class TaskCodeButton extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            for (int i=Constants.SMS_TIME_LIMIT;i>0;i--){
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return  null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btn_get_code.setText("验证码("+values[0]+")");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btn_get_code.setClickable(true);
            btn_get_code.setText(R.string.action_get_code);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn_get_code.setClickable(false);
        }
    }

    private void getCheckCode(){
        btn_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String telephone=et_telephone.getText().toString();
                if(!telephone.equals("")){
                    new TaskCodeButton().execute();
                    BmobSMS.requestSMSCode(telephone, Constants.SMS_TEMPLATENAME, new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (null==e){
                                Toast.makeText(RegisterActivity.this,R.string.success_send_code,Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,R.string.error_send_code,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this,R.string.error_empty_telephone,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void redirectToMainActivity(){
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();
    }

    private void redirectToLoginActivity(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
