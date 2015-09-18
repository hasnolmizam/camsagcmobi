package com.agc.cams;

import static com.agc.cams.CommonUtilities.SENDER_ID;
import static com.agc.cams.CommonUtilities.displayMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gcm.GCMBaseIntentService;
//import android.speech.tts.TextToSpeech;
//import android.speech.tts.TextToSpeech.OnInitListener;

//public class GCMIntentService extends GCMBaseIntentService implements OnInitListener {
public class GCMIntentService extends GCMBaseIntentService  {

	JSONParser jParser = new JSONParser();
    public double latitude = 0;
    public double longitude = 0;
    public String GLOBAL_PHID = "0";
    public String GLOBAL_usernameRequester = "";
    
	private static final String TAG = "GCMIntentService";
	//private static TextToSpeech tts;


    public GCMIntentService() {
        super(SENDER_ID);
        
    }
 
    @Override
    public void onCreate() {

		//tts = new TextToSpeech(this,this);
		//tts.setLanguage(Locale.getDefault());
        super.onCreate();
    }
    
    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //HASNOLMIZAM displayMessage(context, "Your device registred with GCM");
        //Log.d("NAME", MainActivity.name);
        
    
        //HASNOLMIZAM
        //ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);
        //ServerUtilities.register(context, "John", "Doe", registrationId);
        ServerUtilities.register(context, MainActivity.fullnameGCM, MainActivity.useridGCM, registrationId, MainActivity.versionGCM);
    
    
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        //HASNOLMIZAM displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        
        
        String message = intent.getExtras().getString("price");
       
