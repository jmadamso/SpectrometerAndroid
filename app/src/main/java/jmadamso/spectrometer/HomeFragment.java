package jmadamso.spectrometer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getActivity(), "fragment onAttach to " + getActivity().getTitle() , Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //work on the views like this:
        //TextView tv = view.findViewById(R.id.welcomeTV);
        //tv.setText("hello!);

        //and use stuff from AppDriver super-activity (IE: BTSERVICE) like this:
        //int i = ((AppDriver)getActivity()).publicMethod;

        return view;
    }

}
