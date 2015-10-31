package me.laudoak.oakpark.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.ViewPagerAdapter;
import me.laudoak.oakpark.fragment.LoginFragment;
import me.laudoak.oakpark.fragment.RegisterFragment;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class EnterActivity extends XBaseActivity{

    private static final String TAG = "EnterActivity";

    private ViewPager viewPager;
    private TextView loginText,registerText;
    private ImageView indicator;

    private ImageView close;

    private List<Fragment> fragments;

    private int currPage;
    private int indicatorWidth;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_enter);
    }

    @Override
    public void buildView() {

        buildBar();

        viewPager = (ViewPager) findViewById(R.id.register_viewpager);
        loginText = (TextView) findViewById(R.id.enter_top_tv_login);
        registerText = (TextView) findViewById(R.id.enter_top_tv_register);
        indicator = (ImageView) findViewById(R.id.enter_top_indicator);

        indicatorWidth = indicator.getLayoutParams().width;

        fragments = new ArrayList<Fragment>();
        fragments.add(LoginFragment.newInstance());
        fragments.add(RegisterFragment.newInstance());

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.d(TAG, "positionOffset" + positionOffset);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();

                if (currPage == 0 && position == 0) {
                    layoutParams.leftMargin = (int) (indicatorWidth * positionOffset);
                } else if (currPage == 1 && position == 0) {
                    layoutParams.leftMargin = (int) (currPage * indicatorWidth + (positionOffset - 1) * indicatorWidth);
                }

                indicator.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {
                currPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    private void buildBar() {
        getSupportActionBar().hide();
        close = (ImageView) findViewById(R.id.ca_enter_back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterActivity.this.finish();
            }
        });
    }

}
