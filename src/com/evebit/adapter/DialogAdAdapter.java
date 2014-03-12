package com.evebit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.HandOnEastWind.LauchActivity;
import com.evebit.HandOnEastWind.R;
import com.evebit.adapter.ListAdapter.ListItemView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogAdAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> list;   
    private LayoutInflater listContainer;  
    public ImageLoader imageLoader; 
    private String imageString;
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/download_ad/";  
 
    public final class ListItemView{  
        
    	public TextView ad_name;
    	public ImageView ad_imageView;

 }     
    public DialogAdAdapter(Context context, ArrayList<HashMap<String, String>> list,String imageString) {
  		// TODO Auto-generated constructor stub	
  		this.context=context;
  	    this.list=list;	 
  	    this.imageString = imageString;
  	    listContainer = LayoutInflater.from(context);  
  	    imageLoader = new ImageLoader(context,imageString);
    }

    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		  ListItemView  listItemView = null;   
		  

	        if (convertView == null) {   
	            listItemView = new ListItemView();               
	            convertView = listContainer.inflate(R.layout.list_dialog_ad, null);            
	            listItemView.ad_name = (TextView)convertView.findViewById(R.id.dialog_ad_name); 
	            listItemView.ad_imageView = (ImageView)convertView.findViewById(R.id.dialog_ad_image); 
	            convertView.setTag(listItemView);   
	        }else {   
	            listItemView = (ListItemView)convertView.getTag();   
	        }    
	         
	        
	      
		    listItemView.ad_name.setText((String) list.get(position).get(LauchActivity.LAUCH_DATE_node_title)); 		    
		    imageLoader.DisplayImage((String) list.get(position).get(LauchActivity.LAUCH_DATE_field_thumbnails), listItemView.ad_imageView);	
			
		
		return convertView;   
		  
	}

}
