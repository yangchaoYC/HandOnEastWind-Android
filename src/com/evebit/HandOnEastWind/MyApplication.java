package com.evebit.HandOnEastWind;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

	}

}
