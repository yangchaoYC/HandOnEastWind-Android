package com.evebit.HandOnEastWind;


import java.util.ArrayList;
import java.util.Hashtable;
import com.evebit.HandOnEastWind.R;
import com.evebit.adapter.Normal;
import com.umeng.analytics.MobclickAgent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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


/**
 * 新闻浏览页面
 * 设计逻辑：
 
 *
 */

@SuppressLint("JavascriptInterface")
public class WebActivity extends Activity {
      
	 //文章ID==LauchActivity.LAUCH_URL + "news/"+nid+".html"
	private WebView webView;//主体
	private String webValue;//存储网页返回的值，一般为视频地址
	private Handler webHandler = new Handler();
	
	private String base = null;
	private ArrayList<Hashtable<String, String>> Data = new ArrayList<Hashtable<String, String>>();//存储获取的网络信息
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		 
		 setContentView(R.layout.activity_web);
		 
		 base = "<base href=\""+ LauchActivity.LAUCH_URL  +" \" />";
			webView = (WebView)findViewById(R.id.webView_WebView);

			initWeb(Data.get(0).get("title"),Data.get(0).get("name"),"我是时间",Data.get(0).get("nid"));


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
		private void initWeb(String title,String introtext,String time,String nid) {
			
			
			Log.v("-----yyyyy----", introtext);
			//byte b[] = android.util.Base64.decode(introtext, Base64.DEFAULT);//解码
			//introtext = new String(b);
			
			Normal normal = new Normal(this);
			String summary = normal.getFromAssets("template.html");
			summary = summary.replace("base", base);
			summary = summary.replace("URL", LauchActivity.LAUCH_URL + "news/"+nid+".html");
			summary = summary.replace("titleString", title);
			summary = summary.replace("introtextString", introtext);
			summary = summary.replace("timeString", time);
			webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
			//mWebView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setBuiltInZoomControls(true);
			webView.loadDataWithBaseURL("file:///android_asset/",summary, "text/html", "UTF-8", "about:blank");
		
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
	
	
	
	}
