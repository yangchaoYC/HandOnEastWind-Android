package com.evebit.HandOnEastWind;

import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * ��ʼ��ҳ��
 * @author guan
 *
 */

public class LauchActivity extends Activity {

	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lauch);
		button = (Button)findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LauchActivity.this, TabMainActivity.class);
				startActivity(intent);	
			}
		});
		
		
		MobclickAgent.updateOnlineConfig(LauchActivity.this);
		
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

}
