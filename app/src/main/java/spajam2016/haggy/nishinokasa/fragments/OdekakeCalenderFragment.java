package spajam2016.haggy.nishinokasa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spajam2016.haggy.nishinokasa.R;

/**
 * Odekake with Nishino-Kasa Calender Fragment
 */
public class OdekakeCalenderFragment extends Fragment {

    public OdekakeCalenderFragment() {
        // Required empty public constructor
    }

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
        return inflater.inflate(R.layout.fragment_odekake_calender, container, false);
    }

}
