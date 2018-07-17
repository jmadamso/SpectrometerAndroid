package joe.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.os.Bundle;
import android.widget.Toast;

import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.PositionMetrics;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.TextOrientation;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;


public class ResultActivity extends Activity {

    private XYPlot plot;
    static private double[] spectrumArray = new double[defines.NUM_WAVELENGTHS];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);


        //grab the array that was passed in:
        Bundle b = this.getIntent().getExtras();
        spectrumArray = b.getDoubleArray("spectrumArray");


        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = new Number[1024];
        Number[] series1Numbers = new Number[1024];

        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        int i;
        for(i = 0; i < series1Numbers.length; i++) {
            series1Numbers[i] = spectrumArray[i];
            domainLabels[i] = Double.parseDouble(df.format(defines.wavelengthArray[i]));
        }





        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);



        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        //series1Format.setInterpolationParams(
                //new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.getLegend().setVisible(false);
        plot.setDomainStep(StepMode.SUBDIVIDE, 9);
        plot.setRangeStep(StepMode.SUBDIVIDE, 6);

        //plot.getGraph().position(
                //100, HorizontalPositioning.ABSOLUTE_FROM_LEFT,
                //100, VerticalPositioning.ABSOLUTE_FROM_TOP);
        //plot.getGraph().setPadding(0,0,0,0);
        //plot.setMarkupEnabled(true);

        //plot.getGraph().setSize(new Size(
                //PixelUtils.dpToPix(50), SizeMode.FILL,
                //PixelUtils.dpToPix(50), SizeMode.FILL));

        //MARGIN is where labels go. increase this is range labels are cutoff
        plot.getGraph().setMarginLeft(200);


        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

    }
}
