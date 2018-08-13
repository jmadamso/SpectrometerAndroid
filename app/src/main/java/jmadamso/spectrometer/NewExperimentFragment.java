package jmadamso.spectrometer;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewExperimentFragment extends Fragment {

    private View myView;

    public NewExperimentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instantiate the view from an xml layout:
        View view = inflater.inflate(R.layout.fragment_new_exp, container, false);

        myView = view;
        return view;
    }

    public void onResume() {
        super.onResume();
        String availability,description;
        int color;
        AppDriver parent = (AppDriver) getActivity();

        if (parent != null ? parent.isRunningExperiment() : false) {
            availability = "DEVICE UNAVAILABLE";
            description = parent.getExpStatusString();
            color = Color.RED;
        } else {
            availability = "DEVICE AVAILABLE";
            description = parent.getSettingsString();
            color = Color.GREEN;
        }

        TextView tv = myView.findViewById(R.id.statusText);
        if(tv != null) {
            tv.setText(availability);
            tv.setTextColor(color);
        }

        tv = myView.findViewById(R.id.settingsText);
        if(tv != null) {
            tv.setText(description);
        }

        Button b = myView.findViewById(R.id.button);
        Button b2 = myView.findViewById(R.id.button2);
        if(parent.isRunningExperiment() && b != null && b2 != null) {
            b.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }
    }








}
