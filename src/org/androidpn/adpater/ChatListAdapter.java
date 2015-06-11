package org.androidpn.adpater;

import java.util.*;

import org.androidpn.adpater.UserListAdapter.ViewHolder;
import org.androidpn.client.Constants;
import org.androidpn.data.Chat;
import org.androidpn.data.Message;
import org.androidpn.data.User;
import org.androidpn.demoapp.ChatActivity;
import org.androidpn.demoapp.R;

import com.androidpn.db.DBManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {
	private int mTextViewResourceID = 0;  
    private Context mContext;
    private List<Chat> chatList;
    private Chat chat;
    
    public ChatListAdapter(Context context, int textViewResourceId, 
    		List<Chat> chats) {  
        mTextViewResourceID = textViewResourceId;  
        mContext = context;
        chatList = chats;
    }    
 
    public int getCount() {  
        return chatList.size();  
    }  
 
    @Override  
    public boolean areAllItemsEnabled() {  
        return false;  
    }  
 
    public Object getItem(int position) {  
        return position;  
    }  
 
    public long getItemId(int position) {  
        return position;  
    }  
 
    public View getView(final int position, View convertView, ViewGroup parent) {  
        ImageView image = null;  
        TextView time = null;   
        TextView user = null;
        TextView body = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {  
        	convertView = LayoutInflater.from(mContext).inflate(  
        		mTextViewResourceID, null);  
	        image = (ImageView) convertView.findViewById(R.id.array_image);  
	        time = (TextView) convertView.findViewById(R.id.array_time);  
	        user = (TextView)convertView.findViewById(R.id.array_user); 
	        body = (TextView)convertView.findViewById(R.id.array_body);
	        
	        viewHolder = new ViewHolder();
	        viewHolder.img = image;
	        viewHolder.time = time;
	        viewHolder.user = user;
	        viewHolder.body = body;
	        
	        convertView.setTag(viewHolder);
        } else {
        	viewHolder = (ViewHolder)convertView.getTag();
        }
        
        chat = chatList.get(position);
        List<Message> messages = chat.getMessages();
        Message msg = messages.get(messages.size()-1);
        User fromUser = DBManager.getInstance(mContext).getUser(msg.getFrom());
        boolean isOnline = false;
        if(fromUser != null && fromUser.getUserStatus().equals("online")) {
        	isOnline = true;
        } else if(fromUser == null && Constants.xmppManager.getConnection() != null) {
        	isOnline = Constants.xmppManager.getConnection().isAuthenticated();
        }
        viewHolder.img.setImageResource(isOnline ? 
        		R.drawable.online : R.drawable.offline);
        viewHolder.time.setText(msg.getSentDate());
        viewHolder.user.setText(msg.getFrom());
        viewHolder.body.setText(msg.getBody());
        
        convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
            	intent.setClass(mContext, ChatActivity.class);
            	intent.putExtra(Constants.CHAT_EXTRA, chat);
            	mContext.startActivity(intent);
			}
		});
        
        return convertView;  
    } 
    
    static class ViewHolder {
		public ImageView img;
		public TextView time;
		public TextView user;
		public TextView body;
	}
}
