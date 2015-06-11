package org.androidpn.adpater;

import java.util.List;

import org.androidpn.client.Constants;
import org.androidpn.data.Chat;
import org.androidpn.data.User;
import org.androidpn.demoapp.ChatActivity;
import org.androidpn.demoapp.R;
import org.jivesoftware.smack.util.StringUtils;

import com.androidpn.db.DBManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {  
    
	private int mTextViewResourceID = 0;  
    private Context mContext;
    private List<User> userlist;
    
    public UserListAdapter(Context context, int textViewResourceId, List<User> users) {  
        mTextViewResourceID = textViewResourceId;  
        mContext = context;
        userlist = users;
    }  
 
    private int[] colors = new int[] { 0xff626569, 0xff4f5257 };  
 
    public int getCount() {  
        return userlist.size();  
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
        TextView user = null;   
        Button button = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {  
        	convertView = LayoutInflater.from(mContext).inflate(  
        		mTextViewResourceID, null);  
	        image = (ImageView) convertView.findViewById(R.id.array_image);  
	        user = (TextView) convertView.findViewById(R.id.array_user);  
	        button = (Button)convertView.findViewById(R.id.array_button);  
	        button.setOnClickListener(new OnClickListener() {  
	            @Override  
	            public void onClick(View v) {  
	            	Intent intent = new Intent();
	            	intent.setClass(mContext, ChatActivity.class);
	            	String from_user = Constants.xmppManager.getUsername();
	            	User user = userlist.get(position);
	            	String to_user = user.getUsername();
	            	String paticipants = "";
	            	if(from_user.compareTo(to_user) < 0)
	            		paticipants = from_user + "_" + to_user;
	            	else
	            		paticipants = to_user + "_" + from_user;
	            	Chat chat = DBManager.getInstance(mContext).getChat("chat", paticipants);
	            	if(chat == null) {
	            		chat = new Chat();
	            		chat.setLocal(from_user);
	            		chat.setRemote(to_user);
	            		chat.setType("chat");
	            	}
	            	intent.putExtra("chat", chat);
	            	mContext.startActivity(intent);
	            }  
	        });
	        viewHolder = new ViewHolder();
	        viewHolder.img = image;
	        viewHolder.user = user;
	        viewHolder.btn = button;
	        convertView.setTag(viewHolder);
        } else {
        	viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.user.setText(userlist.get(position).getUsername());  
        String userStatus = userlist.get(position).getUserStatus();
        if(userStatus.equals(Constants.USER_ONLINE)) {
        	convertView.setBackgroundColor(colors[0]);
        	viewHolder.img.setImageResource(R.drawable.online);
        } else if(userStatus.equals(Constants.USER_OFFLINE)) {
        	convertView.setBackgroundColor(colors[1]);
        	viewHolder.img.setImageResource(R.drawable.offline);
        }
        return convertView;  
    } 
    
    static class ViewHolder {
		public ImageView img;
		public TextView user;
		public Button btn;
	}
} 
