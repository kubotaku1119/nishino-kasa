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
import android.widget.TextView;

import com.kubotaku.android.openweathermap.lib.WeatherInfo;

import java.util.List;

import spajam2016.haggy.nishinokasa.R;
import spajam2016.haggy.nishinokasa.api.WeatherGetter;

/**
 * Nishino-Kasa Status Fragment.
 */
public class KasaStatusFragment extends Fragment {

    private static final String TAG = KasaStatusFragment.class.getSimpleName();

    //todo:好感度設定する
    private int mLove=50;


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
//        mLove = 15;
        setLoveGage(mLove);
    }

    private WeatherGetter.OnGetWeatherListener onGetWeatherListener = new WeatherGetter.OnGetWeatherListener() {
        @Override
        public void OnGetForecast(List<WeatherInfo> forecast) {
            final int today = forecast.get(0).getWeatherId();
            final int tomorrow = forecast.get(1).getWeatherId();
//            int today = 300;
//            int tomorrow = 810;


            setCurrentWeather(today);
            setKasaState(today, mLove);

            setComment(today, tomorrow, mLove);
        }
    };


    public enum WeatherState{
        Snow,
        Rain,
        Cloud,
        Fine
    }

    private WeatherState getWeatherState(int weatherId) {
        switch((int)(weatherId / 100)){
            case 3:
            case 5:
                return WeatherState.Rain;
            case 6:
                return WeatherState.Snow;
            default:
                if(weatherId == 800){
                    return WeatherState.Fine;
                }else{
                    return WeatherState.Cloud;
                }
        }
    }

    private void setCurrentWeather(int weatherId){
        final View view = getView();
        final ImageView weatherImage = (ImageView) view.findViewById(R.id.status_image_weather);

        switch(getWeatherState(weatherId)){
            case Rain:
                weatherImage.setImageResource(R.mipmap.rainy);
                break;
            case Snow:
                weatherImage.setImageResource(R.mipmap.snow);
                break;
            case Fine:
                weatherImage.setImageResource(R.mipmap.sunny);
                break;
            case Cloud:
                weatherImage.setImageResource(R.mipmap.cloudy);
                break;
        }
    }

    private void setKasaState(int weatherId, int love){
        final View view = getView();
        final ImageView kasaImage = (ImageView) view.findViewById(R.id.status_image_kasa);

        if(love < 25){
            kasaImage.setImageResource(R.mipmap.sulk_kasa);
            return;
        }

        switch(getWeatherState(weatherId)){
            case Rain:
                kasaImage.setImageResource(R.mipmap.open_kasa);
                break;
            case Snow:
                kasaImage.setImageResource(R.mipmap.open_kasa);
                break;
            case Fine:
                kasaImage.setImageResource(R.mipmap.close_kasa);
                break;
            case Cloud:
                kasaImage.setImageResource(R.mipmap.half_kasa);
                break;
        }
    }

    private void setLoveGage(int love){
        final View view = getView();
        final ImageView loveImage = (ImageView) view.findViewById(R.id.status_image_love);

        loveImage.setScaleX((float)love/100);
        loveImage.setScaleY((float)love/100);
    }

    private void setComment(int todaysWeather, int tomorrowsWeather, int love){
        final View view = getView();
        final TextView kasaComment = (TextView) view.findViewById(R.id.text_kasa_comment);

        if(love < 25){
            kasaComment.setText("もう構って\nくれないのね");
            return;
        }

        switch(getWeatherState(todaysWeather)){
            case Rain:
                kasaComment.setText("ねぇ、おでかけ\nしましょうよ");
                break;
            case Snow:
                kasaComment.setText("ねぇ、おでかけ\nしましょうよ");
                break;
            case Fine:
                switch(getWeatherState(tomorrowsWeather)){
                    case Rain:
                    case Snow:
                        kasaComment.setText("明日は一緒に\nおでかけしたいな");
                        break;
                    default:
                        kasaComment.setText("ええ、とっても\nいいお天気ね…");
                        break;
                }
                break;
            case Cloud:
                switch(getWeatherState(tomorrowsWeather)){
                    case Rain:
                    case Snow:
                        kasaComment.setText("明日は一緒に\nおでかけしたいな");
                        break;
                    default:
                        kasaComment.setText("ただ一緒に\nいたいだけなの");
                        break;
                }
                break;
        }

    }

    public void setLove(int love){
        mLove = love;
    }

    public int getLove(){
        return mLove;
    }

}
