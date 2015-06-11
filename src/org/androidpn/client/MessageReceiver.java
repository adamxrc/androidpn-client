package org.androidpn.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.androidpn.data.Message;
import org.androidpn.data.User;
import org.androidpn.demoapp.ChatActivity;

import com.androidpn.db.DBManager;
import com.androidpn.util.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {
private static final String LOGTAG = LogUtil.makeLogTag(MessageReceiver.class);
	
	public MessageReceiver() {
    }

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "MessageReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_MESSAGE.equals(action)) {
        	String id = intent.getStringExtra(Constants.MESSAGE_ID);
            String type = intent.getStringExtra(Constants.MESSAGE_TYPE);
            String subject = intent.getStringExtra(Constants.MESSAGE_SUBJECT);
            String body = intent.getStringExtra(Constants.MESSAGE_BODY);
            String from = intent.getStringExtra(Constants.MESSAGE_FROM);
            String to = intent.getStringExtra(Constants.MESSAGE_TO);
            String sent_date = intent.getStringExtra(Constants.MESSAGE_SENT_DATE);
            String thread = intent.getStringExtra(Constants.MESSAGE_THREAD);
            String packetId = intent.getStringExtra(Constants.PACKET_ID);
            
            Log.d(LOGTAG, "id=" + id);
            Log.d(LOGTAG, "type=" + type);
            Log.d(LOGTAG, "subject=" + subject);
            Log.d(LOGTAG, "body=" + body);
            Log.d(LOGTAG, "from=" + from);
            Log.d(LOGTAG, "to=" + to);
            Log.d(LOGTAG, "sent_date=" + sent_date);
            Log.d(LOGTAG, "thread=" + thread);
            Log.d(LOGTAG, "packetId=" + packetId);
            
            Message msg = new Message();
            msg.setId(id);
            msg.setType(type);
            msg.setSubject(subject);
            msg.setBody(body);
            msg.setFrom(from);
            msg.setSentDate(sent_date);
            msg.setTo(to);
            
            DBManager.getInstance(context).addMesssage(msg);
            
            intent = new Intent();
            intent.setAction(Constants.CHAT_LIST_RECEIVER);
            intent.putExtra("message", msg);
            context.sendBroadcast(intent);
            
            intent = new Intent();
            intent.setAction(Constants.CHAT_RECIEVER);
            intent.putExtra("from", from);
            intent.putExtra("message", msg);
            context.sendBroadcast(intent);
            
            Log.d(LOGTAG, "runningActivity=" + Utils.getRunningActivityName(context));
            
            if(Constants.CHAT_ACTIVITY_NAME.equals(Utils.getRunningActivityName(context)) && 
            		ChatActivity.chat.getRemote().equals(from)) {
            	Log.d(LOGTAG, "chat remote=" + ChatActivity.chat.getRemote());
            	return;
            }
            
            Notifier notifier = new Notifier(context);
            notifier.notifyMsg(id, type, from, to, body, sent_date, packetId);
        }
	}

}
