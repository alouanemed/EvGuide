package com.lpii.evma.model;
  
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.util.TextUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.BilletController;
import com.lpii.evma.controller.EventsController;
import com.squareup.picasso.Picasso;

public class BilletAdapter extends BaseAdapter {
	// private ArrayList<EventPack> productsList;
	ArrayList<HashMap<String, String>> HashMapList;
	private Activity activity;
	private final Typeface tf;
	BilletController  mBilletController;

	public BilletAdapter(Activity context,
			ArrayList<HashMap<String, String>> mHashMapList, Typeface tfBold,	BilletController xmEventsController) {
		super();

		this.HashMapList = mHashMapList;
		activity = context;
		this.tf = tfBold;
		this.mBilletController = xmEventsController;

	}

	/**
	 * Define the ViewHolder for our adapter
	 */
	public static class ViewHolder {
		
		public TextView barcode_attendee_name;
		public TextView Qr_ticket_name;
 
 
 		public ImageView img_barcode_stamp;
 		public ImageView img_barcode_image;
 
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        View v = convertView;
        ViewHolder holder;
        if (v == null) {

            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.my_ticket_single_event_item, null);

            holder = new ViewHolder(); 
            
            holder.barcode_attendee_name = (TextView) v.findViewById(R.id.textview_barcode_attendee_name);
            holder.Qr_ticket_name = (TextView) v.findViewById(R.id.textview_barcode_ticket_name);
 
            holder.img_barcode_stamp = (ImageView) v.findViewById(R.id.imageview_barcode_stamp);
            holder.img_barcode_image = (ImageView) v.findViewById(R.id.imageview_barcode_image);

            v.setTag(holder);
        } else holder = (ViewHolder) v.getTag();
        // ------------------------------------------
     
 
		// pack title
		if (!TextUtils.isEmpty(HashMapList.get(position).get("pack_Title"))) {
			holder.barcode_attendee_name.setText(EvmaApp.CurrentUser.getFull_name());
			holder.barcode_attendee_name.setTypeface(tf);
			holder.barcode_attendee_name.setVisibility(View.VISIBLE);
		} else {
			holder.barcode_attendee_name.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(HashMapList.get(position).get("pack_Title"))) {
			holder.Qr_ticket_name.setText(HashMapList.get(position).get("pack_Title"));
			holder.Qr_ticket_name.setTypeface(tf);
		} else {
			holder.Qr_ticket_name.setVisibility(View.GONE);
		}
		String SingleusURL = String.format(EvmaApp.UserTicketsURL,EvmaApp.CurrentUser.getUsername());
 	    String urldisplay =SingleusURL + "Image-" + HashMapList.get(position).get("billet_id") +".png";
 	    System.out.println("Lets  display  : "  + urldisplay);
		Picasso.with(activity).load(urldisplay).placeholder(R.drawable.broken_qr_code).into(holder.img_barcode_image);

 	   //Picasso.with(context).load("http://postimg.org/image/wjidfl5pd/").into(imageView);
 	   //new DownloadImageTask(holder.imgThumbnail).execute(urldisplay);
 	     
	 
      
        return v;
    }
	  
	@Override
	public int getCount() {
		return HashMapList == null ? 0 : HashMapList.size();
	}

	@Override
	public Object getItem(int position) {
		return HashMapList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
  