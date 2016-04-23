package spajam2016.haggy.nishinokasa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spajam2016.haggy.nishinokasa.R;

/**
 * Nishino-Kasa Status Fragment.
 */
public class KasaStatusFragment extends Fragment {

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

}
