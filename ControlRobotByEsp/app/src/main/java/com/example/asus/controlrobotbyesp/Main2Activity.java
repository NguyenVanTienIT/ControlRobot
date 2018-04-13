package com.example.asus.controlrobotbyesp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {
    //UI Elements
    Button btnTop, btnLeft, btnRight, btnDown,btnStop;
    ImageView imageView;
    FrameLayout frameLayout;
    FrameLayout frameLayout2;

    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String wifiModuleUrl;
    public static String ESPIP = "0";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        wifiModuleIP = intent.getStringExtra("IP");
        wifiModulePort = intent.getIntExtra("PORT", 100);
        wifiModuleUrl = intent.getStringExtra("CAMERA");



        btnTop = (Button) findViewById(R.id.btnLED1);
        btnLeft = (Button) findViewById(R.id.btnLED2);
        btnRight = (Button) findViewById(R.id.btnLED3);
        btnDown = (Button) findViewById(R.id.btnLED4);
        btnStop = (Button) findViewById(R.id.btnStop);

        imageView = (ImageView) findViewById(R.id.expanLayout);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout2 = findViewById(R.id.frameLayout2);

// view cameraip

        webView=(WebView) findViewById(R.id.WebView1 );
        webView.setWebViewClient(new MyBroswer());
        String url = "http://192.168.43.197:8888/";
       // String url = "http://"+wifiModuleUrl+"/";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);



        // xet su kien nhan giu nut


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getVisibility() == View.VISIBLE){
                    imageView.setImageResource(R.drawable.ic_expand_less);
                    frameLayout2.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    return;
                }
                if(frameLayout.getVisibility() == View.GONE){
                    imageView.setImageResource(R.drawable.ic_expand_more);
                    frameLayout2.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ESPIP = "0";
                Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                turnLED1.execute();
            }
        });


        btnTop.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                try {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ESPIP = "1";
                        Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                        turnLED1.execute().get();
                        btnTop.setBackgroundColor(Color.RED);
                        Log.d("TEST", "Xe tiến về trước");
                        return false;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        ESPIP = "0";
                        Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                        turnLED1.execute().get();
                        btnTop.setBackgroundColor(Color.GRAY);
                        Log.d("TEST", "Xe đã dừng");
                        return true;
                    }
                    return false;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ESPIP = "2";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    turnLED1.execute();
                    btnLeft.setBackgroundColor(Color.RED);
                    Log.d("TEST", "Xe rẽ trái");
                    return false;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    ESPIP = "0";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    turnLED1.execute();
                    btnLeft.setBackgroundColor(Color.GRAY);
                    Log.d("TEST", "Xe đã dừng");
                    return  true;
                }
                return false;
            }
        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ESPIP = "3";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    try {
                        turnLED1.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    btnRight.setBackgroundColor(Color.RED);
                    Log.d("TEST", "Xe đang rẽ trái");
                    return false;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    ESPIP = "0";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    turnLED1.execute();
                    btnRight.setBackgroundColor(Color.GRAY);
                    Log.d("TEST", "Xe đã dừng");
                    return  true;
                }
                return false;
            }
        });

        btnDown.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ESPIP = "4";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    turnLED1.execute();
                    btnDown.setBackgroundColor(Color.RED);
                    Log.d("TEST", "Xe đang lùi");
                    return false;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    ESPIP = "0";
                    Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                    turnLED1.execute();
                    btnDown.setBackgroundColor(Color.GRAY);
                    Log.d("TEST", "Xe đã dừng");
                    return  true;
                }

                return false;
            }
        });
    }

    //helper class
    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;
        DataOutputStream dataOutputStream;
        @Override
        protected Void doInBackground(Void... params) {
            try {

                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIP);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);

                if(socket != null && socket.isConnected()) {
                    Log.d("LOI","Da xay ra loi");
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeBytes(ESPIP);
                    dataOutputStream.close();
                    socket.close();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Loi",Toast.LENGTH_SHORT).show();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
                return null;
            } catch (Exception e) {
                //e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
                return null;
            }

            try {
                dataOutputStream.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }



    public class ServerListenner extends AsyncTask<Void, String, Void>{
        Socket socket;
        BufferedReader in;
        @Override
        protected Void doInBackground(Void... params) {

            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(MainActivity.wifiModuleIP);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               // nhietDo_doAm = in.readLine();
               // while (nhietDo_doAm != null){
               //     publishProgress(nhietDo_doAm);
                //    nhietDo_doAm = in.readLine();
               // }
               // Log.d("NhietDo",nhietDo_doAm);
                Log.d("NhietDo","Da den day roi ban oi");
                in.close();
                socket.close();
                //publishProgress(nhietDo_doAm);
                return  null;
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
            }

            try {
                in.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Loi",Toast.LENGTH_SHORT).show();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
           // if(nhietDo_doAm != null)  txtNhietdo.setText(nhietDo_doAm);
        }
    }

    private class MyBroswer extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }




}
