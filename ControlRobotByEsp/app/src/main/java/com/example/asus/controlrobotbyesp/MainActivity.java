package com.example.asus.controlrobotbyesp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Button btnConnection;
    EditText txtAddress,txtPort,txtCamera;
    Socket socket;

    Socket myAppSocket = null;
    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String wifiCamera = "";
    public static String ESPIP = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnConnection = (Button) findViewById(R.id.connection);

        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtPort = (EditText) findViewById(R.id.txtPort);
        txtCamera = (EditText) findViewById(R.id.txtCamera);


        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiModuleIP = txtAddress.getText().toString();
                wifiModulePort = Integer.parseInt(txtPort.getText().toString());
                wifiCamera = txtCamera.getText().toString();

                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra("IP",wifiModuleIP);
                        intent.putExtra("PORT",wifiModulePort);
                        intent.putExtra("CAMERA",wifiCamera);
                        startActivity(intent);

            }
        });
    }
}
