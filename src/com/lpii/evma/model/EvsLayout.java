//package com.lpii.evma.model;
//
//import com.lpii.evma.R;
//import com.tipsandtricks.HelloCard.adapters.BaseInflaterAdapter;
//import com.tipsandtricks.HelloCard.adapters.CardItemData;
//import com.tipsandtricks.HelloCard.adapters.inflaters.CardInflater;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewTreeObserver.OnGlobalLayoutListener;
//import android.view.animation.AnimationUtils;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//public class EvsLayout extends LinearLayout implements OnGlobalLayoutListener {
//
//	public EvsLayout(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initLayoutObserver();
//
//	}
//
//	public EvsLayout(Context context) {
//		super(context);
//		initLayoutObserver();
//	}
//
//	private void initLayoutObserver() {
//		setOrientation(LinearLayout.VERTICAL);
//		getViewTreeObserver().addOnGlobalLayoutListener(this);
//	}
//
//	@Override
//	public void onGlobalLayout() {
//		getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;
//		
//		ListView list = (ListView)findViewById(R.id.list_view);
//
//		list.addHeaderView(new View(this));
//		list.addFooterView(new View(this));
//
//		BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
//		for (int i = 0; i < 50; i++)
//		{
//			CardItemData data = new CardItemData("Item " + i + " Line 1", "Item " + i + " Line 2", "Item " + i + " Line 3");
//			adapter.addItem(data, false);
//		}
//
//		list.setAdapter(adapter);
//
//		boolean inversed = false;
//		final int childCount = getChildCount();
//
//		for (int i = 0; i < childCount; i++) {
//			View child = getChildAt(i);
//
//			int[] location = new int[2];
//
//			child.getLocationOnScreen(location);
//
//			if (location[1] > heightPx) {
//				break;
//			}
//
//			if (!inversed) {
//				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
//						R.anim.slide_up_left));
//			} else {
//				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
//						R.anim.slide_up_right));
//			}
//
//			inversed = !inversed;
//		}
//
//	}
//
//}
