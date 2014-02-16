package com.evebit.HandOnEastWind;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
/**
 * ��5��ѡ�����ҳ
 * 
 * EastWindNewsActivity ������
 * EastWindActivity     ����
 * CarTravelActivity    ��֮��
 * CarTechActivity      ��Ƽ�
 * EquipFixActivity     װ��ά�޼���
 * @author guan
 *
 */
public class TabMainActivity extends ActivityGroup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		 // ��ȡTabHost����  
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup(); 
        tabHost.setup(this.getLocalActivityManager());        
        
        
        //�ֱ�Ϊ�ײ���5��ѡ�
        TabSpec firstSpec=tabHost.newTabSpec("������");
        firstSpec.setIndicator("������", null);
        Intent firstIntent= new Intent(this, EastWindNewsActivity.class);
        firstSpec.setContent(firstIntent);
        tabHost.addTab(firstSpec);

        TabSpec secondSpec=tabHost.newTabSpec("����");
        secondSpec.setIndicator("����", null);
        Intent secondIntent= new Intent(this, EastWindActivity.class);
        secondSpec.setContent(secondIntent);
        tabHost.addTab(secondSpec);

        TabSpec thirdSpec=tabHost.newTabSpec("��֮��");
        thirdSpec.setIndicator("��֮��", null);
        Intent thirdIntent= new Intent(this, CarTravelActivity.class);
        thirdSpec.setContent(thirdIntent);
        tabHost.addTab(thirdSpec);
        
        TabSpec forthSpec=tabHost.newTabSpec("��Ƽ�");
        forthSpec.setIndicator("��Ƽ�", null);
        Intent forthIntent= new Intent(this,CarTechActivity.class);
        forthSpec.setContent(forthIntent);
        tabHost.addTab(forthSpec);
        
        TabSpec fifthSpec=tabHost.newTabSpec("װ��ά�޼���");
        fifthSpec.setIndicator("װ��ά�޼���", null);
        Intent fifthIntent= new Intent(this, EquipFixActivity.class);
        fifthSpec.setContent(fifthIntent);
        tabHost.addTab(fifthSpec);
        
		
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	

}
