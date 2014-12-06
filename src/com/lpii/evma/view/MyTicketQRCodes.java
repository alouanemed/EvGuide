package com.lpii.evma.view;
 

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lowagie.text.Image;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.BilletController;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.BilletAdapter;
import com.lpii.evma.model.Event;

public class MyTicketQRCodes extends ListActivity{

 

	ImageView mimageview_barcode_image;
	Button mbtnDownloadTicket;

	String pack_id;
	ImageView BilletQrCode;
	Bitmap bitmap;
	Image imgQRCODEBilletCode = null;
	String root;
	String fnames[];
	TextView textview_barcode_EvTitle;
	TextView mtextview_barcode_attendee_name;
	TextView mtextview_barcode_ticket_name; 
	TextView mtextview_barcode_count; 
	// Stirngs
	String Event_id;
	ArrayList<String> qrCodesURL;

	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	BilletController mBillerController;
	Event ev;

	String upLoadServerUri = null;

	final String uploadFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Evmax/";

	int CurrentIndex =0 ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myticket_details); 
		textview_barcode_EvTitle = (TextView) findViewById(R.id.textview_barcode_EvTitle);
 		qrCodesURL = new ArrayList<String>();
 		Intent orderDatasIntent = getIntent();
 		
 		String Packid = "";
		mBillerController = EvmaApp.CurrBilletController;
 		if (orderDatasIntent != null) {
 			Packid= orderDatasIntent.getStringExtra(ForfaitController.TAG_pack_Title);
 			textview_barcode_EvTitle.setText( Packid);
 //			textview_barcode_EvTitle.setText( orderDatasIntent.getStringExtra(EventsController.TAG_NAME));
			 
		} 
		//getQRName();
 		mBillerController.getQRCodeBilletbyPackID(Packid);
		Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
		ListAdapter adapterx = new BilletAdapter(MyTicketQRCodes.this, mBillerController.getSinglePackCodeslist(), tfBold, mBillerController);
		setListAdapter(adapterx);
 		//new  getBillets().execute();

	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
//		
//		File f = new File(Environment.getExternalStorageDirectory().getPath()+"/saved_images/" + qrCodesURL.get(CurrentIndex) );
//		System.out.println("file path " + f.getAbsolutePath());
//		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
//		mimageview_barcode_image.setImageBitmap(bmp);
//		System.out.println(qrCodesURL);  
		 
	}
 
 

	private class getBillets extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			EvmaApp.isReqFromOrga = true;
			System.out.println("LastEventID  " + EvmaApp.LastEventID);
			mBillerController.getTicketsQRName(EvmaApp.LastEventID);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
			ListAdapter adapterx = new BilletAdapter(MyTicketQRCodes.this, mBillerController.getSinglePackCodeslist(), tfBold, mBillerController);
			setListAdapter(adapterx);
			 
		}

	}
}
 