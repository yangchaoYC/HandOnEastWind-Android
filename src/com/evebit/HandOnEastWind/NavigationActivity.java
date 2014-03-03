package com.evebit.HandOnEastWind;

import com.facebook.Session.NewPermissionsRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 导航页面 activity_navigation
 * 此页面包含5个频道
 * 东风汽车报 (9个栏目)：头条、要闻、生产经营、东风党建、和谐东风、东风人、东风文艺、专题报道、四城视点
 * 东风(4个栏目)：专题、企业、观点、对话
 * 汽车之旅(8个栏目)：旅游资讯、“驾”临天下、名车靓影、城市约会、乐途影像、名家专栏、微博·贴士邦
 * 汽车科技(12个栏目)：播报、国际前研、新车测评、政能量、创新观察、人物专访、特别关注、特稿、设计•研究、试验•测试、工艺•材料、公告牌
 * 维修装备技术(6个栏目)：行业资讯、工作研究、故障维修、技术改造、节能技术、汽车研究
 * 
 * 设计逻辑
 * 此页面为主选项卡默认显示的导航页，可支持手势滑动至初始页面
 * 点击不同的栏目，发送广播至主选项卡页面TabMainActivity，切换到新闻频道
 * 并且发送广播至新闻页面EastWindNewsActivity，告知需要几个栏目
 * @author guan
 *
 */

public class NavigationActivity extends Activity  implements OnTouchListener,  OnGestureListener {

	//滑动功能
	private long firstime = 0;
	 GestureDetector mGestureDetector;  
	 private static final int FLING_MIN_DISTANCE = 50;  
	 private static final int FLING_MIN_VELOCITY = 0; 
	 private TextView textView;
	 //导航页面 5个频道对应的LinearLayout区域：东风汽车报 东风 汽车之旅 汽车科技 维修装备技术
	 private LinearLayout newsLinearLayout,dongfengLinearLayout,travelLinearLayout,techLinearLayout,fixLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		 //滑动选项卡
		 mGestureDetector = new GestureDetector(this);	 
	     LinearLayout navigationView=(LinearLayout)findViewById(R.id.navigation);  
	     //navigationView.setOnTouchListener(this);  
	    // navigationView.setLongClickable(true);  
	    
	     //导航页面 5个频道：东风汽车报 东风 汽车之旅 汽车科技 维修装备技术
	     newsLinearLayout = (LinearLayout)findViewById(R.id.navigation_news);
	     dongfengLinearLayout = (LinearLayout)findViewById(R.id.navigation_dongfeng);
	     travelLinearLayout = (LinearLayout)findViewById(R.id.navigation_travel);
	     techLinearLayout = (LinearLayout)findViewById(R.id.navigation_tech);
	     fixLinearLayout = (LinearLayout)findViewById(R.id.navigation_fix);
	     textView = (TextView)findViewById(R.id.navigation_company);
	     
	     
	     travelLinearLayout.setOnTouchListener(this);  
	     travelLinearLayout.setLongClickable(true);  
	     textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "正在开发中",Toast.LENGTH_SHORT).show();
			}
		});
	     
	     newsLinearLayout.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub	
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("15");
					brodeNewsColumn(1);
					brodeTabhost();		
					brodeCache();
				}
			});
	     
	     dongfengLinearLayout.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
				Shared("24");
					brodeNewsColumn(2); 
					brodeTabhost();	
					brodeCache();
			}
		});
		
		
	     travelLinearLayout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
				Shared("28");
				brodeNewsColumn(3);
				brodeTabhost();
				brodeCache();
			}
		});
	     
	     techLinearLayout.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("35");
					brodeNewsColumn(4);
					brodeTabhost();
					brodeCache();
				}
			});
	     
	     fixLinearLayout.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("47");
					brodeNewsColumn(5);
					brodeTabhost();
					brodeCache();
				}
			});
	}
	
	
	private void Shared(String shaerd)
	{
		SharedPreferences settings = this.getSharedPreferences("CheckLoginXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putString("CheckLogin",shaerd);
		localEditor.commit(); 
	}
	
	/**
	 * 广播告知导航频道切换到新闻频道
	 */
	public void brodeTabhost(){
		Intent intent =  new Intent();
		intent.setAction("tabHost");
		sendBroadcast(intent);
	}
	/**
	 * 通知刷新缓存
	 */
	public void brodeCache(){
		Intent intent =  new Intent();
		intent.setAction("cache");
		sendBroadcast(intent);
	}
	
	/**
	 * 广播告知新闻频道需要几个栏目，以动态改变
	 * @param column 所需栏目数
	 */
	public void brodeNewsColumn(int column){
		Intent intent_column =  new Intent();
		intent_column.setAction("news");
		intent_column.putExtra("column", column);
		sendBroadcast(intent_column);
	}
	
	@Override
	public boolean onDown(MotionEvent arg0) {
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
             //  Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();   
           } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
                   && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
               // Fling right  
        	   onBackPressed();
             //  Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();   
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
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		 Log.i("touch","touch");  
         return mGestureDetector.onTouchEvent(event);   
	}

	/**
	 * 屏蔽返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		long secondtime = System.currentTimeMillis();
		if (secondtime - firstime > 2000) {
			Toast.makeText(NavigationActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
			firstime = System.currentTimeMillis();
			return true;
		} else {
			 finish();
			 Intent startMain = new Intent(Intent.ACTION_MAIN);   
             startMain.addCategory(Intent.CATEGORY_HOME);   
             startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
             startActivity(startMain);   
             System.exit(0); 
		}
	
	return super.onKeyDown(keyCode, event);	
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
