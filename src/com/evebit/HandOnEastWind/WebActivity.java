package com.evebit.HandOnEastWind;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;

import com.evebit.DB.DBSize;
import com.evebit.DB.DBTime;
import com.evebit.HandOnEastWind.R;
import com.evebit.adapter.Normal;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.UMWXHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;


/**
 * 新闻浏览页面
 * 设计逻辑：
 
 *
 */

@SuppressLint("JavascriptInterface")
public class WebActivity extends Activity implements android.view.View.OnClickListener{
      
	 //文章ID==LauchActivity.LAUCH_URL + "news/"+nid+".html"
	private WebView webView;//主体
	private String webValue;//存储网页返回的值，一般为视频地址
	private Handler webHandler = new Handler();
	private String nid,node_title,node_created,field_channel,field_newsfrom,body_1,body_2;
	private String base = null;
	private String Size = null;
	private String imageString;
	private Button size1Button,size2Button,size3Button;
	private ImageView backImageView,shareImageView;
	private FinalDb db = null;
	int size;
	private String Body;
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
	
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled", "NewApi" })
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		 
		 setContentView(R.layout.activity_web);
		 
		 
		 nid = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_nid);
		 node_title = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_node_title);
		 node_created = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_node_created);
		 field_channel = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_field_channel);
		 field_newsfrom = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_field_newsfrom);
		 body_1 = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_body_1);
		 body_2 = getIntent().getExtras().getString(LauchActivity.LAUCH_DATE_body_2);
		 
		 base = "<base href=\""+ LauchActivity.LAUCH_URL  +" \" />";
		 Log.e("web----92", nid);
		 db = FinalDb.create(this);
		 size1Button = (Button)findViewById(R.id.Web_button_size1);
		 size2Button = (Button)findViewById(R.id.Web_button_size2);
		 size3Button = (Button)findViewById(R.id.Web_button_size3);
		 backImageView = (ImageView)findViewById(R.id.Web_Button_back);
		 shareImageView = (ImageView)findViewById(R.id.Web_Button_Shaer);
		 
		 
		 
		 //share
		 mController.getConfig().removePlatform(SHARE_MEDIA.DOUBAN,SHARE_MEDIA.RENREN,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE);
		
		 mController.setShareContent("我在掌上东风应用中看到一条信息,你也来看看把!---"+LauchActivity.LAUCH_URL + "news/"+nid+".html");
				
		 mController.setShareMedia(new UMImage(WebActivity.this,R.drawable.ic_launcher));
					
		 mController.getConfig().supportQQPlatform(WebActivity.this, "http://www.baidu.com/");   
					
		 mController.getConfig().supportQQPlatform(WebActivity.this, "http://www.umeng.com/social");  		
		 String appID = "wx3fd2ba543fd26795";
					//分享内容跳转的URL
		 String contentUrl = LauchActivity.LAUCH_URL + "news/"+nid+".html";
		 UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(WebActivity.this,appID, contentUrl);
		 wxHandler.setWXTitle(node_title);
		 UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(WebActivity.this,appID, contentUrl) ;
		 circleHandler.setCircleTitle(node_title);

		 mController.getConfig().setSsoHandler(new SinaSsoHandler());
		 mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		 
		 
		 
		

		 size1Button.setOnClickListener(this);
		 size2Button.setOnClickListener(this);
		 size3Button.setOnClickListener(this);
		 backImageView.setOnClickListener(this);
		 shareImageView.setOnClickListener(this);
		 
		webView = (WebView)findViewById(R.id.webView_WebView);
		getSize();//获取字体大小
		image();

		
		//Log.v("-web  136---", body_1);
		//Log.v("-web  136---", body_2);
		if (imageString.equals("flase")) {
			Body =" <div class=\"title\" style=\"font-size:"+size+"px !important\">" +body_1 + "</div>";
		}
		else {		
			Body =" <div class=\"title\" style=\"font-size:"+size+"px !important\">" +fiterHtmlTag(body_1, "img") + "</div>";
		}
		
		

			//webView.setWebChromeClient(mChromeClient);
			webView.getSettings().setPluginsEnabled(true);
			/**
			 * 接收网页的值播放视频，本地html文件内设置支持Javascript
			 */
			WebSettings webSettings = webView.getSettings();       
	        webSettings.setJavaScriptEnabled(true); 
	        webSettings.setDomStorageEnabled(true);
	        webSettings.setUseWideViewPort(false) ;
	        webSettings.setRenderPriority(RenderPriority.HIGH);
	        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	        webSettings.setDisplayZoomControls(false);
	        webSettings.setPluginsEnabled(true);
	        webView.addJavascriptInterface(new Object() {       
	            @SuppressWarnings("unused")
				public void clickOnAndroid( String strings) {   
	            	webValue = strings;
	                webHandler.post(new Runnable() {       
	                    public void run() {       
	                    	Log.e("9999------999", webValue);   
	                    	Play(webValue);
	                    }       
	                });       
	            }       
	        }, "demo"); 
			
	        
	        initWeb(Body);//显示网页

	        
	}
	
	 /** 
     *  
     * 基本功能：过滤指定标签 
     * <p> 
     *  
     * @param str 
     * @param tag 
     *            指定标签 
     * @return String 
     */  
    public static String fiterHtmlTag(String str, String tag) {  
        String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";  
        Pattern pattern = Pattern.compile(regxp);  
        Matcher matcher = pattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        boolean result1 = matcher.find();  
        while (result1) {  
            matcher.appendReplacement(sb, "");  
            result1 = matcher.find();  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }   
	  
	 private void image()
		{
			String condition ="nid='" + "image"+ "'";//搜索条件
			List<DBSize> list = db.findAllByWhere(DBSize.class, condition);
			if (list.size() == 0) {
				imageString = "false";
			}
			else {
				imageString = list.get(0).getSize().toString();
			}
		}
	
		/**
		 * 根据传递的url调用系统播放器进行视频播放
		 * @param url
		 */
		private void Play(String url) {
			String extension = MimeTypeMap.getFileExtensionFromUrl(url);  
			String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);  
			Intent mediaIntent = new Intent(Intent.ACTION_VIEW);  
			mediaIntent.setDataAndType(Uri.parse(url), mimeType);  
			startActivity(mediaIntent); 
		}
		/**
		 * 
		 * @param title	标题
		 * @param introtext	内容
		 * @param time	时间
		 * 根据数据调用本地html进行网页显示
		 */
		private void initWeb(String body) {
			
			//byte b[] = android.util.Base64.decode(introtext, Base64.DEFAULT);//解码
			//introtext = new String(b);
			
			Normal normal = new Normal(this);
			String summary = normal.getFromAssets("template.html");
			summary = summary.replace("base", base);
		//	summary = summary.replace("URL", LauchActivity.LAUCH_URL + "news/"+nid+".html");
			summary = summary.replace("titleString", node_title);
			summary = summary.replace("introtextString", body);
			summary = summary.replace("timeString", node_created);
			summary = summary.replace("meidaSting", field_newsfrom);
			webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
			webView.getSettings().setBuiltInZoomControls(true);
			webView.loadDataWithBaseURL("file:///android_asset/",summary, "text/html", "UTF-8", "about:blank");
		
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
				size = 18;
			}
			else {
				Size = list.get(0).getSize();
				if (Size.equals("1")) {
					size1Button.setTextColor(0xFFAA823C);
					size2Button.setTextColor(0xFF000000);
					size3Button.setTextColor(0xFF000000);
					size = 18;
				}
				else if (Size.equals("2")) {
					size1Button.setTextColor(0xFF000000);
					size2Button.setTextColor(0xFFAA823C);
					size3Button.setTextColor(0xFF000000);
					size = 21;
				}
				else {
					size1Button.setTextColor(0xFF000000);
					size2Button.setTextColor(0xFF000000);
					size3Button.setTextColor(0xFFAA823C);
					size = 28;
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
		
		
	private WebChromeClient mChromeClient = new WebChromeClient()
	{
		 private View myView = null;
	        private CustomViewCallback myCallback = null;

	        // 配置权限 （在WebChromeClinet中实现）
	        @Override
	        public void onGeolocationPermissionsShowPrompt(String origin,
	                GeolocationPermissions.Callback callback) {
	            callback.invoke(origin, true, false);
	            super.onGeolocationPermissionsShowPrompt(origin, callback);
	        }

	        // Android 使WebView支持HTML5 Video（全屏）播放的方法
	        @Override
	        public void onShowCustomView(View view, CustomViewCallback callback) {
	            if (myCallback != null) {
	                myCallback.onCustomViewHidden();
	                myCallback = null;
	                return;
	            }

	            ViewGroup parent = (ViewGroup) webView.getParent();
	            parent.removeView(webView);
	            parent.addView(view);
	            myView = view;
	            myCallback = callback;
	            mChromeClient = this;
	        }

	        @Override
	        public void onHideCustomView() {
	            if (myView != null) {
	                if (myCallback != null) {
	                    myCallback.onCustomViewHidden();
	                    myCallback = null;
	                }

	                ViewGroup parent = (ViewGroup) myView.getParent();
	                parent.removeView(myView);
	                parent.addView(webView);
	                myView = null;
	            }
	        }
	};
		
	
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Web_button_size1:
			Updatesize("1");
			size1Button.setTextColor(0xFFAA823C);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFF000000);
			size = 18;
			if (imageString.equals("flase")) {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +body_1 + "</div>";
			}
			else {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +fiterHtmlTag(body_1, "img") + "</div>";
			}
			
			initWeb(Body);			break;
		case R.id.Web_button_size2:
			Updatesize("2");	
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFFAA823C);
			size3Button.setTextColor(0xFF000000);
			size = 21;
			if (imageString.equals("flase")) {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +body_1 + "</div>";
			}
			else {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +fiterHtmlTag(body_1, "img") + "</div>";
			}
			initWeb(Body);
			break;
		case R.id.Web_button_size3:
			Updatesize("3");
			size1Button.setTextColor(0xFF000000);
			size2Button.setTextColor(0xFF000000);
			size3Button.setTextColor(0xFFAA823C);
			size = 28;
			if (imageString.equals("flase")) {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +body_1 + "</div>";
			}
			else {
				Body =" <div class=\"title\" style=\"font-size:"+size+"px\">" +fiterHtmlTag(body_1, "img") + "</div>";
			}
			
			initWeb(Body);

			break;
		case R.id.Web_Button_back:
			onBackPressed();
			break;
		case R.id.Web_Button_Shaer:
			shareOut();
			mController.openShare(WebActivity.this, false);
		//	
			break;
		default:
			break;
		}
	}

	private void shareOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
	}
