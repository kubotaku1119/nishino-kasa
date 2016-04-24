package spajam2016.haggy.nishinokasa.api;

import android.content.Context;

import com.kubotaku.android.openweathermap.lib.ForecastGetter;
import com.kubotaku.android.openweathermap.lib.IForecastGetter;
import com.kubotaku.android.openweathermap.lib.LatLng;
import com.kubotaku.android.openweathermap.lib.OnForecastGetListener;
import com.kubotaku.android.openweathermap.lib.WeatherInfo;

import java.util.List;

import spajam2016.haggy.nishinokasa.R;

/**
 * Weather info Getter class.
 */
public class WeatherGetter {

    public interface OnGetWeatherListener {
        void OnGetForecast(List<WeatherInfo> forecast);
    }

    private OnGetWeatherListener onGetWeatherListener;

    private final Context context;

    private String apiKey;

    public WeatherGetter(Context context) {
        this.context = context;
        this.apiKey = context.getString(R.string.weather_api_key);
    }

    public void getForecast(double latitude, double longitude, OnGetWeatherListener listener) {
        this.onGetWeatherListener = listener;

        final IForecastGetter forecastGetter = createForecastGetter();
        forecastGetter.setDailyCount(2);
        forecastGetter.setForecastType(IForecastGetter.FORECAST_TYPE_DAILY);
        forecastGetter.setLatLng(new LatLng(latitude, longitude));

        forecastGetter.getForecast(new OnForecastGetListener() {
            @Override
            public void onGetForecast(List<WeatherInfo> list) {
                if (onGetWeatherListener != null) {
                    onGetWeatherListener.OnGetForecast(list);
                }
            }
        });
    }

    private IForecastGetter createForecastGetter() {
        IForecastGetter forecastGetter = null;

        try {
            forecastGetter = ForecastGetter.getInstance(context, apiKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastGetter;
    }
}
