package com.evebit.HandOnEastWind;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 * 加载初始页面
 * @author guan
 *
 */

public class LauchActivity extends Activity implements OnTouchListener, OnGestureListener{
      
	   private ImageView ad_ImageView;  //显示广告图片
	   Bitmap bitmap =null; //显示广告图片
	   private final int SPLASH_DISPLAY_LENGHT = 2000; //2秒后跳转主页面
	   private Boolean flag= false; //第一次进入
	   
	   //滑动选项卡
	   GestureDetector mGestureDetector;  
	   private static final int FLING_MIN_DISTANCE = 50;  
	   private static final int FLING_MIN_VELOCITY = 0;  
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lauch);
		  ad_ImageView = (ImageView)findViewById(R.id.ad_image);
	        
	     mGestureDetector = new GestureDetector(this);  
		 LinearLayout ad=(LinearLayout)findViewById(R.id.ad);  
		 ad.setOnTouchListener(this);  
		 ad.setLongClickable(true);  
	          
	     imgThread();     
		
		
		MobclickAgent.updateOnlineConfig(LauchActivity.this);
		
	}
	
	   private void imgThread()
	    {
	         new Thread()
	         {

	              @Override
	              public void run() {
	                   // TODO Auto-generated method stub
	                
	                        URL imageUrl =null;                      
	                        try {
	                             imageUrl = new URL("http://www.hua.com/flower_picture/meiguihua/images/r14s.jpg");
	                        } catch (Exception e) {
	                             // TODO: handle exception
	                        }
	                        try {
	       
	                             HttpURLConnection connection =(HttpURLConnection)imageUrl.openConnection();
	                             connection.setDoInput(true);
	                             connection.connect();
	                             InputStream is=connection.getInputStream();
	                             BufferedInputStream bis = new BufferedInputStream(is);
	                             bitmap = BitmapFactory.decodeStream(bis);
	                             bis.close();
	                             is.close();
	                           
	                        } catch (Exception e) {
	                             // TODO: handle exception
	                           e.printStackTrace();
	                        }
	                
	                 
	                   handler.sendEmptyMessage(1);
	              }
	            
	         }.start();
	    }
	    
	     /**
	     * 
	     */
	     private Handler handler = new Handler()
	     {
	          @Override
	          public void handleMessage(Message msg) {
	               // TODO Auto-generated method stub
	               switch (msg.what) {
	               case 1:
	                    ad_ImageView.setImageBitmap(bitmap);
	                    startCountTime();
	                    break;
	               case 2:
	              
	                    break;
	               default:
	                    break;
	               }
	          }

	         
	     };
	    
	     
	     private void startCountTime() {
	          // TODO Auto-generated method stub
	            new Handler().postDelayed(new Runnable() {
	              @Override
	              public void run() {
	            	  flag = true;
	            	  goTest();
	              }

	      }, SPLASH_DISPLAY_LENGHT);
	     }

	 	public void goTest(){
			Intent intent = new Intent(LauchActivity.this, TabMainActivity.class);
			LauchActivity.this.startActivity(intent);
		}
	 	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lauch, menu);
		return true;
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
		if (flag) {
			 if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE   
	                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
	             // Fling left   
	             Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();  
	             goTest();
	         } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
	                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
	             // Fling right   
	             Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();  
	            
	         }   
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
