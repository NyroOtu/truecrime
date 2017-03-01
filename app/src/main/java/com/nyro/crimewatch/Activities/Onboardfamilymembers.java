package com.nyro.crimewatch.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nyro.crimewatch.Models.User;
import com.nyro.crimewatch.R;
import com.nyro.crimewatch.Volley.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Onboardfamilymembers extends AppCompatActivity {



    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;






    ViewFlipper Flipper;

    EditText namefield;
    EditText phonenumberfield;
    EditText keenOneName;
    EditText keenOnePhone;
    EditText keenTwoName;
    EditText keenTwoPhone;
    EditText keenThreeName;
    EditText keenThreePhone;
    EditText keenFourName;
    EditText keenFourPhone;

    Button next,prev;


    User userTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardfamilymembers);

        userTemp = User.load(User.class,1);

        if(userTemp != null){

            Intent intent = new Intent(Onboardfamilymembers.this,MainActivity.class);
            startActivity(intent);
        }



        InitViews();
        //get ViewFlipper from xml Layout
        Flipper =(ViewFlipper)findViewById(R.id.viewFlipper);
        //get button from xml layout
        next =(Button)findViewById(R.id.next);
        prev =(Button)findViewById(R.id.prev);
        //when next is cliceked
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                if (next.getText().equals("SAVE")){




                    VolleyRequest();

/*

*/



                }else{


                    Flipper.showNext();
                    if(Flipper.getDisplayedChild() == 0){
                        prev.setVisibility(View.INVISIBLE);
                    }else{
                        prev.setVisibility(View.VISIBLE);
                    }


                    if(Flipper.getDisplayedChild() == 2){

                        next.setText("SAVE");
                    }else{
                        next.setText("Next");
                    }
                }


            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flipper.showPrevious();
                if(Flipper.getDisplayedChild() == 0){

                    prev.setVisibility(View.INVISIBLE);

                }else{

                    prev.setVisibility(View.VISIBLE);
                }

                if(Flipper.getDisplayedChild() == 2){

                    next.setText("SAVE");
                }else{
                    next.setText("Next");
                }
            }
        });





    }

    public void InitViews(){



        namefield = (EditText)findViewById(R.id.namefield);

        phonenumberfield = (EditText) findViewById(R.id.phonenumberfield);
        keenOneName = (EditText) findViewById(R.id.fammemberName1);
        keenOnePhone = (EditText) findViewById(R.id.phone1value);
        keenTwoName = (EditText) findViewById(R.id.name2value);
        keenTwoPhone = (EditText) findViewById(R.id.phone2value);
        keenThreeName = (EditText) findViewById(R.id.fammemberName3);
        keenThreePhone = (EditText) findViewById(R.id.phone3value);
        keenFourName = (EditText) findViewById(R.id.name4value);
        keenFourPhone = (EditText) findViewById(R.id.phone4value);

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

        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,"SomeURL",new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                User user = new User();
                user.setName(namefield.getText().toString());
                user.setPhone(phonenumberfield.getText().toString());

                user.setKeenOne(keenOneName.getText().toString());
                user.setKeenOnePhone(keenOnePhone.getText().toString());

                user.setKeenTwo(keenTwoName.getText().toString());
                user.setKeenTwoPhone(keenTwoPhone.getText().toString());

                user.setKeenThree(keenThreeName.getText().toString());
                user.setKeenThreePhone(keenThreePhone.getText().toString());


                user.setKeenFour(keenFourName.getText().toString());
                user.setKeenFourPhone(keenFourPhone.getText().toString());


                Long id =   user.save();

                Intent intent = new Intent(Onboardfamilymembers.this,MainActivity.class);
                startActivity(intent);

                System.out.println("User is saved with id  :"  + id);
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
}
