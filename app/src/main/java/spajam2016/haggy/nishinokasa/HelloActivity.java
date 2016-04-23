package spajam2016.haggy.nishinokasa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import spajam2016.haggy.nishinokasa.fragments.HelloKasaFragment;

public class HelloActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout pagerDotIndicator;

    private ImageView[] indicatorDots;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        initViews();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
        }
    }

    private void initViews() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final HelloViewPagerAdapter pagerAdapter = new HelloViewPagerAdapter(fragmentManager);
        viewPager = (ViewPager) findViewById(R.id.hello_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        pagerDotIndicator = (LinearLayout)findViewById(R.id.viewPagerCountDots);
        final int childCount = viewPager.getAdapter().getCount();
        indicatorDots = new ImageView[childCount];
        for (int index = 0; index < childCount; index++) {
            indicatorDots[index] = new ImageView(this);
            indicatorDots[index].setImageResource(R.drawable.nonselecteditem_dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pagerDotIndicator.addView(indicatorDots[index], params);
        }

        indicatorDots[0].setImageResource(R.drawable.selecteditem_dot);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        final int childCount = viewPager.getAdapter().getCount();
        for (int index = 0; index < childCount; index++) {
            indicatorDots[index].setImageResource(R.drawable.nonselecteditem_dot);
        }
        indicatorDots[position].setImageResource(R.drawable.selecteditem_dot);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private static class HelloViewPagerAdapter extends FragmentPagerAdapter {

        public HelloViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HelloKasaFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
