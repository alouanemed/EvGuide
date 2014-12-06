package com.lpii.evma.model;
 

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class EventsAdapter extends BaseAdapter {
	// private ArrayList<EventPack> productsList;
	ArrayList<HashMap<String, String>> HashMapList;
	private Activity activity;
	private final Typeface tf;
	EventsController  mEventsController;

	public EventsAdapter(Activity context,
			ArrayList<HashMap<String, String>> mHashMapList, Typeface tfBold,	EventsController xmEventsController) {
		super();

		this.HashMapList = mHashMapList;
		activity = context;
		this.tf = tfBold;
		this.mEventsController = xmEventsController;

	}

	/**
	 * Define the ViewHolder for our adapter
	 */
	public static class ViewHolder {
		
		public TextView EvTitle;
		public TextView card_event_description;
		public TextView event_description_date;
		public TextView event_xdescription_dateFIN;
		
		public TextView Ev_Idx;
		public TextView Ev_Start_Pricex;
		public TextView EvDateMonth;
		public TextView EvDateDay;
 
 		public ImageView imgThumbnail;
 
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // ---------------
        // Boilerplate view inflation and ViewHolder code
        // ---------------
        View v = convertView;
        ViewHolder holder;
        if (v == null) {

            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_event_item2, null);

            holder = new ViewHolder(); 
            
            holder.EvTitle = (TextView) v.findViewById(R.id.EvTitle);
            holder.card_event_description = (TextView) v.findViewById(R.id.card_event_description);
            holder.event_description_date = (TextView) v.findViewById(R.id.event_description_date);
            holder.event_xdescription_dateFIN = (TextView) v.findViewById(R.id.event_xdescription_dateFIN);
            holder.Ev_Idx = (TextView) v.findViewById(R.id.Ev_Idx);
            holder.Ev_Start_Pricex = (TextView) v.findViewById(R.id.Ev_Start_Pricex); 
            holder.EvDateMonth = (TextView) v.findViewById(R.id.EvDateMonth); 
            holder.EvDateDay = (TextView) v.findViewById(R.id.EvDateDay); 
            
            //holder.price = (TextView) v.findViewById(R.id.price);
            holder.imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);

            v.setTag(holder);
        } else holder = (ViewHolder) v.getTag();
        // ------------------------------------------
        Event ev = new Event(Integer.valueOf(HashMapList.get(position).get(EventsController.TAG_ID)), HashMapList.get(position).get(EventsController.TAG_NAME),
       			Integer.valueOf(HashMapList.get(position).get(EventsController.TAG_user_id)), HashMapList.get(position).get(EventsController.TAG_dateTime), 
       			HashMapList.get(position).get(EventsController.TAG_dateTime_fin), HashMapList.get(position).get(EventsController.TAG_event_Statut),
       			HashMapList.get(position).get(EventsController.TAG_event_Description), HashMapList.get(position).get(EventsController.TAG_event_cover),
       			Boolean.valueOf(HashMapList.get(position).get(EventsController.TAG_visible)));
		System.out.println(ev.toString());
		if (ev != null) {
			// pack title
	        if (!TextUtils.isEmpty(ev.getTitle())) {
	            holder.EvTitle.setText(ev.getTitle());
	            holder.EvTitle.setTypeface(tf);
	            holder.EvTitle.setVisibility(View.VISIBLE);
	        } else {
	            holder.EvTitle.setVisibility(View.GONE);
	        }
	        
 	        if (!TextUtils.isEmpty(ev.getEventID()+"")) {
	            holder.Ev_Idx.setText(ev.getEventID()+"");
	            holder.Ev_Idx.setTypeface(tf); 
	        } else {
	            holder.Ev_Idx.setVisibility(View.GONE);
	        }
	        
	        // pack title
	        if (!TextUtils.isEmpty(ev.getEvent_Description())) {
	            holder.card_event_description.setText(ev.getEvent_Description());
	            holder.card_event_description.setTypeface(tf);
	        } else {
	            holder.card_event_description.setVisibility(View.GONE);
	        }
	        
	     // pack title
	        if (!TextUtils.isEmpty(ev.getDateTime())) {
	            holder.event_description_date.setText(ev.getDateTime());
	            holder.event_description_date.setTypeface(tf);
	        } else {
	            holder.event_description_date.setVisibility(View.GONE);
	        }
	        
 	        if (!TextUtils.isEmpty(ev.getDateTimeFin())) {
	            holder.event_xdescription_dateFIN.setText(ev.getDateTimeFin());
	            holder.event_xdescription_dateFIN.setTypeface(tf);
	        } else {
	            holder.event_xdescription_dateFIN.setVisibility(View.GONE);
	        }
	        
 	       if (!TextUtils.isEmpty(HashMapList.get(position).get(EventsController.TAG_event_MinPrice))) {
	            holder.Ev_Start_Pricex.setText(HashMapList.get(position).get(EventsController.TAG_event_MinPrice));
	            holder.Ev_Start_Pricex.setTypeface(tf);  
	        } else {
	            holder.Ev_Start_Pricex.setVisibility(View.GONE);
	        }
 	       
 	      if (!TextUtils.isEmpty(HashMapList.get(position).get(EventsController.TAG_dateTime_day))) {
	            holder.EvDateDay.setText(HashMapList.get(position).get(EventsController.TAG_dateTime_day));
	            holder.EvDateDay.setTypeface(tf);  
	        } else {
	            holder.EvDateDay.setVisibility(View.GONE);
	        }
 	      
 	     if (!TextUtils.isEmpty(HashMapList.get(position).get(EventsController.TAG_dateTime_month))) {
	            holder.EvDateMonth.setText(HashMapList.get(position).get(EventsController.TAG_dateTime_month));
	            holder.EvDateMonth.setTypeface(tf);  
	        } else {
	            holder.EvDateMonth.setVisibility(View.GONE);
	        }
	        
 	    String urldisplay = EvmaApp.EventCoverUrl + HashMapList.get(position).get(EventsController.TAG_event_cover);
		Picasso.with(activity).load(urldisplay).placeholder(R.drawable.defaultcoverx).into(holder.imgThumbnail);

 	   //Picasso.with(context).load("http://postimg.org/image/wjidfl5pd/").into(imageView);
 	   //new DownloadImageTask(holder.imgThumbnail).execute(urldisplay);
 	     
	      
		}
		 
      
        return v;
    }
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
 	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	  }

	// ---------------
	// More boilerplate methods
	// ---------------

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
 