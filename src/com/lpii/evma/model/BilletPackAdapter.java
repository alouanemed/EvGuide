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

public class BilletPackAdapter extends BaseAdapter {
	// private ArrayList<EventPack> productsList;
	ArrayList<HashMap<String, String>> HashMapList;
	private Activity activity;
	private final Typeface tf;
	BilletController  mBilletController;

	public BilletPackAdapter(Activity context,
			ArrayList<HashMap<String, String>> mHashMapList, Typeface tfBold,	BilletController xmEventsController) {
		super();

		this.HashMapList = mHashMapList;
		activity = context;
		this.tf = tfBold;
		this.mBilletController = xmEventsController;

	}

 
	public static class ViewHolder {
		
		public TextView tvmyticketpackTitle;
		public TextView tvmyticketpackQrCount;
		public TextView tvmyticketpack_id;

 
// 		public ImageView img_barcode_stamp;
 
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        View v = convertView;
        ViewHolder holder;
        if (v == null) {

            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.user_myticket_pack_item, null);

            holder = new ViewHolder(); 
            
            holder.tvmyticketpackTitle = (TextView) v.findViewById(R.id.tvmyticketpackTitle);
            holder.tvmyticketpackQrCount = (TextView) v.findViewById(R.id.tvmyticketpackQrCount);
 
//            holder.img_barcode_image = (ImageView) v.findViewById(R.id.imageview_barcode_image);

            v.setTag(holder);
        } else holder = (ViewHolder) v.getTag();
 
		if (!TextUtils.isEmpty(HashMapList.get(position).get("pack_Count"))) {
			holder.tvmyticketpackQrCount.setText(HashMapList.get(position).get("pack_Count"));
			holder.tvmyticketpackQrCount.setTypeface(tf);
			holder.tvmyticketpackQrCount.setVisibility(View.VISIBLE);
		} else {
			holder.tvmyticketpackQrCount.setText(0+"");
		}

		if (!TextUtils.isEmpty(HashMapList.get(position).get("pack_Title"))) {
			holder.tvmyticketpackTitle.setText(HashMapList.get(position).get("pack_Title"));
			holder.tvmyticketpackTitle.setTypeface(tf);
		} else {
			holder.tvmyticketpackTitle.setVisibility(View.GONE);
		}
		 
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
   