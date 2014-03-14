package com.evebit.HandOnEastWind;

import cn.jpush.android.api.JPushInterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReciever extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		    Bundle bundle = intent.getExtras();	 
	         
	        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
	             
	        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
	            System.out.println("�յ����Զ�����Ϣ����Ϣ�����ǣ�" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
	           
	        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
	            System.out.println("�յ���֪ͨ");
	        
	        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
	            System.out.println("�û��������֪ͨ");
	          
	            Intent i = new Intent(context, TabMainActivity.class);  
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i);
	   
	        } else {
	            Log.d(TAG, "Unhandled intent - " + intent.getAction());
	        }
	}

}
