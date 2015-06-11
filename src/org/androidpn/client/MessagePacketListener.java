package org.androidpn.client;

import org.androidpn.data.Message;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.packet.Packet;

import com.androidpn.db.DBManager;

import android.content.Intent;
import android.util.Log;

public class MessagePacketListener implements PacketListener {
	
	private static final String LOGTAG = LogUtil
            .makeLogTag(MessagePacketListener.class);

    private final XmppManager xmppManager;

    public MessagePacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

	@Override
	public void processPacket(Packet packet) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "MessagePacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof MessageIQ) {
            MessageIQ message = (MessageIQ) packet;
      
            if (message.getChildElementXML().contains(
                    "androidpn:iq:message")) {
            	String messageId = message.getMsgId();
	            String messageType = message.getMsgType();
	            String messageSubject = message.getSubject();
	            String messageBody = message.getBody();
	            String messageFrom = message.getFromUser();
	            String messageTo = message.getToUser();
	            String messageSentDate = message.getSentDate();
	            String messageThread = message.getThread();
	            String packetId = message.getPacketID();
            	if(message.getType() == Type.SET) {
		            Intent intent = new Intent(Constants.ACTION_SHOW_MESSAGE);
		            intent.putExtra(Constants.MESSAGE_ID, messageId);
		            intent.putExtra(Constants.MESSAGE_TYPE, messageType);
		            intent.putExtra(Constants.MESSAGE_SUBJECT, messageSubject);
		            intent.putExtra(Constants.MESSAGE_BODY, messageBody);
		            intent.putExtra(Constants.MESSAGE_FROM, messageFrom);
		            intent.putExtra(Constants.MESSAGE_TO, messageTo);
		            intent.putExtra(Constants.MESSAGE_SENT_DATE, messageSentDate);
		            intent.putExtra(Constants.MESSAGE_THREAD, messageThread);
		            intent.putExtra(Constants.PACKET_ID, packetId);
		            
		            //TODO FIXME 发送收到通知回执
		            message.setPacketID(messageId);
		            IQ result = MessageIQ.createResultIQ(message);
		                
		            try{
		                xmppManager.getConnection().sendPacket(result);
		            }catch(Exception e){}
		                
		            xmppManager.getContext().sendBroadcast(intent);
            	} else if(message.getType() == Type.RESULT) {
            		Message msg = DBManager.getInstance(xmppManager.getContext()).
            			getMessage(messageId);
            		if(msg != null) {
            			msg.setSent(true);
            			DBManager.getInstance(xmppManager.getContext()).updateMessageSent(msg);
            		}
            	}
            }
        }
	}

}
