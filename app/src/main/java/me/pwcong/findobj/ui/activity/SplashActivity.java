package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import me.pwcong.findobj.MyApplication;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;

/**
 * Created by pwcong on 2016/7/15.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView iv_enter;

    @Override
    protected int setView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariable() {


        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f, 1.05f, 1.0f, 1.05f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        iv_enter.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(MyApplication.isLogin()){

                    MyApplication.finishAllActivities();
                    redirectToMainActivity();
                }
                else {
                    redirectToLoginActivity();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void redirectToMainActivity(){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }

    private void redirectToLoginActivity(){
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();
    }
}
