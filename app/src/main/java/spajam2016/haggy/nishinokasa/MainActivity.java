package spajam2016.haggy.nishinokasa;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import spajam2016.haggy.nishinokasa.bluetooth.BleWrapper;
import spajam2016.haggy.nishinokasa.fragments.HelloKasaFragment;
import spajam2016.haggy.nishinokasa.fragments.KasaStatusFragment;
import spajam2016.haggy.nishinokasa.fragments.OdekakeCalenderFragment;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    /**
     * リクエストコード：Bluetoothの有効化
     */
    private static final int REQUEST_ENABLE_BT = 100;

    /**
     * リクエストコード：位置情報許可
     */
    private static final int REQUEST_LOCATION_PERMISSIONS = 101;

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
    protected void onResume() {
        super.onResume();

        // Bluetoothが無効か確認
        if (!BleWrapper.isBluetoothEnable(this)) {
            requestEnableBT();
            return;
        }

        // AndroidM対応、位置情報が有効か確認（BLEのスキャンに位置情報パーミッションが必要なため）
        if (!checkPermissions()) {
            requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }

        startService();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
        }
    }

    private void startService() {
        final Intent intent = new Intent(this, KaService.class);
        startService(intent);
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

    // --------------------------------------------


    /**
     * 必要なパーミッションの確認を行う
     * <p>
     * AndroidM対応
     * </p>
     *
     * @return パーミッションが与えられている
     */
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 指定したパーミッションをユーザーにリクエストする.
     *
     * @param permissions パーミッションリスト
     * @param requestCode リクエエストコード
     */
    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(
                this,
                permissions,
                requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (!checkPermissions()) {
                Toast.makeText(this, "位置情報のリクエストを許可してください", Toast.LENGTH_LONG).show();
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Bluetooth設定用画面を起動する.
     */
    private void requestEnableBT() {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // まだOFFになっているなら、再度呼び出し
                if (resultCode == Activity.RESULT_CANCELED) {
                    requestEnableBT();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
