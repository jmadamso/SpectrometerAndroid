package jmadamso.spectrometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

public class CalibrateActivity extends AppCompatActivity {



    private GraphView mGraphView;

    private static int motorState = 0;
    private static int ledState = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        mGraphView = findViewById(R.id.graph);

    }

    @Override
    protected void onNewIntent(Intent i) {
        //update series here with contents of new intent.
        //we can do this since the mode is set to single
        //task in the manfifest file.

    }

    public void StreamButtonResponse(View view) {

    }

    public void motorButtonResponse(View view) {
        Button mButton = findViewById(R.id.motorButton);

        if(motorState == 0) {
            mButton.setText("Motor Off");
            motorState = 1;
        } else {
            mButton.setText("Motor on");
            motorState = 0;
        }
        setResult(defines.CAL_CMD_MOTOR_BTN);
        //Toast.makeText(this, "Toggling Motor", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void ledButtonResponse(View view) {
        Button mButton = findViewById(R.id.ledButton);

        if(ledState == 0) {
            mButton.setText("LED Off");
            ledState = 1;
        } else {
            mButton.setText("LED On");
            ledState = 0;
        }
        setResult(defines.CAL_CMD_LED_BTN);
        //Toast.makeText(this, "Toggling LED", Toast.LENGTH_SHORT).show();
        finish();

    }


}
