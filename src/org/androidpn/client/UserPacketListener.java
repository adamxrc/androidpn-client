package org.androidpn.client;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;
import android.util.Log;

public class UserPacketListener implements PacketListener {
	
	private static final String LOGTAG = LogUtil.makeLogTag(UserPacketListener.class);
	
	private final XmppManager xmppManager;

    public UserPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

	@Override
	public void processPacket(Packet packet) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "UserPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());
        
        if (packet instanceof UserIQ) {
            UserIQ user = (UserIQ) packet;
            if (user.getChildElementXML().contains("androidpn:iq:user")) {
            	String userId = user.getId();
                String userName = user.getUserName();
                String userStatus = user.getUserStatus();
                String packetId = user.getPacketID();

                Intent intent = new Intent(Constants.ACTION_SHOW_USER);
                intent.putExtra(Constants.USER_ID, userId);
                intent.putExtra(Constants.USER_NAME, userName);
                intent.putExtra(Constants.USER_STATUS, userStatus);
                intent.putExtra(Constants.PACKET_ID, packetId);
            	
            	//TODO FIXME 发送收到通知回执
                IQ result = UserIQ.createResultIQ(user);
                
                try{
                	xmppManager.getConnection().sendPacket(result);
                }catch(Exception e){}
                
                xmppManager.getContext().sendBroadcast(intent);
            }
        }
	}

}
