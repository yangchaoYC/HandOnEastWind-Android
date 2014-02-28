package com.evebit.HandOnEastWind;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;


public class ShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
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
