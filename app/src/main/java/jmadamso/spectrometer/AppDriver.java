package jmadamso.spectrometer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/*
 The main activity for the application. Declares important variables as well as
 sets the initial view for the user to interact with the buttons.
 */


public class AppDriver extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1337;
    private static final int CALIB_SCREEN_COMMANDS = 777;

    private SharedPreferences mPrefs;
    private BluetoothAdapter mBluetoothAdapter;
    private static String mDeviceName;

    //making this static keeps connections when changing orientation or something else
    private static BTService mBTService = null;

    private static double[] spectrumArray = new double[defines.NUM_WAVELENGTHS];

    //the text displays at main page
    private TextView mPressureText;
    private TextView mStatusText;

    private static int motorState = 0;
    private static int ledState = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (mBTService == null) {
            mBTService = new BTService(this, mHandlerBT);

            //apply defaults here too
            mPrefs.edit().clear().apply();
        }

        mBTService.setHandler(mHandlerBT);
        setContentView(R.layout.activity_main);
        mPressureText = findViewById(R.id.pressureText);
        mStatusText = findViewById(R.id.statusText);

        //whenever this is called, update text displays to display their most recent text
        updateTextViews();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(AppDriver.this, "You don't seem to have bluetooth!" , Toast.LENGTH_SHORT).show();
            finish();
        }

        //request to turn on bluetooth if it's off
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        if (mBTService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBTService.getState() == BTService.STATE_NONE) {
                // Start the Bluetooth chat service in order to make connections
                mBTService.start();
            }
        }

        //Toast.makeText(AppDriver.this, "driver onCreate" , Toast.LENGTH_SHORT).show();
    }

    protected void onResume() {
        super.onResume();
        //Toast.makeText(AppDriver.this, "Main onResume", Toast.LENGTH_SHORT).show();
        if(mBTService != null) {
            syncSettings();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mBluetoothAdapter.cancelDiscovery();

    }

    //called by anything we start with startActivityForResult with the original request code.
    //doesn't do much for us but we could add an activity to return a connected device later
    //down the road. Maybe an activity for discovering the spectrometer box
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            //this guy gets called when the bluetooth request dialog
            if (requestCode == REQUEST_ENABLE_BT) {
                if(resultCode  == RESULT_OK){
                    //Toast.makeText(AppDriver.this, "Bluetooth enabled after activity return",Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(AppDriver.this, "You need bluetooth for this!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if (requestCode == CALIB_SCREEN_COMMANDS) {
                Toast.makeText(AppDriver.this, "calib returned " + resultCode, Toast.LENGTH_SHORT).show();

                //now we switch to find out what command the calibration screen
                //wants us to execute:
                switch(resultCode) {

                    //explicit calls to button response functions,
                    //which tolerate being passed null.
                    case defines.CAL_CMD_LED_BTN:
                        ledButtonResponse(null);
                        break;

                    case defines.CAL_CMD_MOTOR_BTN:
                        motorButtonResponse(null);
                        break;

                    case defines.CAL_CMD_STREAM_BTN:
                        streamButtonResponse(null);
                        break;

                    default:

                        break;
                }
            }
        } catch (Exception ex) {
            Toast.makeText(AppDriver.this, ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }



    // Create a BroadcastReceiver for ACTION_FOUND.
    // this guy will listen for when the BT discovery process comes
    // back with whatever devices it found, and tries to connect
    // if we see the one we want:
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName() != null) {
                    Toast.makeText(AppDriver.this, "discovery receiver found " + device.getName() , Toast.LENGTH_SHORT).show();
                    if (device.getName().equals("raspberrypi")) {
                        mBluetoothAdapter.cancelDiscovery();
                        Toast.makeText(AppDriver.this, "trying " + device.getName() +" after discovery", Toast.LENGTH_SHORT).show();
                        mBTService.connect(mBluetoothAdapter.getRemoteDevice(device.getAddress()));
                    }
                } else {
                    Toast.makeText(AppDriver.this, "discovery receiver found " + device.getAddress() , Toast.LENGTH_SHORT).show();
                }



                //String deviceName = device.getName();
                //String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };


    public void bluetoothButtonResponse(View view) {

        //Toast.makeText(AppDriver.this, "OBSOLETE BUTTON LOL", Toast.LENGTH_SHORT).show();

        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = new byte[1];
            b[0] = defines.REQUEST_PRESSURE;
            mBTService.write(b);
            //Toast.makeText(AppDriver.this, "Turning motor ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }

    }

    public void ledButtonResponse(View view) {
        Button mButton = findViewById(R.id.ledButton);

        if(ledState == 0) {
            if (mBTService.getState() == BTService.STATE_CONNECTED) {
                byte[] b = new byte[1];
                b[0] = defines.LED_ON;
                mBTService.write(b);
                mButton.setText("LED Off");
                Toast.makeText(AppDriver.this, "Turning LED ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
            }
            ledState = 1;
        } else {
            if (mBTService.getState() == BTService.STATE_CONNECTED) {
                byte[] b = new byte[1];
                //b[0] = defines.LED_OFF;
                //mBTService.write(b);
                b[0] = defines.EXP_STATUS;
                mBTService.write(b);

                mButton.setText("LED On");
                Toast.makeText(AppDriver.this, "Turning LED OFF", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
            }
            ledState = 0;
        }
    }

    public void viewSnapshotResponse(View view) {
        //now take us to see result
        Bundle b = new Bundle();
        b.putDoubleArray("spectrumArray",spectrumArray);
        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    private void syncSettings() {
        //format a settings string and send it
        String settingsStr = defines.SETTINGS +
                mPrefs.getString("num_scans_entry","") + ";" +
                mPrefs.getString("scan_time_entry", "") + ";" +
                mPrefs.getString("integration_time_entry", "") + ";" +
                mPrefs.getString("boxcar_list", "") + ";" +
                mPrefs.getString("averaging_list", "");

        //toast with the final string
        //Toast.makeText(AppDriver.this, settingsStr, Toast.LENGTH_SHORT).show();

        //convert the string to a byte array, check if clear, then send
        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = settingsStr.getBytes();
            mBTService.write(b);
        } else {
            //Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }

    }

    public void snapshotButtonResponse(View view) {
        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = new byte[1];
            b[0] = defines.REQUEST_SPECTRA;
            mBTService.write(b);
            Toast.makeText(AppDriver.this, "Requesting snapshot", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }
    }

    public void startButtonResponse(View view) {
        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = new byte[1];
            b[0] = defines.EXP_START;
            mBTService.write(b);
            Toast.makeText(AppDriver.this, "trying to start exp", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }
    }

    public void calibrateButtonResponse(View view) {
        Intent i = new Intent(this, CalibrateActivity.class);
        startActivityForResult(i, CALIB_SCREEN_COMMANDS);
        //setContentView(R.layout.activity_calibrate);
    }

    public void settingsButtonResponse(View view) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
    }

    public void motorButtonResponse(View view) {
        Button mButton = findViewById(R.id.motorButton);

        if(motorState == 0) {
                if (mBTService.getState() == BTService.STATE_CONNECTED) {
                    byte[] b = new byte[1];
                    b[0] = defines.MOTOR_ON;
                    mBTService.write(b);
                    mButton.setText("Motor Off");
                    Toast.makeText(AppDriver.this, "Turning motor ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
                }
                motorState = 1;
            } else {
                if (mBTService.getState() == BTService.STATE_CONNECTED) {
                    byte[] b = new byte[1];
                    b[0] = defines.MOTOR_OFF;
                    mBTService.write(b);
                    mButton.setText("Motor On");
                    Toast.makeText(AppDriver.this, "Turning motor OFF", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
                }
                motorState = 0;
            }

    }

    public void streamButtonResponse(View view) {


    }

    public void connectButtonResponse(View view) {
        int found = 0;
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //String test = sharedPref.getString("boxcar_list", "");
        //Toast.makeText(AppDriver.this, "boxcar val is " + test, Toast.LENGTH_SHORT).show();

        //declare a set of devices and look through it for the pi
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceName.equals("raspberrypi")) {
                    BluetoothDevice device2 = mBluetoothAdapter.getRemoteDevice(deviceHardwareAddress);
                    // Attempt to connect to the device if we find it. Service handles all connection
                    //Toast.makeText(AppDriver.this, "trying " + deviceName , Toast.LENGTH_SHORT).show();
                    Toast.makeText(AppDriver.this, "trying " + deviceHardwareAddress + " before discovery", Toast.LENGTH_SHORT).show();
                    mBTService.connect(device2);
                    found = 1;
                    break;
                }
                //Toast.makeText(AppDriver.this, deviceHardwareAddress , Toast.LENGTH_SHORT).show();
            }
            //if we don't have the pi in our list of connected devices:
            if (found == 0) {
                Toast.makeText(AppDriver.this, "no paired device found. trying discovery.", Toast.LENGTH_SHORT).show();

                //this stuff ensures the app has permission to get the hardware descriptors from
                //remote devices. For some reason that's the same thing as location access.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

                //and finally start the discovery:
                mBluetoothAdapter.startDiscovery();
            }


        } else {
            //if we get here, the paired device list is 0 and we should still try discovery
            Toast.makeText(AppDriver.this, "failed to find device" , Toast.LENGTH_SHORT).show();
        }
    }

    //update the status text according to the bluetooth service state
    private void updateTextViews() {
        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            mStatusText.setText("Bluetooth Status: Connected to " + mDeviceName);
        } else if (mBTService.getState() == BTService.STATE_CONNECTING) {
            mStatusText.setText("Bluetooth Status: Connecting");
        } else if (mBTService.getState() == BTService.STATE_NONE) {
            mStatusText.setText("Bluetooth Status: Not Connected");
        }
    }






    //this thing is like our ambassador that we send to other activities to monitor and report back
    //returns and responses from other activities will end up here

    //msg.what --> What type of message is it?
    //msg.arg1 --> Arbitrary sub-type attached to message by sender
    //msg.obj  --> The object that came attached to the message

    //toast responses to be replaced with actual code as needed
    @SuppressLint("HandlerLeak")
    private final Handler mHandlerBT = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //if we get this message, we should update the display to reflect the state...
                case defines.MESSAGE_STATE_CHANGE:
                    updateTextViews();
                    //... and if we need to respond to specific state changes, do it here
                    switch (msg.arg1) {
                        case BTService.STATE_CONNECTED:
                            //Toast.makeText(AppDriver.this, "Moved to state CONNECTED" , Toast.LENGTH_SHORT).show();
                            //mStatusText.setText("Bluetooth Status: Connected");
                            break;

                        case BTService.STATE_CONNECTING:
                            //Toast.makeText(AppDriver.this, "Moved to state CONNECTING" , Toast.LENGTH_SHORT).show();
                            //mStatusText.setText("Bluetooth Status: Connecting");
                            break;

                        case BTService.STATE_NONE:
                            //Toast.makeText(AppDriver.this, "Moved to state NONE" , Toast.LENGTH_SHORT).show();
                            //mStatusText.setText("Bluetooth Status: Not Connected");
                            break;
                    }
                    break;

                //if we get this message, we have written something to the buffer.
                //note, everything we write across a socket is BYTES so we have to convert to strings
                case defines.MESSAGE_WRITE:
                    //display the thing we got:
                    /*
                    byte[] writeBuf = (byte[]) msg.obj;
                    String s1 = new String(writeBuf);
                    Toast.makeText(AppDriver.this, "sent " + s1 , Toast.LENGTH_LONG).show();
                    */
                    break;

                //if we get this message, we have received the pressure reading. So we update
                //the user.
                case defines.REQUEST_PRESSURE:
                    //String Str1 = new String((byte[])msg.obj);
                    //Toast.makeText(AppDriver.this, "got " + Str1 + " at requestPressure", Toast.LENGTH_LONG).show();

                    //the server has responded with a pressure reading.
                    //the first byte contained the identifier so we throw it away
                    byte[] pressureBytes = Arrays.copyOfRange((byte[]) msg.obj, 1, ((byte[]) msg.obj).length);
                    String pString = new String(pressureBytes);
                    //Toast.makeText(AppDriver.this, "formed " + pString + " at requestPressure", Toast.LENGTH_LONG).show();

                    //write the pressure string to the reading
                    mPressureText.setText("Current Pressure: " + pString);
                    //mPressureReading = Integer.parseInt(pString);
                    break;



                //if we get this message, msg.obj contains a spectrum reading.
                //the server will send 128 strings with 8 values each. format:
                //[command][offset];[val0];[val1]; .... ;[val7]
                case defines.REQUEST_SPECTRA:
                    //Toast.makeText(AppDriver.this, "made it to requestSpectra", Toast.LENGTH_LONG).show();
                    int index;
                    int k;

                    //throw away the identifier byte and parse out the intensities.
                    //place parsed floats into tokens[] as strings
                    byte[] tmpBytes = Arrays.copyOfRange((byte[]) msg.obj, 1, (((byte[]) msg.obj).length) + 1);
                    String tmpStr = new String(tmpBytes);
                    //Toast.makeText(AppDriver.this, "formed " + tmpStr + " at requestPressure", Toast.LENGTH_LONG).show();

                    String delim = "[;" + defines.REQUEST_SPECTRA + "]+";
                    String[] tokens = tmpStr.split(delim);
                    ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(tokens));

                    if(tokenList.size() >= 10) {
                        //Toast.makeText(AppDriver.this, "String[] size "+ tokens.length +" || ArrayList size " + tokenList.size(), Toast.LENGTH_LONG).show();

                        //if we get here, incoming strings got squished together, so we need to remove
                        //the index from the extra strings to place them as normal in the loop later.

                        //loop backwards to get multiples of 9 out:
                        for (int i = tokenList.size()-9; i >= 1; i-= 9) {
                            tokenList.remove(i);
                        }

                        //Toast.makeText(AppDriver.this, tokenList.toString(), Toast.LENGTH_LONG).show();

                    }

                    //int offset = Integer.parseInt(tokens[0]);
                    int offset = Integer.parseInt(tokenList.get(0));

                    //Toast.makeText(AppDriver.this, "The offset is " + offset, Toast.LENGTH_LONG).show();


                    //now iterate through the tokens, grabbing floats.
                    for (index = 0; index < tokenList.size() -1; index++) {
                        //spectrumArray[index + offset] = Float.parseFloat(tokens[index + 1]);
                        //if(index+offset < spectrumArray.length)
                            spectrumArray[index + offset] = Float.parseFloat(tokenList.get(index + 1));

                        //spectrumArray[index + offset] = 5; //Float.parseFloat(tokens[index + 1]);
                        //Toast.makeText(AppDriver.this, "Added" + spectrumArray[index+offset] + " at index " + (index+offset), Toast.LENGTH_LONG).show();
                    }

                    //if we hit this condition, we have received a complete spectrum.
                    //the extra -1 is because the loop above left an extra increment with index++
                    if (offset + index - 1 == defines.NUM_WAVELENGTHS - 1) {

                        Toast.makeText(AppDriver.this, "last spectra index reached", Toast.LENGTH_LONG).show();
                        //now bundle the array and send it to result screen
                        Bundle b = new Bundle();
                        b.putDoubleArray("spectrumArray",spectrumArray);
                        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    break;

                //if we get this message, we have received something
                case defines.MESSAGE_READ:
                    //display the thing we received

                    byte[] readBuf = (byte[]) msg.obj;
                    String s2 = new String(readBuf);
                    Toast.makeText(AppDriver.this, "got " + s2 , Toast.LENGTH_LONG).show();

                    break;

                case defines.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mDeviceName = msg.getData().getString(defines.DEVICE_NAME);
                    //Toast.makeText(getApplicationContext(), "connected to " + str, Toast.LENGTH_SHORT).show();
                    updateTextViews();
                    break;

                //if we get this message, the service wants us to display a toast
                case defines.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(defines.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
