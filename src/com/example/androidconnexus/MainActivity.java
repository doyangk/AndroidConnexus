package com.example.androidconnexus;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {
	Button login;
	Button viewstream;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addListenerOnButton() {
		login = (Button) findViewById(R.id.button1);
		login.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				Toast toast = Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT);
			    toast.show();
			    if (checkUserAccount())
			    {
			    	Intent myIntent=new Intent(arg0.getContext(),ViewStreamsActivity.class );
			        startActivity(myIntent);
			        finish();
			    }
			}
 
		});
		
		viewstream = (Button) findViewById(R.id.button2);
		viewstream.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				Toast toast = Toast.makeText(getApplicationContext(), "View Streams", Toast.LENGTH_SHORT);
			    toast.show();
			    // Later Go to ViewStream
		        Intent myIntent=new Intent(arg0.getContext(),ViewStreamsActivity.class );
		        startActivity(myIntent);
		        finish();
			}
 
		});
	}
	
	@Override
	protected void onResume() {
	  super.onResume();
	  if (checkPlayServices()) {// && checkUserAccount()) {
	    // Then we're good to go!
	  }
	}
	
	private boolean checkPlayServices() {
       int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	   if (status != ConnectionResult.SUCCESS) {
	      if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
		      showErrorDialog(status);
		  } else {
		      Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
		      finish();
		  }
		  
	      return false;
	   }
	   return true;
	}
			 
	void showErrorDialog(int code) {
	   GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }
	
	
	private boolean checkUserAccount() {
	   String accountName = AccountUtils.getAccountName(this);
	   if (accountName == null) {
	      // Then the user was not found in the SharedPreferences. Either the
		  // application deliberately removed the account, or the application's
		  // data has been forcefully erased.
		  showAccountPicker();
		  return false;
	   }
		 
	   Account account = AccountUtils.getGoogleAccountByName(this, accountName);
	   if (account == null) {
	      // Then the account has since been removed.
		  AccountUtils.removeAccount(this);
		  showAccountPicker();
		  return false;
	   }
		 
	   return true;
   }
	private void showAccountPicker() {
		  Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(
		      null, null, new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE },
		      true, null, null, null, null);
		  startActivityForResult(pickAccountIntent, REQUEST_CODE_PICK_ACCOUNT);
		}
	

	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	static final int REQUEST_CODE_PICK_ACCOUNT = 1002;
	 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  switch (requestCode) {
	    case REQUEST_CODE_RECOVER_PLAY_SERVICES:
	      /* ... */
	    case REQUEST_CODE_PICK_ACCOUNT:
	      if (resultCode == RESULT_OK) {
	        String accountName = data.getStringExtra(
	            AccountManager.KEY_ACCOUNT_NAME);
	        AccountUtils.setAccountName(this, accountName);
	        
	        Intent nxt = new Intent(this, ViewStreamsActivity.class);
	        startActivity(nxt);
	        finish();
	        
	      } else if (resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "This application requires a Google account.",
	            Toast.LENGTH_SHORT).show();
	        finish();
	      }
	      return;
	   }
	   super.onActivityResult(requestCode, resultCode, data);
	 }


}
