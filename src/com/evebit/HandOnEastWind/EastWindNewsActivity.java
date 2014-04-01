package com.evebit.HandOnEastWind;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.evebit.DB.DBSize;
import com.evebit.DB.DBTime;
import com.evebit.DB.DBUser;
import com.evebit.ListView.XListView;
import com.evebit.ListView.XListView.IXListViewListener;
import com.evebit.adapter.ListAdapter;
import com.evebit.adapter.Normal;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
		private String title2[] = {  "专 题", "企 业", "观 点", "对 话"};
		private String title3[] = {  "旅游资讯", "“驾”临天下", "名车靓影", "城市约会", "乐途影像", "名家专栏", "微博•贴士邦"};
		private String title4[] = {  "播报", "国际前研", "新车测评", "政能量", "创新观察", "人物专访", "特别关注", "特稿", "设计•研究", "试验•测试" , "工艺•材料", "公告牌"};
		private String title5[] = {  "行业资讯", "工作研究", "故障维修", "技术改造", "节能技术", "汽车研究"};
		private long firstime = 0;
		private ImageView news_choose_ImageView; //点击拉开更多频道
	    private LinearLayout newsDownLayout;//底部显示新闻的layout
	    private LinearLayout newsUpLayout;//上层选择频道的layout
	    private ImageView news_choose_ok; //点击确定选择频道
	        
	    private LinearLayout news_ad_Layout;//上层广告的layout
	    private ImageView ad_image_close,  ad_image;
	   	private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/download_ad/";  
	   	private final int SPLASH_DISPLAY_LENGHT = 5000; 
	   	    
	    private Button button1;
	    private Button button2;
	    private Button button3;
	    private Button button4;
	    private Button button5;
	    private Button button6;
	    private Button button7;
	    private Button button8;
	    private Button button9;
	    private Button button10;
	    private Button button11;
	    private Button button12;
	    private ArrayList <Button> buttons;

	    Normal normal;
		private String test = "EastWindNews";
		
		private ArrayList<TextView> textViews;
		private ViewPager viewPager;
		private ArrayList<View> pageViews;
		private HorizontalScrollView horizontalScrollView;
		private int H_width;
        private LinearLayout linearLayout;
        private XListView list_page_view1,list_page_view2,list_page_view3,list_page_view4,list_page_view5,list_page_view6,list_page_view7,list_page_view8,list_page_view9,list_page_view10,list_page_view11,list_page_view12;
        private ProgressDialog progressDialog;
        
        
	    private int LoadPage = 0; //当前为哪个频道，0,1 为第一个频道，2,3,4,5、、、0代表第一次进入
	    private int LookPage = 0; //当前的栏目id,
	    
	    private boolean flag = true; //默认可以滑动
	    private boolean Dialog =true;
	    		
	    private FinalDb db = null;//数据库对象
	    private String imageString;
	    private ArrayList<XListView> listArray = new ArrayList<XListView>();
	    private ListAdapter Adapter1 ;
	    private ListAdapter Adapter2;
	    private ListAdapter Adapter3;
	    private ListAdapter Adapter4;
	    private ListAdapter Adapter5;
	    private ListAdapter Adapter6;
	    private ListAdapter Adapter7;
	    private ListAdapter Adapter8;
	    private ListAdapter Adapter9;
	    private ListAdapter Adapter10;
	    private ListAdapter Adapter11;
	    private ListAdapter Adapter12;	    
	    private int pageId = 15;//频道id
        private int  mark [] =  {0,0,0,0,0,0,0,0,0,0,0,0};
        private String time = null;
	    private String NewsUrl = "";
	    private ArrayList<HashMap<String, String>> dateMap =  new ArrayList<HashMap<String, String>>();	 
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
        private ArrayList<ArrayList<HashMap<String, String>>>  arrayArray = new ArrayList<ArrayList<HashMap<String,String>>>();//存放所有数据数组的数组

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
		H_width = (getWindowManager().getDefaultDisplay().getWidth() /18);

		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			
			//String regEx="•";  
	        // Pattern   p   =   Pattern.compile(regEx);     
	       //  Matcher   m   =   p.matcher(title[i]);     	      					
			
	         if(title[i].length()==2){
	        	 textView.setLayoutParams(new LayoutParams(H_width*3,LayoutParams.WRAP_CONTENT));
	         }else if (title[i].length()==3) {
	        	 textView.setLayoutParams(new LayoutParams(H_width*4,LayoutParams.WRAP_CONTENT));
			}else if (title[i].length()==4){
	        	 textView.setLayoutParams(new LayoutParams(H_width*5,LayoutParams.WRAP_CONTENT));
	         }else if (title[i].length()==5){
	        	 textView.setLayoutParams(new LayoutParams(11*H_width/2,LayoutParams.WRAP_CONTENT));
			}else {
				 textView.setLayoutParams(new LayoutParams(13*H_width/2,LayoutParams.WRAP_CONTENT));
			}//H_width*m.replaceAll("").trim().length()
			
			textView.setText(title[i]);
			textView.setTextSize(20);
			textView.setTextColor(0x80FFFFFF);
		
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			textViews.add(textView);
			buttons.get(i).setText(title[i]);	
			buttons.get(i).setTextSize(15);
			
			View view = new View(this);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//layoutParams.width = 1;
			//layoutParams.height = height - 40;
			layoutParams.gravity = Gravity.CENTER;
			view.setLayoutParams(layoutParams);
			//view.setBackgroundColor(Color.GRAY);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
			}
			//Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());
			for (int j = title.length; j < 12; j++) {
				buttons.get(j).setVisibility(View.INVISIBLE);
			}
			
			
			
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
				//textViews.get(id).setBackgroundDrawable(new BitmapDrawable(bitmap));
				textViews.get(id).setTextColor(Color.WHITE);
				//textViews.get(id).setBackgroundResource(R.drawable.textbg_true);
				if (title[i].length()>2) {
					textViews.get(id).setBackgroundResource(R.drawable.scoll_ture4);
				} else {
					textViews.get(id).setBackgroundResource(R.drawable.scoll_ture2);
				}
				
				if (i > 2) {
					horizontalScrollView.smoothScrollTo((textViews.get(i).getWidth() * i - 180), 0);
				} else {
					horizontalScrollView.smoothScrollTo(0, 0);
				}
				viewPager.setCurrentItem(i);
			} else {
				textViews.get(i).setBackgroundDrawable(new BitmapDrawable());
				textViews.get(i).setTextColor(0xFFFFE8BF);
				textViews.get(i).setBackgroundResource(R.drawable.textbg_flase);
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


	      time = Time();
	      db = FinalDb.create(this);//实例化数据对象
	      
	      normal = new Normal(this);
	     
	      
	      pageId = Integer.valueOf(getShared());
	      
	      switch (pageId) {
		case 15:
			 InItView(9);
			break;
		case 24:
			 InItView(4);	
			break;
		case 28:
			 InItView(8);
			break;
		case 35:
			 InItView(12);
			break;
		case 47:
			 InItView(6);
			break;
		default:
			break;
		}

	      
	}
	
	private String getShared()
	{
		String user_name_string= null;
		SharedPreferences settings = this.getSharedPreferences("CheckLoginXML", 0);
		user_name_string = settings.getString("CheckLogin", "");	
		return user_name_string;
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
		   eastWindNewsGroup = (ViewGroup)inflater.inflate(R.layout.activity_easewindnews, null); 
		   viewPager = (ViewPager)eastWindNewsGroup.findViewById(R.id.tabView1_container);	  
		   viewPager.setAdapter(new myPagerView());
	       setContentView(eastWindNewsGroup);
	       horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
	       linearLayout = (LinearLayout) findViewById(R.id.ll_main);

	       news_choose_ImageView = (ImageView)findViewById(R.id.news_choose);
	       news_choose_ok = (ImageView)findViewById(R.id.news_choose_ok);
	   
	       news_ad_Layout = (LinearLayout)findViewById(R.id.news_ad);
	       ad_image_close = (ImageView)news_ad_Layout.findViewById(R.id.news_image_ad_close);
	       ad_image = (ImageView)news_ad_Layout.findViewById(R.id.news_image_ad);
	       
	     
	       
	       newsUpLayout = (LinearLayout)findViewById(R.id.news_up);
	       newsDownLayout = (LinearLayout)findViewById(R.id.news_down);
	 	   button1 = (Button)newsUpLayout.findViewById(R.id.news_button1);
	 	   button2 = (Button)newsUpLayout.findViewById(R.id.news_button2);
	 	   button3 = (Button)newsUpLayout.findViewById(R.id.news_button3);
	 	   button4 = (Button)newsUpLayout.findViewById(R.id.news_button4);
	 	   button5 = (Button)newsUpLayout.findViewById(R.id.news_button5);
	 	   button6 = (Button)newsUpLayout.findViewById(R.id.news_button6);
	 	   button7 = (Button)newsUpLayout.findViewById(R.id.news_button7);
	 	   button8 = (Button)newsUpLayout.findViewById(R.id.news_button8);
	 	   button9 = (Button)newsUpLayout.findViewById(R.id.news_button9);
	 	   button10 = (Button)newsUpLayout.findViewById(R.id.news_button10);
	 	   button11 = (Button)newsUpLayout.findViewById(R.id.news_button11);
	 	   button12 = (Button)newsUpLayout.findViewById(R.id.news_button12);
	      
	 	   buttons = new ArrayList<Button>();
	 	  
	       buttons.add(button1);
	       buttons.add(button2);
	       buttons.add(button3);
	       buttons.add(button4);
	       buttons.add(button5);
	       buttons.add(button6);
	       buttons.add(button7);
	       buttons.add(button8);
	       buttons.add(button9);
	       buttons.add(button10);
	       buttons.add(button11);
	       buttons.add(button12);
	       
	       
	       
	       switch (i) {
		case 9:	
			 showAd("ad1.jpg");
			 InItTitle1(title1);
			 setSelector(0,title1);	
			 list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
			 list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
			 list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
			 list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
			 list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
			 list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
			 list_page_view7 = (XListView)page_view_7.findViewById(R.id.page_view_7);
			 list_page_view8 = (XListView)page_view_8.findViewById(R.id.page_view_8); 
			 list_page_view9 = (XListView)page_view_9.findViewById(R.id.page_view_9);
			 listCheck(i);
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
        	 showAd("ad2.jpg");
            InItTitle1(title2);
        	setSelector(0,title2);
        	list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
      	    list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
      	    list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
      	    list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
      	  listCheck(i);
      	    listArray.add(list_page_view1);
      	    listArray.add(list_page_view2);
      	    listArray.add(list_page_view3);
      	    listArray.add(list_page_view4);
      	    array_deledate();
        	setListView();
			break;
        case 8:
        	 showAd("ad3.jpg");
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
            listCheck(i);
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
        	 showAd("ad4.jpg");
        
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
    	        listCheck(i);
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
        	 showAd("ad5.jpg");

        	InItTitle1(title5);
        	setSelector(0,title5);
 		   list_page_view1 = (XListView)page_view_1.findViewById(R.id.page_view_1);
 	       list_page_view2 = (XListView)page_view_2.findViewById(R.id.page_view_2); 
 	       list_page_view3 = (XListView)page_view_3.findViewById(R.id.page_view_3);
 	       list_page_view4 = (XListView)page_view_4.findViewById(R.id.page_view_4);
 	       list_page_view5 = (XListView)page_view_5.findViewById(R.id.page_view_5);
 	       list_page_view6 = (XListView)page_view_6.findViewById(R.id.page_view_6);
 	      listCheck(i);
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
		
	       buttonClick(i);
	       
	       ad_image_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				news_ad_Layout.setVisibility(View.GONE);
			}
		});

	       news_choose_ImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				newsUpLayout.setVisibility(View.VISIBLE);
				
			}
		});
	       
	       news_choose_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newsUpLayout.setVisibility(View.GONE);
				
			}
		});
	       
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
				//	handler.sendEmptyMessage(1);	
					
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
     *显示广告
     */
    private void showAd(String mFileName) {
		// TODO Auto-generated method stub
    	   File file = new File(ALBUM_PATH+mFileName);  
           if(file.exists())  
           {  
        	news_ad_Layout.setVisibility(View.VISIBLE);
           	Bitmap bm = BitmapFactory.decodeFile(ALBUM_PATH+mFileName);  
           	ad_image.setImageBitmap(bm);   
           	startCountTime();
           }else {
        	news_ad_Layout.setVisibility(View.GONE);
		}          
	}

    /**
     * 计时两秒，结束后结束广告
     */
    private void startCountTime() {
         // TODO Auto-generated method stub
           new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
               closeAdImage();
             }			

     }, SPLASH_DISPLAY_LENGHT);
    }
    
    
    private void closeAdImage() {
		// TODO Auto-generated method stub
    	news_ad_Layout.setVisibility(View.GONE);
	}
    
	private void buttonClick(final int choose) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    
					
					newsUpLayout.setVisibility(View.GONE);
			
					switch (choose) {
					case 9:
						setSelector(Integer.parseInt(v.getTag().toString()),title1);
						break;
					case 4:
						setSelector(Integer.parseInt(v.getTag().toString()),title2);	
						break;
					case 8:
						setSelector(Integer.parseInt(v.getTag().toString()),title3);
						break;
					case 12:
						setSelector(Integer.parseInt(v.getTag().toString()),title4);
						break;
					case 6:
						setSelector(Integer.parseInt(v.getTag().toString()),title5);
						break;
					default:
						break;
					}			
				}
			});
		}
		
	   
	}
    
    
    /**
     * 委托绑定列表点击事项
     * @param key
     */
    private void listCheck(int key)
    {
    	list_page_view1.setOnItemClickListener(new ListOnCheck());
    	list_page_view2.setOnItemClickListener(new ListOnCheck());
    	list_page_view3.setOnItemClickListener(new ListOnCheck());
    	list_page_view4.setOnItemClickListener(new ListOnCheck());
    	switch (key) {
		case 9:
			list_page_view5.setOnItemClickListener(new ListOnCheck());
	    	list_page_view6.setOnItemClickListener(new ListOnCheck());
	    	list_page_view7.setOnItemClickListener(new ListOnCheck());
	    	list_page_view8.setOnItemClickListener(new ListOnCheck());
	    	list_page_view9.setOnItemClickListener(new ListOnCheck());
			break;
		case 8:
			list_page_view5.setOnItemClickListener(new ListOnCheck());
	    	list_page_view6.setOnItemClickListener(new ListOnCheck());
	    	list_page_view7.setOnItemClickListener(new ListOnCheck());
	    	list_page_view8.setOnItemClickListener(new ListOnCheck());
			break;
		case 12:
			list_page_view5.setOnItemClickListener(new ListOnCheck());
	    	list_page_view6.setOnItemClickListener(new ListOnCheck());
	    	list_page_view7.setOnItemClickListener(new ListOnCheck());
	    	list_page_view8.setOnItemClickListener(new ListOnCheck());
	    	list_page_view9.setOnItemClickListener(new ListOnCheck());
	    	list_page_view10.setOnItemClickListener(new ListOnCheck());
	    	list_page_view11.setOnItemClickListener(new ListOnCheck());
	    	list_page_view12.setOnItemClickListener(new ListOnCheck());
			break;
		case 6:
			list_page_view5.setOnItemClickListener(new ListOnCheck());
	    	list_page_view6.setOnItemClickListener(new ListOnCheck());
			break;
		default:
			break;
		}
    }
    
    
    /**
     * 
     * @param pageID  频道ID
     * 查询数据库对应频道的日期，如果此日期与手机本地日期一致，则代表此频道已经刷新，滑动后不需要重新加载，如果不是则重新加载数据
     */
    private void selectorDate(int pageID)
    {
    	setUrl(pageID);
    	if (arrayArray.get(LookPage).size() == 0) {
	    	String condition ="nid='" + pageID+ "'";//搜索条件
			List<DBTime> list = db.findAllByWhere(DBTime.class, condition);
			if (list.size() != 0) {
				if (String.valueOf(list.get(0).getTime()).equals(time)) {
					mark[LookPage] = 1;
					AddDateListView();
				}
				else {
					if (normal.note_Intent()) {
						progressDialog = ProgressDialog.show(EastWindNewsActivity.this, "", "正在刷新...", true, false);
						progressDialog.setCancelable(true);
						Dialog = false ; 
						deleteTimeThread();
				    	dataThread(1);
					}
					else {
						Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
						mark[LookPage] = 1;
						AddDateListView();
					}
				}	
			}
			else 
			{
				if (normal.note_Intent()) {
					progressDialog = ProgressDialog.show(EastWindNewsActivity.this, "", "正在刷新...", true, false);
					progressDialog.setCancelable(true);
					Dialog = false ; 
					AddTimeThread();
			    	dataThread(1);
				}
				else {
					Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
					mark[LookPage] = 1;
					AddDateListView();
				}
			}
    	}
    }
    
    /**
     * 添加缓存数据到数组
     */
    private void AddDateListView()
    {
    	new Thread()
    	{
			@Override
			public void run() {
				// TODO Auto-generated method stub
			//	Log.v("east----593", pageId+"");
				String condition ="page='" + (pageId+LookPage)+ "'";//搜索条件
				List<DBUser> list = db.findAllByWhere(DBUser.class, condition);
				for (int i = 0; i < list.size(); i++) {
					
						HashMap<String, String> itemMap = new HashMap<String, String>();
						itemMap.put(LauchActivity.LAUCH_DATE_page,list.get(i).getPage());
						itemMap.put(LauchActivity.LAUCH_DATE_nid,list.get(i).getNid());
						itemMap.put(LauchActivity.LAUCH_DATE_node_title,list.get(i).getNode_title());
						itemMap.put(LauchActivity.LAUCH_DATE_node_created,list.get(i).getNode_created());
						itemMap.put(LauchActivity.LAUCH_DATE_field_channel,list.get(i).getField_channel());
						itemMap.put(LauchActivity.LAUCH_DATE_field_newsfrom,list.get(i).getField_newsfrom());
						itemMap.put(LauchActivity.LAUCH_DATE_field_thumbnails,list.get(i).getField_thumbnails());
						itemMap.put(LauchActivity.LAUCH_DATE_field_summary,list.get(i).getField_summary());
						itemMap.put(LauchActivity.LAUCH_DATE_body_1,list.get(i).getBody_1());
						itemMap.put(LauchActivity.LAUCH_DATE_body_2,list.get(i).getBody_2());
						arrayArray.get(LookPage).add(itemMap);
				}
				
				handler.sendEmptyMessage(4);
				
			}
    		
    	}.start();
    	
		

    }
    
    /**
     * 加载缓存数据显示
     */
    
    private void image()
	{
		String condition ="nid='" + "image"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			imageString = "flase";
		}
		else {
			imageString = list.get(0).getSize().toString();
		}
	}
    
    
    private void ShowList()
    {
    	image();
		switch (LookPage) {
		case 0:
			Adapter1= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter1);//
			break;
		case 1:
			Adapter2= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter2);//
			break;
		case 2:
			Adapter3= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter3);//
			break;
		case 3:
			Adapter4= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter4);//
			break;
		case 4:
			Adapter5= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter5);//
			break;
		case 5:
			Adapter6= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter6);//
			break;
		case 6:
			Adapter7= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter7);//
			break;
		case 7:
			Adapter8= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter8);//
			break;
		case 8:
			Adapter9= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter9);//
			break;
		case 9:
			Adapter10= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter10);//
			break;
		case 10:
			Adapter11= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter11);//
			break;
		case 11:
			Adapter12= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter12);//
			break;
		default:
			break;
		}
		
		
    }
    
    /**
     * 添加新日期
     */
    private void AddTimeThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DBTime user = new DBTime();
				user.setNid(String.valueOf(pageId+LookPage));
				user.setTime(time);
				db.save(user);
			}
			
		}.start();
	}
    /**
     * 删除日期，添加新日期
     */
    private void deleteTimeThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + (pageId+LookPage)+ "'";//删除条件,先删除这个记录然后添加
				db.deleteByWhere(DBTime.class, condition);
				handler.sendEmptyMessage(2);
			}
			
		}.start();
	}
    
    
    private String Time()
    {
    	String time = null;
    	Calendar calendar = Calendar.getInstance();//取得当前时间
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH));
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		time = year+"-"+month +"-" + day;
    	return time;
    }
	/**
	 * 9   第1个频道 共9个栏目
	 */
    private void setListView()
    {  
       selectorDate(pageId);
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
						flag = true;
					}
					else {
						for (int i = 0; i < arrayArray.size(); i++) {
							arrayArray.get(i).clear();
							listArray.clear();
							mark[i] = 0;//清空页数
						}
						pageViews.clear();
						flag = true;
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
						case 10:
							unregisterReceiver(receiver);
							break;
						
						default:
							break;
						}	
					}			
				}
			}
		};

		class ListOnCheck implements OnItemClickListener
	    {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EastWindNewsActivity.this, WebActivity.class);
				
				intent.putExtra(LauchActivity.LAUCH_DATE_nid, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_nid));
				intent.putExtra(LauchActivity.LAUCH_DATE_node_title, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_node_title));
				intent.putExtra(LauchActivity.LAUCH_DATE_node_created, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_node_created));
				intent.putExtra(LauchActivity.LAUCH_DATE_field_channel, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_field_channel));
				intent.putExtra(LauchActivity.LAUCH_DATE_field_newsfrom, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_field_newsfrom));
				intent.putExtra(LauchActivity.LAUCH_DATE_body_1, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_body_1));
				intent.putExtra(LauchActivity.LAUCH_DATE_body_2, arrayArray.get(LookPage).get(arg2-1).get(LauchActivity.LAUCH_DATE_body_2));
				
				startActivity(intent);
			}
			
	    }
		
    /**
     * 下拉刷新
     */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (normal.note_Intent()) {
			if (flag) {//执行下拉刷新
				mark[LookPage] = 0 ;
				flag = false;//刷新期间不允许viewpage滑动
				dataThread(6);
			}
		}
		else {
			onLoad();
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
		
		//onLoad();
	}

