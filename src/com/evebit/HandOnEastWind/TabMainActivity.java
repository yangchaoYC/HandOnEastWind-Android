package com.evebit.HandOnEastWind;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
/**
 * 5个主选项卡
 * 
 * EastWindNewsActivity 
 * EastWindActivity     
 * CarTravelActivity    
 * CarTechActivity      
 * EquipFixActivity     
 * @author guan
 *
 */
public class TabMainActivity extends ActivityGroup implements OnTouchListener, OnGestureListener{

	     //滑动功能
		 GestureDetector mGestureDetector;  
		 private static final int FLING_MIN_DISTANCE = 50;  
		 private static final int FLING_MIN_VELOCITY = 0; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);
		
		//检测更新
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
	
		
		
		 // TabHost 
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup(); 
        tabHost.setup(this.getLocalActivityManager());        
        
        
        //
        TabSpec firstSpec=tabHost.newTabSpec("东风");
        firstSpec.setIndicator("东风", null);
        Intent firstIntent= new Intent(this, EastWindNewsActivity.class);
        firstSpec.setContent(firstIntent);
        tabHost.addTab(firstSpec);

        TabSpec secondSpec=tabHost.newTabSpec("����");
        secondSpec.setIndicator("����", null);
        Intent secondIntent= new Intent(this, EastWindActivity.class);
        secondSpec.setContent(secondIntent);
        tabHost.addTab(secondSpec);

        TabSpec thirdSpec=tabHost.newTabSpec("��֮��");
        thirdSpec.setIndicator("��֮��", null);
        Intent thirdIntent= new Intent(this, CarTravelActivity.class);
        thirdSpec.setContent(thirdIntent);
        tabHost.addTab(thirdSpec);
        
        TabSpec forthSpec=tabHost.newTabSpec("��Ƽ�");
        forthSpec.setIndicator("��Ƽ�", null);
        Intent forthIntent= new Intent(this,CarTechActivity.class);
        forthSpec.setContent(forthIntent);
        tabHost.addTab(forthSpec);
        
        TabSpec fifthSpec=tabHost.newTabSpec("װ��ά�޼���");
        fifthSpec.setIndicator("װ��ά�޼���", null);
        Intent fifthIntent= new Intent(this, EquipFixActivity.class);
        fifthSpec.setContent(fifthIntent);
        tabHost.addTab(fifthSpec);
        
      //滑动选项卡
		 mGestureDetector = new GestureDetector(this);  
		 tabHost=(TabHost)findViewById(R.id.tabhost);  
		 tabHost.setOnTouchListener(this);  
		 tabHost.setLongClickable(true); 
	}
	
	@SuppressWarnings("deprecation")
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@SuppressWarnings("deprecation")
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
				 if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE   
		                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
		             // Fling left   
		             Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();  
		             
		         } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
		                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
		             // Fling right   
		             Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();  
		             onBackPressed();
		         }   
		         return false;   
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("touch","touch");  
        return mGestureDetector.onTouchEvent(event);  
	}
	
}
