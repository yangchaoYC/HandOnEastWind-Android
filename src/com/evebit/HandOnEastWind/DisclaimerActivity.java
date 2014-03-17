package com.evebit.HandOnEastWind;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class DisclaimerActivity extends Activity {

	
	private ImageView imageView;
	private long firstime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disclaimer);
		imageView = (ImageView)findViewById(R.id.disclaimer_back);
		
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	     
	/**
	 * 屏蔽返回按钮
	 
	@Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		long secondtime = System.currentTimeMillis();
		if (keyCode == KeyEvent.KEYCODE_BACK) { 
			if (secondtime - firstime > 2000) {
				Toast.makeText(DisclaimerActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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
		}
	return super.onKeyDown(keyCode, event);		
	}

	*/
	


	/**
	 * 屏蔽菜单键
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lauch, menu);
		return true;
	}
}
