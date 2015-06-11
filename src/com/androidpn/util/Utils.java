package com.androidpn.util;

import android.app.ActivityManager;
import android.content.Context;

public class Utils {
	
	public static String getRunningActivityName(Context context){          
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();  
        return runningActivity;                 
	}

}
