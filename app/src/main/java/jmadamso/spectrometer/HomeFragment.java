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
        //Toast.makeText(getActivity(), "fragment onAttach to " + getActivity().getTitle() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "fragment view created" , Toast.LENGTH_SHORT).show();

        //instantiate the view from an xml layout and get references to its ui stuff:
       return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onStart() {
        super.onStart();
        AppDriver parent = (AppDriver)getActivity();
        if (parent != null) {
            parent.updateTextViews();
        }
    }
}
