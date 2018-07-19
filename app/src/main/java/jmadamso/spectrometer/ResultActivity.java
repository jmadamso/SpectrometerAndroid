package jmadamso.spectrometer;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.RoundingMode;
import java.text.DecimalFormat;



public class ResultActivity extends AppCompatActivity {

    static private double[] spectrumArray = new double[defines.NUM_WAVELENGTHS];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //grab the array that was passed in:
        Bundle b = this.getIntent().getExtras();
        //should this be a reference assign or a hard copy?? shrug
        spectrumArray = b.getDoubleArray("spectrumArray");

        setContentView(R.layout.activity_result);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        ResultFragmentAdapter adapter = new ResultFragmentAdapter(this, getSupportFragmentManager(),spectrumArray);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        /*
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Toast.makeText(ResultActivity.this, "Tab at pos = " + pos , Toast.LENGTH_SHORT).show();
                switch(pos) {
                    case 0:
                        //set view to the graph
                        break;

                    case 1:
                        //set view to info screen
                        break;

                    case 2:
                        //set view to share screen
                        break;

                    default:

                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        */




        GraphView graph = findViewById(R.id.graph);
        if(graph == null) {
            //Toast.makeText(ResultActivity.this, "null graph :(", Toast.LENGTH_SHORT).show();
        }
        //initGraph(graph);

        //setContentView(R.layout.activity_result);
    }





}
