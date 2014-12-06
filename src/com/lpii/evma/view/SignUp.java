package com.lpii.evma.view;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ua.org.zasadnyy.zvalidations.Field;
import ua.org.zasadnyy.zvalidations.Form;
import ua.org.zasadnyy.zvalidations.FormUtils;
import ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import ua.org.zasadnyy.zvalidations.ValidationFailedRenderer;
import ua.org.zasadnyy.zvalidations.validations.NotEmpty;
import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.UserController;
import com.lpii.evma.model.User;

import de.keyboardsurfer.android.widget.crouton.Crouton;


public class SignUp extends Activity implements OnClickListener{
	EvmaApp EvmaApplication;
	
	Button btn_sign_up;
	FloatLabelEditText etPassword,etUsername;
	FloatLabelEditText etFirstName,etLastName,etEmail;
	TextView tv;
	ProgressDialog dialog = null;
	CheckBox cbIsOrga;
	
	//Login Controller
	UserController uUserController;
	User us;
	
	String url = "";
	Boolean rslt = false;
	String Role;
	
	//form validation
	private Map<Integer, ValidationFailedRenderer> mVaildationRenderers;
	 // Form used for validation
	private Form mForm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		EvmaApplication = (EvmaApp) getApplication();
		

		btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
		etUsername = (FloatLabelEditText) findViewById(R.id.signUsername);
		etPassword = (FloatLabelEditText) findViewById(R.id.password);
		etFirstName = (FloatLabelEditText) findViewById(R.id.first_name);
		etLastName = (FloatLabelEditText) findViewById(R.id.last_name);
		etEmail = (FloatLabelEditText) findViewById(R.id.email);
		cbIsOrga = (CheckBox) findViewById(R.id.isOrga);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
		etUsername.getEditText().setTypeface(tf);
		etPassword.getEditText().setTypeface(tf);
		etFirstName.getEditText().setTypeface(tf);
		etLastName.getEditText().setTypeface(tf);
		etEmail.getEditText().setTypeface(tf);
		cbIsOrga.setTypeface(tf);
		//etEmail.getEditText().setInputType(EditText)
		
		
		initValidationForm();
		
		btn_sign_up.setOnClickListener(this);
		uUserController = new UserController();
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		cbIsOrga.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true) {
					Role = "Organisateur";
				}else{
					Role = "Utilisateur";
				}
				  
			}
		});
		
		
	}

	@Override
	public void onClick(View arg0) {
		FormUtils.hideKeyboard(SignUp.this, etUsername.getEditText());
        if (mForm.isValid()) {
            //Crouton.makeText(this, getString(R.string.form_is_valid), Style.CONFIRM).show();
            dialog = ProgressDialog.show(SignUp.this, "", "Validating user...", true);

			new AddUserAsync().execute();
			if (EvmaApp.isError == true) {
				dialog.dismiss();
				runOnUiThread(new Runnable() {
					public void run() {
						Crouton crouton = Crouton
								.makeText(
										SignUp.this,
										"L'inscription de votre compte a échoué ,  Veuillez ",
										de.keyboardsurfer.android.widget.crouton.Style.ALERT);
						crouton.show();
						dialog.dismiss();
						;
					}
				});

	   		}else{
				dialog.dismiss();
	   		}		
   		
        }
		
	}
	
	
	private void initValidationForm() {
        mForm = new Form(this);
        mForm.setValidationFailedRenderer(new com.lpii.evma.controller.CroutonValidationFailedRenderer(this));

        mForm.addField(Field.using(etFirstName.getEditText()).validate(NotEmpty.build(this)));
        mForm.addField(Field.using(etLastName.getEditText()).validate(NotEmpty.build(this)));
        mForm.addField(Field.using(etUsername.getEditText()).validate(NotEmpty.build(this)));
        mForm.addField(Field.using(etEmail.getEditText()).validate(NotEmpty.build(this)));
        mForm.addField(Field.using(etPassword.getEditText()).validate(NotEmpty.build(this)));
        
        //mForm.addField(Field.using(mAge).validate(InRange.build(this, 0, 120)));
        
        mForm.getValidationFailedRenderer().clear();
        mForm.setValidationFailedRenderer(new TextViewValidationFailedRenderer(this));
    }

	
	
	private class AddUserAsync extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 
	
		}
	
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Date DateNow = Calendar.getInstance().getTime();
				Date d_usr = null;
				System.out.println(DateNow.toString());
				String strDate ="";
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				strDate = sdfDate.format(now);
				System.out.println("daaaaate  "   +strDate);
				us = new User("0", etUsername.getText().toString(), etPassword.getText().toString(),Role, etFirstName.getText().toString()+etLastName.getText().toString(), etEmail.getText().toString(), strDate,strDate);
				
				uUserController.AddUser(us, "AddUser");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
			
			System.out.println("doInBackground donnne");
			 
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
 
			System.out.println("done adding us  ID " ); 
			if (EvmaApp.isError == false) {

				Crouton crouton = Crouton.makeText(SignUp.this,
						"Félicitations, votre compte a été créé avec succès ",
						de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);
				crouton.show();
				// Toast.makeText(LoginEv.this,"Login Success",
				// Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				SharedPreferences  mPrefs = getSharedPreferences("Mypref" ,	MODE_PRIVATE);
		    	
		    	Editor prefsEditor = mPrefs.edit();
		        Gson gson = new Gson();
		        String json = gson.toJson(EvmaApp.CurrentUser);
		        prefsEditor.putString("CurrentUser", json);
		        prefsEditor.commit();
		        
		    	if (EvmaApp.CurrentUser.getRole().equals("Utilisateur")) {
		    		EvmaApp.CurrentUsername = EvmaApp.CurrentUser.getUsername();
					EvmaApp.CurrentUserEmail = EvmaApp.CurrentUser.getEmail();
					EvmaApp.CurrentOrganizer = "";
					EvmaApp.CurrentUsernameID =   Integer.valueOf(EvmaApp.CurrentUser.getUser_id())  ;
					Intent inToMain = new Intent(SignUp.this,MainEv.class);
	 		    	startActivity(inToMain);
				}else{
					EvmaApp.CurrentOrganizer = EvmaApp.CurrentUser.getUsername();
					EvmaApp.CurrentUsername = "";
					EvmaApp.CurrentUsernameID  =   Integer.valueOf(EvmaApp.CurrentUser.getUser_id())  ;
					Intent intUser = new Intent(SignUp.this,MainEv.class);
					startActivity(intUser);
				}
 		    	 
//				EvmaApp.CurrentOrganizer = "";
//				EvmaApp.CurrentUser = us;
//				Intent inToMain = new Intent(SignUp.this, MainEv.class);
//				startActivity(inToMain);

	   		}
			 
	
		}
	
	}
	
	public void showAlert(){
		runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
		    	builder.setTitle("Login Error.");
		    	builder.setMessage("User not Found.")  
		    	       .setCancelable(false)
		    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface dialog, int id) {
		    	           }
		    	       });		    	       
		    	AlertDialog alert = builder.create();
		    	alert.show();		    	
		    }
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}


}

 