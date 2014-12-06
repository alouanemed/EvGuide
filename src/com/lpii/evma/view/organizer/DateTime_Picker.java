package com.lpii.evma.view.organizer;
 

import com.lpii.evma.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DateTime_Picker extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organizer_event_create_datetime);

		View tv = findViewById(R.id.text);
		((TextView) tv).setText("Example of displaying an alert dialog with a DialogFragment");

		/* Watch for button clicks.
		Button button = (Button) findViewById(R.id.show);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog();
			}
		});*/
	}

	void showDialog() {
		DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.event_end_date);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void doPositiveClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "Positive click!");
	}

	public void doNegativeClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "Negative click!");
	}

	public static class MyAlertDialogFragment extends DialogFragment {

		public static MyAlertDialogFragment newInstance(int title) {
			MyAlertDialogFragment frag = new MyAlertDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int title = getArguments().getInt("title");

			return new AlertDialog.Builder(getActivity())
					.setIcon(R.drawable.profile_bg)
					.setTitle(title)
					.setPositiveButton(R.string.confirm,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((DateTime_Picker) getActivity())
											.doPositiveClick();
								}
							})
					.setNegativeButton(R.string.Annuler,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((DateTime_Picker) getActivity())
											.doNegativeClick();
								}
							}).create();
		}
	}

}

 