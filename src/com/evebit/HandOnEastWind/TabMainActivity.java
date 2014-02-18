package com.evebit.HandOnEastWind;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
public class TabMainActivity extends TabActivity implements OnCheckedChangeListener{

	private RadioGroup mainTab;
    private TabHost tabhost;
    private Intent navigation;
    private Intent news;
    private Intent share;
	private Intent setting;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		    super.onCreate(savedInstanceState);
		    
			setContentView(R.layout.activity_tab_main);
			
			IntentFilter intentFilter = new IntentFilter();  
		    intentFilter.addAction("tanHost");  
		    registerReceiver(receiver, intentFilter);

			
			//检测更新
			UmengUpdateAgent.setUpdateOnlyWifi(false);
			UmengUpdateAgent.update(this);		
		
			//寻找radioGroup的选项卡
		    mainTab=(RadioGroup)findViewById(R.id.main_radio);
	        mainTab.setOnCheckedChangeListener(this);        
	        tabhost = getTabHost();
	        
	        navigation = new Intent(this, NavigationActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("navigation")
	                .setIndicator(getResources().getString(R.string.main_navigation), getResources().getDrawable(R.drawable.icon_1_n))
	                .setContent(navigation));
	      
	        news  = new Intent(this, EastWindNewsActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("news")
	                .setIndicator(getResources().getString(R.string.main_news), getResources().getDrawable(R.drawable.icon_2_n))
	                .setContent(news));
	     /*   
	        share = new Intent(this, CarTravelActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("share")
	                .setIndicator(getResources().getString(R.string.main_share), getResources().getDrawable(R.drawable.icon_3_n))
	                .setContent(share));
	        
	        setting = new Intent(this,CarTechActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("setting")
	                .setIndicator(getResources().getString(R.string.main_setting), getResources().getDrawable(R.drawable.icon_4_n))
	                .setContent(setting));*/
	        
	        
	        
	        
	    /*    news = new Intent(this, EastWindNewsActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("news")
	                .setIndicator(getResources().getString(R.string.main_news), getResources().getDrawable(R.drawable.icon_1_n))
	                .setContent(news));
	      
	        eastWind  = new Intent(this, EastWindActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("eastWind")
	                .setIndicator(getResources().getString(R.string.main_eastwind), getResources().getDrawable(R.drawable.icon_2_n))
	                .setContent(eastWind));
	        
	        travle = new Intent(this, CarTravelActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("travle")
	                .setIndicator(getResources().getString(R.string.main_travle), getResources().getDrawable(R.drawable.icon_3_n))
	                .setContent(travle));
	        
	        tech = new Intent(this,CarTechActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("tech")
	                .setIndicator(getResources().getString(R.string.main_tech), getResources().getDrawable(R.drawable.icon_4_n))
	                .setContent(tech));
	        
	         fix = new Intent(this, EquipFixActivity  .class);
	         tabhost.addTab(tabhost.newTabSpec("fix")
	                    .setIndicator(getResources().getString(R.string.main_fix), getResources().getDrawable(R.drawable.icon_5_n))
	                    .setContent(fix));*/
        
     
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		 switch(checkedId){
	        case R.id.radio_button0:
	            this.tabhost.setCurrentTabByTag("navigation");	            
	            break;
	        case R.id.radio_button1:
	            this.tabhost.setCurrentTabByTag("news");
	            break;
	        case R.id.radio_button2:
	            this.tabhost.setCurrentTabByTag("share");
	            break;
	        case R.id.radio_button3:
	            this.tabhost.setCurrentTabByTag("setting");
	            break;
	      
	        }
	}
	
	
      private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();  
			String nameString = intent.getStringExtra("name");
			if (action.equals("tanHost")) {
				 tabhost.setCurrentTabByTag("news");

				Log.v("-------", "ceshi---------");
			}
			
		}
	};


}
