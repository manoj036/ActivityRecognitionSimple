package com.example.mbreath.activityrecognitionsimple;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by mbreath on 26/08/17.
 */

public class ActivityRecognizedService extends IntentService {

    private static final String TAG = "ActivityRecognizedServi";
    public Activity activity;
    public static String INTENTSERVICE_ACTION = "ActivityData";

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    private int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0;

    public ActivityRecognizedService(String name){
        super(name);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {

        for( DetectedActivity activity : probableActivities ) {
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( "ActivityRecogition", "In Vehicle: " + activity.getConfidence() );
                    a = activity.getConfidence();
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e( "ActivityRecogition", "On Bicycle: " + activity.getConfidence() );
                    b = activity.getConfidence();
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( "ActivityRecogition", "On Foot: " + activity.getConfidence() );
                    c = activity.getConfidence();
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( "ActivityRecogition", "Running: " + activity.getConfidence() );
                    d = activity.getConfidence();
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( "ActivityRecogition", "Still: " + activity.getConfidence() );
                    e = activity.getConfidence();
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e( "ActivityRecogition", "Tilting: " + activity.getConfidence() );
                    f = activity.getConfidence();
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e( "ActivityRecogition", "Walking: " + activity.getConfidence() );
//                    if( activity.getConfidence() >= 5 ) {
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//                        builder.setContentText( "Are you walking?" );
//                        builder.setSmallIcon( R.mipmap.ic_launcher );
//                        builder.setContentTitle( getString( R.string.app_name ) );
//                        NotificationManagerCompat.from(this).notify(0, builder.build());
//                    }
                    g = activity.getConfidence();
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( "ActivityRecogition", "Unknown: " + activity.getConfidence() );
                    h = activity.getConfidence();
                    break;
                }
            }
        }
        Intent intent = new Intent();
        intent.setAction(ActivityRecognizedService.INTENTSERVICE_ACTION);
        int array[]={a,b,c,d,e,f,g,h};
        int max=0,maxindex=0;
        for(int j=0;j<8;j++){
            if (array[j]>max){
                max=array[j];
                maxindex=j;
            }
        }

        intent.putExtra("a",a);
        intent.putExtra("b",b);
        intent.putExtra("c",c);
        intent.putExtra("d",d);
        intent.putExtra("e",e);
        intent.putExtra("f",f);
        intent.putExtra("g",g);
        intent.putExtra("h",h);
        intent.putExtra("i",maxindex);
        Log.d(TAG, "handleDetectedActivities: i="+maxindex);
        sendBroadcast(intent);

    }
}
