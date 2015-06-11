package org.androidpn.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.androidpn.data.User;

import com.androidpn.db.DBManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UserReceiver extends BroadcastReceiver {
	
	private static final String LOGTAG = LogUtil.makeLogTag(UserReceiver.class);
	
	public UserReceiver() {
    }

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "UserReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_USER.equals(action)) {
            String userId = intent.getStringExtra(Constants.USER_ID);
            String userName = intent.getStringExtra(Constants.USER_NAME);
            String userStatus = intent.getStringExtra(Constants.USER_STATUS);
            String packetId = intent.getStringExtra(Constants.PACKET_ID);
            
            Log.d(LOGTAG, "userId=" + userId);
            Log.d(LOGTAG, "userName=" + userName);
            Log.d(LOGTAG, "userStatus=" + userStatus);
            Log.d(LOGTAG, "packetId=" + packetId);
            
            String[] username = userName.split(",");
            String[] userstatus = userStatus.split(",");
            for(int i = 0; i < username.length; i++) {
	            User user = DBManager.getInstance(context).getUser(username[i]);
	            if(user == null) {
	            	user = new User();
	            	user.setId(userId);
	            	user.setUsername(username[i]);
	            	user.setUserStatus(userstatus[i]);
	            	Date date = new Date();
	            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	user.setCreateDate(format.format(date));
	            	user.setUpdateDate(format.format(date));
	            	DBManager.getInstance(context).addUser(user);
	            } else {
	            	user.setUserStatus(userstatus[i]);
	            	Date date = new Date();
	            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	user.setUpdateDate(format.format(date));
	            	DBManager.getInstance(context).updateUserStatus(user);
	            }
            }
            
            intent = new Intent();
            intent.setAction(Constants.USER_LIST_RECEIVER);
            context.sendBroadcast(intent);
        }
	}

}
