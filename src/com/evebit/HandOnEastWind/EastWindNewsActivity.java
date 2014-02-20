package com.evebit.HandOnEastWind;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.ListView.XListView;
import com.evebit.ListView.XListView.IXListViewListener;
import com.evebit.adapter.ListAdapter;
import com.facebook.Session.NewPermissionsRequest;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


/**
 * 新闻页面 EastWindNewsActivity
 * 
 * 
 * @author guan
 *
 */

public class EastWindNewsActivity extends Activity  implements OnClickListener,IXListViewListener{

	    //12个pageView的容器
	    private View page_view_1,page_view_2,page_view_3,page_view_4,page_view_5,page_view_6,page_view_7,page_view_8,page_view_9,page_view_10,page_view_11,page_view_12;
	
	    private ViewGroup eastWindNewsGroup; //东风汽车报的选项卡集合
	    /*
	    * * 东风汽车报 (9个栏目)：头条、要闻、生产经营、东风党建、和谐东风、东风人、东风文艺、专题报道、四城视点
        * 东风(4个栏目)：专题、企业、观点、对话
        * 汽车之旅(8个栏目)：旅游资讯、“驾”临天下、名车靓影、城市约会、乐途影像、名家专栏、微博·贴士邦
        * 汽车科技(12个栏目)：播报、国际前研、新车测评、政能量、创新观察、人物专访、特别关注、特稿、设计•研究、试验•测试、工艺•材料、公告牌
        * 维修装备技术(6个栏目)：行业资讯、工作研究、故障维修、技术改造、节能技术、汽车研究
        */
		private String title1[] = {  "头条", "要闻", "生产经营", "东风党建", "和谐东风",  "东风人", "东风文艺", "专题报道", "四城视点" };
		private String title2[] = {  "专题", "企业", "观点", "对话"};
		private String title3[] = {  "旅游资讯", "“驾”临天下", "名车靓影", "城市约会", "乐途影像", "名家专栏", "微博·贴士邦"};
		private String title4[] = {  "播报", "国际前研", "新车测评", "政能量", "创新观察", "人物专访", "特别关注", "特稿", "设计•研究", "试验•测试" , "工艺•材料", "公告牌"};
		private String title5[] = {  "行业资讯", "工作研究", "故障维修", "技术改造", "节能技术", "汽车研究"};
		
		private ArrayList<TextView> textViews;
		private ViewPager viewPager;
		private ArrayList<View> pageViews;
		private HorizontalScrollView horizontalScrollView;
		private int H_width;
        private LinearLayout linearLayout;
        private XListView list_page_view1,list_page_view2,list_page_view3,list_page_view4,list_page_view5,list_page_view6,list_page_view7,list_page_view8,list_page_view9,list_page_view10,list_page_view11,list_page_view12;
	    
        private Boolean addListView = false;
	    private Boolean moveItem = false;
        
	    private ArrayList<XListView> listArray = new ArrayList<XListView>();
	    
        /**
         * 存放数据数组
         */
        private ArrayList<HashMap<String, String>> listData1 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData2 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData3 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData4 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData5 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData6 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData7 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData8 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData9 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData10 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData11 = new ArrayList<HashMap<String, String>>();	    
        private ArrayList<HashMap<String, String>> listData12 = new ArrayList<HashMap<String, String>>();	    

        /**
         * 初始化pageView容器
         * @param i 需要几个pageview
         * 
         * * 东风汽车报 (9个栏目)：头条、要闻、生产经营、东风党建、和谐东风、东风人、东风文艺、专题报道、四城视点
         * 东风(4个栏目)：专题、企业、观点、对话
         * 汽车之旅(8个栏目)：旅游资讯、“驾”临天下、名车靓影、城市约会、乐途影像、名家专栏、微博·贴士邦
         * 汽车科技(12个栏目)：播报、国际前研、新车测评、政能量、创新观察、人物专访、特别关注、特稿、设计•研究、试验•测试、工艺•材料、公告牌
         * 维修装备技术(6个栏目)：行业资讯、工作研究、故障维修、技术改造、节能技术、汽车研究
         * 
         * 
         */
	    void InItView(int i) {
	    	
	    	    pageViews = new ArrayList<View>(); 
			    LayoutInflater inflater = getLayoutInflater(); 
		        //12个pageView的容器对应的xml
			    page_view_1 = inflater.inflate(R.layout.page_view1, null);
			    page_view_2 = inflater.inflate(R.layout.page_view2, null);
			    page_view_3 = inflater.inflate(R.layout.page_view3, null);
			    page_view_4 = inflater.inflate(R.layout.page_view4, null);
				page_view_5 = inflater.inflate(R.layout.page_view5, null);
				page_view_6 = inflater.inflate(R.layout.page_view6, null);
				page_view_7 = inflater.inflate(R.layout.page_view7, null);
			    page_view_8 = inflater.inflate(R.layout.page_view8, null);
			    page_view_9 = inflater.inflate(R.layout.page_view9, null);
				page_view_10 = inflater.inflate(R.layout.page_view10, null);
				page_view_11 = inflater.inflate(R.layout.page_view11, null);
				page_view_12 = inflater.inflate(R.layout.page_view12, null);
		      
	    	
		    	pageViews.add(page_view_1);
		        pageViews.add(page_view_2);
		        pageViews.add(page_view_3); 
		        pageViews.add(page_view_4);
	       
	        switch (i) {
			case 6:
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 		     
			     
				break;
			case 8:
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 
			     pageViews.add(page_view_7);
			     pageViews.add(page_view_8);
			    
			     break;
			case 9:
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 
			     pageViews.add(page_view_7);
			     pageViews.add(page_view_8);
			     pageViews.add(page_view_9);
			    
				break;
			case 12:
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 
			     pageViews.add(page_view_7);
			     pageViews.add(page_view_8);
			     pageViews.add(page_view_9); 
			     pageViews.add(page_view_10);
			     pageViews.add(page_view_11);
			     pageViews.add(page_view_12);
			    
				break;
			
			default:
				break;
			}
   
		    setView(i);

	}    


