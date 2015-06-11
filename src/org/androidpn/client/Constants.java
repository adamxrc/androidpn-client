/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

/**
 * Static constants for this package.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class Constants {

    public static final String SHARED_PREFERENCE_NAME = "client_preferences";

    // PREFERENCE KEYS

    public static final String CALLBACK_ACTIVITY_PACKAGE_NAME = "CALLBACK_ACTIVITY_PACKAGE_NAME";

    public static final String CALLBACK_ACTIVITY_CLASS_NAME = "CALLBACK_ACTIVITY_CLASS_NAME";

    public static final String API_KEY = "API_KEY";

    public static final String VERSION = "VERSION";

    public static final String XMPP_HOST = "XMPP_HOST";

    public static final String XMPP_PORT = "XMPP_PORT";

    public static final String XMPP_USERNAME = "XMPP_USERNAME";

    public static final String XMPP_PASSWORD = "XMPP_PASSWORD";

    // public static final String USER_KEY = "USER_KEY";

    public static final String DEVICE_ID = "DEVICE_ID";

    public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";

    public static final String NOTIFICATION_ICON = "NOTIFICATION_ICON";

    public static final String SETTINGS_NOTIFICATION_ENABLED = "SETTINGS_NOTIFICATION_ENABLED";

    public static final String SETTINGS_SOUND_ENABLED = "SETTINGS_SOUND_ENABLED";

    public static final String SETTINGS_VIBRATE_ENABLED = "SETTINGS_VIBRATE_ENABLED";

    public static final String SETTINGS_TOAST_ENABLED = "SETTINGS_TOAST_ENABLED";

    // NOTIFICATION FIELDS

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

    public static final String NOTIFICATION_URI = "NOTIFICATION_URI";
    
    public static final String PACKET_ID = "PACKET_ID";
    
    public static final String NOTIFICATION_FROM = "NOTIFICATION_FROM";
    
    // USER FIELDS
    
    public static final String USER_ID = "USER_ID";

    public static final String USER_NAME = "USER_NAME";

    public static final String USER_STATUS = "USER_STATUS";
    
    // MESSAGE FIELDS
    
    public static final String MESSAGE_ID = "MESSAGE_ID";
    
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    
    public static final String MESSAGE_SUBJECT = "MESSAGE_SUBJECT";

    public static final String MESSAGE_BODY = "MESSAGE_BODY";

    public static final String MESSAGE_FROM = "MESSAGE_FROM";
    
    public static final String MESSAGE_TO = "MESSAGE_TO";
    
    public static final String MESSAGE_SENT_DATE = "MESSAGE_SENT_DATE";
    
    public static final String MESSAGE_THREAD = "MESSAGE_THREAD";
    
    public static final String MESSAGE_IS_COME = "MESSAGE_IS_COME";

    // INTENT ACTIONS

    public static final String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

    public static final String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

    public static final String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

    public static final String ACTION_SHOW_USER = "org.androidpn.client.SHOW_USER";
    
    public static final String ACTION_SHOW_MESSAGE = "org.androidpn.client.SHOW_MESSAGE";
    
    public static XmppManager xmppManager = null;
    
    public static final int CHAT_HISTORY_COUNT = 5;
    
    public static final String USER_ONLINE = "online";
    
    public static final String USER_OFFLINE = "offline";
    
    public static final String USER_LIST_RECEIVER = "user_list_receiver";
    
    public static final String CHAT_LIST_RECEIVER = "chat_list_receiver";
    
    public static final String CHAT_RECIEVER = "chat_receiver";
    
    public static final String CHAT_EXTRA = "chat";
    
    public static final String CHAT_ACTIVITY_NAME = "org.androidpn.demoapp.ChatActivity";

}
