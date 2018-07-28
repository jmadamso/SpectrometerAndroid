package jmadamso.spectrometer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;




public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //grab the array that was passed in:
        Bundle b = this.getIntent().getExtras();
        if(b == null) {
            b = new Bundle();
        }
        //should this be a reference assign or a hard copy?? shrug
        double[] spectrumArray = b.getDoubleArray("spectrumArray");


        //and if we get a null, that will cause a few problems
        if (spectrumArray == null) {
            spectrumArray = new double[1024];
        }

        setContentView(R.layout.activity_result);
        ViewPager viewPager = findViewById(R.id.viewpager);
        ResultFragmentAdapter adapter = new ResultFragmentAdapter(this, getSupportFragmentManager(), spectrumArray);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
