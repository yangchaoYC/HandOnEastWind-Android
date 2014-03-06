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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

/**
 * 主选项卡页面，对应activity_tab_main，包含底部4个选项卡，显示导航页面
 * 4个选项卡
 * navigation 导航
 * news       新闻  
 * share      分享   
 * setting    设置         
 * @author guan
 *
 * 设计逻辑：
 * 此为初始页面跳转到的主选项卡页面，可以采用手势滑动，滑动到初始页面
 * 主选项卡页面默认显示导航页面NavigationActivity
 * 导航页面NavigationActivity可选择不同频道，点击选择之后发送广播到activity_tab_main，底部选项跳转至新闻，页面跳转至新闻页面
 */

public class TabMainActivity extends TabActivity implements OnCheckedChangeListener{

	private RadioGroup mainTab;
	private RadioButton radioButton0;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	
	private Drawable drawableTop0;
	private Drawable drawableTop1;
	private Drawable drawableTop2;
	private Drawable drawableTop3;
	
	private Drawable drawableTop0_red;
	private Drawable drawableTop1_red;
	private Drawable drawableTop2_red;
	private Drawable drawableTop3_red;

	
    private TabHost tabhost;
    private Intent navigation; //导航频道
    private Intent news; //新闻频道
    private Intent share; //分享按钮
	private Intent setting; //设置按钮
	
	 // tabhsot 的标志位 为跳转做准备
	 private int tabIndex;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		    super.onCreate(savedInstanceState);		    
			setContentView(R.layout.activity_tab_main);
			
			//设置接收广播，导航频道的点击事件切换到新闻频道
			IntentFilter intentFilter = new IntentFilter();  
		    intentFilter.addAction("tabHost");  
		    registerReceiver(receiver, intentFilter);
			
			//检测更新功能
			UmengUpdateAgent.setUpdateOnlyWifi(false);
			UmengUpdateAgent.update(this);		
		
			//寻找radioGroup的选项卡
		    mainTab = (RadioGroup)findViewById(R.id.main_radio);
		    radioButton0 = (RadioButton)findViewById(R.id.radio_button0);
		    radioButton1 = (RadioButton)findViewById(R.id.radio_button1);
		    radioButton2 = (RadioButton)findViewById(R.id.radio_button2);
		    radioButton3 = (RadioButton)findViewById(R.id.radio_button3);
		    
		    drawableTop0 = getResources().getDrawable(R.drawable.tab_navigation);
		    drawableTop1 = getResources().getDrawable(R.drawable.tab_news);
		    drawableTop2 = getResources().getDrawable(R.drawable.tab_share);
		    drawableTop3 = getResources().getDrawable(R.drawable.tab_setting);
		    
		    drawableTop0_red = getResources().getDrawable(R.drawable.tab_navigation_red);
		    drawableTop1_red = getResources().getDrawable(R.drawable.tab_news_red);
		    drawableTop2_red = getResources().getDrawable(R.drawable.tab_share_red);
		    drawableTop3_red = getResources().getDrawable(R.drawable.tab_setting_red);
		
	        mainTab.setOnCheckedChangeListener(this);        
	        tabhost = getTabHost();
	        
	        //导航频道
	        navigation = new Intent(this, NavigationActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("navigation")
	                .setIndicator(getResources().getString(R.string.main_navigation), getResources().getDrawable(R.drawable.icon_1_n))
	                .setContent(navigation));
	      //新闻频道
	        news  = new Intent(this, EastWindNewsActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("news")
	                .setIndicator(getResources().getString(R.string.main_news), getResources().getDrawable(R.drawable.icon_2_n))
	                .setContent(news));
	        
	        share = new Intent(this, ShareActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("share")
	                .setIndicator(getResources().getString(R.string.main_share), getResources().getDrawable(R.drawable.icon_3_n))
	                .setContent(share));
	        
	        setting = new Intent(this,SettingActivity.class);
	        tabhost.addTab(tabhost.newTabSpec("setting")
	                .setIndicator(getResources().getString(R.string.main_setting), getResources().getDrawable(R.drawable.icon_4_n))
	                .setContent(setting));
	   

	}
	
	//检测更新功能
	@SuppressWarnings("deprecation")
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	//检测更新功能
	@SuppressWarnings("deprecation")
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	
	public void setDefaulftTab(){
		  radioButton0.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop0 , null, null);
		  radioButton1.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop1 , null, null);
		  radioButton2.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop2 , null, null);
		  radioButton3.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop3 , null, null);		  
	}

	
	
    //底部四个选项卡，分别为导航频道，新闻频道，分享按钮，设置按钮
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		 switch(checkedId){
		     //导航
	        case R.id.radio_button0:	      
	            this.tabhost.setCurrentTabByTag("navigation");
	            setDefaulftTab();
	            radioButton0.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop0_red , null, null);	            
	            break;
	        //新闻    
	        case R.id.radio_button1:	    
	            this.tabhost.setCurrentTabByTag("news");	
	            setDefaulftTab();
	            radioButton1.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop1_red , null, null);
	            break;
	        //分享   
	        case R.id.radio_button2:	    
	            this.tabhost.setCurrentTabByTag("share"); 
	            setDefaulftTab();
	            radioButton2.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop2_red , null, null);	       
	            break;
	       //设置     
	        case R.id.radio_button3:	       
	            this.tabhost.setCurrentTabByTag("setting");
	            setDefaulftTab();
	            radioButton3.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop3_red , null, null);
	            break;      
	        }
	}
	
	/**
	 * 广播接收器
	 * 接收到切换到新闻频道，选项卡切换到新闻
	 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();  
			if (action.equals("tabHost")) {	
				int key = intent.getIntExtra("key",0);
				if (key == 1) {
					tabhost.setCurrentTabByTag("news");
					radioButton1.setChecked(true);
				}
				else {
					unregisterReceiver(receiver);
				}
				
			//int column = intent.getIntExtra("column",0);
				
			}
			
		}
	};
	
	
	
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
