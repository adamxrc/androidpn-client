package org.androidpn.demoapp;

import java.util.List;

import org.androidpn.adpater.UserListAdapter;
import org.androidpn.client.Constants;
import org.androidpn.data.User;

import com.androidpn.db.DBManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class UserListActivity extends Activity {
	
	private ListView listView;
	private UserListAdapter listAdapter;
	private List<User> userList;
	private UserListReceiver receiver = new UserListReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlist);
		
		listView = (ListView)this.findViewById(R.id.listView1);
		userList = DBManager.getInstance(this).getAllUsers();
		listAdapter = new UserListAdapter(this, R.layout.userlistview, userList);
		listView.setAdapter(listAdapter);
		IntentFilter filter = new IntentFilter(Constants.USER_LIST_RECEIVER);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	class UserListReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			userList = DBManager.getInstance(context).getAllUsers();
			listAdapter = new UserListAdapter(UserListActivity.this, R.layout.userlistview, userList);
			listView.setAdapter(listAdapter);
			listAdapter.notifyDataSetChanged();
		}
		
	}

}
