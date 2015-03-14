package com.zhitu.xxf;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RegisterActivity extends Activity {
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
	private String mPasswordConfirm;
	private String muserName;
	// UI references.
	private EditText mNameView;
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		actionBar = getActionBar();
		actionBar.setTitle("注册用户");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		// Set up the login form.

		mNameView = (EditText) findViewById(R.id.register_username);

		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.register_usermail);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.resgister_password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.register || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		mPasswordConfirmView = (EditText) findViewById(R.id.resgister_password_confirm);
		mPasswordConfirmView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.register || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.register_form);
		mLoginStatusView = findViewById(R.id.register_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.register_status_message);

		findViewById(R.id.findpwd_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
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
		mNameView.setError(null);
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		muserName = mNameView.getText().toString();
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPasswordConfirm = mPasswordConfirmView.getText().toString();
		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(muserName)) {
			mNameView.setError("用户名不能为空");
			focusView = mNameView;
			cancel = true;
		} else if (muserName.length() < 2) {
			mNameView.setError("用户名长度应大于2");
			focusView = mNameView;
			cancel = true;
		}

		// Check for a valid password.

		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError("请输入密码");
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 2) {
			mPasswordView.setError("密码长度应大于5");
			focusView = mPasswordView;
			cancel = true;
		}
		if (TextUtils.isEmpty(mPasswordConfirm)) {
			mPasswordConfirmView.setError("请再次输入密码");
			focusView = mPasswordConfirmView;
			cancel = true;
		} else if (!mPasswordConfirm.equals(mPassword)) {
			mPasswordConfirmView.setError("密码不一致");
			mPasswordConfirmView.setText("");
			focusView = mPasswordConfirmView;
			cancel = true;
		}

		// Check for a valid email address.
		if (!mEmail.contains("@") || mEmail.endsWith("@")
				|| mEmail.startsWith("@")) {
			mEmailView.setError("邮箱格式不正确");
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
			mLoginStatusMessageView.setText("正在注册用户...");
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
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.

				Thread.sleep(2000);
				ZhituNetWork netWork = new ZhituNetWork();
				if (netWork.isRegisterUserOK(muserName, mPassword, mEmail)) {
					return true;
				} else {
					return false;
				}
			} catch (InterruptedException e) {
				return false;
			}

			/*
			 * for (String credential : DUMMY_CREDENTIALS) { String[] pieces =
			 * credential.split(":"); if (pieces[0].equals(mEmail)) { // Account
			 * exists, return true if the password matches. return
			 * pieces[1].equals(mPassword); } }
			 */

			// TODO: register the new account here.
			// return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
				/*Toast toast = Toast.makeText(getApplicationContext(),
						"注册成功，请登录", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 100);
				toast.show();*/
				makeCustomToast("注册成功!", Toast.LENGTH_LONG);
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				intent.putExtra("username", muserName);
				startActivity(intent);
			} else {
				/*
				 * mPasswordView
				 * .setError(getString(R.string.error_incorrect_password));
				 * mPasswordView.requestFocus();
				 */
				/*Toast toast = Toast.makeText(getApplicationContext(),
						"注册失败，请检查用户名", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 100);
				toast.show();*/
				makeCustomToast("注册失败", Toast.LENGTH_LONG);
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
