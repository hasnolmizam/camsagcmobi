package com.agc.cams;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	// give your server registration url here
    //static final String SERVER_URL = "http://10.0.2.2/gcm_server_php/register.php";

	//static final String SERVER_URL = "http://www.visionice.net/ds/gcm_server_php/mdecgcm/register_mdec.php";
	
	
	//static final String SERVER_URL = "http://192.168.22.20/mdecgcm/register_mdec.php";
	//static final String SERVER_URL = "http://corrad.mdec.com.my/mdecgcm/register_mdec.php";
	static final String SERVER_URL = "http://10.17.14.210/camsgcm/register_cams.php";
	//static final String SERVER_URL = "http://www.visionice.net/ds/gcm_server_php/register.php";
	
	
	
	
	//static final String SERVER_URL = "http://10.9.220.10/corrad/api_generator.php?api_name=register_mdec";
		

    // Google project id
    static final String SENDER_ID = "375548924029"; 

    /**
     * Tag used on log messages.
     */
    static final String TAG = "CAMSAGC GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.agc.cams.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
