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
package org.androidpn.demoapp;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity extends Activity {
	 Button setButton,connectButton,exitButton,userlistButton,chatlistButton;
	 EditText userEdit, pwdEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DemoAppActivity", "onCreate()...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Settings
        setButton = (Button) findViewById(R.id.btn_settings);
        connectButton = (Button) findViewById(R.id.btn_connect);
        exitButton = (Button) findViewById(R.id.btn_exit);
        userlistButton = (Button) findViewById(R.id.btn_userlist);
        chatlistButton = (Button) findViewById(R.id.btn_chatlist);
        userEdit = (EditText) findViewById(R.id.edit_user);
        pwdEdit = (EditText) findViewById(R.id.edit_pwd);
        userlistButton.setEnabled(false);
        chatlistButton.setEnabled(false);
        if(Constants.xmppManager != null && Constants.xmppManager.getConnection() != null 
        		&& Constants.xmppManager.getConnection().isAuthenticated()) {
        	connectButton.setVisibility(View.GONE);
            exitButton.setVisibility(View.VISIBLE);
            userlistButton.setEnabled(true);
            chatlistButton.setEnabled(true);
            userEdit.setText(Constants.xmppManager.getUsername());
            pwdEdit.setText(Constants.xmppManager.getPassword());
        }
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ServiceManager.viewNotificationSettings(DemoAppActivity.this);
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	// Start the service
            	String username = userEdit.getText().toString().trim();
            	String password = pwdEdit.getText().toString().trim();
            	if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            		Toast.makeText(DemoAppActivity.this, "username or password is empty", 
            				Toast.LENGTH_LONG).show();
            		return;
            	}
                ServiceManager serviceManager = new ServiceManager(DemoAppActivity.this);
                serviceManager.setNotificationIcon(R.drawable.notification);
                serviceManager.setUsername(username);
                serviceManager.setPassword(password);
                serviceManager.startService();
                connectButton.setVisibility(View.GONE);
                exitButton.setVisibility(View.VISIBLE);
                userlistButton.setEnabled(true);
                chatlistButton.setEnabled(true);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	// Start the service
                ServiceManager serviceManager = new ServiceManager(DemoAppActivity.this);
                serviceManager.setNotificationIcon(R.drawable.notification);
                serviceManager.stopService();
                exitButton.setVisibility(View.GONE);
                connectButton.setVisibility(View.VISIBLE);
                userlistButton.setEnabled(false);
                chatlistButton.setEnabled(false);
            }
        });
        userlistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(DemoAppActivity.this, UserListActivity.class);
            	startActivity(intent);
            }
        });
        chatlistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(DemoAppActivity.this, ChatListActivity.class);
            	startActivity(intent);
            }
        });
    }

}