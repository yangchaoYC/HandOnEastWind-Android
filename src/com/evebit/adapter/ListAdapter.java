package com.evebit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.HandOnEastWind.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> list;   
    private LayoutInflater listContainer;          
    
    public final class ListItemView{  
    
    	public TextView title;
    	public ImageView bigImageView;
    
 }     
    
    public ListAdapter(Context context, ArrayList<HashMap<String, String>> list) {
		// TODO Auto-generated constructor stub	
		this.context=context;
	    this.list=list;	 
	    listContainer = LayoutInflater.from(context);  
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
	            convertView = listContainer.inflate(R.layout.list_view1_test, null);            
	            listItemView.title= (TextView)convertView.findViewById(R.id.title);  
	            listItemView.bigImageView = (ImageView)convertView.findViewById(R.id.big_image); 
	            convertView.setTag(listItemView);   
	        }else {   
	            listItemView = (ListItemView)convertView.getTag();   
	        }   
	        
		    if (position == 0) {
		    	 listItemView.bigImageView.setVisibility(View.VISIBLE);
			}  else {
				 listItemView.bigImageView.setVisibility(View.GONE);
			}  
		listItemView.title.setText((String) list.get(position).get("title"));   
		return convertView;   
	}

}
