package com.evebit.HandOnEastWind;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
/**
 * 包含5个选项卡的主页
 * 
 * EastWindNewsActivity 东风汽车报
 * EastWindActivity     东风
 * CarTravelActivity    汽车之旅
 * CarTechActivity      汽车科技
 * EquipFixActivity     装备维修技术
 * @author guan
 *
 */
public class TabMainActivity extends ActivityGroup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);
		
		 // 获取TabHost对象  
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup(); 
        tabHost.setup(this.getLocalActivityManager());        
        
        
        //分别为底部的5个选项卡
        TabSpec firstSpec=tabHost.newTabSpec("东风汽车报");
        firstSpec.setIndicator("东风汽车报", null);
        Intent firstIntent= new Intent(this, EastWindNewsActivity.class);
        firstSpec.setContent(firstIntent);
        tabHost.addTab(firstSpec);

        TabSpec secondSpec=tabHost.newTabSpec("东风");
        secondSpec.setIndicator("东风", null);
        Intent secondIntent= new Intent(this, EastWindActivity.class);
        secondSpec.setContent(secondIntent);
        tabHost.addTab(secondSpec);

        TabSpec thirdSpec=tabHost.newTabSpec("汽车之旅");
        thirdSpec.setIndicator("汽车之旅", null);
        Intent thirdIntent= new Intent(this, CarTravelActivity.class);
        thirdSpec.setContent(thirdIntent);
        tabHost.addTab(thirdSpec);
        
        TabSpec forthSpec=tabHost.newTabSpec("汽车科技");
        forthSpec.setIndicator("汽车科技", null);
        Intent forthIntent= new Intent(this,CarTechActivity.class);
        forthSpec.setContent(forthIntent);
        tabHost.addTab(forthSpec);
        
        TabSpec fifthSpec=tabHost.newTabSpec("装备维修技术");
        fifthSpec.setIndicator("装备维修技术", null);
        Intent fifthIntent= new Intent(this, EquipFixActivity.class);
        fifthSpec.setContent(fifthIntent);
        tabHost.addTab(fifthSpec);
        
		
	}

}
