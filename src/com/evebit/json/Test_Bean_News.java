package com.evebit.json;


import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Test_Bean_News {

	private Test_Model_News  msg;

	public Test_Model_News  getData() {
		return msg;
	}

	public void setData(Test_Model_News  msg) {
		this.msg = msg;
	}

	/**
	 * @param data
	 * @return
	 * @throws WHT_Exception 
	 */
	public static Test_Bean_News dataParser(String data) throws Y_Exception {
		Test_Bean_News reigst = new Test_Bean_News();

		try {
			Test_Model_News  myanswerlist = (Test_Model_News) JsonUtil.DataToObject(data, new TypeToken<Test_Model_News >(){}.getType());
			reigst.setData(myanswerlist);
		} 
		catch(JsonParseException e) {
			throw Y_Exception.dataParser(e);
		}
		
		return reigst;
	}
}
