package spajam2016.haggy.nishinokasa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import spajam2016.haggy.nishinokasa.R;

/**
 * Hello Fragment
 */
public class HelloKasaFragment extends Fragment {

    public static final int HELLO_1 = 0;

    public static final int HELLO_2 = 1;

    public static final int HELLO_3 = 2;

    public static final int HELLO_4 = 3;

    public static final int HELLO_5 = 4;

    private static final String ARGS_INDEX = "args_index";

    private int helloIndex;


    public HelloKasaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1.
     * @return A new instance of fragment HelloKasaFragment.
     */
    public static HelloKasaFragment newInstance(final int index) {
        HelloKasaFragment fragment = new HelloKasaFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            helloIndex = getArguments().getInt(ARGS_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello_kasa, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupViews();
    }

    private void setupViews() {
        final View view = getView();

        final TextView textView = (TextView) view.findViewById(R.id.hello_fragment_text);
        textView.setText(getHelloTextResource());

        final ImageView imageView = (ImageView) view.findViewById(R.id.hello_fragment_image);
        imageView.setImageResource(getHelloImageResource());
    }

    //ここから下を編集
    private int getHelloTextResource() {
//        return R.string.hello_text_0;
        switch (helloIndex) {
            case 0:
                return R.string.hello_text_0;
            case 1:
                return R.string.hello_text_1;
            case 2:
                return R.string.hello_text_2;
            case 3:
                return R.string.hello_text_3;
            case 4:
                return R.string.hello_text_4;
        }
        return 0;
    }
    private int getHelloImageResource() {
//        return R.mipmap.open_kasa;
        switch (helloIndex) {
            case 0:
                return R.mipmap.open_kasa;
            case 1:
                return R.mipmap.hello_image1;
            case 2:
                return R.mipmap.hello_image2;
            case 3:
                return R.mipmap.hello_image3;
            case 4:
                return R.mipmap.open_kasa;
        }
        return 0;
    }


}
