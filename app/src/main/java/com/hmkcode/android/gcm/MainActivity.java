package com.hmkcode.android.gcm;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hmkcode.android.gcm.exception.OperationFailureException;
import com.hmkcode.android.gcm.exception.RegistrationException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.notifyme.currency.model.Device;
import org.notifyme.currency.model.Subscription;

public class MainActivity extends Activity implements OnClickListener {

    Button btnRegId;
    EditText etRegId;
	GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "586316759371";
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnRegId = (Button) findViewById(R.id.btnGetRegId);
        etRegId = (EditText) findViewById(R.id.etRegId);

        btnRegId.setOnClickListener(this);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Installation.registerOnFirstLaunch(MainActivity.this);
                } catch (RegistrationException e) {
                    Log.e("Error: ", "Failed to send installation id", e);
                }
                return null;
                }
        }.execute(null, null, null);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM",  msg);


                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                new AsyncTask<Void, Void, List<Subscription>>() {
                    @Override
                    protected List<Subscription> doInBackground(Void... params) {
                        try {
                            return new Server(new HttpClient()).getSubscriptions(new Device(Installation.id(MainActivity.this)));
                        } catch (OperationFailureException e) {
                            // TODO: report an error to UI
                            e.printStackTrace();
                        }

                        return new LinkedList<Subscription>();
                    }

                    @Override
                    protected void onPostExecute(List<Subscription> subscriptions){
                        //TODO: fill UI with subscriptions
                        for (Subscription s : subscriptions){
                            Log.i("Subscription: ", s.toJSONString());
                        }
                    }
                }.execute(null, null, null);
            }
        }.execute(null, null, null);


    }
    public void getRegId(){
    	new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM",  msg);

                   
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                etRegId.setText(msg + "\n");
            }
        }.execute(null, null, null);
    }
	@Override
	public void onClick(View v) {
		getRegId();
	}
    
}
