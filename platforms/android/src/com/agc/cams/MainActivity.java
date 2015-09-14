/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.agc.cams;

import static com.agc.cams.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.agc.cams.CommonUtilities.EXTRA_MESSAGE;
import static com.agc.cams.CommonUtilities.SENDER_ID;

import java.util.List;

import org.apache.cordova.CordovaActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends CordovaActivity
{
	//public static String usernameGCM;
	public static String fullnameGCM;
	public static String useridGCM;
	public static String versionGCM;
	public static String regIdGCM;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	AsyncTask<Void, Void, Void> mRegisterTask;

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
    
    	
    	setBadge(getApplicationContext(), 0);
    	
		cd = new ConnectionDetector(getApplicationContext());
		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to working internet connection", false);
			// stop executing code by return
			//return;
			//finish();
			//System.exit(0);
		}
		
		
		
		
		
		//=========================================================================
		/*
			regIdGCM = "";
			//try register terlebih sebagai client biasa...
			try {
				registerGCM_PUBLIC();
			}
			catch (Exception e)
			{

			}
			regIdGCM = "" + GCMRegistrar.getRegistrationId(this);

			//Toast.makeText(getApplicationContext(), "regIdGCM=" + regIdGCM , Toast.LENGTH_LONG).show();
			//check message..
			//new CheckMessage().execute();
		*/
		//==========================================================================
			
			SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
			String fullnameG = settingsS.getString("CAMSAGC_FULLNAME", ""); 
			String useridG = settingsS.getString("CAMSAGC_USERID", ""); 
			String CAMSAGC_GCM_OPEN_ACCOUNTID = settingsS.getString("CAMSAGC_GCM_OPEN_ACCOUNTID", "");
			String CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID = settingsS.getString("CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID", "");
			//String usernameG = settingsS.getString("MDEC_USERNAME", ""); 
			//String sessionG = settingsS.getString("MDEC_SESSION", ""); 

			regIdGCM = "";
			if (useridG.equals(""))
			{
				
				//Toast.makeText(getApplicationContext(), "registerGCM_PUBLIC()" , Toast.LENGTH_LONG).show();
				//try register terlebih sebagai client biasa...
				try {
					registerGCM_PUBLIC();
				}
				catch (Exception e)
				{
					//Toast.makeText(getApplicationContext(), "error registerGCM_PUBLIC() :" + e.toString() , Toast.LENGTH_LONG).show();
				}

				regIdGCM = "" + GCMRegistrar.getRegistrationId(this);

				//Toast.makeText(getApplicationContext(), "regIdGCM=" + regIdGCM , Toast.LENGTH_LONG).show();
				//check message..
				//new CheckMessage().execute();

			}
			else if (!useridG.equals(""))
			{
				//usernameGCM = usernameG;
				fullnameGCM = fullnameG;
				useridGCM = useridG;
				
				//Toast.makeText(getApplicationContext(), "registerGCM()" , Toast.LENGTH_LONG).show();
				registerGCM();

				//check message..
				//new CheckMessage().execute();
			}
			
			//Toast.makeText(getApplicationContext(), "regIdGCM XX=" + regIdGCM , Toast.LENGTH_LONG).show();
			
			
			String versionName = "";
			try {
				versionName = getPackageManager()
						.getPackageInfo(getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
        super.onCreate(savedInstanceState);
        //super.setIntegerProperty("splashscreen", R.drawable.splash2);

        
		        
		    	Bundle extras;
		    	String autoOpenAccountId = "";
		    	extras = this.getIntent().getExtras();
		    	 
		    	if(extras == null)
		    	{        
		    		autoOpenAccountId = "";
		    	}
		    	else
		    	{            
		    		autoOpenAccountId = "" + extras.getString("accountid");
		    	}
		    	

		    	
		    	String autoContactInteractionId = "";
		    	extras = this.getIntent().getExtras();
		    	 
		    	if(extras == null)
		    	{        
		    		autoContactInteractionId = "";
		    	}
		    	else
		    	{            
		    		autoContactInteractionId = "" + extras.getString("contactinteractionid");
		    	}

		
        //super.init();
        // Set by <content src="index.html" /> in config.xml
        //super.loadUrl(Config.getStartUrl());
        
        String urlphonegap = "";
        //if (CAMSAGC_GCM_OPEN_ACCOUNTID.equals("") || CAMSAGC_GCM_OPEN_ACCOUNTID.equals("null") || CAMSAGC_GCM_OPEN_ACCOUNTID.equals("NULL"))
        if (autoOpenAccountId.equals("") || autoOpenAccountId.equals("null") || autoOpenAccountId.equals("NULL"))
        {
        	urlphonegap = "file:///android_asset/www/agc/index.html?version="+ versionName + "&regid=" + regIdGCM;
        	Log.v("Hello 111111111 =", urlphonegap);
        	//Toast.makeText(getApplicationContext(), "Hello 111111111 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 111111111 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 111111111 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 111111111 =" + urlphonegap, Toast.LENGTH_LONG).show();
        }
        else if (autoContactInteractionId.equals(""))
        {
        	//urlphonegap = "file:///android_asset/www/index.html?version="+ versionName + "&regid=" + regIdGCM + "#companylist/null/" + CAMSAGC_GCM_OPEN_ACCOUNTID;
        	urlphonegap = "file:///android_asset/www/agc/index.html?version="+ versionName + "&regid=" + regIdGCM + "#companyDetail/" + autoOpenAccountId;
        	Log.v("Hello 22222==", urlphonegap);
        	//Toast.makeText(getApplicationContext(), "Hello 22222 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 22222 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 22222 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 22222 =" + urlphonegap, Toast.LENGTH_LONG).show();
        }
        else 
        {
        	//urlphonegap = "file:///android_asset/www/index.html?version="+ versionName + "&regid=" + regIdGCM + "#companylist/null/" + CAMSAGC_GCM_OPEN_ACCOUNTID;
        	urlphonegap = "file:///android_asset/www/agc/index.html?version="+ versionName + "&regid=" + regIdGCM + "#companyDetail/" + autoOpenAccountId + "/" + autoContactInteractionId;
        	Log.v("Hello 333333333= ", urlphonegap);
        	//Toast.makeText(getApplicationContext(), "Hello 333333333 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 333333333 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 333333333 =" + urlphonegap, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), "Hello 333333333 =" + urlphonegap, Toast.LENGTH_LONG).show();
        }

        
    	//urlphonegap = "file:///android_asset/www/DASH.html";
        
        
		//alert.showAlertDialog(mdecCRM.this, CAMSAGC_GCM_OPEN_ACCOUNTID, CAMSAGC_GCM_OPEN_ACCOUNTID, false);
		//alert.showAlertDialog(mdecCRM.this, autoOpenAccountId, autoOpenAccountId, false);
    	
		if (CAMSAGC_GCM_OPEN_ACCOUNTID.equals(""))
    	{
    		//CAMSAGC_GCM_OPEN_ACCOUNTID
    	}
    	else
    	{
    		//clear balik 
    		SharedPreferences settingsS1 = getSharedPreferences("CAMSAGC", 0);
    		SharedPreferences.Editor editor1 = settingsS1.edit();
    		editor1.putString("CAMSAGC_GCM_OPEN_ACCOUNTID", ""); 
    		editor1.commit();
    	}
		
		
		if (CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID.equals(""))
    	{
    		//CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID
    	}
    	else
    	{
    		//clear balik 
    		SharedPreferences settingsS1 = getSharedPreferences("CAMSAGC", 0);
    		SharedPreferences.Editor editor1 = settingsS1.edit();
    		editor1.putString("CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID", ""); 
    		editor1.commit();
    	}
        
        
        super.loadUrl(urlphonegap);
    }
    
    
	public void registerGCM_PUBLIC()
	{

		try
		{

			// Getting name, email from intent
			Intent i = getIntent();

			//name = i.getStringExtra("name");
			//email = i.getStringExtra("email");		

			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(this);

			// Make sure the manifest was properly set - comment out this line
			// while developing the app, then uncomment it when it's ready.
			GCMRegistrar.checkManifest(this);

			//lblMessage = (TextView) findViewById(R.id.lblMessage);

			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));

			// Get GCM registration id
			final String regId = "" + GCMRegistrar.getRegistrationId(this);
			regIdGCM = regId;
			
			//Toast.makeText(getApplicationContext(), "regId dalam registerGCM_PUBLIC=" + regId, Toast.LENGTH_LONG).show();
			
			
			// Check if regid already presents
			if (regId.equals("")) {
				// Registration is not present, register now with GCM			
				//Toast.makeText(getApplicationContext(), "NOT registered with GCM", Toast.LENGTH_LONG).show();

				GCMRegistrar.register(this, SENDER_ID);

				//Toast.makeText(getApplicationContext(), "Berjaya", Toast.LENGTH_LONG).show();

				
			//} else {
			}
			
			//HASNOLMIZAM-query balik
			final String regIdII = "" + GCMRegistrar.getRegistrationId(this);
			
			//Toast.makeText(getApplicationContext(), "regIdII ==> " +regIdII, Toast.LENGTH_LONG).show();
			 
			if (!regIdII.equals("")) {
				//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				// Device is already registered on GCM


				//??? if (GCMRegistrar.isRegisteredOnServer(this)) {
				//??? // Skips registration.				
				//??? 	Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				//??? } else {

				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						//unregister dulu..context
						ServerUtilities.unregister(context, regIdII);

						// Register on our server
						// On server creates a new user

						//HASNOLMIZAM
						//ServerUtilities.register(context, name, email, regId);

						String versionName = "";
						try {
							versionName = getPackageManager()
									.getPackageInfo(getPackageName(), 0).versionName;
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Log.d(com.agc.cams.CommonUtilities.TAG, "---------------------------------");
				        Log.d(com.agc.cams.CommonUtilities.TAG, "---------------------------------");
				        Log.d(com.agc.cams.CommonUtilities.TAG, "versionName ==> " +versionName);
				        Log.d(com.agc.cams.CommonUtilities.TAG, "---------------------------------");
				        Log.d(com.agc.cams.CommonUtilities.TAG, "---------------------------------");
						
				        //Toast.makeText(getApplicationContext(), "versionName ==> " +versionName, Toast.LENGTH_LONG).show();
				        
						//ServerUtilities.register(context, fullnameGCM, useridGCM, regId, versionName);
						ServerUtilities.register(context, regIdII, regIdII, regIdII, versionName);
						
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
				//??? }
			}			
		}
		catch (Exception e)
		{
			//resume..
		}


	}

	
	
	public void registerGCM()
	{

		try
		{

			// Getting name, email from intent
			Intent i = getIntent();

			//name = i.getStringExtra("name");
			//email = i.getStringExtra("email");		

			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(this);

			// Make sure the manifest was properly set - comment out this line
			// while developing the app, then uncomment it when it's ready.
			GCMRegistrar.checkManifest(this);

			//lblMessage = (TextView) findViewById(R.id.lblMessage);

			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));

			// Get GCM registration id
			final String regId = "" + GCMRegistrar.getRegistrationId(this);
			regIdGCM = regId;
			
			// Check if regid already presents
			if (regId.equals("")) {
				// Registration is not present, register now with GCM			
				GCMRegistrar.register(this, SENDER_ID);
			} else {
				// Device is already registered on GCM


				//??? if (GCMRegistrar.isRegisteredOnServer(this)) {
				//??? // Skips registration.				
				//??? 	Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				//??? } else {

				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						//unregister dulu..context
						ServerUtilities.unregister(context, regId);

						// Register on our server
						// On server creates a new user

						//HASNOLMIZAM
						//ServerUtilities.register(context, name, email, regId);

						String versionName = "";
						try {
							versionName = getPackageManager()
									.getPackageInfo(getPackageName(), 0).versionName;
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ServerUtilities.register(context, fullnameGCM, useridGCM, regId, versionName);

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
				//??? }
			}			
		}
		catch (Exception e)
		{
			//resume..
		}


	}

	
	
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */

			// Showing received message
			//HASNOLMIZAM lblMessage.append(newMessage + "\n");			
			//HASNOLMIZAM Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	
	public void setBadge(Context context, int count) {
	    String launcherClassName = getLauncherClassName(context);
	    if (launcherClassName == null) {
	        return;
	    }
	    Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
	    intent.putExtra("badge_count", count);
	    intent.putExtra("badge_count_package_name", context.getPackageName());
	    intent.putExtra("badge_count_class_name", launcherClassName);
	    context.sendBroadcast(intent);
	    setBadgeLocal(count);
	    
	}

	public void setBadgeLocal(int nilai) {
	    //simpan dalam local
	    //------------------
		SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		SharedPreferences.Editor editor = settingsS.edit();
		editor.putString("CAMSAGC_NOTIFICATION", "" + nilai); 
		editor.commit();
	}

	public static String getLauncherClassName(Context context) {

	    PackageManager pm = context.getPackageManager();

	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_LAUNCHER);

	    List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
	    for (ResolveInfo resolveInfo : resolveInfos) {
	        String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
	        if (pkgName.equalsIgnoreCase(context.getPackageName())) {
	            String className = resolveInfo.activityInfo.name;
	            return className;
	        }
	    }
	    return null;
	}
	
	
}

