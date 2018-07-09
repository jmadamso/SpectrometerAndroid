package joe.helloworld;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.*;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Set;


public class AppDriver extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1337;

    private SharedPreferences mPrefs;
    private BluetoothAdapter mBluetoothAdapter;
    private static String mDeviceName;

    //making this static keeps connections when changing orientation or something else
    private static BTService mBTService = null;

    private static double[] spectrumArray = new double[1024];
    private int mPressureReading;

    //the text displays at main page
    private TextView mPressureText;
    private TextView mStatusText;

    //this thing is like our soldier that we send to other activities to monitor and report back
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
                    //the server has responded with a pressure reading.
                    //the first byte contained the identifier so we throw it away
                    byte[] pressureBytes = Arrays.copyOfRange((byte[]) msg.obj, 1, ((byte[]) msg.obj).length);
                    String pString = new String(pressureBytes);
                    mPressureText.setText("Current Pressure: " + pString);
                    mPressureReading = Integer.parseInt(pString);
                    break;

                //if we get this message, we can expect one intensity reading.
                //the server will send 1024 of these upon receiving the command.
                //PROTOCOL: [command][index];[intensity]
                case defines.REQUEST_SPECTRA:

                    //throw away the identifier byte and parse out the index and intensity
                    byte[] tmpBytes = Arrays.copyOfRange((byte[]) msg.obj, 1, (((byte[]) msg.obj).length) + 1);
                    String tmpStr = new String(tmpBytes);
                    String delim = "[;]+";
                    String[] tokens = tmpStr.split(delim);
                    int index = Integer.parseInt(tokens[0]);
                    spectrumArray[index] = Float.parseFloat(tokens[1]);
                    if(index == 1023) {
                        //STATIC INT SPECTRUMREADY = TRUE
                        //UPDATE GRAPH?
                    } else {
                        //SPECTRUMREADY = FALSE;
                    }
                    break;

                //if we get this message, we have received something
                case defines.MESSAGE_READ:
                    //display the thing we received
                    /*
                    byte[] readBuf = (byte[]) msg.obj;
                    String s2 = new String(readBuf);
                    Toast.makeText(AppDriver.this, "got " + s2 , Toast.LENGTH_LONG).show();
                    */
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (mBTService == null) {
            mBTService = new BTService(this, mHandlerBT);
        }

        mBTService.setHandler(mHandlerBT);
        setContentView(R.layout.activity_main);
        mPressureText = findViewById(R.id.pressureText);
        mStatusText = findViewById(R.id.statusText);

        updateTextViews();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(AppDriver.this, "You don't seem to have bluetooth!" , Toast.LENGTH_SHORT).show();
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

    public void intButtonResponse(View view) {
        //Toast.makeText(AppDriver.this, "OBSOLETE BUTTON LOL", Toast.LENGTH_SHORT).show();
        String intStr = defines.INTEGRATION_TIME + mPrefs.getString("integration_time_entry", "");
        //Toast.makeText(AppDriver.this, intStr, Toast.LENGTH_SHORT).show();

        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = intStr.getBytes();
            mBTService.write(b);
        } else {
            Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }

    }

    public void settingsButtonResponse(View view) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
    }

    public void motorOnButtonResponse(View view) {
            if (mBTService.getState() == BTService.STATE_CONNECTED) {
                byte[] b = new byte[1];
                b[0] = defines.MOTOR_ON;
                mBTService.write(b);
                Toast.makeText(AppDriver.this, "Turning motor ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
            }

    }

    public void motorOffButtonResponse(View view) {
        if (mBTService.getState() == BTService.STATE_CONNECTED) {
            byte[] b = new byte[1];
            b[0] = defines.MOTOR_OFF;
            mBTService.write(b);
            Toast.makeText(AppDriver.this, "Turning motor OFF", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppDriver.this, "Bluetooth not connected", Toast.LENGTH_SHORT).show();
        }

    }

    public void startButtonResponse(View view) {

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
                    Toast.makeText(AppDriver.this, "trying " + deviceName , Toast.LENGTH_SHORT).show();
                    mBTService.connect(device2);
                    break;
                }
                //Toast.makeText(AppDriver.this, deviceHardwareAddress , Toast.LENGTH_SHORT).show();
            }
        } else {
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

    //called by anything we start with startActivityForResult with the original request code.
    //doesn't do much for us but we could add an activity to return a connected device later
    //down the road. Maybe an activity for discovering the spectrometer box
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            //this guy gets called when the bluetooth request dialog
            if (requestCode == REQUEST_ENABLE_BT  && resultCode  == RESULT_OK) {
                //Toast.makeText(AppDriver.this, "Bluetooth enabled after activity return",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(AppDriver.this, ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }


}