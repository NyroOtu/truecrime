package com.nyro.crimewatch.Activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nyro.crimewatch.Models.User;
import com.nyro.crimewatch.R;
import com.nyro.crimewatch.Utilities.GPSTracker;
import com.nyro.crimewatch.Utilities.ShakeDetector;
import com.nyro.crimewatch.Volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//extends Activity implements SensorListener

public class MainActivity extends AppCompatActivity {


    //Sensor Variables

    GPSTracker gpsTracker;
    Button Helpbutton;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//This line of Code gets the first role in your user table

        Helpbutton = (Button) findViewById(R.id.Helpbutton);
        Helpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = User.load(User.class,1);





               if(haveNetworkConnection()){

                   VolleyRequest();
               }else {
                   if(gpsTracker.canGetLocation()) {

                       Double latitude =  gpsTracker.getLatitude();

                       Double longitude =  gpsTracker.getLongitude();

                       sendSMS("0501149284","latitude " + latitude  + "longitude " + longitude);

                       Toast.makeText(MainActivity.this,"latitude " + latitude  + "longitude " + longitude,Toast.LENGTH_LONG).show();

                   }else{


                       gpsTracker.showSettingsAlert();

                   }

               }


            }
        });

        gpsTracker = new GPSTracker(MainActivity.this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {




               if(gpsTracker.canGetLocation()) {

                   Double latitude =  gpsTracker.getLatitude();

                   Double longitude =  gpsTracker.getLongitude();

                   sendSMS("0501149284","latitude " + latitude  + "longitude " + longitude);

                   Toast.makeText(MainActivity.this,"latitude " + latitude  + "longitude " + longitude,Toast.LENGTH_LONG).show();

               }else{


                   gpsTracker.showSettingsAlert();

               }


            }
        });


    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    private void  sendSMS(String mobNo, String message) {
        final ProgressDialog pDialog  = new ProgressDialog(this);
        pDialog.setMessage("Sending Request..");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();


        String smsSent = "SMS_SENT";
        String smsDelivered = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(smsSent), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(smsDelivered), 0);

        // Receiver for Sent SMS.
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                pDialog.dismiss();

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:





                        //  Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:


                        // Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        // Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        // Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                        //  Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsSent));

        // Receiver for Delivered SMS.
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:

                        pDialog.dismiss();


                        Toast.makeText(getBaseContext(), "SMS Delivered ", Toast.LENGTH_SHORT).show();




                        break;
                    case Activity.RESULT_CANCELED:
                        pDialog.dismiss();
                        Toast.makeText(getBaseContext(), "SMS Cancelled ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsDelivered));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mobNo, null, message, sentPI, deliveredPI);
    }
    private  void VolleyRequest(){

        final  ProgressDialog pDialog  = new ProgressDialog(this);
        pDialog.setMessage("Please  wait...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("aparam","");
        params.put("aparam","");
        params.put("aparam", "");

        // Message.messageShort(MyApplication.getAppContext(), params.toString());

        volleySingleton=VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,"SomeURL",new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        VolleyLog.d("Response Login",response.toString());
                        pDialog.hide();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                //  controlHelpers.AnAler,"Connection Failed !","Sorry Try Again").show();

                VolleyLog.d("Error Login"," "+error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "Ozinbo");
                return headers;
            }
        };

        // Message.messageShort(getApplicationContext(),  request.toString());
        requestQueue.add(request);

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}

