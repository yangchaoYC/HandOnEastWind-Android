package com.evebit.HandOnEastWind;



import com.facebook.Session.NewPermissionsRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NavigationActivity extends Activity  implements OnTouchListener,  OnGestureListener {

	//滑动功能
	 GestureDetector mGestureDetector;  
	 private static final int FLING_MIN_DISTANCE = 50;  
	 private static final int FLING_MIN_VELOCITY = 0; 
	
	private Button button;
	
	//Broade broad =  new Broade();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		 //滑动选项卡
		 mGestureDetector = new GestureDetector(this);	 
	     LinearLayout navigationView=(LinearLayout)findViewById(R.id.navigation);  
	     navigationView.setOnTouchListener(this);  
	     navigationView.setLongClickable(true);  
		
		
		button = (Button) findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	Intent intent =new Intent(NavigationActivity.this, EastWindNewsActivity.class);
			//	startActivity(intent);
				
				Intent intent =  new Intent();
				intent.setAction("tabHost");
				//intent.putExtra("name", "静态");
				sendBroadcast(intent);

					
				
			}
		});
		
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
               Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();   
           } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
                   && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
               // Fling right  
        	   onBackPressed();
               Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();   
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

	
	
}