/**
 * 加载更多
 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (normal.note_Intent())
		{
		if (arrayArray.get(LookPage).size()/(mark[LookPage]+1) == 15) {
			if (flag) {//执行加载更多
				mark[LookPage] = mark[LookPage] + 1;//记录下拉页数
				setUrl(pageId + LookPage);
				flag = false;//加载更多期间不允许vviewpage滑动
				dataThread(8);
			}
		}
		else {
			handler.sendEmptyMessage(7);	
		}
	}
	else {
		onLoad();
		Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
	}
	//	selectorDate(pageId + LookPage);//判断数据地址
		//onLoad();
	}

	
	
	/**
	 * 下拉或加载更多关闭
	 */
	private void onLoad() {
		flag =true;
		
		for (int i = 0; i < listArray.size(); i++) {		
			listArray.get(i).stopRefresh();
			listArray.get(i).stopLoadMore();
		}
	
	}
	

	private void dataThread(final int what) {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				Test_Bean data;
				try {	
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
					
					for (Test_Model test_Model : datalist) {	
						HashMap<String, String> itemMap = new HashMap<String, String>();
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
					if (dateMap.size() == 0) {
						handler.sendEmptyMessage(7);	
					}
					else {
						handler.sendEmptyMessage(what);	
					}
									
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
	@SuppressLint("SimpleDateFormat")
	private void setOneListView(int what) {
		// TODO Auto-generated method stub   
		for (int i = 0; i < dateMap.size(); i++) {
			HashMap<String, String> itemMap = new HashMap<String, String>();
			
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			itemMap.put(LauchActivity.LAUCH_DATE_nid,dateMap.get(i).get(LauchActivity.LAUCH_DATE_nid));
			itemMap.put(LauchActivity.LAUCH_DATE_node_title,(dateMap.get(i).get(LauchActivity.LAUCH_DATE_node_title)));
			String time = dateMap.get(i).get(LauchActivity.LAUCH_DATE_node_created)+"000";
			itemMap.put(LauchActivity.LAUCH_DATE_node_created,sdf.format(new Date(Long.parseLong(time))));
			itemMap.put(LauchActivity.LAUCH_DATE_field_channel,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_channel));
			itemMap.put(LauchActivity.LAUCH_DATE_field_newsfrom,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_newsfrom));
			itemMap.put(LauchActivity.LAUCH_DATE_field_thumbnails,(dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_thumbnails)));
			itemMap.put(LauchActivity.LAUCH_DATE_field_summary,dateMap.get(i).get(LauchActivity.LAUCH_DATE_field_summary));
			itemMap.put(LauchActivity.LAUCH_DATE_body_1,dateMap.get(i).get(LauchActivity.LAUCH_DATE_body_1));
			itemMap.put(LauchActivity.LAUCH_DATE_body_2,dateMap.get(i).get(LauchActivity.LAUCH_DATE_body_2));
			arrayArray.get(LookPage).add(itemMap);
		}
		dateMap.clear();
		handler.sendEmptyMessage(what);
	}
