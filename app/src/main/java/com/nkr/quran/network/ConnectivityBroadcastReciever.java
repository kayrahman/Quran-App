package com.nkr.quran.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ConnectivityBroadcastReciever extends BroadcastReceiver {

    OnConnectivityChangeListener mOnConnectivityChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        mOnConnectivityChangeListener = (OnConnectivityChangeListener)context;

        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){

            boolean noConnectivity = intent.getBooleanExtra(
              ConnectivityManager.EXTRA_NO_CONNECTIVITY,false
            );

            if(noConnectivity) {

               mOnConnectivityChangeListener.onConnectivityChangeListener(true);

                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
            }else{

                mOnConnectivityChangeListener.onConnectivityChangeListener(false);

              //  Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();

            }

        }

    }
}
