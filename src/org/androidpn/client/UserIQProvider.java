package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class UserIQProvider implements IQProvider {
	
	public UserIQProvider() {
	}

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		// TODO Auto-generated method stub
		UserIQ user = new UserIQ();
        for (boolean done = false; !done;) {
            int eventType = parser.next();
            if (eventType == 2) {
                if ("id".equals(parser.getName())) {
                    user.setId(parser.nextText());
                }else if ("userName".equals(parser.getName())) {
                    user.setUserName(parser.nextText());
                }else if ("userStatus".equals(parser.getName())) {
                    user.setUserStatus(parser.nextText());
                }
            } else if (eventType == 3
                    && "user".equals(parser.getName())) {
                done = true;
            }
        }

        return user;
	}

}
