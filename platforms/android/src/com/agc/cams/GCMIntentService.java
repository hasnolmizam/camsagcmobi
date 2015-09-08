package com.agc.cams;

import static com.agc.cams.CommonUtilities.SENDER_ID;
import static com.agc.cams.CommonUtilities.displayMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
//import android.speech.tts.TextToSpeech;
//import android.speech.tts.TextToSpeech.OnInitListener;

//public class GCMIntentService extends GCMBaseIntentService implements OnInitListener {
public class GCMIntentService extends GCMBaseIntentService  {

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
    	        Log.i(TAG, "Received message");

    	        //pecahkan message utk dapatkan accountid..
    	        String accountid = "";
    	        String contactinteractionid = "";
    	        //String XArray = 
    	        
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
    	        
    	        
    	        displayMessage(context, message);
    	        // notifies user
    	        //generateNotification(context, message);
    	        if (contactinteractionid.equals("") || contactinteractionid.equals("null"))
    	        {
    	        	generateNotificationCustomLayout(context, message, accountid);
    	        }
    	        else
    	        {
    	        	generateNotificationCustomLayout2(context, message, accountid, contactinteractionid);
        	    }
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
        int icon = R.drawable.mdeccrm3;
        
        //CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String masaSekarang = "" + dateFormat.format(date);

        Notification notification = new Notification(icon, message, when);
        
        
        
				        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
				    	//contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        //contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        contentView.setImageViewResource(R.id.image, R.drawable.mdeccrm3);
				    	
				        
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
        int icon = R.drawable.mdeccrm3;
        
        //CharSequence tickerText = "Happy Birthday!";
        long when = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String masaSekarang = "" + dateFormat.format(date);

        Notification notification = new Notification(icon, message, when);
        
        
        
				        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
				    	//contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        //contentView.setImageViewResource(R.id.image, R.drawable.komlogopng);
				        contentView.setImageViewResource(R.id.image, R.drawable.mdeccrm3);
				    	
				        
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
	
	
	
}
