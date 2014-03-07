package com.evebit.HandOnEastWind;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Bean_News;
import com.evebit.json.Test_Model;
import com.evebit.json.Test_Model_News;
import com.evebit.json.Y_Exception;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 导航页面 activity_navigation
 * 此页面包含5个频道
 * 东风汽车报 (9个栏目)：头条、要闻、生产经营、东风党建、和谐东风、东风人、东风文艺、专题报道、四城视点
 * 东风(4个栏目)：专题、企业、观点、对话
 * 汽车之旅(8个栏目)：旅游资讯、“驾”临天下、名车靓影、城市约会、乐途影像、名家专栏、微博·贴士邦
 * 汽车科技(12个栏目)：播报、国际前研、新车测评、政能量、创新观察、人物专访、特别关注、特稿、设计•研究、试验•测试、工艺•材料、公告牌
 * 维修装备技术(6个栏目)：行业资讯、工作研究、故障维修、技术改造、节能技术、汽车研究
 * 
 * 设计逻辑
 * 此页面为主选项卡默认显示的导航页，可支持手势滑动至初始页面
 * 点击不同的栏目，发送广播至主选项卡页面TabMainActivity，切换到新闻频道
 * 并且发送广播至新闻页面EastWindNewsActivity，告知需要几个栏目
 * @author guan
 *
 */

public class NavigationActivity extends Activity  implements OnTouchListener,  OnGestureListener {

	//滑动功能
	private long firstime = 0;
	 GestureDetector mGestureDetector;  
	 private static final int FLING_MIN_DISTANCE = 50;  
	 private static final int FLING_MIN_VELOCITY = 0; 
	 private TextView textView;
	 //导航页面 5个频道对应的LinearLayout区域：东风汽车报 东风 汽车之旅 汽车科技 维修装备技术
	 private LinearLayout newsLinearLayout,dongfengLinearLayout,travelLinearLayout,techLinearLayout,fixLinearLayout;

	 
	 private final static String TAG = "NavigationActivity";  
	 private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/download_ad/";  
	 private Bitmap mBitmap1;  
	 private Bitmap mBitmap2;  
	 private Bitmap mBitmap3;  
	 private Bitmap mBitmap4;  
	 private Bitmap mBitmap5;  
	 private String ad1_FileName;  
	 private String ad2_FileName;  
	 private String ad3_FileName;  
	 private String ad4_FileName;  
	 private String ad5_FileName;  
	 private String ad1 = "http://zhangshangdongfeng.demo.evebit.com/mobile/adstart?nid=1160";  
	 private String ad2 = "http://zhangshangdongfeng.demo.evebit.com/mobile/adstart?nid=1161";  
	 private String ad3 = "http://zhangshangdongfeng.demo.evebit.com/mobile/adstart?nid=1162";  
	 private String ad4 = "http://zhangshangdongfeng.demo.evebit.com/mobile/adstart?nid=1163";  
	 private String ad5 = "http://zhangshangdongfeng.demo.evebit.com/mobile/adstart?nid=1164";  
	 
	 private String ad1_filePath = "";  
	 private String ad2_filePath = "";  
	 private String ad3_filePath = "";  
	 private String ad4_filePath = "";  
	 private String ad5_filePath = "";  
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		 //滑动选项卡
		 mGestureDetector = new GestureDetector(this);	 
	     LinearLayout navigationView=(LinearLayout)findViewById(R.id.navigation);  
	     //navigationView.setOnTouchListener(this);  
	    // navigationView.setLongClickable(true);  
	    
	     //导航页面 5个频道：东风汽车报 东风 汽车之旅 汽车科技 维修装备技术
	     newsLinearLayout = (LinearLayout)findViewById(R.id.navigation_news);
	     dongfengLinearLayout = (LinearLayout)findViewById(R.id.navigation_dongfeng);
	     travelLinearLayout = (LinearLayout)findViewById(R.id.navigation_travel);
	     techLinearLayout = (LinearLayout)findViewById(R.id.navigation_tech);
	     fixLinearLayout = (LinearLayout)findViewById(R.id.navigation_fix);
	     textView = (TextView)findViewById(R.id.navigation_company);
	     
	     downloadAdImage();
	     
	     
	     Log.v("---116-", "beijing");
	     
