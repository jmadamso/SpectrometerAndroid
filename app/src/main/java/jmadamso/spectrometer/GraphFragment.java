package jmadamso.spectrometer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {

    private double[] mSpectrumArray;

    public GraphFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        if(b == null) {
            b = new Bundle();
        }
        mSpectrumArray = b.getDoubleArray("spectrumArray");
        if(mSpectrumArray == null) {
            mSpectrumArray = new double[defines.NUM_WAVELENGTHS];
            //default y=x if nothing was passed in
            //for(int i = 0; i < defines.NUM_WAVELENGTHS; i++) {
            //    mSpectrumArray[i] = i;
            //}
        }

        View view = inflater.inflate(R.layout.view_graph, container, false);
        GraphView graph = view.findViewById(R.id.graph);

        if(graph != null) {
            initGraph(graph);
        }
        return view;
    }

    private void initGraph(GraphView graph) {
        if(graph == null) {
            return;
        }

        // first series is a line
        DataPoint[] points = new DataPoint[defines.NUM_WAVELENGTHS];

        double wavelengths[] = new double[defines.NUM_WAVELENGTHS];
        double largest = 0;
        int largestIndex = 0;
        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        int i;

        for (i = 0; i < points.length; i++) {
            wavelengths[i] = Double.parseDouble(df.format(defines.wavelengthArray[i]));
            points[i] = new DataPoint(wavelengths[i], mSpectrumArray[i]);

            if (mSpectrumArray[i] > largest ) {
                largest = mSpectrumArray[i];
                largestIndex = i;
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);



        // set manual X and Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(largest);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX((int) wavelengths[0]);
        graph.getViewport().setMaxX((int) wavelengths[wavelengths.length-1]);

        // enable scaling
        graph.getViewport().setScalable(true);

        series.setTitle("Spectrum");
        graph.addSeries(series);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

}
