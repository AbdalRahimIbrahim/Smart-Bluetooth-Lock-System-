package com.example.smartlock;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnConnect, btnUnlock, btnLock;
    TextView statusText;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice device;
    OutputStream outputStream;

    // UUID الخاص بخدمة البلوتوث (Serial Port Profile)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // العنوان MAC لجهاز ESP32 (غيّرو حسب جهازك)
    private static final String DEVICE_ADDRESS = "00:22:03:01:92:2A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.btnConnect);
        btnUnlock = findViewById(R.id.btnUnlock);
        btnLock = findViewById(R.id.btnLock);
        statusText = findViewById(R.id.statusText);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btnConnect.setOnClickListener(v -> connectToDevice());

        btnUnlock.setOnClickListener(v -> sendCommand("U")); // Unlock
        btnLock.setOnClickListener(v -> sendCommand("L"));   // Lock
    }

    private void connectToDevice() {
        device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();

            statusText.setText("Connected to Smart Lock");
            btnUnlock.setEnabled(true);
            btnLock.setEnabled(true);

        } catch (IOException e) {
            statusText.setText("Connection Failed");
            e.printStackTrace();
        }
    }

    private void sendCommand(String cmd) {
        if (outputStream != null) {
            try {
                outputStream.write(cmd.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
