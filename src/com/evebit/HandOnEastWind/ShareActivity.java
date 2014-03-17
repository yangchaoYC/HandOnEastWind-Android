package com.evebit.HandOnEastWind;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;


public class ShareActivity extends Activity {

	private long firstime = 0;
	
	
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
		long secondtime = System.currentTimeMillis();
		if (keyCode == KeyEvent.KEYCODE_BACK) { 
			if (secondtime - firstime > 2000) {
				Toast.makeText(ShareActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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
	return super.onKeyDown(keyCode, event);		}

	/**
	 * 屏蔽菜单键
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
