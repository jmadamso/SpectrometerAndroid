package jmadamso.spectrometer;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewExperimentFragment extends Fragment {

    View myView;

    public NewExperimentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //instantiate the view from an xml layout:
        AppDriver parent = (AppDriver) getActivity();
        View view;

        //we have two options based upon whether or not the device is busy:
        if(parent.isRunningExperiment() == true) {
            view = inflater.inflate(R.layout.fragment_new_exp_busy, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_new_exp_idle, container, false);
        }

        myView = view;
        return view;
    }

    public void onResume() {
        super.onResume();
        myView.findViewById(R.id.expSettingsTV);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String settingsStr = "Current Settings:\n" +
                "Doctor Name: " + sharedPref.getString("doctor_name_entry","") + "\n" +
                "Patient ID: " + sharedPref.getString("patient_name_entry","") + "\n" +
                "Scans to Take: " + sharedPref.getString("num_scans_entry","") + "\n" +
                "Time Between Scans: " + sharedPref.getString("scan_time_entry","") + " seconds\n" +
                "Integration Time: " + sharedPref.getString("integration_time_entry","") + "\n" +
                "Boxcar Width: " + sharedPref.getString("boxcar_list","") + "\n" +
                "Averages per Scan: " + sharedPref.getString("averaging_list","") + "\n";




        TextView tv = myView.findViewById(R.id.expSettingsTV);
        if(tv != null) {
            tv.setText(settingsStr);
        }
    }








}
