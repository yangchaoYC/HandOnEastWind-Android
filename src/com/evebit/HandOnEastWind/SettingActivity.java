package com.evebit.HandOnEastWind;


import java.util.List;

import com.evebit.DB.DBSize;
import com.evebit.DB.DBTime;

import net.tsz.afinal.FinalDb;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SettingActivity extends Activity implements android.view.View.OnClickListener{

	  ImageView soundOpenImage ;
	  ImageView soundCloseImage ;
	  
	  ImageView pushOpenImage ; //默认显示推送开启，点击后推送关闭
	  ImageView pushCloseImage ;
	  
	  ImageView imageOpen ; //默认显示图片开启，点击后无图模式
	  ImageView imageClose ;
	  
	  private Button size1Button,size2Button,size3Button;
	  private String Size = null;
	  private FinalDb db = null;
	
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
		db = FinalDb.create(this);
		
		
		 size1Button = (Button)findViewById(R.id.Setting_button_size1);
		 size2Button = (Button)findViewById(R.id.Setting_button_size2);
		 size3Button = (Button)findViewById(R.id.Setting_button_size3);


		 size1Button.setOnClickListener(this);
		 size2Button.setOnClickListener(this);
		 size3Button.setOnClickListener(this);
		 getSize();
		 push();
		 sound();
		 image();
		
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
				
				CheckSound("true");
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
				
				
				CheckSound("flase");
			}
		});
		
		
		
		
		
		pushOpenImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.GONE);
				pushCloseImage.setVisibility(View.VISIBLE);
				JPushInterface.resumePush(getApplicationContext());
				Checkpush("true");
			}
	    });
		
		pushCloseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.VISIBLE);
				pushCloseImage.setVisibility(View.GONE);
				JPushInterface.stopPush(getApplicationContext());
				Checkpush("flase");
			}
	    });
		
		imageOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.VISIBLE);
				imageOpen.setVisibility(View.GONE);
				CheckImage("true");
			}
	    });
		
		imageClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.GONE);
				imageOpen.setVisibility(View.VISIBLE);
				CheckImage("flase");
			}
	    });
		
	}

	

	
	
	private void image()
	{
		String condition ="nid='" + "image"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			imageThread("flase");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				imageOpen.setVisibility(View.GONE);
				imageClose.setVisibility(View.VISIBLE);
			}
			else {
				imageOpen.setVisibility(View.GONE);
				imageClose.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void CheckImage(String check)
	{
		String condition ="nid='" + "image"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			imageThread("flase");
		}
		else {
			imageThread(check);
		}
	}
	
	
	private void imageThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "image"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("image");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	
	

	private void sound()
	{
		String condition ="nid='" + "sound"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("true");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				soundOpenImage.setVisibility(View.GONE);
				soundCloseImage.setVisibility(View.VISIBLE);
			}
			else {
				soundOpenImage.setVisibility(View.VISIBLE);
				soundCloseImage.setVisibility(View.GONE);
			}
		}
	}
	
	private void CheckSound(String check)
	{
		String condition ="nid='" + "sound"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			soundThread("flase");
		}
		else {
			soundThread(check);
		}
	}
	
	
	private void soundThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "sound"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("sound");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	
	
	
	
	
	
	
	private void push()
	{
		String condition ="nid='" + "push"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("true");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				pushOpenImage.setVisibility(View.GONE);
				pushCloseImage.setVisibility(View.VISIBLE);
			}
			else {
				pushOpenImage.setVisibility(View.VISIBLE);
				pushCloseImage.setVisibility(View.GONE);
			}
		}
	}
	
	private void Checkpush(String check)
	{
		String condition ="nid='" + "push"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("flase");
		}
		else {
			pushThread(check);
		}
	}
	
	
	private void pushThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "push"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("push");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	/**
	 * 查询字体大小
	 * @param size
	 */
	private void getSize()
	{
		String condition ="nid='" + "size"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			DBSize("1");
			size1Button.setTextColor(0xFFAA823C);
		}
		else {
			Size = list.get(0).getSize();
			if (Size.equals("1")) {
				size1Button.setTextColor(0xFFAA823C);
				size2Button.setTextColor(0xFF000000);
				size3Button.setTextColor(0xFF000000);
			}
			else if (Size.equals("2")) {
				size1Button.setTextColor(0xFF000000);
				size2Button.setTextColor(0xFFAA823C);
				size3Button.setTextColor(0xFF000000);
			}
			else {
				size1Button.setTextColor(0xFF000000);
				size2Button.setTextColor(0xFF000000);
				size3Button.setTextColor(0xFFAA823C);
			}
		}
	}
	
	/**
	 * 修改字体大小
	 * @param size
	 */
	private void Updatesize(String size)
	{
		String condition ="nid='" + "size"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			DBSize(size);
		}
		else {
			String delete ="nid='" + "size"+ "'";//搜索条件
			db.deleteByWhere(DBSize.class, delete);
			DBSize(size);
		}
	}
	
	
	private void DBSize(final String size)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String condition ="nid='" + "size"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("size");
				user.setSize(size);
				db.save(user);
			}
			
		}.start();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Setting_button_size1:
			Updatesize("1");
			size1Button.setTextColor(0xFFAA823C);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFF000000);
			break;
		case R.id.Setting_button_size2:
			Updatesize("2");	
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFFAA823C);
			size3Button.setTextColor(0xFF000000);
			break;
		case R.id.Setting_button_size3:
			Updatesize("3");
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFFAA823C);
			break;

		default:
			break;
		}
	}

}