	/**
	 * 
	 * @param newNumber 传递栏目数
	 */
	void InItTitle1(String title[]) {
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


	public void setSelector(int id) {
		for (int i = 0; i < title1.length; i++) {
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
		
		  //实例化广播接收器
		  IntentFilter intentFilter = new IntentFilter();  
	      intentFilter.addAction("news");  
	      registerReceiver(receiver, intentFilter);
         
	      //默认为东风汽车报频道，此频道有9个栏目
		  InItView(9);

	}

    public void setView(int i){
    	
    	   LayoutInflater inflater = getLayoutInflater(); 
		   eastWindNewsGroup = (ViewGroup)inflater.inflate(R.layout.tab_view1, null); 
		   viewPager = (ViewPager)eastWindNewsGroup.findViewById(R.id.tabView1_container);	  
		   viewPager.setAdapter(new myPagerView());
		   
	       setContentView(eastWindNewsGroup);
	       horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
	       linearLayout = (LinearLayout) findViewById(R.id.ll_main);
	      
	       
	      	   
	   
	       InItTitle1(title1);
	       
	       
	       
		   setListView(i);	   
		   setSelector(0);
	
			viewPager.setAdapter(new myPagerView());
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

		
			@Override
			public Object instantiateItem(View arg0, int arg1) {
			
				((ViewPager) arg0).addView(pageViews.get(arg1));
		    	return pageViews.get(arg1);
			}

		}
    
	
	
	
	private void setListView(int j) {
		// TODO Auto-generated method stub
		//头条，要闻   对应的list列表的id
		//if (addListView == false) {
			    list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
		        list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
		        list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
		        list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
		        list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
		        list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
		        list_page_view7 = (XListView)page_view_7.findViewById(R.id.page_view_7);
		        list_page_view8 = (XListView)page_view_8.findViewById(R.id.page_view_8); 
		        list_page_view9 = (XListView)page_view_9.findViewById(R.id.page_view_9);
		        list_page_view10 = (XListView)page_view_10.findViewById(R.id.page_view_10);
		        list_page_view11 = (XListView)page_view_11.findViewById(R.id.page_view_11);
		        list_page_view12 = (XListView)page_view_12.findViewById(R.id.page_view_12);
		//}
	      
		//addListView =true;
		       listArray.add(list_page_view1);
		       listArray.add(list_page_view2);
		       listArray.add(list_page_view3);
		       listArray.add(list_page_view4);
		       listArray.add(list_page_view5);
		       listArray.add(list_page_view6);
		       listArray.add(list_page_view7);
		       listArray.add(list_page_view8);
		       listArray.add(list_page_view9);
		       listArray.add(list_page_view10);
		       listArray.add(list_page_view11);
		       listArray.add(list_page_view12);
		       
		       for (int i = 0; i < listArray.size(); i++) {
		    	   listArray.get(i).setPullLoadEnable(true);
		    	   listArray.get(i).setXListViewListener(this);
			}
		     

	       //头条，要闻   对应的 list中加载的数据 此为静态数据
	     	        
	        HashMap<String, String> itemMap = new HashMap<String, String>();   
	        HashMap<String, String> itemMap2 = new HashMap<String, String>();
	        HashMap<String, String> itemMap3 = new HashMap<String, String>();
	        HashMap<String, String> itemMap4 = new HashMap<String, String>();      
	        HashMap<String, String> itemMap5 = new HashMap<String, String>();
	        HashMap<String, String> itemMap6 = new HashMap<String, String>();
	        HashMap<String, String> itemMap7 = new HashMap<String, String>();   
	        HashMap<String, String> itemMap8 = new HashMap<String, String>();
	        HashMap<String, String> itemMap9 = new HashMap<String, String>();
	        HashMap<String, String> itemMap10 = new HashMap<String, String>();
	        HashMap<String, String> itemMap11 = new HashMap<String, String>();
	        HashMap<String, String> itemMap12 = new HashMap<String, String>();
	        
	        for (int i =1; i < 10; i++) {			
				itemMap.put("title","张家朝");	
				itemMap.put("bigImageView",getString(R.drawable.ic_launcher));
				listData1.add(itemMap);	
				
				itemMap2.put("title","张丰盛的");	
				itemMap2.put("bigImageView",getString(R.drawable.ic_launcher));
				listData2.add(itemMap2);	
				
				itemMap3.put("title","张飞V大飞");	
				itemMap3.put("bigImageView",getString(R.drawable.ic_launcher));
				listData3.add(itemMap3);	
				
				itemMap4.put("title","张规划图");	
				itemMap4.put("bigImageView",getString(R.drawable.ic_launcher));
				listData4.add(itemMap4);	
				
				itemMap5.put("title","张大多数");	
				itemMap5.put("bigImageView",getString(R.drawable.ic_launcher));
				listData5.add(itemMap5);	
				
				itemMap6.put("title","张北京");	
				itemMap6.put("bigImageView",getString(R.drawable.ic_launcher));
				listData6.add(itemMap6);	
				
				itemMap7.put("title","张山水");	
				itemMap7.put("bigImageView",getString(R.drawable.ic_launcher));
				listData7.add(itemMap7);	
				
				itemMap8.put("title","张哇哇哇");	
				itemMap8.put("bigImageView",getString(R.drawable.ic_launcher));
				listData8.add(itemMap8);	
				
				itemMap9.put("title","张wdw");	
				itemMap9.put("bigImageView",getString(R.drawable.ic_launcher));
				listData9.add(itemMap9);	
				
				itemMap10.put("title","张lko");	
				itemMap10.put("bigImageView",getString(R.drawable.ic_launcher));
				listData10.add(itemMap10);	
				
				itemMap11.put("title","张王串场");	
				itemMap11.put("bigImageView",getString(R.drawable.ic_launcher));
				listData11.add(itemMap11);	
				
				itemMap12.put("title","张咯品牌");	
				itemMap12.put("bigImageView",getString(R.drawable.ic_launcher));
				listData12.add(itemMap12);	
			}
	      
	    	ListAdapter adapter = new ListAdapter(this, listData1);
			list_page_view1.setAdapter(adapter);
			
			ListAdapter adapter2 = new ListAdapter(this, listData2);
			list_page_view2.setAdapter(adapter2);
			
			ListAdapter adapter3 = new ListAdapter(this, listData3);
			list_page_view3.setAdapter(adapter3);
			
			ListAdapter adapter4 = new ListAdapter(this, listData4);
			list_page_view4.setAdapter(adapter4);
			
			ListAdapter adapter5 = new ListAdapter(this, listData5);
			list_page_view5.setAdapter(adapter5);
			
			ListAdapter adapter6 = new ListAdapter(this, listData6);
			list_page_view6.setAdapter(adapter6);
			
			ListAdapter adapter7 = new ListAdapter(this, listData7);
			list_page_view7.setAdapter(adapter7);
			
			ListAdapter adapter8 = new ListAdapter(this, listData8);
			list_page_view8.setAdapter(adapter8);
			
			ListAdapter adapter9 = new ListAdapter(this, listData9);
			list_page_view9.setAdapter(adapter9);
			
			ListAdapter adapter10 = new ListAdapter(this, listData10);
			list_page_view10.setAdapter(adapter10);
			
			ListAdapter adapter11 = new ListAdapter(this, listData11);
			list_page_view11.setAdapter(adapter11);
			
			ListAdapter adapter12 = new ListAdapter(this, listData12);
			list_page_view12.setAdapter(adapter12);
			
	}




	
     /**
      * 广播接收器
      * 接受需要的栏目数，并由此可判断为哪个栏目
      */
	 private BroadcastReceiver receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();  			
				if (action.equals("news")) {
					int column = intent.getIntExtra("column",0);
					pageViews.clear();
					switch (column) {
					case 9:				
						InItView(9);
						break;
					case 4:				
						InItView(4);
						break;
					case 8:
						InItView(8);
						break;
					case 12:	
						InItView(12);
						break;
					case 6:	
						InItView(6);
						break;
					default:
						break;
					}				
				}
				
			}
		};

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		onLoad();
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		onLoad();
	}

	/**
	 * 下拉或加载更多关闭
	 */
	private void onLoad() {
		for (int i = 0; i < listArray.size(); i++) {
			
			listArray.get(i).stopRefresh();

			listArray.get(i).stopLoadMore();
			
			//sumListView.setRefreshTime(timeString);
		}
		
		
	}
}
