package com.lpii.evma.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.lpii.evma.R;
import com.lpii.evma.controller.ForfaitController;

import java.util.ArrayList;
import java.util.HashMap;

public class ForfaitAdapter extends BaseAdapter {
	// private ArrayList<EventPack> productsList;
	ArrayList<HashMap<String, String>> HashMapList;
	private Activity activity;
	private final Typeface tf;
	ForfaitController mForfaitController;

	public ForfaitAdapter(Activity context,
			ArrayList<HashMap<String, String>> mHashMapList, Typeface tf,	ForfaitController xmForfaitController) {
		super();

		this.HashMapList = mHashMapList;
		activity = context;
		this.tf = tf;
		this.mForfaitController = xmForfaitController;

	}

	/**
	 * Define the ViewHolder for our adapter
	 */
	public static class ViewHolder {

		public TextView tvforfaitTitle;
		public TextView tvForfaitType;
		public TextView tvforfaitSoldetickets;
		public TextView tvforfaittickets;
		public TextView tv_event_forf_id;
		public TextView pack_Statut_Pack; 

		// public TextView title;
		public TextView description;
		public ImageView image;
		public TextView price;

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
            v = vi.inflate(R.layout.event_forfait_list_item, null);

            holder = new ViewHolder(); 
            
            holder.tvforfaitTitle = (TextView) v.findViewById(R.id.tvforfaitTitle);
            holder.tvForfaitType = (TextView) v.findViewById(R.id.tvForfaitType);
            holder.tvforfaitSoldetickets = (TextView) v.findViewById(R.id.tvforfaitSoldetickets);
            holder.tvforfaittickets = (TextView) v.findViewById(R.id.tvforfaittickets);
            holder.tv_event_forf_id = (TextView) v.findViewById(R.id.tv_event_forf_id);
            holder.pack_Statut_Pack = (TextView) v.findViewById(R.id.pack_Statut_Pack); 
            
            //holder.price = (TextView) v.findViewById(R.id.price);
            holder.image = (ImageView) v.findViewById(R.id.image);

            v.setTag(holder);
        } else holder = (ViewHolder) v.getTag();
        // ------------------------------------------

        // This is where we actually start filling the data
        
		
		EventPack evPack = mForfaitController.getSingleForfaitByID(HashMapList.get(position).get(ForfaitController.TAG_pack_id), "WithoutString");
		System.out.println(evPack.toString());
		if (evPack != null) {
			// pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_Title())) {
	            holder.tvforfaitTitle.setText(evPack.getTAG_pack_Title());
	            holder.tvforfaitTitle.setTypeface(tf);
	            holder.tvforfaitTitle.setVisibility(View.VISIBLE);
	            if (evPack.getTAG_pack_isVisible().equals("1")) {
	            	holder.tvforfaitTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.visible, 0);
				}else  if (evPack.getTAG_pack_isVisible().equals("0")) {
					System.out.println("ot should be hidden");
					holder.tvforfaitTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.hidden, 0);
				} 
	            if (evPack.getTAG_pack_Qty().equals(evPack.getTAG_pack_Solde())) {
					holder.tvforfaitTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.my_plans_attending, 0);
				}
	        } else {
	            holder.tvforfaitTitle.setVisibility(View.GONE);
	        }
	        
	     // pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_id())) {
	            holder.tv_event_forf_id.setText(evPack.getTAG_pack_id());
	            holder.tv_event_forf_id.setTypeface(tf); 
	        } else {
	            holder.tv_event_forf_id.setVisibility(View.GONE);
	        }
	        
	        // pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_Price())) {
	            holder.tvForfaitType.setText(evPack.getTAG_pack_Price());
	            holder.tvForfaitType.setTypeface(tf);
	            holder.tvForfaitType.setVisibility(View.VISIBLE); 
	        } else {
	            holder.tvForfaitType.setVisibility(View.GONE);
	        }
	        
	     // pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_Solde())) {
	            holder.tvforfaitSoldetickets.setText(evPack.getTAG_pack_Solde());
	            holder.tvforfaitSoldetickets.setTypeface(tf);
	            holder.tvforfaitSoldetickets.setVisibility(View.VISIBLE); 
	        } else {
	            holder.tvforfaitSoldetickets.setVisibility(View.GONE);
	        }
	        
	        // pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_Qty())) {
	            holder.tvforfaittickets.setText(evPack.getTAG_pack_Qty());
	            holder.tvforfaittickets.setTypeface(tf);
	            holder.tvforfaittickets.setVisibility(View.VISIBLE); 
	        } else {
	            holder.tvforfaittickets.setVisibility(View.GONE);
	        }
	        
	     // pack title
	        if (!TextUtils.isEmpty(evPack.getTAG_pack_Statut_Pack())) {
	            holder.pack_Statut_Pack.setText(evPack.getTAG_pack_Statut_Pack());
	            holder.pack_Statut_Pack.setTypeface(tf);  
	        } else {
	            holder.pack_Statut_Pack.setVisibility(View.GONE);
	        }
	        
	      
		}
		 
      
        return v;
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
