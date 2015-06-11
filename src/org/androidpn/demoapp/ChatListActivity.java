package org.androidpn.demoapp;

import java.util.*;

import org.androidpn.adpater.ChatListAdapter;
import org.androidpn.adpater.UserListAdapter;
import org.androidpn.client.Constants;
import org.androidpn.data.Chat;
import org.androidpn.data.Message;
import org.androidpn.data.User;
import org.androidpn.demoapp.UserListActivity.UserListReceiver;

import com.androidpn.db.DBManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

public class ChatListActivity extends Activity {
	
	private ListView listView;
	private ChatListAdapter listAdapter;
	private List<Chat> chatList;
	private ChatListReceiver receiver = new ChatListReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlist);
		
		listView = (ListView)this.findViewById(R.id.listView1);
		chatList = DBManager.getInstance(this).getAllChats();
		listAdapter = new ChatListAdapter(this, R.layout.chatlistview, chatList);
		listView.setAdapter(listAdapter);
		IntentFilter filter = new IntentFilter(Constants.CHAT_LIST_RECEIVER);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	class ChatListReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			chatList = DBManager.getInstance(context).getAllChats();
			listAdapter = new ChatListAdapter(ChatListActivity.this, R.layout.chatlistview, chatList);
			listView.setAdapter(listAdapter);
			listAdapter.notifyDataSetChanged();
		}
		
	}

}
