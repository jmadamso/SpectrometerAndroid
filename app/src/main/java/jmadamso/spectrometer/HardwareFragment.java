package jmadamso.spectrometer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class HardwareFragment extends Fragment {


    public HardwareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Toast.makeText(getActivity(), "fragment onAttach to " + getActivity().getTitle() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hardware, container, false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AppDriver a = (AppDriver)getActivity();
        BTService b = null;
        if (a!= null) {
            b = a.getBTService();
        }

        if (b != null) {
            //shortcut to convert char -> string -> byte array for serial
            b.write(("" + defines.HARDWARE_OFF).getBytes());
        }

        Toast.makeText(getActivity(), "Resetting hardware controls", Toast.LENGTH_SHORT).show();
    }








}
