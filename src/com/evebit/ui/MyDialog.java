package com.evebit.ui;


import com.evebit.HandOnEastWind.R;
import com.evebit.ListView.XListView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class MyDialog extends Dialog {

	private Context context;
	private ImageView close_ImageView;
	private XListView ad_ListView;	
	
	public MyDialog(Context context) {
		// TODO Auto-generated constructor stub
		super(context,R.style.MyDialog);
		this.context = context;
		setMyDialog();
	}

	private void setMyDialog() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ad, null);
		close_ImageView = (ImageView)view.findViewById(R.id.dialog_ad_close);
		ad_ListView = (XListView)view.findViewById(R.id.dialog_ad_list);
		super.setContentView(view);
		
	}

	public ImageView getClose_ImageView() {
		return close_ImageView;
	}

	public void setClose_ImageView(ImageView close_ImageView) {
		this.close_ImageView = close_ImageView;
	}

	public ListView getAd_ListView() {
		return ad_ListView;
	}

	public void setAd_ListView(XListView ad_ListView) {
		this.ad_ListView = ad_ListView;
	}

}