	     travelLinearLayout.setOnTouchListener(this);  
	     travelLinearLayout.setLongClickable(true);  
	     textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "正在开发中",Toast.LENGTH_SHORT).show();
			}
		});
	     
	     newsLinearLayout.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub	
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("15");
					brodeNewsColumn(1);
					brodeTabhost(1);		
					brodeCache();
				}
			});
	     
	     dongfengLinearLayout.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
				Shared("24");
					brodeNewsColumn(2); 
					brodeTabhost(1);	
					brodeCache();
			}
		});
		
		
	     travelLinearLayout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
				Shared("28");
				brodeNewsColumn(3);
				brodeTabhost(1);
				brodeCache();
			}
		});
	     
	     techLinearLayout.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    //广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("35");
					brodeNewsColumn(4);
					brodeTabhost(1);
					brodeCache();
				}
			});
	     
	     fixLinearLayout.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//广播新闻频道需要几个栏目，广播告知导航频道切换到新闻频道
					Shared("47");
					brodeNewsColumn(5);
					brodeTabhost(1);
					brodeCache();
				}
			});
	}
	
	/**
	 * 下载5个广告图片到本地
	 */
	private void downloadAdImage() {
		// TODO Auto-generated method stub
		
		new Thread(){
			public void run() {
				Test_Bean data1,data2, data3,data4,data5;
				try {
					  Log.v("-----201-----","beijingll");
					data1 = DataManeger.getTestData(ad1);
					 Log.v("-----202-----","beijingmm");
					data2 = DataManeger.getTestData(ad2);
					data3 = DataManeger.getTestData(ad3);
					data4 = DataManeger.getTestData(ad4);
					data5 = DataManeger.getTestData(ad5);
					
					
					ArrayList<Test_Model> datalist1 = data1.getData();
					ArrayList<Test_Model> datalist2 = data1.getData();
					ArrayList<Test_Model> datalist3 = data1.getData();
					ArrayList<Test_Model> datalist4 = data1.getData();
					ArrayList<Test_Model> datalist5 = data1.getData();
					
					ad1_filePath = datalist1.get(0).getField_thumbnails();
					ad2_filePath = datalist1.get(0).getField_thumbnails();
					ad3_filePath = datalist1.get(0).getField_thumbnails();
					ad4_filePath = datalist1.get(0).getField_thumbnails();
					ad5_filePath = datalist1.get(0).getField_thumbnails();
			
			
					Log.v("-----217-----","beijing"+ad5_filePath);
				    handler.sendEmptyMessage(0);
				} catch (Y_Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		
		}.start();		
		
		
		  
	}

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:		
				new Thread(connectNet).start();  
				break;		
			default:
				break;
			}
		}

		
		
	};
	
	
	  /* 
     * 连接网络 
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问 
     */  
    private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try {  
              
            	ad1_FileName = "ad1.jpg";  
            	ad2_FileName = "ad2.jpg"; 
            	ad3_FileName = "ad3.jpg"; 
            	ad4_FileName = "ad4.jpg"; 
            	ad5_FileName = "ad5.jpg"; 
            	
                //以下是取得图片的两种方法  
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap  
                byte[] data1 = getImage(ad1_filePath); 
                byte[] data2 = getImage(ad2_filePath); 
                byte[] data3 = getImage(ad3_filePath); 
                byte[] data4 = getImage(ad4_filePath); 
                byte[] data5 = getImage(ad5_filePath); 
                
                if(data1!=null){  
                    mBitmap1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);// bitmap  
                }else{  
                   // Toast.makeText(NavigationActivity.this, "Image error!", 1).show();  
                }  
             
                if(data2!=null){  
                    mBitmap2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);// bitmap  
                }else{  
                   // Toast.makeText(NavigationActivity.this, "Image error!", 1).show();  
                }  
                
                if(data3!=null){  
                    mBitmap3 = BitmapFactory.decodeByteArray(data3, 0, data3.length);// bitmap  
                }else{  
                   // Toast.makeText(NavigationActivity.this, "Image error!", 1).show();  
                }  
                
                if(data4!=null){  
                    mBitmap4 = BitmapFactory.decodeByteArray(data4, 0, data4.length);// bitmap  
                }else{  
                   // Toast.makeText(NavigationActivity.this, "Image error!", 1).show();  
                }  
                
                if(data5!=null){  
                    mBitmap5 = BitmapFactory.decodeByteArray(data5, 0, data5.length);// bitmap  
                }else{  
                   // Toast.makeText(NavigationActivity.this, "Image error!", 1).show();  
                }  
            
                Log.v("-----304-----","beijing"+data5.length);
                // 发送消息
                connectHanlder.sendEmptyMessage(0);  
                Log.d(TAG, "set image ...");  
            } catch (Exception e) {  
                Toast.makeText(NavigationActivity.this,"无法链接网络！", 1).show();  
                e.printStackTrace();  
            }  
  
        }  
  
    };  
    
    private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            Log.d(TAG, "display image");  
            // 保存图片          
            new Thread(saveFileRunnable).start();  
        }  
    };  
    
    private Runnable saveFileRunnable = new Runnable(){  
        @Override  
        public void run() {  
            try {  
            	  Log.v("-----330-----","beijingggg");
                saveFile(mBitmap1, ad1_FileName);  
                saveFile(mBitmap2, ad2_FileName);  
                saveFile(mBitmap3, ad3_FileName);  
                saveFile(mBitmap4, ad4_FileName);  
                saveFile(mBitmap5, ad5_FileName);  
            //    mSaveMessage = "图片保存成功！";  
            } catch (IOException e) {  
            //    mSaveMessage = "图片保存失败！";  
                e.printStackTrace();  
            }  
           
        }  
  
    };  
    
  /*  private Handler messageHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {                 
            File file = new File(ALBUM_PATH+mFileName);  
            if(file.exists())  
            {  
            	Bitmap bm = BitmapFactory.decodeFile(ALBUM_PATH+mFileName);  
            	mImageView.setImageBitmap(bm);            	
            }                       
        }  
    };  
    */
    /** 
     * 保存文件 
     * @param bm 
     * @param fileName 
     * @throws IOException 
     */  
    public void saveFile(Bitmap bm, String fileName) throws IOException {  
        File dirFile = new File(ALBUM_PATH);  
        Log.v("-----364-----","beijing"+ALBUM_PATH + fileName);
     
        if(!dirFile.exists()){  
            dirFile.mkdir();  
            Log.v("-----368-----","beijing"+ALBUM_PATH + fileName);
        }  
        File myCaptureFile = new File(ALBUM_PATH + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }  
    
    /** 
     * Get image from newwork 
     * @param path The path of image 
     * @return byte[] 
     * @throws Exception 
     */  
    public byte[] getImage(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        InputStream inStream = conn.getInputStream();  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return readStream(inStream);  
        }  
        return null;  
    }  
    
    /** 
     * Get data from stream 
     * @param inStream 
     * @return byte[] 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1){  
            outStream.write(buffer, 0, len);  
        }  
        outStream.close();  
        inStream.close();  
        return outStream.toByteArray();  
    }  
    
    /** 
     * Get image from newwork 
     * @param path The path of image 
     * @return InputStream 
     * @throws Exception 
     */  
    public InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    }  

	private void Shared(String shaerd)
	{
		SharedPreferences settings = this.getSharedPreferences("CheckLoginXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putString("CheckLogin",shaerd);
		localEditor.commit(); 
	}
	
	/**
	 * 广播告知导航频道切换到新闻频道
	 */
	public void brodeTabhost(int key){
		Intent intent =  new Intent();
		intent.setAction("tabHost");
		intent.putExtra("key", key);
		sendBroadcast(intent);
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
	 * 广播告知新闻频道需要几个栏目，以动态改变
	 * @param column 所需栏目数
	 */
	public void brodeNewsColumn(int column){
		Intent intent_column =  new Intent();
		intent_column.setAction("news");
		intent_column.putExtra("column", column);
		sendBroadcast(intent_column);
	}
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
            float velocityY) {
		// TODO Auto-generated method stub  
        if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE   
                   && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
               // Fling left   
             //  Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();   
           } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
                   && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
               // Fling right  
        	   onBackPressed();
        	   brodeNewsColumn(10);
        	   brodeTabhost(2);
        	  // finish();
             //  Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();   
           }   
           return false;   
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		 Log.i("touch","touch");  
         return mGestureDetector.onTouchEvent(event);   
	}

	/**
	 * 屏蔽返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		long secondtime = System.currentTimeMillis();
		if (secondtime - firstime > 2000) {
			Toast.makeText(NavigationActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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
