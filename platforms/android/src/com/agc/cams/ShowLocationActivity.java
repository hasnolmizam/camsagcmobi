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

public class ShowLocationActivity extends CordovaActivity
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
			alert.showAlertDialog(ShowLocationActivity.this,
					"Internet Connection Error",
					"Please connect to working internet connection", false);
			// stop executing code by return
			//return;
			//finish();
			//System.exit(0);
		}
		
        super.onCreate(savedInstanceState);
        //super.setIntegerProperty("splashscreen", R.drawable.splash2);

		        
    	Bundle extras;
    	String phid = "";
    	extras = this.getIntent().getExtras();
    	 
    	if(extras == null)
    	{        
    		phid = "";
    	}
    	else
    	{            
    		phid = "" + extras.getString("phid");
    	}
		    	

        //super.init();
        // Set by <content src="index.html" /> in config.xml
        //super.loadUrl(Config.getStartUrl());
  
		//String urlphonegap = "file:///android_asset/www/agc/showlocation.html?phid="+ phid;
    	//String urlphonegap = "file:///android_asset/www/agc/showlocationBACKUP.html?phid="+ phid;
    	String urlphonegap = "file:///android_asset/www/agc/showlocation4.html?phid="+ phid;
		
        super.loadUrl(urlphonegap);
    }
    
 
	
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

