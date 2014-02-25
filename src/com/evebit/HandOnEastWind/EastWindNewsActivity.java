package com.evebit.HandOnEastWind;

import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.ListView.XListView;
import com.evebit.ListView.XListView.IXListViewListener;
import com.evebit.adapter.ListAdapter;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;

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
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
        
	    private int LoadPage = 0; //当前为哪个频道，0,1 为第一个频道，2,3,4,5、、、0代表第一次进入
	    private int LookPage = 0; //当前的栏目id,
	    
	    private boolean flag = true; //默认可以滑动

	    
	    private ArrayList<XListView> listArray = new ArrayList<XListView>();
	    
	    private int pageId = 15;//频道id
        
        private int  mark [] =  {0,0,0,0,0,0,0,0,0,0,0,0};
        
	    private String NewsUrl = "";
	    private ArrayList<HashMap<String, Object>> dateMap =  new ArrayList<HashMap<String, Object>>();	 
	    private ArrayList<HashMap<String, Object>> listData1 = new ArrayList<HashMap<String, Object>>();	      
        private ArrayList<HashMap<String, Object>> listData2 = new ArrayList<HashMap<String, Object>>();	         
        private ArrayList<HashMap<String, Object>> listData3 = new ArrayList<HashMap<String, Object>>();	     
        private ArrayList<HashMap<String, Object>> listData4 = new ArrayList<HashMap<String, Object>>();	        
        private ArrayList<HashMap<String, Object>> listData5 = new ArrayList<HashMap<String, Object>>();	      
        private ArrayList<HashMap<String, Object>> listData6 = new ArrayList<HashMap<String, Object>>();	      
        private ArrayList<HashMap<String, Object>> listData7 = new ArrayList<HashMap<String, Object>>();	      
        private ArrayList<HashMap<String, Object>> listData8 = new ArrayList<HashMap<String, Object>>();	       
        private ArrayList<HashMap<String, Object>> listData9 = new ArrayList<HashMap<String, Object>>();	  
        private ArrayList<HashMap<String, Object>> listData10 = new ArrayList<HashMap<String, Object>>();	        
        private ArrayList<HashMap<String, Object>> listData11 = new ArrayList<HashMap<String, Object>>();	       
        private ArrayList<HashMap<String, Object>> listData12 = new ArrayList<HashMap<String, Object>>();	  
        private ArrayList<ArrayList<HashMap<String, Object>>>  arrayArray = new ArrayList<ArrayList<HashMap<String,Object>>>();//存放所有数据数组的数组

	    void InItView(int i) {
	    	
	    	    pageViews = new ArrayList<View>(); 
			    LayoutInflater inflater = getLayoutInflater(); 
			    
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
	        case 4:
				 LoadPage = 2;
				break;
			case 6:
				 LoadPage = 5;
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 		     
			     
				break;
			case 8:
				 LoadPage = 3;     //12个pageView的容器对应的xml
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 
			     pageViews.add(page_view_7);

			     break;
			case 9:
				 LoadPage = 1;
				 pageViews.add(page_view_5);
			     pageViews.add(page_view_6); 
			     pageViews.add(page_view_7);
			     pageViews.add(page_view_8);
			     pageViews.add(page_view_9);
			    
				break;
			case 12:
				 LoadPage = 4;
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
 * 顶部对应的滑动条
 * @param title 传递对应的栏目列表
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
			//Log.e("aa", "text_width=" + textView.getWidth());
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
			//Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());

		}
	}

