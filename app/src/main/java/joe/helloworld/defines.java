package joe.helloworld;

/**
 * Created by Joseph on 5/26/2018.
 * Holds constants, message types, and commands
 */

public class defines {
    // BTService outgoing messages
    static final int MESSAGE_STATE_CHANGE = 1;
    static final int MESSAGE_READ = 2;
    static final int MESSAGE_WRITE = 3;
    static final int MESSAGE_DEVICE_NAME = 4;
    static final int MESSAGE_TOAST = 5;

    // Key names received from the BTService Handler
    static final String DEVICE_NAME = "device_name";
    static final String TOAST = "toast";

    // Spectrometer commands. Identical to the enum on the pi side
    static final char MOTOR_ON = '1';
    static final char MOTOR_OFF = '2';
    static final char REQUEST_PRESSURE = '3';
    static final char REQUEST_SPECTRA = '4';
    static final char SETTINGS = '5';
    static final char QUIT = '6';

    //size of array we expect for an entire spectrum reading.
    //1024 doubles * 8 bytes/double (as string) + 1 for ;
    static final int SPECTRUM_ARRAY_SIZE = 1024;
    static final int NUM_WAVELENGTHS = 1024;

    static final double[] wavelengthArray = {
            //1024 wavelengths go here
    };



}
