package com.evebit.HandOnEastWind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.evebit.adapter.ListAdapter;
import com.evebit.adapter.ViewPageAdapter;

import android.R.integer;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class EastWindNewsActivity extends Activity  implements OnClickListener{

	/*
	
	  //选项卡
	 private ViewPager viewPager;
	 private ArrayList<View> pageViews; //选项卡列表
	 private ViewGroup eastWindNewsGroup; //东风汽车报的选项卡集合
*/	 //东风汽车报下的栏目 头条，要闻，生产经营，东风党建等
	   private View view1_title,view1_important,view1_produce,view1_politic;
	
	    private ViewGroup eastWindNewsGroup; //东风汽车报的选项卡集合
	  
	    private RadioGroup radioGroup;
		private String title[] = {  "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "tem" };
		private final int height = 70;
		private ArrayList<TextView> textViews;
		private ViewPager viewPager;
		private ArrayList<View> pageViews;
		private HorizontalScrollView horizontalScrollView;
		private int H_width;
        private LinearLayout linearLayout;
	
        private String tab ;
	/***
	 * init view
	 */
	void InItView(int i) {
		    pageViews = new ArrayList<View>(); 
		    LayoutInflater inflater = getLayoutInflater(); 
	        //头条，要闻    对应的界面
	        view1_title = inflater.inflate(R.layout.view1_list_tile, null);
	        view1_important = inflater.inflate(R.layout.view1_list_important, null);
	        view1_produce = inflater.inflate(R.layout.view1_list_produce, null);
	        view1_politic = inflater.inflate(R.layout.view1_list_politic, null);
	        

	        
	        pageViews.add(view1_title);
	        pageViews.add(view1_important);
	        pageViews.add(view1_produce); 
	        if (i== 4) {
	        	 pageViews.add(view1_politic);
			}
	       
	        
	        mm();
		
		
	}
	
	
	
	/***
	 * init title
	 */
	void InItTitle() {
		int width = getWindowManager().getDefaultDisplay().getWidth() / 4;
		for (int i = 0; i < title.length; i++) {
			RadioButton radioButton = new RadioButton(this, null, R.style.radioButton);
			radioButton.setText(title[i]);
			radioButton.setTextSize(17);
			radioButton.setTextColor(Color.BLACK);
			radioButton.setWidth(width);
			radioButton.setHeight(height);
			radioButton.setGravity(Gravity.CENTER);
			radioGroup.addView(radioButton);
		}
	}

	/***
	 * init title
	 */
	void InItTitle1() {
		textViews = new ArrayList<TextView>();
		H_width = getWindowManager().getDefaultDisplay().getWidth() / 4;
		int height = 70;
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(title[i]);
			textView.setTextSize(17);
			textView.setTextColor(Color.BLACK);
			textView.setWidth(H_width);
			Log.e("aa", "text_width=" + textView.getWidth());
			textView.setHeight(height - 30);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			textViews.add(textView);
			// �ָ���
			View view = new View(this);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height - 40;
			layoutParams.gravity = Gravity.CENTER;
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GRAY);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
			}
			Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());

		}
	}

	
	/***
	 * 
	 */
	public void setSelector(int id) {
		for (int i = 0; i < title.length; i++) {
			if (id == i) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grouplist_item_bg_normal);
				textViews.get(id).setBackgroundDrawable(new BitmapDrawable(bitmap));
				textViews.get(id).setTextColor(Color.RED);
				if (i > 2) {
					horizontalScrollView.smoothScrollTo((textViews.get(i).getWidth() * i - 180), 0);
				} else {
					horizontalScrollView.smoothScrollTo(0, 0);
				}
				viewPager.setCurrentItem(i);
			} else {
				textViews.get(i).setBackgroundDrawable(new BitmapDrawable());
				textViews.get(i).setTextColor(Color.BLACK);
			}
		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  
		
		  IntentFilter intentFilter = new IntentFilter();  
	      intentFilter.addAction("test");  
	      registerReceiver(receiver, intentFilter);
	       
		

			InItView(4);

	}

    public void mm(){
    	 LayoutInflater inflater = getLayoutInflater(); 
		    eastWindNewsGroup = (ViewGroup)inflater.inflate(R.layout.tab_view1, null); 
		    viewPager = (ViewPager)eastWindNewsGroup.findViewById(R.id.tabView1_container);
		  
		   viewPager.setAdapter(new myPagerView());
	       setContentView(eastWindNewsGroup);
	       
	   
	       
	       
	       horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
	       linearLayout = (LinearLayout) findViewById(R.id.ll_main);
	       InItTitle1();
		   setSelector(0);
		
			
			
	    	//头条，要闻   对应的list列表的id
	       ListView listView1_title = (ListView)view1_title.findViewById(R.id.list_view1_title);
	       ListView listView1_important = (ListView)view1_important.findViewById(R.id.list_view1_important); 
	       ListView listView1_produce = (ListView)view1_produce.findViewById(R.id.list_view1_produce);
	       ListView listView1_politic = (ListView)view1_politic.findViewById(R.id.list_view1_politic);
	       
	       listView1_title.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					 pageViews.clear();
					
				}
				
			});
	       
	       //头条，要闻   对应的 list中加载的数据 此为静态数据
	        ArrayList<HashMap<String, String>> listData_title = new ArrayList<HashMap<String, String>>();
	        ArrayList<HashMap<String, String>> listData_important = new ArrayList<HashMap<String, String>>();
	        ArrayList<HashMap<String, String>> listData_produce = new ArrayList<HashMap<String, String>>();
	        ArrayList<HashMap<String, String>> listData_politic = new ArrayList<HashMap<String, String>>();

	        for (int i =1; i < 10; i++) {
				HashMap<String, String> itemMap = new HashMap<String, String>();
				itemMap.put("title","张家朝");	
				itemMap.put("bigImageView",getString(R.drawable.ic_launcher));
				listData_title.add(itemMap);
			}
	        
	        for (int i =1; i < 10; i++) {
				HashMap<String, String> itemMap = new HashMap<String, String>();
				itemMap.put("title","熊波");	
				itemMap.put("bigImageView",getString(R.drawable.ic_launcher));
				listData_important.add(itemMap);
			}
         
	        for (int i =1; i < 10; i++) {
				HashMap<String, String> itemMap = new HashMap<String, String>();
				itemMap.put("title","小龙");		
				itemMap.put("bigImageView",getString(R.drawable.ic_launcher));
				listData_produce.add(itemMap);
			}
	        
	        for (int i =1; i < 10; i++) {
				HashMap<String, String> itemMap = new HashMap<String, String>();
				itemMap.put("title","余多");
				itemMap.put("bigImageView",getString(R.drawable.ic_launcher));
				listData_politic.add(itemMap);
			}
	        
	        //头条和要闻对应的adapter
	        ListAdapter title_View1_Adapter = new ListAdapter(this, listData_title);
	        ListAdapter important_View1_Adapter = new ListAdapter(this,listData_important);	
	        ListAdapter produce_View1_Adapter = new ListAdapter(this, listData_produce);
	        ListAdapter politic_View1_Adapter = new ListAdapter(this,listData_politic);	
	        
	        listView1_title.setAdapter(title_View1_Adapter);	
	        listView1_important.setAdapter(important_View1_Adapter);	
	        listView1_produce.setAdapter(produce_View1_Adapter);	
	        listView1_politic.setAdapter(politic_View1_Adapter);	
			
			
			
			viewPager.clearAnimation();
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					setSelector(arg0);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});	
	}
	
	
	@Override
	public void onClick(View v) {
		setSelector(v.getId());
	}

	class myPagerView extends PagerAdapter {
		
		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		/***
		 * ��ȡÿһ��item�� ����listview�е�getview
		 */
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

	}
	

	  private BroadcastReceiver receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();  
				
				if (action.equals("test")) {
					String  taba = intent.getStringExtra("tab");
					//Log.v("eastwindnews --- ---  ", taba);
				
					if (taba.equals("second")) {
						pageViews.clear();
						InItView(3);
					}
				}
				
			}
		};


}
