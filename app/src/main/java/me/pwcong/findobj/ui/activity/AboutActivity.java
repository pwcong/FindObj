package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;

/**
 * Created by pwcong on 2016/7/20.
 */
public class AboutActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int setView() {
        return R.layout.activity_about;
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


    }

    private void redirectToMainActivity(){
        startActivity(new Intent(AboutActivity.this,MainActivity.class));
        finish();
    }
}
