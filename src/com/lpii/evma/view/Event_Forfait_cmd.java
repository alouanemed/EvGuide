package com.lpii.evma.view;

import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.EventPack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Event_Forfait_cmd extends Activity{


	EditText etEvPackQty;
	TextView etEvPackDesc;
	Button btnNextQty;
	ForfaitController mForfaitController;
	Intent in ;
	NumberPicker PickerQty;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_forfait_cmd_qty_activity);
		
		etEvPackQty = (EditText) findViewById(R.id.etEvPackQty);
		etEvPackDesc = (TextView) findViewById(R.id.tvPackDesc);
		btnNextQty = (Button) findViewById(R.id.BtnEventForfaitCmd);
		PickerQty = (NumberPicker) findViewById(R.id.pickerOrderQty);
		
		PickerQty.setMaxValue(99);
		PickerQty.setMinValue(1);
		PickerQty.setWrapSelectorWheel(false);
	 
		
		Intent inPack = getIntent();
		
		
		String EvForfaitiD = inPack.getStringExtra( ForfaitController.TAG_pack_id); 
		String EvForfaitPrice = inPack.getStringExtra( ForfaitController.TAG_pack_Price);
		String EvForfaitQty = inPack.getStringExtra( ForfaitController.TAG_pack_Qty);
		String EvForfaitTitle = inPack.getStringExtra( ForfaitController.TAG_pack_Title);
		String EvForfaitDesc = inPack.getStringExtra( ForfaitController.TAG_pack_Description);
		String evForfaitfees = inPack.getStringExtra(ForfaitController.TAG_pack_fees);
		
		etEvPackDesc.setText(EvForfaitDesc);
		
		
		in = new Intent(this, OrderOverview.class);
		in.putExtra(ForfaitController.TAG_pack_id, EvForfaitiD);
		/*in.putExtra(ForfaitController.TAG_pack_Title, evpk.getTAG_pack_Title());
		in.putExtra(ForfaitController.TAG_pack_Qty, evpk.getTAG_pack_Qty());
		in.putExtra(ForfaitController.TAG_pack_Price, evpk.getTAG_pack_Price());
		in.putExtra(ForfaitController.TAG_pack_Description, evpk.getTAG_pack_Description());
		in.putExtra(ForfaitController.TAG_pack_fees, evpk.getTAG_pack_pack_fees());
		
		in.putExtra(EventsController.TAG_ID, EvForfaitiD);*/
		
		
		 
		
		
		btnNextQty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("order");
				in.putExtra(EventsController.TAG_pack_Qty, PickerQty.getValue() + "");
				startActivity(in);
				
			}
		});
		
		
		
	}
	
	

}
