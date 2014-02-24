package com.evebit.HandOnEastWind;


import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		Button buttonClose = (Button)findViewById(R.id.sound_close);
		Button buttonOpen = (Button)findViewById(R.id.sound_open);
		
		
		buttonClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.statusBarDrawable = R.drawable.jpush_notification_icon;
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ�Զ���ʧ	
				builder.notificationDefaults = Notification.DEFAULT_LIGHTS ;  // ����Ϊ�������𶯶�Ҫ
				
				JPushInterface.setDefaultPushNotificationBuilder(builder);
			}
		});
		
		buttonOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.statusBarDrawable = R.drawable.jpush_notification_icon;
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ�Զ���ʧ	
				builder.notificationDefaults = Notification.DEFAULT_SOUND ;  // ����Ϊ�������𶯶�Ҫ
				//&& Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND
				//JPushInterface.setPushNotificationBuilder(1, builder);
				JPushInterface.setDefaultPushNotificationBuilder(builder);
			}
		});
		
		
	}

	/**
	 * 屏蔽返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 屏蔽菜单键
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
