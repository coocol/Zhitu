package com.zhitu.xxf;


import com.google.gson.Gson;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private Button mFindButton;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private ActionBar actionBar;
	private MenuItem registerMenuItem;

	public void showError() {
		/*Toast toast = Toast.makeText(getApplicationContext(), "密码/用户名错误",
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();*/
		makeCustomToast(" 密码/用户名错误", Toast.LENGTH_LONG);
	}

	public void makeCustomToast(String text,int duration) {	
		View layout = getLayoutInflater().inflate(R.layout.custom_toast, 
		        (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        // set a message
        TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
        toastText.setText(text);

        // Toast...
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		actionBar = getActionBar();
		actionBar.setTitle("登录");
		actionBar.show();
		mFindButton = (Button) findViewById(R.id.findpwd_button);
		mFindButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String usernameString = mEmailView.getText().toString();
				if (usernameString == null || usernameString.equals("")) {
					mEmailView.setError("请输入用户名");
				} else {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, PwdLostActivity.class);
					intent.putExtra("username", usernameString);
					startActivity(intent);
				}

			}
		});

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.login_username);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.login_password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login_username
								|| id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						attemptLogin();

					}
				});
		if (getIntent() != null && getIntent().getExtras() != null) {
			String registeredName = (String) getIntent().getExtras().get(
					"username");
			if (registeredName != null) {
				mEmailView.setText(registeredName);
				mPasswordView.setFocusable(true);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem emptyItem = menu.add(0, 0, 0, "");
		registerMenuItem = menu.add(0, 0, 0, "  注册    ");

		registerMenuItem
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,
								RegisterActivity.class);
						startActivity(intent);
						return true;
					}
				});
		emptyItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		registerMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		emptyItem.setEnabled(false);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError("密码不能为空");
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 2) {
			mPasswordView.setError("密码长度必须大于6");
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError("用户名不能为空");
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText("正在登录...");
			showProgress(true);
			mAuthTask = new UserLoginTask();
		
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		
		public class GsonUserInfo{
			public String status;
			public UserInfo user;
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public UserInfo getUser() {
				return user;
			}
			public void setUser(UserInfo user) {
				this.user = user;
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {

			ZhituNetWork netWork = new ZhituNetWork();
			String s = netWork.isUserLoginValid(mEmail, mPassword);
			if (s!=null) {
				String resString = new JsonHandle().getSimpleStatusValue(s);
				if(resString!=null && resString.equals("Login Success!")){
					Gson gson = new Gson();
					
					GsonUserInfo gsonUserInfo = gson.fromJson(s, GsonUserInfo.class);
					AppValues.userInfo = gsonUserInfo.getUser();
					return true;
				}else {
					return false;
				}
			} else {
				return false;
			}
		
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			mAuthTask = null;
			showProgress(false);

			if (success) {
				makeCustomToast("登陆成功!", Toast.LENGTH_LONG);
				
				AppValues.username = mEmail;
				//finish();
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, com.zhitu.xxf.MainPageActivity.class);
				LoginActivity.this.startActivity(intent);
			} else {
				makeCustomToast("登陆失败", Toast.LENGTH_LONG);
				// mPasswordView.setError(getString(R.string.error_incorrect_password));
				/*Toast toast = Toast.makeText(LoginActivity.this, "登录失败",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();*/
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
