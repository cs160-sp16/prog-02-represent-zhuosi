package com.cs260.zhuosi.zsrepresent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private final String detailId = "/DETAILID";
    private final String Shacksignal = "/SHAKE";
    private static final String NAME_LIST = "/name_list";
    private static final String PARTY_LIST = "/party_list";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());

        if( messageEvent.getPath().equalsIgnoreCase( detailId ) ) {
            String message = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Intent intent = new Intent(this, DetailedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra(detailId, message);

            System.out.println("About to start the detailed page for representative at position " + message);
            startActivity(intent);
        }else if( messageEvent.getPath().equalsIgnoreCase( Shacksignal )){
            Intent intent = new Intent(this, CongressionalActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            System.out.println("about to start the shacked view");
            startActivity(intent);
            System.out.println("Started");

            String name_list = "Apple;Banana;Orange";
            String party_list = "d;r;i";

            intent = new Intent(this, PhoneToWatchService.class);
            intent.putExtra(NAME_LIST, name_list);
            intent.putExtra(PARTY_LIST, party_list);
            startService(intent);

        }
    }
}
