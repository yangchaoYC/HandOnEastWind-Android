package com.evebit.HandOnEastWind;


import com.evebit.HandOnEastWind.R;
import com.evebit.adapter.Normal;

import com.umeng.analytics.MobclickAgent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 新闻浏览页面
 * 设计逻辑：
 
 *
 */

public class WebActivity extends Activity {
      
	 
	private WebView webView;//主体
	private String webValue;//存储网页返回的值，一般为视频地址
	private Handler webHandler = new Handler();
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_web);
			webView = (WebView)findViewById(R.id.webView_WebView);
		 
			initWeb("我是标题","我是内容","我是时间");
			
			webView.getSettings().setPluginsEnabled(true);
			/**
			 * 接收网页的值播放视频，本地html文件内设置支持Javascript
			 */
			WebSettings webSettings = webView.getSettings();       
	        webSettings.setJavaScriptEnabled(true);       
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
	      
		//	webView.loadUrl("file:///android_asset/template.html");	

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
		private void initWeb(String title,String introtext,String time) {
			
			
			Log.v("-----yyyyy----", introtext);
			byte b[] = android.util.Base64.decode(introtext, Base64.DEFAULT);//解码
			introtext = new String(b);
			
			Normal normal = new Normal(this);
			String summary = normal.getFromAssets("template.html");
			summary = summary.replace("titleString", title);
			summary = summary.replace("introtextString", introtext);
			summary = summary.replace("timeString", time);
			webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
			//mWebView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setBuiltInZoomControls(true);
			webView.loadDataWithBaseURL("file:///android_asset/",summary, "text/html", "UTF-8", "about:blank");
		
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
	}
