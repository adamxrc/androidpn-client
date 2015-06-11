package org.androidpn.demoapp;

import java.text.SimpleDateFormat;
import java.util.*;

import org.androidpn.adpater.ChatMsgViewAdapter;
import org.androidpn.client.Constants;
import org.androidpn.client.MessageIQ;
import org.androidpn.client.XmppManager;
import org.androidpn.data.Chat;
import org.androidpn.data.Message;
import org.androidpn.data.User;
import org.jivesoftware.smack.packet.IQ;

import com.androidpn.db.DBManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity implements OnClickListener {
	
	private Button mBtnSend;// 发送btn
	private Button mBtnBack;// 返回btn
	private EditText mEditTextContent;
	private ListView mListView;
	private TextView mTxvHistory;
	private TextView mTxvToUser;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<Message> mDataArrays = new ArrayList<Message>();// 消息对象数组
	public static Chat chat;
	private ChatReveiver receiver = new ChatReveiver();
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		initView();// 初始化view
		
		initData();// 初始化数据

		if(mAdapter.getCount() > 0)
			mListView.setSelection(mAdapter.getCount() - 1);
	}
	
	/**
	 * 初始化view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mTxvHistory = (TextView) findViewById(R.id.txvHistory);
		mTxvToUser = (TextView) findViewById(R.id.txvToUser);
		mTxvHistory.setTextColor(Color.BLUE);
		mTxvHistory.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  
		mTxvHistory.getPaint().setAntiAlias(true);
		mContext = this;
	}
	
	private void initData() {
		chat = (Chat)getIntent().getSerializableExtra(Constants.CHAT_EXTRA);
		
		if(chat != null) {
			mDataArrays = chat.getMessages();
			mTxvToUser.setText(chat.getRemote());
		}
		
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
		new Thread(runnable).start();
		
		IntentFilter filter = new IntentFilter(Constants.CHAT_RECIEVER);
		registerReceiver(receiver, filter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:// 发送按钮点击事件
			send();
			break;
		case R.id.btn_back:// 返回按钮点击事件
			finish();// 结束,实际开发中，可以返回主界面
			break;
		}
	}

	/**
	 * 发送消息
	 */
	private void send() {
		String contString = mEditTextContent.getText().toString().trim();
		if (contString.length() > 0) {
			//Random random = new Random();
			//String id = Integer.toHexString(random.nextInt());
			String id = Constants.xmppManager.newRandomUUID();
			Message msg = new Message();
			msg.setId(id);
			msg.setType(chat.getType());
			msg.setFrom(chat.getLocal());
			msg.setTo(chat.getRemote());
			msg.setSentDate(getDate());
			msg.setSubject(contString);
			msg.setBody(contString);
			msg.setComMeg(false);

			mDataArrays.add(msg);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
			DBManager.getInstance(this).addMesssage(msg);

			mEditTextContent.setText("");// 清空编辑框数据

			mListView.setSelection(mAdapter.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
			
			if(Constants.xmppManager != null) {
				MessageIQ iq = new MessageIQ();
				iq.setMsgId(msg.getId());
				iq.setMsgType(msg.getType());
				iq.setFromUser(msg.getFrom());
				iq.setToUser(msg.getTo());
				iq.setSubject(msg.getSubject());
				iq.setBody(msg.getBody());
				iq.setSentDate(msg.getSentDate());
				iq.setThread(chat.getPaticipants());
				
				try {
					Constants.xmppManager.getConnection().sendPacket(iq);
				} catch(IllegalStateException e) {
					
				} catch(NullPointerException e) {
					
				} catch(Exception e) {
					
				}
			}
		}
	}

	/**
	 * 发送消息时，获取当前事件
	 * 
	 * @return 当前时间
	 */
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
	
	class ChatReveiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String from = intent.getStringExtra("from");
			if(!from.equals(chat.getRemote())) return;
			Message msg = (Message)intent.getSerializableExtra("message");
			mDataArrays.add(msg);
			mAdapter.notifyDataSetChanged();
			DBManager.getInstance(context).addMesssage(msg);
			mListView.setSelection(mAdapter.getCount() - 1);
			
			MessageIQ iq = new MessageIQ();
			iq.setPacketID(msg.getId());
			IQ result = MessageIQ.createResultIQ(iq);
			try {
				Constants.xmppManager.getConnection().sendPacket(result);
			} catch(IllegalStateException e) {
				
			} catch(NullPointerException e) {
				
			} catch(Exception e) {
				
			}
			
			msg.setRead(true);
			DBManager.getInstance(mContext).updateMessageRead(msg);
		}
		
	}
	
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(Message msg : mDataArrays) {
				if (!msg.isRead()) {
					MessageIQ iq = new MessageIQ();
					iq.setPacketID(msg.getId());
					IQ result = MessageIQ.createResultIQ(iq);
					try {
						Constants.xmppManager.getConnection().sendPacket(result);
					} catch(IllegalStateException e) {
						
					} catch(NullPointerException e) {
						
					} catch(Exception e) {
						
					}
					
					msg.setRead(true);
					DBManager.getInstance(mContext).updateMessageRead(msg);
				}
			}
		}
		
	};

}
