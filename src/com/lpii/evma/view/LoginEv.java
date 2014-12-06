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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.UserController;
import com.lpii.evma.model.User;

import de.keyboardsurfer.android.widget.crouton.Crouton;


public class LoginEv extends Activity implements OnClickListener{
	EvmaApp EvmaApplication;
	
	ImageButton btnLogin;
	EditText etPassword,etUsername;
	TextView tv;
	ProgressDialog dialog = null;
	
	//Login Controller
	UserController loginC;
	
	String url = "";
	Boolean rslt = false;
	
	//form validation
	private Map<Integer, ValidationFailedRenderer> mVaildationRenderers;
	 // Form used for validation
	private Form mForm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lgn);
		EvmaApplication = (EvmaApp) getApplication();
		Gson gson = new Gson();
    	SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
	    String json = mPrefs.getString("CurrentUser", "");
	    User usx = gson.fromJson(json, User.class);
	    if (usx != null) {
	    	EvmaApp.CurrentUser = usx; 
		    if (EvmaApp.CurrentUser !=null) {
		    	if (EvmaApp.CurrentUser.getRole().equals("Utilisateur")) {
		    		EvmaApp.CurrentUsername = EvmaApp.CurrentUser.getUsername();
					EvmaApp.CurrentUserEmail = EvmaApp.CurrentUser.getEmail();
					EvmaApp.CurrentOrganizer = "";
					EvmaApp.CurrentUsernameID =   Integer.valueOf(EvmaApp.CurrentUser.getUser_id())  ;
					Intent inToMain = new Intent(LoginEv.this,MainEv.class);
				    	startActivity(inToMain);
				}else{
					EvmaApp.CurrentOrganizer = EvmaApp.CurrentUser.getUsername();
					EvmaApp.CurrentUsername = "";
					EvmaApp.CurrentUsernameID  =   Integer.valueOf(EvmaApp.CurrentUser.getUser_id())  ;
					Intent intUser = new Intent(LoginEv.this,MainEv.class);
					startActivity(intUser);
				}
		    }
		}

		//
		btnLogin = (ImageButton) findViewById(R.id.btnLoginCheck);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
		etUsername.setTypeface(tf);
		etPassword.setTypeface(tf);
		
		initValidationForm();
		
		btnLogin.setOnClickListener(this);
		loginC = new UserController();
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		 
		
	}

	@Override
	public void onClick(View arg0) {
		//FormUtils.hideKeyboard(LoginEv.this, etUsername);
        if (mForm.isValid()) {
        	new LoginAsync().execute();
            //Crouton.makeText(this, getString(R.string.form_is_valid), Style.CONFIRM).show();
 	   		 		
   		
        }
		
	}
	

	private class LoginAsync extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
            dialog = ProgressDialog.show(LoginEv.this, "", "Validating user...", true);

	
		}
	
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				rslt = loginC.loginEvuser(EvmaApplication.LoginUrl, etUsername.getText().toString(), etPassword.getText().toString());
			    System.out.println("val of rslt " + rslt);
				
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
			dialog.dismiss();
			System.out.println("done Login ..." ); 
			if (EvmaApp.isError == false) {

				Crouton crouton = Crouton.makeText(LoginEv.this, "Login Success", de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);
		        crouton.show();
		    	//Toast.makeText(LoginEv.this,"Login Success", Toast.LENGTH_SHORT).show();
		    	dialog.dismiss();
		    	EvmaApp.CurrentUsername = etUsername.getText().toString();
//		    	User us = new User(EvmaApp.CurrentUsernameID +"", etUsername.getText().toString(), "", "Utilisateur", "Med Alouane", "", "", "");
//		    	EvmaApp.CurrentUser = us;
		    	
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
					Intent inToMain = new Intent(LoginEv.this,MainEv.class);
	 		    	startActivity(inToMain);
				}else{
					EvmaApp.CurrentOrganizer = EvmaApp.CurrentUser.getUsername();
					EvmaApp.CurrentUsername = "";
					EvmaApp.CurrentUsernameID  =   Integer.valueOf(EvmaApp.CurrentUser.getUser_id())  ;
					Intent intUser = new Intent(LoginEv.this,MainEv.class);
					startActivity(intUser);
				}
 		    	 
		    	

	   		}else{
	   			Crouton crouton = Crouton.makeText(LoginEv.this, "Login Fail Retry ...", de.keyboardsurfer.android.widget.crouton.Style.ALERT);
		        crouton.show();
		    	Toast.makeText(LoginEv.this,"Login Fail", Toast.LENGTH_SHORT).show();
		    	dialog.dismiss();;
	   		}	
			 
	
		}
	
	}
	
	private void initValidationForm() {
        mForm = new Form(this);
        mForm.setValidationFailedRenderer(new com.lpii.evma.controller.CroutonValidationFailedRenderer(this));

        mForm.addField(Field.using(etUsername).validate(NotEmpty.build(this)));
        mForm.addField(Field.using(etPassword).validate(NotEmpty.build(this)));
        //mForm.addField(Field.using(mAge).validate(InRange.build(this, 0, 120)));
        
        mForm.getValidationFailedRenderer().clear();
        mForm.setValidationFailedRenderer(new TextViewValidationFailedRenderer(this));
    }

	
	///
	public void showAlert(){
		runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(LoginEv.this);
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
