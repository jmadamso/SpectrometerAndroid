package jmadamso.spectrometer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //do we have args?
        Bundle b = getArguments();
        ArrayList<String> experimentTitles;
        if (b != null) {
            experimentTitles = b.getStringArrayList("experiment_list");
        } else {
            experimentTitles = new ArrayList<>();
            experimentTitles.add("well");
            experimentTitles.add("here");
            experimentTitles.add("we");
            experimentTitles.add("go");
            experimentTitles.add("again");
            experimentTitles.add("nice");
            experimentTitles.add("job!");
        }



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        LinearLayout layout = v.findViewById(R.id.linear_layout);
        if (layout != null && experimentTitles != null) {
            for (final String s : experimentTitles) {

                Button btn = new Button(getContext());
                btn.setText(s);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "you clicked " + s, Toast.LENGTH_SHORT).show();
                    }
                });
                layout.addView(btn, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                }
            }

        return v;
        }




    public void onStart() {
        super.onStart();

        //request bluetooth list of things here
        AppDriver parent = (AppDriver)getActivity();
        BTService b = null;
        if (parent!= null) {
            b = parent.getBTService();

        }
        String str = "" + defines.EXP_LIST;
        if(b != null) {
            b.write(str.getBytes());
        } else {
            //toast the error
        }
    }

}
