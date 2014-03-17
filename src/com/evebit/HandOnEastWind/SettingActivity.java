package com.evebit.HandOnEastWind;


import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import com.evebit.DB.DBSize;
import com.evebit.DB.DBTime;
import com.evebit.DB.DBUser;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import net.tsz.afinal.FinalDb;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements android.view.View.OnClickListener {

	  ImageView soundOpenImage ;
	  ImageView soundCloseImage ;
	  
	  ImageView pushOpenImage ; //默认显示推送开启，点击后推送关闭
	  ImageView pushCloseImage ;
	  
	  ImageView imageOpen ; //默认显示图片开启，点击后无图模式
	  ImageView imageClose ;
	  private TextView versionTextView;//当前版本
	  private String version="";
	  long fileSize = 0;  
      String cacheSize = "0KB";
      
      private long firstime = 0;
      private File filesDir ;
      private File cacheDir ;
	  private Button size1Button,size2Button,size3Button;//字体大小按钮
	  private String Size = null;
	  private FinalDb db = null;
	  private TextView cacheTextView;//显示缓存
	  private TableRow updateTableRow;//更新按钮
	  private TableRow cacheTableRow,disclaimerRow,aboutTableRow;//更新按钮
	  //private File cacheDir;  
	  //private File fileDir;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		soundOpenImage = (ImageView)findViewById(R.id.setting_sound_open);
		soundCloseImage = (ImageView)findViewById(R.id.setting_sound_close);
		
		pushOpenImage = (ImageView)findViewById(R.id.setting_push_open);
		pushCloseImage = (ImageView)findViewById(R.id.setting_push_close);
		
		imageOpen = (ImageView)findViewById(R.id.setting_image_open);
		imageClose = (ImageView)findViewById(R.id.setting_image_close);
		db = FinalDb.create(this);
		
		cacheTextView = (TextView)findViewById(R.id.setting_storage);
		cacheTableRow = (TableRow)findViewById(R.id.Setting_Cache);
		
		versionTextView = (TextView)findViewById(R.id.setting_version);
	
		try {
			version = getVersionName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		versionTextView.setText(getString(R.string.setting_version)+version);
		
		updateTableRow = (TableRow)findViewById(R.id.Setting_Update);
		disclaimerRow = (TableRow)findViewById(R.id.Setting_Disclaimer);
		aboutTableRow = (TableRow)findViewById(R.id.Setting_About);

		
		 size1Button = (Button)findViewById(R.id.Setting_button_size1);
		 size2Button = (Button)findViewById(R.id.Setting_button_size2);
		 size3Button = (Button)findViewById(R.id.Setting_button_size3);


		 size1Button.setOnClickListener(this);
		 size2Button.setOnClickListener(this);
		 size3Button.setOnClickListener(this);
		 getSize();//获取当前字体大小
		 push();//获取当前是否开启推送
		 sound();//获取当前是否开启推送铃声
		 image();//获取当前是否是无图模式
		 cacheDir = this.getCacheDir(); //获取缓存目录
		 filesDir = this.getFilesDir(); //获取缓存目录
		 
		 

		 cache();//获取当前缓存文件
		 
		 
		 IntentFilter intentFilter = new IntentFilter();  
		    intentFilter.addAction("cache");  
		    registerReceiver(receiver, intentFilter);
		 
		soundOpenImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				soundOpenImage.setVisibility(View.GONE);
				soundCloseImage.setVisibility(View.VISIBLE);
				
				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  	
			
				builder.notificationDefaults = Notification.DEFAULT_SOUND ;  
				JPushInterface.setDefaultPushNotificationBuilder(builder);
				
				CheckSound("true");
			}
		});
		
		soundCloseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				soundOpenImage.setVisibility(View.VISIBLE);
				soundCloseImage.setVisibility(View.GONE);
				
				BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
				builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  
				builder.notificationDefaults = Notification.DEFAULT_LIGHTS ;  
				JPushInterface.setDefaultPushNotificationBuilder(builder);
				
				
				CheckSound("flase");
			}
		});
			
		pushOpenImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.GONE);
				pushCloseImage.setVisibility(View.VISIBLE);
				JPushInterface.resumePush(getApplicationContext());
				Checkpush("true");
			}
	    });
		
		pushCloseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushOpenImage.setVisibility(View.VISIBLE);
				pushCloseImage.setVisibility(View.GONE);
				JPushInterface.stopPush(getApplicationContext());
				Checkpush("flase");
			}
	    });
		
		imageOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.VISIBLE);
				imageOpen.setVisibility(View.GONE);
				CheckImage("true");
			}
	    });
		
		imageClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageClose.setVisibility(View.GONE);
				imageOpen.setVisibility(View.VISIBLE);
				CheckImage("flase");
			}
	    });
		
		updateTableRow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UmengUpdateAgent.forceUpdate(SettingActivity.this);
				
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

					@Override
					public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
						// TODO Auto-generated method stub
						 switch (updateStatus) {
					        case UpdateStatus.Yes: // has update
					            UmengUpdateAgent.showUpdateDialog(SettingActivity.this, updateInfo);
					            break;
					        case UpdateStatus.No: // has no update
					            Toast.makeText(SettingActivity.this, "没有更新", Toast.LENGTH_SHORT).show();
					            break;					     
					        case UpdateStatus.Timeout: // time out
					            Toast.makeText(SettingActivity.this, "超时", Toast.LENGTH_SHORT).show();
					            break;
					        }
						
					}													
				});		
				
			}
		});
		
		
		
		cacheTableRow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				cleanCacheFile(cacheDir+"", "20.cer");
				
				db.deleteByWhere(DBUser.class, "");
				
				db.deleteByWhere(DBTime.class, "");
               // handler.sendEmptyMessage(1);
		        cacheTextView.setText("    已有0KB缓存   ");

		        openDialog(getString(R.string.cache_scucess), SettingActivity.this);
		        
			}
		});
		
		disclaimerRow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, DisclaimerActivity.class);
				startActivity(intent);
			}
		});
		
		aboutTableRow.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
		
		
	}

	private String getVersionName() throws Exception
	   {
	           // 获取packagemanager的实例
	           PackageManager packageManager = getPackageManager();
	           // getPackageName()是你当前类的包名，0代表是获取版本信息
	           PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
	           String version = packInfo.versionName;
	           return version;
	   }
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			cache();
		}
		
	};
	
	private void cache()
	{   
//        if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){  
//        	  
//            File externalCacheDir = getExternalCacheDir(this);//"<sdcard>/Android/data/<package_name>/cache/"  
//            fileSize += getDirSize(externalCacheDir);             
//  
//        } 

        //	cacheSize = getPathSize(folderFullPath);
		//Log.v("setting---222", getPathSize(filesDir+""));
		//Log.v("setting---222", filesDir+"");


		//Log.v("setting---222", getPathSize("/data/data/com.evebit.HandOnEastWind/databases"));

        cacheTextView.setText("    已有"+getPathSize("/data/data/com.evebit.HandOnEastWind/databases")+"缓存文件    ");
	}
	
	
	/**
     * <删除指定文件夹下除指定文件之外的所有文件>
     * 
     * @param final String appCachePath 被删除的文件夹目录
     * @param final String excludedFileName 文件夹第一级目录下，不想被删除的文件的名称
     * @throw  内部捕捉异常，删除失败，不做处理
     * @return void
     */
    public static void cleanCacheFile(final String appCachePath,
        final String excludedFileName) {
        try {
            File file = new File(appCachePath.trim());
            if (null != file && file.isDirectory()) {
                for (File file1 : file.listFiles()) {
                    if (file1.isDirectory()) {
                        deleteAllFile(file1.getAbsolutePath());
                    } else {
                        if (!excludedFileName.equals(file1.getName())) {
                            file1.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    private static void deleteAllFile(String folderFullPath) {
        File file = new File(folderFullPath);
        if (null != file && file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = fileList[i].getPath();
                    deleteAllFile(filePath);
                }
            } else if (file.isFile()) {
                file.delete();
            }
        }
    }
	
	  public static String getPathSize(String path) {
	        String flieSizesString = "";
	        File file = new File(path.trim());
	        long fileSizes = 0;
	        if (null != file && file.exists()) {
	            if (file.isDirectory()) { // 如果路径是文件夹的时候
	                fileSizes = getFileFolderTotalSize(file);
	            } else if (file.isFile()) {
	                fileSizes = file.length();
	            }
	        }
	        flieSizesString = formatFileSizeToString(fileSizes);
	        return flieSizesString;
	    }
	  
	  
	  private static long getFileFolderTotalSize(File fileDir) {
	        long totalSize = 0;
	        File fileList[] = fileDir.listFiles();
	        for (int fileIndex = 0; fileIndex < fileList.length; fileIndex++) {
	            if (fileList[fileIndex].isDirectory()) {
	                totalSize =
	                    totalSize + getFileFolderTotalSize(fileList[fileIndex]);
	            } else {
	                totalSize = totalSize + fileList[fileIndex].length();
	            }
	        }
	        return totalSize;
	    }

	    private static String formatFileSizeToString(long fileSize) {// 转换文件大小
	        String fileSizeString = "";
	        DecimalFormat decimalFormat = new DecimalFormat("#.00");
	        if (fileSize < 1024) {
	            fileSizeString = decimalFormat.format((double)fileSize) + "B";
	        } else if (fileSize < (1 * 1024 * 1024)) {
	        
	            fileSizeString =
	                decimalFormat.format((double)fileSize / 1024) + "KB";
	        } else if (fileSize < (1 * 1024 * 1024 * 1024)) {
	            fileSizeString =
	                decimalFormat.format((double)fileSize / (1 * 1024 * 1024))
	                    + "M";
	        } else {
	            fileSizeString =
	                decimalFormat.format((double)fileSize
	                    / (1 * 1024 * 1024 * 1024))
	                    + "G";
	        }
	        return fileSizeString;
	    }
	


	    /**
		  * 弹出对话框
		  * @param alertStr 提示信息
		  * @param context 当前上下文
		  */
		 protected void openDialog(String alertStr, Context mContext) {
		  
		  new AlertDialog.Builder(mContext)
		  .setMessage(alertStr)	 
		
		  .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  }).show();
		  
		 }
	    

	private void image()
	{
		String condition ="nid='" + "image"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			imageThread("flase");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				imageOpen.setVisibility(View.GONE);
				imageClose.setVisibility(View.VISIBLE);
			}
			else {
				imageOpen.setVisibility(View.VISIBLE);
				imageClose.setVisibility(View.GONE);
			}
		}
	}
	
	private void CheckImage(String check)
	{
		String condition ="nid='" + "image"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			imageThread("flase");
		}
		else {
			imageThread(check);
		}
	}
	
	
	private void imageThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "image"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("image");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	
	

	private void sound()
	{
		String condition ="nid='" + "sound"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("true");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				soundOpenImage.setVisibility(View.GONE);
				soundCloseImage.setVisibility(View.VISIBLE);
			}
			else {
				soundOpenImage.setVisibility(View.VISIBLE);
				soundCloseImage.setVisibility(View.GONE);
			}
		}
	}
	
	private void CheckSound(String check)
	{
		String condition ="nid='" + "sound"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			soundThread("flase");
		}
		else {
			soundThread(check);
		}
	}
	
	
	private void soundThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "sound"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("sound");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	
	
	
	
	
	
	
	private void push()
	{
		String condition ="nid='" + "push"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("true");
		}
		else {
			if (!list.get(0).getSize().toString().equals("flase")) {
				pushOpenImage.setVisibility(View.GONE);
				pushCloseImage.setVisibility(View.VISIBLE);
			}
			else {
				pushOpenImage.setVisibility(View.VISIBLE);
				pushCloseImage.setVisibility(View.GONE);
			}
		}
	}
	
	private void Checkpush(String check)
	{
		String condition ="nid='" + "push"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			pushThread("flase");
		}
		else {
			pushThread(check);
		}
	}
	
	
	private void pushThread(final String key)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String condition ="nid='" + "push"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("push");
				user.setSize(key);
				db.save(user);
			}
			
		}.start();
	}
	
	/**
	 * 查询字体大小
	 * @param size
	 */
	private void getSize()
	{
		String condition ="nid='" + "size"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			DBSize("1");
			size1Button.setTextColor(0xFFAA823C);
		}
		else {
			Size = list.get(0).getSize();
			if (Size.equals("1")) {
				size1Button.setTextColor(0xFFAA823C);
				size2Button.setTextColor(0xFF000000);
				size3Button.setTextColor(0xFF000000);
			}
			else if (Size.equals("2")) {
				size1Button.setTextColor(0xFF000000);
				size2Button.setTextColor(0xFFAA823C);
				size3Button.setTextColor(0xFF000000);
			}
			else {
				size1Button.setTextColor(0xFF000000);
				size2Button.setTextColor(0xFF000000);
				size3Button.setTextColor(0xFFAA823C);
			}
		}
	}
	
	/**
	 * 修改字体大小
	 * @param size
	 */
	private void Updatesize(String size)
	{
		String condition ="nid='" + "size"+ "'";//搜索条件
		List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
		if (list.size() == 0) {
			DBSize(size);
		}
		else {
			String delete ="nid='" + "size"+ "'";//搜索条件
			db.deleteByWhere(DBSize.class, delete);
			DBSize(size);
		}
	}
	
	
	private void DBSize(final String size)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String condition ="nid='" + "size"+ "'";
				db.deleteByWhere(DBSize.class, condition);
				
				DBSize user = new DBSize();
				user.setNid("size");
				user.setSize(size);
				db.save(user);
			}
			
		}.start();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Setting_button_size1:
			Updatesize("1");
			size1Button.setTextColor(0xFFAA823C);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFF000000);
			break;
		case R.id.Setting_button_size2:
			Updatesize("2");	
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFFAA823C);
			size3Button.setTextColor(0xFF000000);
			break;
		case R.id.Setting_button_size3:
			Updatesize("3");
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFFAA823C);
			break;

		default:
			break;
		}
	}

	
	/**
	 * 广播接收器
	 * 接收到切换到新闻频道，选项卡切换到新闻
	 */
      private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();  
			if (action.equals("cache")) {
				Log.v("setting---623", "测试");
				cache();
			}
			
		}
	};
	/**
	 * 屏蔽返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		long secondtime = System.currentTimeMillis();
		if (keyCode == KeyEvent.KEYCODE_BACK) { 
			if (secondtime - firstime > 2000) {
				Toast.makeText(SettingActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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

	
}
