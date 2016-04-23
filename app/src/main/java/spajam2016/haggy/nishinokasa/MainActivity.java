package spajam2016.haggy.nishinokasa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import spajam2016.haggy.nishinokasa.fragments.HelloKasaFragment;
import spajam2016.haggy.nishinokasa.fragments.KasaStatusFragment;
import spajam2016.haggy.nishinokasa.fragments.OdekakeCalenderFragment;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout pagerDotIndicator;

    private ImageView[] indicatorDots;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ActionBarにアイコン表示
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_main);

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
        final MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(fragmentManager);
        viewPager = (ViewPager) findViewById(R.id.hello_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        pagerDotIndicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
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

    private static class MainViewPagerAdapter extends FragmentPagerAdapter {

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                case 0:
                    return KasaStatusFragment.newInstance();

                case 1:
                    return OdekakeCalenderFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
