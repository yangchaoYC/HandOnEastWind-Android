package com.evebit.HandOnEastWind;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import com.evebit.DB.DBSize;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 初始页面
 * 设计逻辑：
 * 加载初始页面，activity_lauch 页面
 * 加载此页面中id为ad_image的imagView
 * 加载完毕之后等待两秒跳转到主选项卡页面 TabMainActivity
 * 
 * 主选项卡页面处于选项卡页面时可用手势滑动至此页面，此时可使用手势左滑动跳转至主选项卡页面 TabMainActivity
 * 此处用flag标记是否第一次登陆，第一次登陆则为false,跳转之后修改为true
 * 在手势滑动做判断，若为第一次登陆，则不支持左右滑动，若是由主选项卡也滑动过来，则不是第一次登陆，支持左右滑动
 * 
 * 
 * 1: 进入查询数据库是否存储图片id，如果没有则开启获取图片线程并且进行跳转
 * 2：如果有图片id，显示本地缓存的图片，停顿2秒后进行跳转，并且开启线程检查此张图片id与服务器图片id是否一致，如果一致则不动作，如果不一致则开启获取图片线程
 * 3：开启图片线程流程：1：删除本地缓存图片，2：拉取网络图片，并且缓存到本地
 * @author guan
 *
 */

public class LauchActivity extends Activity implements OnTouchListener, OnGestureListener{
      
	   private ImageView ad_ImageView;  //显示广告图片
	   Bitmap bitmap =null; //显示广告图片
	   private final int SPLASH_DISPLAY_LENGHT = 2000; //2秒后跳转主页面
	   private Boolean flag= false; //标记是否第一次登陆
	   private FinalDb db = null;
	   public static final String LAUCH_URL = "http://115.29.16.55/"; 
	   
	   public static final String LAUCH_DATE_node_title = "node_title"; 
	   public static final String LAUCH_DATE_node_created = "node_created"; 
	   public static final String LAUCH_DATE_field_channel = "field_channel"; 
	   public static final String LAUCH_DATE_field_newsfrom = "field_newsfrom"; 
	   public static final String LAUCH_DATE_field_thumbnails = "field_thumbnails"; 
	   public static final String LAUCH_DATE_field_summary = "field_summary"; 
	   public static final String LAUCH_DATE_body_1 = "body_1"; 
	   public static final String LAUCH_DATE_body_2 = "body_2"; 
	   public static final String LAUCH_DATE_nid = "nid"; 
	   public static final String LAUCH_DATE_page = "page"; 
	   
	   //滑动选项卡
	   GestureDetector mGestureDetector;  
	   private static final int FLING_MIN_DISTANCE = 50;  
	   private static final int FLING_MIN_VELOCITY = 0;  
	   private String imgUrl = LAUCH_URL + "/mobile/adstart?nid=1136";//
	   private String image_Url = null;
	   private String image_ID = null;
	   private String image_dateID = null;

	   private Bitmap mBitmap;  //缓存网络获取的图片
	   private final static String ALBUM_PATH  
       = Environment.getExternalStorageDirectory() + "/download_test/";//存储图片的路径
	   public static final String LAUCH_mFileName = "test.jpg";//存储图片名字