/**
 * 此处对应 点击上部的滑动条中的某一项，跳转到相应的viewpager
 * @param id 对应的滚动条中的那一栏目
 * @param title 滑动条中栏目数组
 */
	public void setSelector(int id,String title[]) {
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
		
		  //实例化广播接收器
		  IntentFilter intentFilter = new IntentFilter();  
	      intentFilter.addAction("news");  
	      registerReceiver(receiver, intentFilter);
	      arrayArray.add(listData1);
	      arrayArray.add(listData2);
	      arrayArray.add(listData3);
	      arrayArray.add(listData4);
	      arrayArray.add(listData5);
	      arrayArray.add(listData6);
	      arrayArray.add(listData7);
	      arrayArray.add(listData8);
	      arrayArray.add(listData9);
	      arrayArray.add(listData10);
	      arrayArray.add(listData11);
	      arrayArray.add(listData12);
	      //默认为东风汽车报频道，此频道有9个栏目  
	      InItView(9);
	}
	
	
	public void setUrl(int pageID){
		NewsUrl = LauchActivity.LAUCH_URL + "mobile/news/?field_channel_tid="+pageID+"&page="+mark[LookPage];		
	}

	/**
	 * 设置新闻列表的数据
	 * @param i 对应的频道
	 * 9   第1个频道 共9个栏目
	 * 4   第2个频道 共4个栏目
	 * 8   第3个频道 共8个栏目
	 * 12 第4个频道 共12个栏目
	 * 6   第5个频道 共6个栏目
	 * 
	 */
    public void setView(final int i){
    	   LayoutInflater inflater = getLayoutInflater(); 
		   eastWindNewsGroup = (ViewGroup)inflater.inflate(R.layout.tab_view1, null); 
		   viewPager = (ViewPager)eastWindNewsGroup.findViewById(R.id.tabView1_container);	  
		   viewPager.setAdapter(new myPagerView());
	       setContentView(eastWindNewsGroup);
	       horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
	       linearLayout = (LinearLayout) findViewById(R.id.ll_main);

	       
	       switch (i) {
		case 9:		
			 InItTitle1(title1);
			 setSelector(0,title1);	
			 Log.v("eastwindnews --- 316-- ", "---");
			 list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
			 list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
			 list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
			 list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
			 list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
			 list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
			 list_page_view7 = (XListView)page_view_7.findViewById(R.id.page_view_7);
			 list_page_view8 = (XListView)page_view_8.findViewById(R.id.page_view_8); 
			 list_page_view9 = (XListView)page_view_9.findViewById(R.id.page_view_9);
			 listArray.add(list_page_view1);
			 listArray.add(list_page_view2);
			 listArray.add(list_page_view3);
			 listArray.add(list_page_view4);
			 listArray.add(list_page_view5);
			 listArray.add(list_page_view6);
			 listArray.add(list_page_view7);
			 listArray.add(list_page_view8);
			 listArray.add(list_page_view9);
			 array_deledate();
			 setListView();
			break;
        case 4:
            InItTitle1(title2);
        	setSelector(0,title2);
        	list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
      	    list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
      	    list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
      	    list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
      	    listArray.add(list_page_view1);
      	    listArray.add(list_page_view2);
      	    listArray.add(list_page_view3);
      	    listArray.add(list_page_view4);
      	    array_deledate();
        	setListView();
			break;
        case 8:
        	InItTitle1(title3);
        	setSelector(0,title3);
        	list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
            list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
            list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
            list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
            list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
            list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
            list_page_view7 = (XListView)page_view_7.findViewById(R.id.page_view_7);
            list_page_view8 = (XListView)page_view_8.findViewById(R.id.page_view_8); 
            listArray.add(list_page_view1);
            listArray.add(list_page_view2);
            listArray.add(list_page_view3);
            listArray.add(list_page_view4);
            listArray.add(list_page_view5);
            listArray.add(list_page_view6);
            listArray.add(list_page_view7);
            listArray.add(list_page_view8);
            array_deledate();
        	setListView();
			break;
        case 12:
        	
        
        	 InItTitle1(title4);
        	 setSelector(0,title4);
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
    	       array_deledate();
        	 setListView();
			break;
        case 6:
        	

        	InItTitle1(title5);
        	setSelector(0,title5);
 		   list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
 	       list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
 	       list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
 	       list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
 	       list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
 	       list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
 	       listArray.add(list_page_view1);
 	       listArray.add(list_page_view2);
 	       listArray.add(list_page_view3);
 	       listArray.add(list_page_view4);
 	       listArray.add(list_page_view5);
 	       listArray.add(list_page_view6);
 	       array_deledate();
           setListView();
			break;
		default:
			break;
		}
		
			viewPager.setAdapter(new myPagerView());
			viewPager.clearAnimation();
			viewPager.setOnTouchListener( new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if (!flag) {
						return true; //不能滑动
					}else {
						return false;
					}
				}
			});
			
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int arg0) {	
					LookPage = arg0;
					handler.sendEmptyMessage(1);	
					
					switch (i) {
					case 9:
						setSelector(arg0,title1);
						selectorDate(pageId+LookPage);//切换数据
						break;
					case 4:
						setSelector(arg0,title2);	
						selectorDate(pageId+LookPage);//切换数据
						break;
					case 8:
						setSelector(arg0,title3);
						selectorDate(pageId+LookPage);//切换数据
						break;
					case 12:
						setSelector(arg0,title4);
						selectorDate(pageId+LookPage);//切换数据
						break;
					case 6:
						setSelector(arg0,title5);
						selectorDate(pageId+LookPage);//切换数据
						break;
					default:
						break;
					}
					
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {

				}
			});
		}

    /**
     * 
     * @param pageID  频道ID
     */
    private void selectorDate(int pageID)
    {
    	Log.v("-----east----483", String.valueOf(LookPage));
    	setUrl(pageID);
    	dataThread();
    }
    
    
    /**
     * 点击事件，loadpage对应的其相对应的栏目
     * 0,1 对应第1个栏目
     * 2   对应第2个栏目 
     * 3   对应第3个栏目
     * 4   对应第4个栏目
     * 5   对应第5个栏目
     */
		@Override
		public void onClick(View v) {
			LookPage = v.getId();
			Log.v("eastwindnews---lookpage ----", String.valueOf(LookPage));
			switch (LoadPage) {
			case 0:
				setSelector(v.getId(),title1);
				break;
			case 1:
				setSelector(v.getId(),title1);
				break;
			case 2:
				setSelector(v.getId(),title2);	
				break;
			case 3:
				setSelector(v.getId(),title3);
				break;
			case 4:
				setSelector(v.getId(),title4);
				break;
			case 5:
				setSelector(v.getId(),title5);
				break;
			default:
				break;
			}	
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
    
	/**
	 * 设置需要获取数据的url
	 * 不同频道field_channel_tid id不同
	 * 默认为第一页的数据 ， 点击加载更多page+1，添加第后一页的数据，假设没有，提示没有更多 添加page参数
	 */
	

		/**
		 * 委托绑定listview的下拉与加载事件
		 */
	private void array_deledate()
	{
		   for (int i = 0; i < listArray.size(); i++) {
		    	   listArray.get(i).setPullLoadEnable(true);
		    	   listArray.get(i).setXListViewListener(this);
			}
	}
		
	/**
	 * 9   第1个频道 共9个栏目
	 */
       private void setListView(){  
           setUrl(pageId);
           dataThread();
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
					if (column == LoadPage) {
						
					}
					else {
						for (int i = 0; i < arrayArray.size(); i++) {
							arrayArray.get(i).clear();
							mark[i] = 0;//清空页数
						}
						pageViews.clear();
						
						switch (column) {
						case 1:	
							LookPage = 0;
							pageId = 15;
							InItView(9);
							break;			
						case 2:	
					        pageId = 24;
							LookPage = 0;
							InItView(4);
							break;
						case 3:
						    pageId = 28;
							LookPage = 0;
							InItView(8);
							break;
						case 4:	
						    pageId = 35;
							LookPage = 0;
							InItView(12);
							break;
						case 5:	
					    	pageId = 47;
							LookPage = 0;
							InItView(6);
							break;
						default:
							break;
						}	
					}
								
				}
				
			}
		};

    /**
     * 下拉刷新
     */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
		//mark = 0;	
		mark[LookPage] = 0 ;
		onLoad();
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
		
		mark[LookPage] = mark[LookPage] + 1;//记录下拉页数
	//	selectorDate(pageId + LookPage);//判断数据地址
		onLoad();
	}

	/**
	 * 下拉或加载更多关闭
	 */
	private void onLoad() {
		for (int i = 0; i < listArray.size(); i++) {		
			listArray.get(i).stopRefresh();
			listArray.get(i).stopLoadMore();
		}
	
	}
	
	/**
	 * 添加jason中读取数据,传入url
	 */
	private void dataThread() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				Test_Bean data;
				try {	
					  Log.v("-------470------", NewsUrl);
					data = DataManeger.getTestData(NewsUrl);
					ArrayList<Test_Model> datalist = data.getData();
					/**
					nid;//文章ID
					node_title;//文章标题
					node_created;//文章创建时间
					field_channel;//频道
					field_newsfrom;//新闻来源
					field_thumbnails;//文章缩略图
					field_summary;//文章摘要
					body_1;//图片格式详细内容
					body_2;//无图片格式详细内容
					 */
					HashMap<String, Object> itemMap = new HashMap<String, Object>();
					for (Test_Model test_Model : datalist) {					    
						itemMap.put(LauchActivity.LAUCH_DATE_nid,(test_Model.getId()==null? "": test_Model.getId()));
						itemMap.put(LauchActivity.LAUCH_DATE_node_title,(test_Model.getNode_title()==null? "": test_Model.getNode_title()));	
						itemMap.put(LauchActivity.LAUCH_DATE_node_created, (test_Model.getNode_created()==null? "": test_Model.getNode_created()));
						itemMap.put(LauchActivity.LAUCH_DATE_field_channel, (test_Model.getField_channel()==null? "": test_Model.getField_channel()));
						itemMap.put(LauchActivity.LAUCH_DATE_field_newsfrom,(test_Model.getField_newsfrom()==null? "": test_Model.getField_newsfrom()));
						itemMap.put(LauchActivity.LAUCH_DATE_field_thumbnails, (test_Model.getField_thumbnails()==null? "": test_Model.getField_thumbnails()));
						itemMap.put(LauchActivity.LAUCH_DATE_field_summary, (test_Model.getField_summary()==null? "": test_Model.getField_summary()));
						itemMap.put(LauchActivity.LAUCH_DATE_body_1, (test_Model.getBody_1()==null? "": test_Model.getBody_1()));
						itemMap.put(LauchActivity.LAUCH_DATE_body_2, (test_Model.getBody_2()==null? "": test_Model.getBody_2()));
						dateMap.add(itemMap);	
					}
					
					handler.sendEmptyMessage(1);					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}			
		}.start();
	}

	/**
	 * 把从json中获取的数据存入listview中
	 * 此为一个列表的数据
	 */
	private void setOneListView(XListView list_page_view, ArrayList<HashMap<String, Object>> listData) {
		// TODO Auto-generated method stub   
		
		for (int i = 0; i < dateMap.size(); i++) {
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put(LauchActivity.LAUCH_DATE_nid,dateMap.get(i).get(LauchActivity.LAUCH_DATE_nid));
			itemMap.put(LauchActivity.LAUCH_DATE_node_title,("¥："+dateMap.get(i).get(LauchActivity.LAUCH_DATE_node_title)));
			itemMap.put(LauchActivity.LAUCH_DATE_node_created,dateMap.get(i).get(LauchActivity.LAUCH_DATE_node_created));
			itemMap.put(LauchActivity.LAUCH_DATE_field_channel,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_channel));
			itemMap.put(LauchActivity.LAUCH_DATE_field_newsfrom,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_newsfrom));
			itemMap.put(LauchActivity.LAUCH_DATE_field_thumbnails,(dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_thumbnails)));
			itemMap.put(LauchActivity.LAUCH_DATE_field_summary,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_summary));
			itemMap.put(LauchActivity.LAUCH_DATE_body_1,dateMap.get(i).get(LauchActivity.LAUCH_DATE_body_1));
			itemMap.put(LauchActivity.LAUCH_DATE_body_2,dateMap.get(i).get(LauchActivity.LAUCH_DATE_body_2));
			listData.add(itemMap);
		}
		dateMap.clear();
		ListAdapter adapter = new ListAdapter(this, listData);
		list_page_view.setAdapter(adapter);//
	}
	
	/**
	 * 选择显示不同的列表
	 */
	private void chooseShowView(){
		if (LoadPage == 0 || LoadPage ==1) {//判断是哪个频道
			switch (LookPage) {//判断是哪个栏目
			case 0:
				setOneListView(list_page_view1,listData1);		
				break;
			case 1:
				setOneListView(list_page_view2,listData2);					
				break;
			case 2:
				setOneListView(list_page_view3,listData3);	
				break;
			case 3:
				setOneListView(list_page_view4,listData4);	
				break;
			case 4:
				setOneListView(list_page_view5,listData5);	
				break;
			case 5:
				setOneListView(list_page_view6,listData6);	
				break;
			case 6:
				setOneListView(list_page_view7,listData7);	
				break;
			case 7:
				setOneListView(list_page_view8,listData8);	
				break;
			case 8:
				setOneListView(list_page_view9,listData9);	
				break;
			default:
				break;
			}
		}else if(LoadPage == 2){
			switch (LookPage) {
			case 0:
				setOneListView(list_page_view1,listData1);		
				break;
			case 1:
				setOneListView(list_page_view2,listData2);						
				break;
			case 2:
				setOneListView(list_page_view3,listData3);		
				break;
			case 3:
				setOneListView(list_page_view4,listData4);		
				break;
			default:
				break;
			}
		}else if(LoadPage == 3){
			switch (LookPage) {
			case 0:
				setOneListView(list_page_view1,listData1);		
				break;
			case 1:
				setOneListView(list_page_view2,listData2);				
				break;
			case 2:
				setOneListView(list_page_view3,listData3);
				break;
			case 3:
				setOneListView(list_page_view4,listData4);
				break;
			case 4:
				setOneListView(list_page_view5,listData5);
				break;
			case 5:
				setOneListView(list_page_view6,listData6);
				break;
			case 6:
				setOneListView(list_page_view7,listData7);
				break;
			case 7:
				setOneListView(list_page_view8,listData8);
			default:
				break;
			}	
		}else if(LoadPage == 4){
			switch (LookPage) {
			case 0:
				setOneListView(list_page_view1,listData1);		
				break;
			case 1:
				setOneListView(list_page_view2,listData2);					
				break;
			case 2:
				setOneListView(list_page_view3,listData3);	
				break;
			case 3:
				setOneListView(list_page_view4,listData4);	
				break;
			case 4:
				setOneListView(list_page_view5,listData5);	
				break;
			case 5:
				setOneListView(list_page_view6,listData6);	
				break;
			case 6:
				setOneListView(list_page_view7,listData7);	
				break;
			case 7:
				setOneListView(list_page_view8,listData8);	
				break;
			case 8:
				setOneListView(list_page_view9,listData9);	
				break;
			case 9:
				setOneListView(list_page_view10,listData10);	
				break;
			case 10:
				setOneListView(list_page_view11,listData11);	
				break;
			case 11:
				setOneListView(list_page_view12,listData12);	
				break;
			default:
				break;
			}
		}else if (LoadPage == 5) {
			switch (LookPage) {
			case 0:
				setOneListView(list_page_view1,listData1);		
				break;
			case 1:
				setOneListView(list_page_view2,listData2);				
				break;
			case 2:
				setOneListView(list_page_view3,listData3);
				break;
			case 3:
				setOneListView(list_page_view4,listData4);
				break;
			case 4:
				setOneListView(list_page_view5,listData5);
				break;
			case 5:
				setOneListView(list_page_view6,listData6);
				break;
			default:
				break;
			}		
		}
		
	}
	
	/**
	 * 通过handler
	 */
	private Handler handler = new Handler()
	{
		
		@Override
		public void handleMessage(Message msg) {
		
			switch (msg.what) {
			case 1:
				
				chooseShowView();
				break;

			default:
				break;
			}
		}
	
	};
	
	
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
