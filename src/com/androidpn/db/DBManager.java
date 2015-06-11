package com.androidpn.db;

import java.sql.Date;
import java.util.*;

import org.androidpn.client.LogUtil;
import org.androidpn.data.Chat;
import org.androidpn.data.Message;
import org.androidpn.data.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	
	private static final String LOGTAG = LogUtil.makeLogTag(DBManager.class);
	
	private static DBManager dbManager = null;
	private DBHelper dbHelper = null;
	private SQLiteDatabase db;
	
	private DBManager(Context context) {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static DBManager getInstance(Context context) {
		if(dbManager == null)
			dbManager = new DBManager(context);
		return dbManager;
	}
	
	public synchronized void createTable(String sql) {
		try {
			db.execSQL(sql);
		} catch(SQLException e) {
			Log.d(LOGTAG, "createTable failed: "+e.toString());
		}
	}
	
	public synchronized void dropTable(String sql) {
		try {
			db.execSQL(sql);
		} catch(SQLException e) {
			Log.d(LOGTAG, "dropTable failed: "+e.toString());
		}
	}
	
	public synchronized User getUser(String username) {
		User user = null;
		Cursor c = db.rawQuery("select * from users where username=?", new String[]{username});
		if(c.moveToNext()) {
			user = new User();
			user.setId(c.getString(c.getColumnIndex("userid")));
			user.setUsername(c.getString(c.getColumnIndex("username")));
			user.setUserStatus(c.getString(c.getColumnIndex("userstatus")));
			user.setCreateDate(c.getString(c.getColumnIndex("createdate")));
			user.setUpdateDate(c.getString(c.getColumnIndex("updatedate")));
		}
		return user;
	}
	
	public synchronized List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		Cursor c = db.rawQuery("select * from users", null);
		while(c.moveToNext()) {
			User user = new User();
			user.setId(c.getString(c.getColumnIndex("userid")));
			user.setUsername(c.getString(c.getColumnIndex("username")));
			user.setUserStatus(c.getString(c.getColumnIndex("userstatus")));
			user.setCreateDate(c.getString(c.getColumnIndex("createdate")));
			user.setUpdateDate(c.getString(c.getColumnIndex("updatedate")));
			users.add(user);
		}
		
		return users;
	}
	
	public synchronized List<User> getUsers(int count) {
		List<User> users = new ArrayList<User>();
		Cursor c = db.rawQuery("select * from users", null);
		int i = 0;
		while(c.moveToNext() && i < count) {
			User user = new User();
			user.setId(c.getString(c.getColumnIndex("userid")));
			user.setUsername(c.getString(c.getColumnIndex("username")));
			user.setUserStatus(c.getString(c.getColumnIndex("userstatus")));
			user.setCreateDate(c.getString(c.getColumnIndex("createdate")));
			user.setUpdateDate(c.getString(c.getColumnIndex("updatedate")));
			users.add(user);
			i++;
		}
		
		return users;
	}
	
	public synchronized void addUser(User user) {  
		if(getUser(user.getUsername()) != null)
			return;
		db.execSQL("insert into users(userid,username,userstatus,createdate,updatedate) " + 
			"values(?, ?, ?, ?, ?)", 
            new Object[]{user.getId(), user.getUsername(), user.getUserStatus(), 
            user.getCreateDate(), user.getUpdateDate()});  
    }
	
	public synchronized void addUsers(List<User> users) {  
        db.beginTransaction();  //开始事务  
        try {  
            for (User user : users) {  
                db.execSQL("insert into users(userid,username,userstatus,createdate,updatedate) " + 
                "values(?, ?, ?, ?, ?)", 
                new Object[]{user.getId(), user.getUsername(), user.getUserStatus(), 
                user.getCreateDate(), user.getUpdateDate()});  
            }  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
            db.endTransaction();    //结束事务  
        }  
    }  
	
	public synchronized void updateUserStatus(User user) {
		ContentValues cv = new ContentValues();  
        cv.put("userstatus", user.getUserStatus()); 
        cv.put("updatedate", user.getUpdateDate());
        db.update("users", cv, "username = ?", new String[]{user.getUsername()});
	}
	
	public synchronized void removeUser(User user) {
		db.delete("users", "username = ?", new String[]{user.getUsername()}); 
	}
	
	public synchronized List<Chat> getAllChats() {
		List<Chat> chats = new ArrayList<Chat>();
		Cursor c = db.rawQuery("select * from messages order by sent_date asc", null);
		while(c.moveToNext()) {
			Message message = new Message();
			message.setId(c.getString(c.getColumnIndex("msg_id")));
			message.setType(c.getString(c.getColumnIndex("msg_type")));
			message.setFrom(c.getString(c.getColumnIndex("from_user")));
			message.setTo(c.getString(c.getColumnIndex("to_user")));
			message.setSentDate(c.getString(c.getColumnIndex("sent_date")));
			message.setSubject(c.getString(c.getColumnIndex("subject")));
			message.setBody(c.getString(c.getColumnIndex("body")));
			int is_come_msg = c.getInt(c.getColumnIndex("is_come_msg"));
			if(is_come_msg == 1)
				message.setComMeg(true);
			else if(is_come_msg == 0)
				message.setComMeg(false);
			int is_sent = c.getInt(c.getColumnIndex("is_sent"));
			if(is_sent == 1)
				message.setSent(true);
			else if(is_sent == 0)
				message.setSent(false);
			int is_read = c.getInt(c.getColumnIndex("is_read"));
			if(is_read == 1)
				message.setRead(true);
			else if(is_sent == 0)
				message.setRead(false);
			
			String type = message.getType();
			String from = message.getFrom();
			String to = message.getTo();
			String[] tos = to.split(",");
			String[] users = new String[tos.length+1];
			for(int i = 0; i < tos.length; i++) 
				users[i] = tos[i];
			users[users.length-1] = from;
			Arrays.sort(users);
			
			String paticipants = "";
			for(int i = 0; i < users.length; i++)
				paticipants += users[i] + "_";
			paticipants = paticipants.substring(0, paticipants.length()-1);
			
			Chat chat = null;
			for(Chat chat1 : chats) {
				if(chat1.getType().equals(type) && 
					chat1.getPaticipants().equals(paticipants)) {
					chat = chat1;
					break;
				}
			}
			if(chat == null) {
				chat = new Chat();
				if(message.isComMeg()) {
					chat.setLocal(message.getTo());
					chat.setRemote(message.getFrom());
				} else {
					chat.setLocal(message.getFrom());
					chat.setRemote(message.getTo());
				}
				chat.setType(type);
				chat.setPaticipants(paticipants);
				chats.add(chat);
			}
			chat.getMessages().add(message);
		}
		return chats;
	}
	
	public synchronized Chat getChat(String type, String paticipants) {
		Chat chat = null;
		Cursor c = db.rawQuery("select * from messages order by sent_date asc", null);
		while(c.moveToNext()) {
			Message message = new Message();
			message.setId(c.getString(c.getColumnIndex("msg_id")));
			message.setType(c.getString(c.getColumnIndex("msg_type")));
			message.setFrom(c.getString(c.getColumnIndex("from_user")));
			message.setTo(c.getString(c.getColumnIndex("to_user")));
			message.setSentDate(c.getString(c.getColumnIndex("sent_date")));
			message.setSubject(c.getString(c.getColumnIndex("subject")));
			message.setBody(c.getString(c.getColumnIndex("body")));
			int is_come_msg = c.getInt(c.getColumnIndex("is_come_msg"));
			if(is_come_msg == 1)
				message.setComMeg(true);
			else if(is_come_msg == 0)
				message.setComMeg(false);
			int is_sent = c.getInt(c.getColumnIndex("is_sent"));
			if(is_sent == 1)
				message.setSent(true);
			else if(is_sent == 0)
				message.setSent(false);
			int is_read = c.getInt(c.getColumnIndex("is_read"));
			if(is_read == 1)
				message.setRead(true);
			else if(is_sent == 0)
				message.setRead(false);
			
			String type1 = message.getType();
			String from = message.getFrom();
			String to = message.getTo();
			String[] tos = to.split(",");
			String[] users = new String[tos.length+1];
			for(int i = 0; i < tos.length; i++) 
				users[i] = tos[i];
			users[users.length-1] = from;
			Arrays.sort(users);
			
			String paticipants1 = "";
			for(int i = 0; i < users.length; i++)
				paticipants1 += users[i] + "_";
			paticipants1 = paticipants1.substring(0, paticipants1.length()-1);
			
			if(type1.equals(type) && paticipants1.equals(paticipants)) {
				if(chat == null) {
					chat = new Chat();
					chat.setLocal(message.isComMeg() ? message.getTo() : message.getFrom());
					chat.setRemote(message.isComMeg() ? message.getFrom() : message.getTo());
					chat.setType(type);
					chat.setPaticipants(paticipants);
					chat.getMessages().add(message);
				} else {
					chat.getMessages().add(message);
				}
			}
		}
		return chat;
	}
	
	public synchronized List<Message> getMessages(String from, String to) {
		List<Message> messages = new ArrayList<Message>();
		Cursor c = db.rawQuery("select * from messages where from_user=? and " +
			"to_user=? order by time asc", new String[]{from, to});
		while(c.moveToNext()) {
			Message message = new Message();
			message.setId(c.getString(c.getColumnIndex("msg_id")));
			message.setType(c.getString(c.getColumnIndex("msg_type")));
			message.setFrom(c.getString(c.getColumnIndex("from_user")));
			message.setTo(c.getString(c.getColumnIndex("to_user")));
			message.setSentDate(c.getString(c.getColumnIndex("sent_date")));
			message.setSubject(c.getString(c.getColumnIndex("subject")));
			message.setBody(c.getString(c.getColumnIndex("body")));
			int is_come_msg = c.getInt(c.getColumnIndex("is_come_msg"));
			if(is_come_msg == 1)
				message.setComMeg(true);
			else if(is_come_msg == 0)
				message.setComMeg(false);
			int is_sent = c.getInt(c.getColumnIndex("is_sent"));
			if(is_sent == 1)
				message.setSent(true);
			else if(is_sent == 0)
				message.setSent(false);
			int is_read = c.getInt(c.getColumnIndex("is_read"));
			if(is_read == 1)
				message.setRead(true);
			else if(is_sent == 0)
				message.setRead(false);
			messages.add(message);
		}
		return messages;
	}
	
	public synchronized List<Message> getMessages(String from, String to, int count, int offset) {
		List<Message> messages = new ArrayList<Message>();
		Cursor c = db.rawQuery("select * from messages where from_user=? and " +
			"to_user=? order by time asc limit ? offset ?", new String[]{from, to, 
			String.valueOf(count), String.valueOf(offset)});
		while(c.moveToNext()) {
			Message message = new Message();
			message.setId(c.getString(c.getColumnIndex("msg_id")));
			message.setType(c.getString(c.getColumnIndex("msg_type")));
			message.setFrom(c.getString(c.getColumnIndex("from_user")));
			message.setTo(c.getString(c.getColumnIndex("to_user")));
			message.setSentDate(c.getString(c.getColumnIndex("sent_date")));
			message.setSubject(c.getString(c.getColumnIndex("subject")));
			message.setBody(c.getString(c.getColumnIndex("body")));
			int is_come_msg = c.getInt(c.getColumnIndex("is_come_msg"));
			if(is_come_msg == 1)
				message.setComMeg(true);
			else if(is_come_msg == 0)
				message.setComMeg(false);
			int is_sent = c.getInt(c.getColumnIndex("is_sent"));
			if(is_sent == 1)
				message.setSent(true);
			else if(is_sent == 0)
				message.setSent(false);
			int is_read = c.getInt(c.getColumnIndex("is_read"));
			if(is_read == 1)
				message.setRead(true);
			else if(is_sent == 0)
				message.setRead(false);
			messages.add(message);
		}
		return messages;
	}
	
	public synchronized Message getMessage(String msgId) {
		Message message = null;
		Cursor c = db.rawQuery("select * from messages where msg_id = ?", new String[]{msgId});
		if(c.moveToNext()) {
			message = new Message();
			message.setId(c.getString(c.getColumnIndex("msg_id")));
			message.setType(c.getString(c.getColumnIndex("msg_type")));
			message.setFrom(c.getString(c.getColumnIndex("from_user")));
			message.setTo(c.getString(c.getColumnIndex("to_user")));
			message.setSentDate(c.getString(c.getColumnIndex("sent_date")));
			message.setSubject(c.getString(c.getColumnIndex("subject")));
			message.setBody(c.getString(c.getColumnIndex("body")));
			int is_come_msg = c.getInt(c.getColumnIndex("is_come_msg"));
			if(is_come_msg == 1)
				message.setComMeg(true);
			else if(is_come_msg == 0)
				message.setComMeg(false);
			int is_sent = c.getInt(c.getColumnIndex("is_sent"));
			if(is_sent == 1)
				message.setSent(true);
			else if(is_sent == 0)
				message.setSent(false);
			int is_read = c.getInt(c.getColumnIndex("is_read"));
			if(is_read == 1)
				message.setRead(true);
			else if(is_sent == 0)
				message.setRead(false);
		}
		return message;
	}
	
	public synchronized void updateMessageSent(Message message) {
		ContentValues cv = new ContentValues();
		cv.put("is_sent", 1);
		db.update("messages", cv, "msg_id=?", new String[]{message.getId()});
	}
	
	public synchronized void updateMessageRead(Message message) {
		ContentValues cv = new ContentValues();
		cv.put("is_read", 1);
		db.update("messages", cv, "msg_id=?", new String[]{message.getId()});
	}
	
	public synchronized void clearMessages(String from, String to) {
		db.delete("messages", "from_user=? and to_user=?", 
			new String[]{from, to});
	}
	
	public synchronized int getMessagesCount(String from, String to) {
		Cursor c = db.rawQuery("select count(*) from messages where from_user=? and " + 
			"to_user=?", new String[]{from, to});
		int count = 0;
		if(c.moveToNext())
			count = c.getInt(0);
		return count;
	}
	
	public synchronized void addMesssage(Message msg) {
		ContentValues cv = new ContentValues();
		cv.put("msg_id", msg.getId());
		cv.put("msg_type", msg.getType());
		cv.put("from_user", msg.getFrom());
		cv.put("to_user", msg.getTo());
		cv.put("sent_date", msg.getSentDate());
		cv.put("subject", msg.getSubject());
		cv.put("body", msg.getBody());
		cv.put("is_come_msg", msg.isComMeg() ? 1 : 0);
		cv.put("is_sent", msg.isSent() ? 1 : 0);
		cv.put("is_read", msg.isRead() ? 1 : 0);
		db.insert("messages", null, cv);
	}

}
