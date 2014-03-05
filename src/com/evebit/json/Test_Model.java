package com.evebit.json;


/**
 * nid                  商品id
 * node_title	                标题
 * field_summary        内容简介
 * field_thumbnails     文章缩略图
 */


public class Test_Model {

	  
	   private String nid;//文章ID
	   /////////////getter and setter///////////////
	   private String node_title;//文章标题
	   private String node_created;//文章创建时间
	   private String field_channel;//频道
	   private String field_newsfrom;//新闻来源
	   private String field_thumbnails;//文章缩略图
	   private String field_summary;//文章摘要
	   private String body_1;//图片格式详细内容
	   private String body_2;//无图片格式详细内容

	   
	   public String getId() {
	   return nid;
	   }
	   public void setId(String nid) {
	   this.nid = nid;
	   }
	   
	   public String getNode_title() {
	   return node_title;
	   }
	   public void setNode_title(String node_title) {
	   this.node_title = node_title;
	   }
	   
	   public String getNode_created() {
	   return node_created;
	   }
	   public void setNode_created(String node_created) {
	   this.node_created = node_created;
	   }
	   
	   public String getField_channel() {
	   return field_channel;
	   }
	   public void setField_channel(String field_channel) {
	   this.field_channel = field_channel;
	   }
	   
	   
	   public String getField_newsfrom() {
	   return field_newsfrom;
	   }
	   public void setField_newsfrom(String field_newsfrom) {
	   this.field_newsfrom = field_newsfrom;
	   }
	   
	   public String getField_thumbnails() {
	   return field_thumbnails;
	   }
	   public void setField_thumbnails(String field_thumbnails) {
	   this.field_thumbnails = field_thumbnails;
	   }
	   
	   public String getField_summary() {
	   return field_summary;
	   }
	   public void setField_summary(String field_summary) {
	   this.field_summary = field_summary;
	   }
	   
	   public String getBody_1() {
	   return body_1;
	   }
	   public void setBody_1(String body_1) {
	   this.body_1 = body_1;
	   }
	   
	   public String getBody_2() {
	   return body_2;
	   }
	   public void setBody_2(String body_2) {
	   this.body_2 = body_2;
	   }
	   
}
