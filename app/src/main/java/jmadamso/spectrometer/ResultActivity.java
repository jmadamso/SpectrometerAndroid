package jmadamso.spectrometer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;



public class ResultActivity extends AppCompatActivity {

    static private double[] spectrumArray = new double[defines.NUM_WAVELENGTHS];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //grab the array that was passed in:
        Bundle b = this.getIntent().getExtras();
        //should this be a reference assign or a hard copy?? shrug
        try{
            spectrumArray = b.getDoubleArray("spectrumArray");
        }
        catch(NullPointerException e) {
            spectrumArray[0] = 6;
            spectrumArray[1] = 9;

        }

        setContentView(R.layout.activity_result);
        ViewPager viewPager = findViewById(R.id.viewpager);

        ResultFragmentAdapter adapter = new ResultFragmentAdapter(this, getSupportFragmentManager(),spectrumArray);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
