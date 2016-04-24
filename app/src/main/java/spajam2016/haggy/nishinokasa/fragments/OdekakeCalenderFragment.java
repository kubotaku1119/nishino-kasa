package spajam2016.haggy.nishinokasa.fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashMap;
import java.util.Map;

import spajam2016.haggy.nishinokasa.R;

/**
 * Odekake with Nishino-Kasa Calender Fragment
 */
public class OdekakeCalenderFragment extends Fragment {

    public OdekakeCalenderFragment() {
        // Required empty public constructor
        mLoveHistory = new HashMap();
        mLoveHistory.put(CalendarDay.from(2016, 2, 30), 1);
        mLoveHistory.put(CalendarDay.from(2016, 3, 3), 1);
        mLoveHistory.put(CalendarDay.from(2016, 3, 5), -1);
        mLoveHistory.put(CalendarDay.from(2016, 3, 10), 1);
        mLoveHistory.put(CalendarDay.from(2016, 3, 15), -1);
        mLoveHistory.put(CalendarDay.from(2016, 3, 23), 1);
    }

    private Map<CalendarDay, Integer> mLoveHistory;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OdekakeCalenderFragment.
     */
    public static OdekakeCalenderFragment newInstance() {
        OdekakeCalenderFragment fragment = new OdekakeCalenderFragment();
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
        View ret = inflater.inflate(R.layout.fragment_odekake_calender, container, false);
        return ret;
    }

    @Override
    public void onStart() {
        super.onStart();
        MaterialCalendarView calender = (MaterialCalendarView)getView().findViewById(R.id.calendarView);
        calender.addDecorator(new LoveDecorator());
        calender.addDecorator(new HateDecorator());
    }

    public class LoveDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            if(!mLoveHistory.containsKey(day)) return false;

            return mLoveHistory.get(day)>0;
        }

        @Override
        public void decorate(DayViewFacade view) {

            final Drawable heart = getDrawableResource(R.drawable.red_heart);
            view.setBackgroundDrawable(heart);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));

        }
    }

    public class HateDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            if(!mLoveHistory.containsKey(day)) return false;

            return mLoveHistory.get(day)<0;
        }

        @Override
        public void decorate(DayViewFacade view) {

            final Drawable heart = getDrawableResource(R.drawable.black_heart);
            view.setBackgroundDrawable(heart);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));

        }
    }

    @SuppressWarnings("deprecation")
    private Drawable getDrawableResource(int id){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return getResources().getDrawable(id, null);
        }
        else{
            return getResources().getDrawable(id);
        }
    }

}
