package com.example.mbreath.activityrecognitionsimple;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "MainActivity";
    public GoogleApiClient mApiClient;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.id1);
        t2 = (TextView) findViewById(R.id.id2);
        t3 = (TextView) findViewById(R.id.id3);
        t4 = (TextView) findViewById(R.id.id4);
        t5 = (TextView) findViewById(R.id.id5);
        t6 = (TextView) findViewById(R.id.id6);
        t7 = (TextView) findViewById(R.id.id7);
        t8 = (TextView) findViewById(R.id.id8);
        t9 = (TextView) findViewById(R.id.id9);
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActivityRecognizedService.INTENTSERVICE_ACTION);
        this.registerReceiver(myBroadcastReceiver,intentFilter);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognizedService.class);
        PendingIntent pendingIntent =  PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 3000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            t1.setText("In vehicle    : "+intent.getIntExtra("a",0));
            t2.setText("On bicycle  : "+intent.getIntExtra("b",0));
            t3.setText("On foot       : "+intent.getIntExtra("c",0));
            t4.setText("Running      : "+intent.getIntExtra("d",0));
            t5.setText("Still             : "+intent.getIntExtra("e",0));
            t6.setText("Tilting         : "+intent.getIntExtra("f",0));
            t7.setText("Walking     : "+intent.getIntExtra("g",0));
            t8.setText("Unknown  : "+intent.getIntExtra("h",0));
            String s[]={"In Vehicle","On bicycle","on Foot","Running","Still","Tilting","Walking","Unknown"};
            t9.setText("Result       :"+s[intent.getIntExtra("i",0)]);
        }
    }
}
