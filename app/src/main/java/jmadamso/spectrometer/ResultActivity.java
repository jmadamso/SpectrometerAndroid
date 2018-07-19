package jmadamso.spectrometer;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
        setContentView(R.layout.activity_result);
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








        //grab the array that was passed in:
        Bundle b = this.getIntent().getExtras();
        spectrumArray = b.getDoubleArray("spectrumArray");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        initGraph(graph);







    }

    private void initGraph(GraphView graph) {


        // first series is a line
        DataPoint[] points = new DataPoint[defines.NUM_WAVELENGTHS];

        double wavelengths[] = new double[1024];

        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        int i;

        for (i = 0; i < points.length; i++) {
            wavelengths[i] = Double.parseDouble(df.format(defines.wavelengthArray[i]));
            points[i] = new DataPoint(wavelengths[i], spectrumArray[i]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        // set manual X bounds
        //graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMinY(-200);
        //graph.getViewport().setMaxY(200);

        //graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(4);
        //graph.getViewport().setMaxX(80);

        // enable scaling
        graph.getViewport().setScalable(true);

        series.setTitle("Spectrum");

        graph.addSeries(series);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