    	if (message != null)
    	{
    		if (!message.equals("")) 
    		{
    	        //Log.i(TAG, "Received message");

    	        //pecahkan message utk dapatkan accountid..
    	        String accountid = "";
    	        String contactinteractionid = "";
    	        String phid = "";
    	        
    	        //String XArray = 
    	        
    	        /*
    	        int i = message.indexOf("#");
    	        if (i >= 0)
    	        {
    	        	
    	        	//ORIGINAL
    	        	//accountid = "" + message.substring(i+1, message.length());
    	        	//message = message.substring(0,i);
    	        	String XArray[] = message.split("#");
    	        	message = XArray[0];
    	        	accountid= XArray[1];
    	        	
    	    		//Toast.makeText(this, "XArray.XArray.LENGTH=" + XArray.length, Toast.LENGTH_LONG).show();
    	    		//Toast.makeText(this, "XArray.XArray.LENGTH=" + XArray.length, Toast.LENGTH_LONG).show();
					//Toast.makeText(this, "XArray.XArray.LENGTH=" + XArray.length, Toast.LENGTH_LONG).show();
					//Toast.makeText(this, "XArray.XArray.LENGTH=" + XArray.length, Toast.LENGTH_LONG).show();
    	        	
    	        	//if (XArray.length == 2)
    	        	//{
    	        		try
    	        		{
    	        			contactinteractionid = "" + XArray[2];
    	        		}
    	        		catch (Exception ex)
    	        		{
    	        			// do nothing
    	        		}
    	        	//}
					//Toast.makeText(this, "XArray.XArray.contactinteractionid=" + contactinteractionid, Toast.LENGTH_LONG).show();
    	        }
    	        */
    	        
    	        String SHOWLOKASI = "N";
    	        int i = message.indexOf("PEMBERITAHUAN LOKASI");
    	        if (i >= 0)
    	        {
    	        	SHOWLOKASI = "Y";
    	        
    	        	String XArray[] = message.split("#");
    	        	phid = XArray[0];
    	        	message= XArray[1];
    	        
    	        }
    	        
    	        i = message.indexOf("TIDAK MENGAKTIFKAN GPS");
    	        if (i >= 0)
    	        {
    	        	SHOWLOKASI = "N";
    	        
    	        	String XArray[] = message.split("#");
    	        	phid = XArray[0];
    	        	message= XArray[1];
    	        
    	        }
    	        
    	        
    	        
    	        String LOCATIONREQUEST = "N";
    	        i = message.indexOf("LOCATIONREQUEST");
    	        if (i >= 0)
    	        {
    	        	LOCATIONREQUEST = "Y";
    	        	String XArray[] = message.split("#");
    	        	phid = XArray[0];
    	        	message= XArray[1];
    	        	GLOBAL_usernameRequester= XArray[2];
    	        
    	        }

    	        GLOBAL_PHID = phid;
    	        
    	        
    	        displayMessage(context, message);
    	        // notifies user
    	        
    	        if (SHOWLOKASI.equals("Y"))
    	        {
    	        	//bentuk custom
    	        	//===============
    	        	//generateNotificationCustomLayoutAGC(context, message, phid);
    	        	
    	        	//bentuk standard
    	        	//===============
    	        	generateNotificationAGC(context, message, phid);
    	        }
    	        else if (LOCATIONREQUEST.equals("Y"))
    	        {
    	        	
    	        	generateNotificationCustomLayoutAGC_SENDLOCATION(context, phid);
    	        }
    	        else 
    	        {
    	        	//bentuk custom
    	        	//===============
    	        	//generateNotificationCustomLayout(context, message, accountid);
    	        	
    	        	//bentuk standard
    	        	//===============
    	        	generateNotificationStandard (context, message, accountid);
    	        }
    	        
    	        /*
    	        
    	        //generateNotification(context, message);
    	        if (contactinteractionid.equals("") || contactinteractionid.equals("null"))
    	        {
    	        	generateNotificationCustomLayout(context, message, accountid);
    	        }
    	        else
    	        {
    	        	generateNotificationCustomLayout2(context, message, accountid, contactinteractionid);
        	    }
        	    */
    		}
    	}

    }

    
    
     
    
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        String message = getString(R.string.gcm_deleted, total);
    	if (message != null)
    	{
    		if (!message.equals("")) 
    		{
    	        Log.i(TAG, "Received deleted messages notification");
    	        displayMessage(context, message);
    	        // notifies user
    	        //generateNotification(context, message);
    	        generateNotificationCustomLayout(context, message, "");
    		}
    	}
        
        
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    
    
    
    private static void generateNotification(Context context, String message) {
        
    	//int icon = R.drawable.komlogoborder;
    	int icon = R.drawable.mdeccrm;
    	
    	
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

    }

    /*
    public void bbbbb(String userFullName)
    {
    	String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        
        
        int icon = R.drawable.birthdaybox;
        CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        
        
				        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
				    	contentView.setImageViewResource(R.id.image, R.drawable.birthdaybox);
				    	contentView.setTextViewText(R.id.title, "Dear " + userFullName);
				    	contentView.setTextViewText(R.id.text, "SMP wish you a very warm birthday.\nHAPPY BIRTHDAY!");
				    	notification.contentView = contentView;    	
				
				    	Intent notificationIntent = new Intent(this, dsmpv2_intro.class);
				    	PendingIntent contentIntent = PendingIntent.getActivity(this, 7, notificationIntent, 0);
				    	notification.contentIntent = contentIntent;
    	
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate;
        
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;

        mNotificationManager.notify(6, notification);       
    }
    */
    

    
   
    
    private void generateNotificationCustomLayoutAGC_SENDLOCATION (Context context, String phid) {
    	
    		//terus dapatkan coordinat dan call API 
    		//http://10.17.14.210/cams/api_generator.php?api_name=check_in_by_applicant&ph_id=600&stf_username=adlina&lng=1.0001&lat=3.00001
    	
    	new SendingLocationInfo().execute();
    	
    	

    }
    
   private void generateNotificationAGC (Context context, String message, String phid) {
        
    	//int icon = R.drawable.komlogoborder;
    	int icon = R.drawable.camsagc3white;
    	
    	
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, ShowLocationActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("phid", phid);
    	
        
        
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    	
        
        
        PendingIntent intent =
                PendingIntent.getActivity(context, 9, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(9, notification);      

        
        int nilai = getBadgeLocal();
        nilai = nilai + 1;
        setBadge(context, nilai);
        
    }




   
    private void generateNotificationCustomLayoutAGC (Context context, String message, String phid) {
    	
    	
		//SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		//SharedPreferences.Editor editor = settingsS.edit();
		//editor.putString("CAMSAGC_GCM_OPEN_ACCOUNTID", accountid); 
		//editor.commit();

		
		//SharedPreferences settingsSq = getSharedPreferences("CAMSAGC", 0);
		//String XXX = settingsSq.getString("CAMSAGC_GCM_OPEN_ACCOUNTID", "TTTTT"); 
		
		//alert.showAlertDialog(this, "CAMSAGC_GCM_OPEN_ACCOUNTID", accountid, false);
		//Toast.makeText(this, 
		//		"CAMSAGC_GCM_OPEN_ACCOUNTID=" + accountid, Toast.LENGTH_LONG).show();

		
       	String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        
        
        //int icon = R.drawable.komlogoborder;
        //int icon = R.drawable.mdeccrm3;
        //
        int icon = R.drawable.camsagc3white;
        
        
        //CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String masaSekarang = "" + dateFormat.format(date);

        Notification notification = new Notification(icon, message, when);
        
        
        
				        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
				    	//contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        //contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        
				        //contentView.setImageViewResource(R.id.image, R.drawable.mdeccrm3);
				        contentView.setImageViewResource(R.id.image, R.drawable.camsagc3);
				        
				        
				    	contentView.setTextViewText(R.id.title, "" + message);
				    	//contentView.setTextViewText(R.id.text, "TX:" + message);
				    	contentView.setTextViewText(R.id.masa, "" + masaSekarang);
				    	
				    	notification.contentView = contentView;    	
				
				    	Intent notificationIntent = new Intent(context, ShowLocationActivity.class);
				    	notificationIntent.putExtra("phid", phid);
				    	
				    	
				    	//PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, 0);
				    	PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				    	//notification.flags |= Notification.FLAG_AUTO_CANCEL;
				    	
				    	notification.contentIntent = contentIntent;
    	
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate;
        
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        
        
        try
        {
            //double ran = Math.random() * 1000;
            //ran = Math.round(ran);
            //String rans = "" + ran;
            //rans = rans.replace(".0", "");
            //int ranx = Integer.parseInt("" + rans);
            
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(998);
            randomInt = randomInt + 1;
            
            //HASNOLMIZAM - FIX 1 TEMPAT SAHAJA.. 11/11/2014
            //mNotificationManager.notify(randomInt, notification);
            mNotificationManager.notify(6, notification);
            
            /*
            if (message.indexOf("[WEB]") >= 0 || message.indexOf("[ANDROID]") >= 0)
            {
            	String newMessage = message.replace("[WEB]-", "");
            	newMessage = newMessage.replace("[ANDROID]-", "");
            	
            	myspeak ("Hello! " + newMessage);
            }
            else
            {
            	myspeak ("Hello! You got 1 new message from DROPSHIP ONLINE MALAYSIA. Please check.");
            }
            	//mysilence(300);
			//myspeak (message);
			
			//message
			*/
			
        }
        catch (Exception x) 
        {
        	//hantar jugak tapi guna id 6
            mNotificationManager.notify(6, notification);      
        }
        
        int nilai = getBadgeLocal();
        nilai = nilai + 1;
        setBadge(context, nilai);

    }
    
    //private static void generateNotificationCustomLayout (Context context, String message) {
    private  void generateNotificationCustomLayout (Context context, String message, String accountid) {
    	
    	
		SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		SharedPreferences.Editor editor = settingsS.edit();
		editor.putString("CAMSAGC_GCM_OPEN_ACCOUNTID", accountid); 
		editor.commit();

		
		//SharedPreferences settingsSq = getSharedPreferences("CAMSAGC", 0);
		//String XXX = settingsSq.getString("CAMSAGC_GCM_OPEN_ACCOUNTID", "TTTTT"); 
		
		//alert.showAlertDialog(this, "CAMSAGC_GCM_OPEN_ACCOUNTID", accountid, false);
		//Toast.makeText(this, 
		//		"CAMSAGC_GCM_OPEN_ACCOUNTID=" + accountid, Toast.LENGTH_LONG).show();

		
       	String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        
        
        //int icon = R.drawable.komlogoborder;
        //int icon = R.drawable.mdeccrm3;
        int icon = R.drawable.camsagc3white;
        
        
        //CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String masaSekarang = "" + dateFormat.format(date);

        Notification notification = new Notification(icon, message, when);
        
        
        
				        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
				    	//contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        //contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        
				        //contentView.setImageViewResource(R.id.image, R.drawable.mdeccrm3);
				        contentView.setImageViewResource(R.id.image, R.drawable.camsagc3);
				        
				        
				        
				    	contentView.setTextViewText(R.id.title, "" + message);
				    	//contentView.setTextViewText(R.id.text, "TX:" + message);
				    	contentView.setTextViewText(R.id.masa, "" + masaSekarang);
				    	
				    	notification.contentView = contentView;    	
				
				    	Intent notificationIntent = new Intent(context, MainActivity.class);
				    	notificationIntent.putExtra("accountid", accountid);
				    	
				    	
				    	//PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, 0);
				    	PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				    	//notification.flags |= Notification.FLAG_AUTO_CANCEL;
				    	
				    	notification.contentIntent = contentIntent;
    	
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate;
        
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        
        
        try
        {
            //double ran = Math.random() * 1000;
            //ran = Math.round(ran);
            //String rans = "" + ran;
            //rans = rans.replace(".0", "");
            //int ranx = Integer.parseInt("" + rans);
            
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(998);
            randomInt = randomInt + 1;
            
            //HASNOLMIZAM - FIX 1 TEMPAT SAHAJA.. 11/11/2014
            //mNotificationManager.notify(randomInt, notification);
            mNotificationManager.notify(6, notification);
            
            /*
            if (message.indexOf("[WEB]") >= 0 || message.indexOf("[ANDROID]") >= 0)
            {
            	String newMessage = message.replace("[WEB]-", "");
            	newMessage = newMessage.replace("[ANDROID]-", "");
            	
            	myspeak ("Hello! " + newMessage);
            }
            else
            {
            	myspeak ("Hello! You got 1 new message from DROPSHIP ONLINE MALAYSIA. Please check.");
            }
            	//mysilence(300);
			//myspeak (message);
			
			//message
			*/
			
        }
        catch (Exception x) 
        {
        	//hantar jugak tapi guna id 6
            mNotificationManager.notify(6, notification);      
        }
        
        int nilai = getBadgeLocal();
        nilai = nilai + 1;
        setBadge(context, nilai);

    }
    
    
    
    private  void generateNotificationStandard (Context context, String message, String accountid) {
    	
		SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		SharedPreferences.Editor editor = settingsS.edit();
		editor.putString("CAMSAGC_GCM_OPEN_ACCOUNTID", accountid); 
		editor.commit();
    	
       	//int icon = R.drawable.komlogoborder;
    	int icon = R.drawable.camsagc3white;
    	
    	
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //notificationIntent.putExtra("phid", phid);
    	
        
        
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    	
        
        
        PendingIntent intent =
                PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

        
        int nilai = getBadgeLocal();
        nilai = nilai + 1;
        setBadge(context, nilai);

    }    

    private  void generateNotificationCustomLayout2 (Context context, String message, String accountid, String contactinteractionid) {
    	
    	
		SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		SharedPreferences.Editor editor = settingsS.edit();
		editor.putString("CAMSAGC_GCM_OPEN_ACCOUNTID", accountid);
		editor.putString("CAMSAGC_GCM_OPEN_CONTACTINTERACTIONID", contactinteractionid);
		
		editor.commit();

		
		//SharedPreferences settingsSq = getSharedPreferences("CAMSAGC", 0);
		//String XXX = settingsSq.getString("CAMSAGC_GCM_OPEN_ACCOUNTID", "TTTTT"); 
		
		//alert.showAlertDialog(this, "CAMSAGC_GCM_OPEN_ACCOUNTID", accountid, false);
		//Toast.makeText(this, 
		//		"CAMSAGC_GCM_OPEN_ACCOUNTID=" + accountid, Toast.LENGTH_LONG).show();

		
       	String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        
        
        //int icon = R.drawable.komlogoborder;
        //int icon = R.drawable.mdeccrm3;
        int icon = R.drawable.camsagc3white;
        
        
        //CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String masaSekarang = "" + dateFormat.format(date);

        Notification notification = new Notification(icon, message, when);
        
        
        
				        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
				    	//contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        //contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        
				        
				        //contentView.setImageViewResource(R.id.image, R.drawable.mdeccrm3);
				        contentView.setImageViewResource(R.id.image, R.drawable.camsagc3);
				    	
				        
				    	contentView.setTextViewText(R.id.title, "" + message);
				    	//contentView.setTextViewText(R.id.text, "TX:" + message);
				    	contentView.setTextViewText(R.id.masa, "" + masaSekarang);
				    	
				    	notification.contentView = contentView;    	
				
				    	Intent notificationIntent = new Intent(context, MainActivity.class);
				    	notificationIntent.putExtra("accountid", accountid);
				    	notificationIntent.putExtra("contactinteractionid", contactinteractionid);
				    	
				    	
				    	
				    	//PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, 0);
				    	PendingIntent contentIntent = PendingIntent.getActivity(context, 7, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				    	//notification.flags |= Notification.FLAG_AUTO_CANCEL;
				    	
				    	notification.contentIntent = contentIntent;
    	
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate;
        
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        
        
        try
        {
            //double ran = Math.random() * 1000;
            //ran = Math.round(ran);
            //String rans = "" + ran;
            //rans = rans.replace(".0", "");
            //int ranx = Integer.parseInt("" + rans);
            
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(998);
            randomInt = randomInt + 1;
            
            
            //set pada satu tempat je la...- hasnol11/11/2014
            //mNotificationManager.notify(randomInt, notification);
            mNotificationManager.notify(6, notification);
            
            /*
            if (message.indexOf("[WEB]") >= 0 || message.indexOf("[ANDROID]") >= 0)
            {
            	String newMessage = message.replace("[WEB]-", "");
            	newMessage = newMessage.replace("[ANDROID]-", "");
            	
            	myspeak ("Hello! " + newMessage);
            }
            else
            {
            	myspeak ("Hello! You got 1 new message from DROPSHIP ONLINE MALAYSIA. Please check.");
            }
            	//mysilence(300);
			//myspeak (message);
			
			//message
			*/
			
        }
        catch (Exception x) 
        {
        	//hantar jugak tapi guna id 6
            mNotificationManager.notify(6, notification);      
        }
        
        int nilai = getBadgeLocal();
        nilai = nilai + 1;
        setBadge(context, nilai);

    }
	public static void myspeak (String mytext)
	{
		//dapatkan VOICE SETTING YANG PERNAH DISIMPAN SEBELUM NI..
		//	SharedPreferences settings = getSharedPreferences("ENC_INFO", 0);
		//	String ENC_INFO_VOICE = settings.getString("ENC_INFO_VOICE", "OFF");
		//	if (ENC_INFO_VOICE.equals("ON"))
		//	{
				//tts.setSpeechRate(1.05f);
				//tts.speak(mytext, TextToSpeech.QUEUE_ADD, null);
		//	}
				
	}
	
	public static void mysilence (int duration)
	{
			//tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
	}
	
	/*
	@Override
	public void onInit(int status) {        
		if (status == TextToSpeech.SUCCESS) {
			//Toast.makeText(MainActivity.this, 
			//		"Text-To-Speech engine is initialized", Toast.LENGTH_LONG).show();
			//myspeak ("We are sorry. We are not able to connect to E N C H R Sys server. ");
			//mysilence(300);
			//myspeak ("");
		}
		else if (status == TextToSpeech.ERROR) {
			//Toast.makeText(MainActivity.this, 
			//		"Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
		}
	}
	*/
	
	
	//public static void setBadge(Context context, int count) {
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

	public int getBadgeLocal() {
	    //dapatkan dalam local
	    //------------------
		SharedPreferences settingsS = getSharedPreferences("CAMSAGC", 0);
		String nilai = "" + settingsS.getString("CAMSAGC_NOTIFICATION", "0");
		int nilaiInt = Integer.parseInt(nilai);
		return nilaiInt;
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
	
	
	
	
	
	
	
	
	
	
	
	
	class SendingLocationInfo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {

			
			//dapatkan location disini...
			
			
			 //GPSTracker gps = new GPSTracker(getApplicationContext());
		    GPSTracker gps = new GPSTracker(getApplicationContext());

            // Check if GPS enabled
            if(gps.canGetLocation()) {

                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();

                // \n is for new line
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                //gps.showSettingsAlert();
           	 //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }
            
						
			super.onPreExecute();

		}

		
		
	
	protected String doInBackground(String... args) {

		//SharedPreferences settings = getActivity().getSharedPreferences("KOMDS", 0);
		//String KOMDS_USERID = settings.getString("KOMDS_USERID", "");
		//String KOMDS_ROLEID = settings.getString("KOMDS_ROLEID", "");

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fieldX","fieldX"));
		params.add(new BasicNameValuePair("ph_id",GLOBAL_PHID));
		//params.add(new BasicNameValuePair("roleid",KOMDS_ROLEID));



		//if (KOMDS_RANKING.equals(""))
		//{


        //query//
		try {


			//hantar maklumat ke API check_in_by_applicant
			//JSONObject json = jParser.makeHttpRequest("http://www.visionice.net/ds/androidservice.php?actionid=getagentprofits&T=" + System.nanoTime(), "GET", params);
			JSONObject json = jParser.makeHttpRequest("http://10.17.14.210/cams/api_generator.php?api_name=check_in_by_applicant&ph_id=" + GLOBAL_PHID + "&stf_username=" + GLOBAL_usernameRequester + "&lng=" + longitude + "&lat=" + latitude + "&T=" + System.nanoTime(), "GET", params);
				
			//if (json == null) 
			//{
			//	return null;
			//}

			/*
			querystatus_GLOBAL = json.getString("querystatus");
			if (querystatus_GLOBAL.equals("OK"))
			{

				totalitemsoldbyyou_GLOBAL = "" + json.getString("totalitemsoldbyyou");
				totalallsalebyyou_GLOBAL =  "" + json.getString("totalallsalebyyou");
				totalallcostbyyou_GLOBAL =  "" + json.getString("totalallcostbyyou");
				totalallprofitbyyou_GLOBAL =  "" + json.getString("totalallprofitbyyou");
				ranking_GLOBAL =  "" + json.getString("ranking");



				if (totalitemsoldbyyou_GLOBAL.equals("null") )
				{
					totalitemsoldbyyou_GLOBAL = "0";
				}

				//simpan dalam session
				SharedPreferences settingsXX = getActivity().getSharedPreferences("KOMDS", 0);
				SharedPreferences.Editor editorXX = settingsXX.edit();
				editorXX.putString("KOMDS_TOTALITEMSOLDBYYOU", totalitemsoldbyyou_GLOBAL);
				editorXX.putString("KOMDS_TOTALALLSALEBYYOU", totalallsalebyyou_GLOBAL); 
				editorXX.putString("KOMDS_TOTALALLCOSTBYYOU", totalallcostbyyou_GLOBAL);
				editorXX.putString("KOMDS_TOTALALLPROFITBYYOU", totalallprofitbyyou_GLOBAL);
				editorXX.putString("KOMDS_RANKING", ranking_GLOBAL); 
				editorXX.commit();



				//username_GLOBAL = json.getString("username");
				//roleid_GLOBAL = json.getString("roleid");
				//rolename_GLOBAL = json.getString("rolename");
				//fullname_GLOBAL = json.getString("fullname");
				//phoneno_GLOBAL = json.getString("phoneno");
				//infocomplete_GLOBAL = json.getString("infocomplete");
				//userid_GLOBAL  = json.getString("userid");
				//usernameGCM = username_GLOBAL;
				//fullnameGCM = fullname_GLOBAL;

				//sellingprice_GLOBAL = json.getString("sellingprice");
				//yourprice_GLOBAL = json.getString("yourprice");
				//modellongdesc_GLOBAL = json.getString("modellongdesc");

			}
			*/
		
		/*
		} catch (JSONException  e) {
			
			
			//e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("4Unable to connect to the Dropship Online Malaysia server. Please try later.") 
			.setCancelable(false)
			.setTitle("Dropship Online Malaysia")
			//.setIcon(R.drawable.komlogo)
			.setIcon(R.drawable.symbolerror)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//goToLoginAgain();
					//viewPager.setAdapter(mAdapter);
					//viewPager.setCurrentItem(2);
					//actionBar = getActionBar();
					//actionBar.setSelectedNavigationItem(2);
					//actionBar.setSelectedNavigationItem(2);
					//viewPager.setCurrentItem(2);
					return;

				}
				//})
				//.setOnCancelListener(new OnCancelListener() {
				//	public void onCancel(DialogInterface dialog)  {
				//		goToLoginAgain();	
				//	}
			});
			AlertDialog alert = builder.create();
			alert.show();
			
			
			*/
		} catch (Exception e) {
			//e.printStackTrace();
			/*
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("5Unable to connect to the Dropship Online Malaysia server. Please try later.") 
			.setCancelable(false)
			.setTitle("Dropship Online Malaysia")
			//.setIcon(R.drawable.komlogo)
			.setIcon(R.drawable.symbolerror)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//goToLoginAgain();
					//viewPager.setAdapter(mAdapter);
					//viewPager.setCurrentItem(2);
					//actionBar = getActionBar();
					//actionBar.setSelectedNavigationItem(2);
					//actionBar.setSelectedNavigationItem(2);
					//viewPager.setCurrentItem(2);

					return;

				}
				//})
				//.setOnCancelListener(new OnCancelListener() {
				//	public void onCancel(DialogInterface dialog)  {
				//		goToLoginAgain();	
				//	}
			});
			AlertDialog alert = builder.create();
			alert.show();
			*/

		}					

		//}
		//else
		//{
		//	querystatus_GLOBAL = "OK";
		//}



		return null;
	}	
	
	
	
	
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
		/*
		//check kat sini..
		SharedPreferences settingsXX1 = getActivity().getSharedPreferences("KOMDS", 0);
		KOMDS_TOTALITEMSOLDBYYOU = "" + settingsXX1.getString("KOMDS_TOTALITEMSOLDBYYOU", "");
		KOMDS_TOTALALLSALEBYYOU = "" + settingsXX1.getString("KOMDS_TOTALALLSALEBYYOU", "");
		KOMDS_TOTALALLCOSTBYYOU = "" + settingsXX1.getString("KOMDS_TOTALALLCOSTBYYOU", "");
		KOMDS_TOTALALLPROFITBYYOU = "" + settingsXX1.getString("KOMDS_TOTALALLPROFITBYYOU", "");
		KOMDS_RANKING = "" + settingsXX1.getString("KOMDS_RANKING", "");
		*/

		/*
		try
		{
			// updating UI from Background Thread
			getActivity().runOnUiThread(new Runnable() {
				public void run() {

					if (querystatus_GLOBAL.equals("OK")) 
					{


						TextView itemSold=(TextView) getActivity().findViewById(R.id.itemSold);
						itemSold.setText("No. of Item Sold : " + KOMDS_TOTALITEMSOLDBYYOU);

						TextView salesrevenue=(TextView) getActivity().findViewById(R.id.salesrevenue);
						salesrevenue.setText("Sales Revenue : RM " + KOMDS_TOTALALLSALEBYYOU);

						TextView grossprofit=(TextView) getActivity().findViewById(R.id.grossprofit);
						grossprofit.setText("Gross Profit : RM " + KOMDS_TOTALALLPROFITBYYOU);

						TextView ranking=(TextView) getActivity().findViewById(R.id.ranking);
						//ranking.setText("" + ranking_GLOBAL);
						ranking.setText("Your Current Level");

						ImageView star=(ImageView) getActivity().findViewById(R.id.star);
						if (KOMDS_RANKING.equals("TOP 5"))
						{
							star.setImageResource(R.drawable.top5);
							star.setVisibility(View.VISIBLE);
							//ranking.setVisibility(View.GONE);
						}
						else if (KOMDS_RANKING.equals("TOP 10"))
						{
							star.setImageResource(R.drawable.top10);
							star.setVisibility(View.VISIBLE);
							//ranking.setVisibility(View.GONE);
						}
						else 
						{
							ranking.setText("Your Current Level : " + KOMDS_RANKING); 
							//ranking.setVisibility(View.VISIBLE);
							star.setVisibility(View.GONE);
						}


						//Date date; // your date
						Calendar cal = Calendar.getInstance();
						//cal.setTime(date);
						int yearz = cal.get(Calendar.YEAR);
						int monthz = cal.get(Calendar.MONTH);
						String monthzx = "";
						//int dayz = cal.get(Calendar.DAY_OF_MONTH);
						if (monthz == 0) { monthzx = "JANUARY"; }
						if (monthz == 1) { monthzx = "FEBRUARY"; }
						if (monthz == 2) { monthzx = "MARCH"; }
						if (monthz == 3) { monthzx = "APRIL"; }
						if (monthz == 4) { monthzx = "MAY"; }
						if (monthz == 5) { monthzx = "JUN"; }
						if (monthz == 6) { monthzx = "JULY"; }
						if (monthz == 7) { monthzx = "AUGUST"; }
						if (monthz == 8) { monthzx = "SEPTEMBER"; }
						if (monthz == 9) { monthzx = "OCTOBER"; }
						if (monthz == 10) { monthzx = "NOVEMBER"; }
						if (monthz == 11) { monthzx = "DECEMBER"; }


						TextView monthstatistic=(TextView) getActivity().findViewById(R.id.monthstatistic);
						monthstatistic.setText(monthzx + " " + yearz);


					} 
					else if (querystatus_GLOBAL.equals("KO")) 
					{

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("Sorry, There is a problem while getting your sales data.")
						.setCancelable(false)
						.setTitle("Dropship Online Malaysia")
						.setIcon(R.drawable.komlogoblack)
						//.setIcon(R.drawable.symbolerror)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//goToLoginAgain();
								return;
							}
							//})
							//.setOnCancelListener(new OnCancelListener() {
							//	public void onCancel(DialogInterface dialog)  {
							//		goToLoginAgain();	
							//	}
						});
						AlertDialog alert = builder.create();
						alert.show();

					} else {

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("6Unable to connect to the Dropship Online Malaysia server. Please try later.") 
						.setCancelable(false)
						.setTitle("Dropship Online Malaysia")
						.setIcon(R.drawable.symbolerror)
						//.setIcon(R.drawable.symbolerror)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//goToLoginAgain();
								//viewPager.setAdapter(mAdapter);
								//viewPager.setCurrentItem(2);
								//actionBar = getActionBar();
								//actionBar.setSelectedNavigationItem(2);
								//actionBar.setSelectedNavigationItem(2);
								//viewPager.setCurrentItem(2);
								return;

							}
							//})
							//.setOnCancelListener(new OnCancelListener() {
							//	public void onCancel(DialogInterface dialog)  {
							//		goToLoginAgain();	
							//	}
						});
						AlertDialog alert = builder.create();
						alert.show();

					}



				}
			});
		}
		catch (Exception c)
		{

		}
		*/

	}
	
	
	}
	
}
