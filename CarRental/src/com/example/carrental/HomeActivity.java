package com.example.carrental;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.model.system.User;
import com.telerik.everlive.sdk.core.query.definition.UserSecretInfo;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

public class HomeActivity extends Activity implements View.OnClickListener {

	Context context = this;
	Button registerBtn, loginBtn;
	EditText username, password;
	EverliveApp app;
	String registrationMsg;
	ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = new EverliveApp("ZEW8xrnCpPDDsuQD");

		setContentView(R.layout.activity_home);

		registerBtn = (Button) findViewById(R.id.RegisterBtn);
		loginBtn = (Button) findViewById(R.id.LoginBtn);
		username = (EditText) findViewById(R.id.UsernameInput);
		password = (EditText) findViewById(R.id.PasswordInput);

		registerBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.RegisterBtn) {

			String usernameAsString = username.getText().toString();
			String passwordAsString = password.getText().toString();

			registerUser(app, usernameAsString, passwordAsString);
			Toast.makeText(context, "Registering...", Toast.LENGTH_LONG).show();
			Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                Toast.makeText(context, registrationMsg, Toast.LENGTH_LONG).show();
	            }
	        }, 5000);

			//Toast.makeText(context, registrationMsg, Toast.LENGTH_LONG).show();
		}

	}

	public void registerUser(EverliveApp app, String username, String password) {

		final User user = new User();
		user.setUsername(username);
		UserSecretInfo secretInfo = new UserSecretInfo();
		secretInfo.setPassword(password);
		app.workWith().users().create(user, secretInfo).executeAsync(new RequestResultCallbackAction() {

			@Override
			public void invoke(RequestResult requestResult) {

				registrationMsg = "";
				if (requestResult.getSuccess()) {
					registrationMsg = "Registration Successful!";
				} else {
					registrationMsg = requestResult.getError().getMessage();
				}
			}

		});

	}
	
}