	   private String test = "LauchActivity";
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_lauch);
		 ad_ImageView = (ImageView)findViewById(R.id.ad_image);
		 MobclickAgent.updateOnlineConfig(LauchActivity.this);
		 //滑动首页选项卡
	     mGestureDetector = new GestureDetector(this);  
		 LinearLayout ad=(LinearLayout)findViewById(R.id.ad);  
		 ad.setOnTouchListener(this);  
		 ad.setLongClickable(true);  
	     Shared();
		 //加载首页的广告图片
	     //imgThread();     
	     //UrlThread();
	     db = FinalDb.create(this);
	     //检测更新
		 MobclickAgent.updateOnlineConfig(LauchActivity.this);	
		 
		 censor();
		 
		 checkPushOpen();
		 checkPushSound();
	}
	
	



	private void checkPushOpen() {
		// TODO Auto-generated method stub
		 String condition ="nid='" + "push"+ "'";//搜索条件
		
			List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
			if (list.size() == 0) {
				JPushInterface.resumePush(getApplicationContext());
			}
			else {
				
				if (list.get(0).getSize().toString().equals("flase")) {
					JPushInterface.stopPush(getApplicationContext());
				}
				else {
					JPushInterface.resumePush(getApplicationContext());
				}
			}
	}
	
	
	 private void checkPushSound() {
		// TODO Auto-generated method stub
		 String condition ="nid='" + "sound"+ "'";//搜索条件
			List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
			if (list.size() == 0) {
	
			}
			else {
				if (list.get(0).getSize().toString().equals("flase")) {
					BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(LauchActivity.this);
					builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  
					builder.notificationDefaults = Notification.DEFAULT_LIGHTS ;  
					JPushInterface.setDefaultPushNotificationBuilder(builder);
				}
				else {
					BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(LauchActivity.this);
					builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  					
					builder.notificationDefaults = Notification.DEFAULT_SOUND ;  
					JPushInterface.setDefaultPushNotificationBuilder(builder);
				}
			}
	}


	/**
     * handler用来处理加载广告和计时跳转的顺序
     * 1：加载缓存图片后延迟2面进行页面push
     * 2：获取图片
     * 3：开启获取图片线程后进行push
     */
     private Handler handler = new Handler()
     {
          @Override
          public void handleMessage(Message msg) {
               // TODO Auto-generated method stub
               switch (msg.what) {
               case 1:
            	   //加载首页广告，加载完之后开始计时2秒，结束后跳转到导航页面
                    ad_ImageView.setImageBitmap(bitmap);
                    handler.sendEmptyMessage(2);
                    break;
               case 2:
            	   	UrlThread(6);
              		startCountTime();//直接push
               	    break;
               case 3:
            	    UrlThread(4);
               		startCountTime();//直接push
               		break;
               	case 4:
               		deleteID();
               		connectNetThread();
               		break;
               	case 5:
               		delete();
               		break;
               	case 6:
               		if (!image_dateID.equals(image_ID)) {
               			deleteID();
                   		connectNetThread();
					}
               		break;
               default:
                    break;
               }
          }
     };
     
     private void deleteID()
     {
    	 new Thread()
    	 {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "logo"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("logo");
				user.setSize(image_ID);
				db.save(user);
			}
    		 
    	 }.start();
     }
     
     /**
      * 删除图片后添加
      */
     private void delete() {
 		// TODO Auto-generated method stub
     	 File file=new File(ALBUM_PATH+LAUCH_mFileName);  
            if(file.exists()){                  
         	   file.delete();   
         	   
         	  StorageThread();
            }else{  
            	StorageThread();
            }  
            
        
 	}
     
	/**
	 * 检测本地是否存储图片ID
	 */
	private void censor()
	{
		String condition ="nid='" + "logo"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			handler.sendEmptyMessage(3);
		}
		else {
		//	Log.v(test, "--211--");
			File file = new File(ALBUM_PATH+LAUCH_mFileName);  
            if(file.exists())  
            {  
            	bitmap = BitmapFactory.decodeFile(ALBUM_PATH+LAUCH_mFileName);  
            }
            image_dateID = list.get(0).getSize().toString();
            handler.sendEmptyMessage(1);
		}
	}

	private void connectNetThread()
	{
		
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 try {  
				byte[] data = getImage(image_Url);  
                if(data!=null){  
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
            
                }else{  
                   // Toast.makeText(MainActivity.this, "Image error!", 1).show();  
                } 
                handler.sendEmptyMessage(5);
			  } catch (Exception e) {  
	                e.printStackTrace();  
	            } 
			}
			
		}.start();
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
    
    
    private void StorageThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {  
	                saveFile(mBitmap, LAUCH_mFileName);  //保存图片
	            } catch (IOException e) {    
	                e.printStackTrace();  
	            } 
			}
			
		}.start();
	}
	
	 /** 
     * 保存文件 
     * @param bm 
     * @param fileName 
     * @throws IOException 
     */  
    public void saveFile(Bitmap bm, String fileName) throws IOException {  
    	Log.v(test,"---313");
        File dirFile = new File(ALBUM_PATH);  
        if(!dirFile.exists()){  //判断是否有这个文件夹，有的话就创建，没有将创建
        	Log.v(test,"---316");
            dirFile.mkdir();  //创建一个新文件夹
        }  
        File myCaptureFile = new File(ALBUM_PATH + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }
    
    
	private void UrlThread(final int what)
	{
		//Log.v("lauch---119", imgUrl);

		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
				try {	
					data = DataManeger.getTestData(imgUrl);
					ArrayList<Test_Model> datalist = data.getData();
					for (Test_Model test_Model : datalist) {	
					 //	Log.v("lauch---119", imgUrl);
					 image_Url = test_Model.getField_thumbnails()==null? "": test_Model.getField_thumbnails();
					 image_ID = test_Model.getNode_created()==null? "": test_Model.getNode_created();
					}
					handler.sendEmptyMessage(what);			
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	private void Shared()
	{
		SharedPreferences settings = this.getSharedPreferences("CheckLoginXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putString("CheckLogin","15");
		localEditor.commit(); 
	}
	/**
	 * 加载广告图片
	 */
	private void imgThread()
	    {
	         new Thread()
	         {

	              @Override
	              public void run() {
	                   // TODO Auto-generated method stub                
	                        URL imageUrl =null;                      
	                        try {
	                             imageUrl = new URL(image_Url);
	                        } catch (Exception e) {
	                             // TODO: handle exception
	                        }
	                        try {
	       
	                             HttpURLConnection connection =(HttpURLConnection)imageUrl.openConnection();
	                             connection.setDoInput(true);
	                             connection.connect();
	                             InputStream is=connection.getInputStream();
	                             BufferedInputStream bis = new BufferedInputStream(is);
	                             bitmap = BitmapFactory.decodeStream(bis);
	                             bis.close();
	                             is.close();	                           
	                        } catch (Exception e) {
	                             // TODO: handle exception
	                           e.printStackTrace();
	                        }	                
	                  //通知告知图片加载完毕
	                   handler.sendEmptyMessage(1);
	              }
	            
	         }.start();
	    }
	    
	    
	    
	     /**
	      * 计时两秒，结束后跳转至主选项卡页面
	      */
	     private void startCountTime() {
	          // TODO Auto-generated method stub
	            new Handler().postDelayed(new Runnable() {
	              @Override
	              public void run() {
	            	  flag = true;
	            	  goTabMain();
	              }

	      }, SPLASH_DISPLAY_LENGHT);
	     }

	    /**
	     *  跳转至主选项卡页面,TabMainActivity
	     */
	 	public void goTabMain(){
			Intent intent = new Intent(LauchActivity.this, TabMainActivity.class);
			LauchActivity.this.startActivity(intent);
		}
	 	
		public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
		}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}
		
			
			
			
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.lauch, menu);
			return true;
		}
	
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if (flag) {
				 if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE   
		                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
		             // Fling left   
		            //onBackPressed();
		             goTabMain();
		         } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE   
		                 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {   
		             // Fling right   
		           
		            
		         }   
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
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			Log.i("touch","touch");  
	        return mGestureDetector.onTouchEvent(event);  
		}
		
		
		
	}
