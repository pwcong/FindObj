package me.pwcong.findobj.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseActivity;

/**
 * Created by pwcong on 2016/7/20.
 */
public class AboutActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.link_source)
    LinearLayout link_source;
    @BindView(R.id.link_blog)
    LinearLayout link_blog;

    @Override
    protected int setView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initVariable() {

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToMainActivity();
            }
        });


        link_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://github.com/pwcong/FindObj");
            }
        });

        link_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("http://pwcong.me");
            }
        });


    }

    private void redirectToMainActivity(){
        startActivity(new Intent(AboutActivity.this,MainActivity.class));
        finish();
    }

    private void openUrl(String url){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
