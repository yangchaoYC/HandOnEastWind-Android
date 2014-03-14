package com.evebit.json;

import android.util.Log;




public class DataManeger {

	public static Test_Bean getTestData(String urlString) throws Y_Exception {
		String url = urlString;
		
		return Test_Bean.dataParser(HttpUtil.httpGet(null, url));
		
	}
	
	public static Test_Bean_News getTestData_News(String urlString) throws Y_Exception {
		String url = urlString;
		
		return Test_Bean_News.dataParser(HttpUtil.httpGet(null, url));
	}
}
