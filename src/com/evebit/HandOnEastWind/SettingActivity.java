package com.evebit.HandOnEastWind;


import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingActivity extends Activity {

	  ImageView soundOpenImage ;
	  ImageView soundCloseImage ;
	  
	  ImageView pushOpenImage ; //默认显示推送开启，点击后推送关闭
	  ImageView pushCloseImage ;
	  
	  ImageView imageOpen ; //默认显示图片开启，点击后无图模式
	  ImageView imageClose ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		soundOpenImage = (ImageView)findViewById(R.id.setting_sound_open);
		soundCloseImage = (ImageView)findViewById(R.id.setting_sound_close);
		
		pushOpenImage = (ImageView)findViewById(R.id.setting_push_open);
		pushCloseImage = (ImageView)findViewById(R.id.setting_push_close);
		
		imageOpen = (ImageView)findViewById(R.id.setting_image_open);
		imageClose = (ImageView)findViewById(R.id.setting_image_close);
		
		soundOpenImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				soundOpenImage.setVisibility(View.GONE);
				soundCloseImage.setVisibility(View.VISIBLE);
				
				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.statusBarDrawable = R.drawable.jpush_notification_icon;
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ�Զ���ʧ	
				builder.notificationDefaults = Notification.DEFAULT_LIGHTS ;  // ����Ϊ�������𶯶�Ҫ
				
				JPushInterface.setDefaultPushNotificationBuilder(builder);
			}
		});
		
		soundCloseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				soundOpenImage.setVisibility(View.VISIBLE);
				soundCloseImage.setVisibility(View.GONE);
				
				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.statusBarDrawable = R.drawable.jpush_notification_icon;
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ�Զ���ʧ	
				builder.notificationDefaults = Notification.DEFAULT_SOUND ;  // ����Ϊ�������𶯶�Ҫ
				//&& Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND
				//JPushInterface.setPushNotificationBuilder(1, builder);
				JPushInterface.setDefaultPushNotificationBuilder(builder);
			}
		});
		
		
		pushOpenImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.GONE);
				pushCloseImage.setVisibility(View.VISIBLE);
			    JPushInterface.stopPush(getApplicationContext());
			}
	    });
		
		pushCloseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.VISIBLE);
				pushCloseImage.setVisibility(View.GONE);
			    JPushInterface.resumePush(getApplicationContext());
			}
	    });
		
		imageOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.VISIBLE);
				imageOpen.setVisibility(View.GONE);
			 
			}
	    });
		
		imageClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.GONE);
				imageOpen.setVisibility(View.VISIBLE);
			 
			}
	    });
		
	}

}
