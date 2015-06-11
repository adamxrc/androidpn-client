package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class MessageIQProvider implements IQProvider {
	
	public MessageIQProvider() {
	}

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		// TODO Auto-generated method stub
		MessageIQ message = new MessageIQ();
        for (boolean done = false; !done;) {
            int eventType = parser.next();
            if (eventType == 2) {
                if ("msg_id".equals(parser.getName())) {
                    message.setMsgId(parser.nextText());
                }else if ("msg_type".equals(parser.getName())) {
                    message.setMsgType(parser.nextText());
                }else if ("subject".equals(parser.getName())) {
                    message.setSubject(parser.nextText());
                }else if ("body".equals(parser.getName())) {
                    message.setBody(parser.nextText());
                }else if ("from_user".equals(parser.getName())) {
                    message.setFromUser(parser.nextText());
                }else if ("to_user".equals(parser.getName())) {
                    message.setToUser(parser.nextText());
                }else if ("sent_date".equals(parser.getName())) {
                    message.setSentDate(parser.nextText());
                }else if ("thread".equals(parser.getName())) {
                    message.setThread(parser.nextText());
                }
            } else if (eventType == 3
                    && "message".equals(parser.getName())) {
                done = true;
            }
        }

        return message;
	}

}