/**
 * 下拉刷新数据添加
 */
	private void setShowListView()
	{
		//ListAdapter adapter = adaptersArray.get(LookPage);
		image();
		switch (LookPage) {
		case 0:
			Adapter1= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter1);//
			break;
		case 1:
			Adapter2= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter2);//
			break;
		case 2:
			Adapter3= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter3);//
			break;
		case 3:
			Adapter4= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter4);//
			break;
		case 4:
			Adapter5= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter5);//
			break;
		case 5:
			Adapter6= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter6);//
			break;
		case 6:
			Adapter7= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter7);//
			break;
		case 7:
			Adapter8= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter8);//
			break;
		case 8:
			Adapter9= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter9);//
			break;
		case 9:
			Adapter10= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter10);//
			break;
		case 10:
			Adapter11= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter11);//
			break;
		case 11:
			Adapter12= new ListAdapter(this, arrayArray.get(LookPage),imageString);
			listArray.get(LookPage).setAdapter(Adapter12);//
			break;
		default:
			break;
		}
		 
		handler.sendEmptyMessage(3);
	}
	
	/**
	 * 加载更多数据添加
	 */
	private void addShowListView()
	{
		switch (LookPage) {
		case 0:
			Adapter1.notifyDataSetChanged();
			break;
		case 1:
			Adapter2.notifyDataSetChanged();
			break;
		case 2:
			Adapter3.notifyDataSetChanged();
			break;
		case 3:
			Adapter4.notifyDataSetChanged();
			break;
		case 4:
			Adapter5.notifyDataSetChanged();
			break;
		case 5:
			Adapter6.notifyDataSetChanged();
			break;
		case 6:
			Adapter7.notifyDataSetChanged();
			break;
		case 7:
			Adapter8.notifyDataSetChanged();
			break;
		case 8:
			Adapter9.notifyDataSetChanged();
			break;
		case 9:
			Adapter10.notifyDataSetChanged();
			break;
		case 10:
			Adapter11.notifyDataSetChanged();
			break;
		case 11:
			Adapter12.notifyDataSetChanged();
			break;
		default:
			break;
		}

		onLoad();
	}
	/**
	 * 通过handler
	 * what:
	 * 1加载数据
	 * 2执行添加日期
	 * 3执行对比数据
	 * 4显示缓存数据列表
	 * 5显示第一次加载数据列表
	 * 6检测属性数据是否有更新，如果有更新则重新刷新列表，如果没有则不做动作
	 * 7没有数据情况提示
	 */
	private Handler handler = new Handler()
	{
		
		@Override
		public void handleMessage(Message msg) {
		
			switch (msg.what) {
			case 1:
				if (!Dialog) {
					progressDialog.dismiss();
					Dialog = true;
				}
				setOneListView(5);
				break;
			case 2:
				AddTimeThread();
				break;
			case 3:
				Contrast();
				break;
			case 4:
				ShowList();
				break;
			case 5:
				setShowListView();
				break;
			case 6://检测属性数据是否有更新，如果有更新则重新刷新列表，如果没有则不做动作
				if (dateMap.size() == arrayArray.get(LookPage).size()) {
					if (dateMap.get(dateMap.size()-1).get(LauchActivity.LAUCH_DATE_nid).toString().equals(arrayArray.get(LookPage).get(dateMap.size()-1).get(LauchActivity.LAUCH_DATE_nid).toString())) {
						
						dateMap.clear();
						handler.sendEmptyMessage(7);
					}
					else {
						if (!Dialog) {
							progressDialog.dismiss();
							Dialog = true;
						}
						onLoad();
						arrayArray.get(LookPage).clear();
						setOneListView(5);
					}
				}
				else {
					if (!Dialog) {
						progressDialog.dismiss();
						Dialog = true;
					}
					onLoad();
					arrayArray.get(LookPage).clear();
					setOneListView(5);
				}
				break;
			case 7://没有数据情况提示
				if (!Dialog) {
					progressDialog.dismiss();
					Dialog = true;
				}
				onLoad();
				Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				setOneListView(9);
				break;
			case 9:
				addShowListView();
				break;
			default:
				break;
			}
		}
	
	};
	
	/**
	 * 比较数据
	 */
	private void Contrast()
	{
		String condition ="page='" + (pageId+LookPage)+ "'";//搜索条件
		List<DBUser> list = db.findAllByWhere(DBUser.class, condition);
		if (list.size() == 0) {
			AddDBUser();
		}
		else {
			db.deleteByWhere(DBUser.class, condition);
			AddDBUser();
		}
	}
	
	
	
	/**
	 * 添加缓存数据
	 */
	private void AddDBUser()
	{   
		brodeCache();
		
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DBUser dbUser = new DBUser();
				for (int i = 0; i < arrayArray.get(LookPage).size(); i++) {
					dbUser.setPage(String.valueOf(pageId+LookPage));	
					dbUser.setNid(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_nid));
					dbUser.setNode_title(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_node_title));
					dbUser.setNode_created(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_node_created));
					dbUser.setField_channel(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_field_channel));
					dbUser.setField_newsfrom(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_field_newsfrom));
					dbUser.setField_thumbnails(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_field_thumbnails));
					dbUser.setField_summary(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_field_summary));
					dbUser.setBody_1(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_body_1));
					dbUser.setBody_2(arrayArray.get(LookPage).get(i).get(LauchActivity.LAUCH_DATE_body_2));
					db.save(dbUser);
				}
				
			}
			
		}.start();
	}
	
	
	
	
	/**
	 * 通知刷新缓存
	 */
	public void brodeCache(){
		Intent intent =  new Intent();
		intent.setAction("cache");
		sendBroadcast(intent);
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
				Toast.makeText(EastWindNewsActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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

	/**
	 * 屏蔽菜单键
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
