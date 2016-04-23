package spajam2016.haggy.nishinokasa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kubotaku.android.openweathermap.lib.WeatherInfo;

import java.util.List;

import spajam2016.haggy.nishinokasa.R;
import spajam2016.haggy.nishinokasa.api.WeatherGetter;

/**
 * Nishino-Kasa Status Fragment.
 */
public class KasaStatusFragment extends Fragment {

    private static final String TAG = KasaStatusFragment.class.getSimpleName();

    public KasaStatusFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment KasaStatusFragment.
     */
    public static KasaStatusFragment newInstance() {
        KasaStatusFragment fragment = new KasaStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kasa_status, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();

        final WeatherGetter weatherGetter = new WeatherGetter(getContext());
        weatherGetter.getForecast(34.702318, 135.4979219, onGetWeatherListener);

        getKasaStatus();
    }

    private WeatherGetter.OnGetWeatherListener onGetWeatherListener = new WeatherGetter.OnGetWeatherListener() {
        @Override
        public void OnGetForecast(List<WeatherInfo> forecast) {
            Log.d(TAG, "onGetForecast");

            final WeatherInfo info = forecast.get(0);
            final int weatherId = info.getWeatherId();

            int condition = (int)(weatherId / 100);
            if ((condition == 3) || (condition == 5) || (condition == 8)) {
                showRain();
            }
        }
    };

    private void showRain() {
        Log.d(TAG, "show rain");
    }

    private void getKasaStatus() {
        // TODO:ここ実装する
        final View view = getView();
        final ImageView kasaImage = (ImageView) view.findViewById(R.id.status_image_kasa);
        kasaImage.setImageResource(R.mipmap.open_kasa);
    }
}
