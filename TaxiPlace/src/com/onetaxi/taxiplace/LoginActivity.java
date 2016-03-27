package com.onetaxi.taxiplace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onetaxi.validations.Field;
import com.onetaxi.validations.Form;
import com.onetaxi.validations.FormUtils;
import com.onetaxi.validations.IsEmail;
import com.onetaxi.validations.NotEmpty;



public class LoginActivity extends Activity {

	Button btLogin,btRegister; 
	EditText etMail,etPswd;
	Form mForm;
	String pswd;
	String regId  ="";
	Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		extras = getIntent().getExtras();

		btLogin = (Button)findViewById(R.id.btLogin);
		btRegister = (Button) findViewById(R.id.btRegister);
		etMail = (EditText) findViewById(R.id.etUsername);
		etPswd = (EditText) findViewById(R.id.etPassword);

		
		//isServiseAvailable = (Check_Services) getApplicationContext();



		initiCallbacks();
		initValidationForm();
	}

	public void initiCallbacks(){
		etPswd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					submit();
					return true;
				}
				return false;
			}
		});

		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable())
					submit();
				else
					Toast.makeText(getApplicationContext(), "No INternet", Toast.LENGTH_SHORT).show();

			}
		});
		
		btRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this,Register.class));

			}
		});

	}


	public  boolean isNetworkAvailable(){
		ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivity.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();

	}

	private void initValidationForm() {
		mForm = new Form(this);
		mForm.addField(Field.using(etMail).validate(IsEmail.build(this)).validate(NotEmpty.build(this)));
		mForm.addField(Field.using(etPswd).validate(NotEmpty.build(this)));
	}
	private void submit() {
		FormUtils.hideKeyboard(LoginActivity.this, etPswd);
		startActivity(new Intent(LoginActivity.this,MainActivity.class));
		if (mForm.isValid()) {
			if(isNetworkAvailable()){
				if(etMail.getText().toString().matches("[0-9]+")){

				}else{

				}
			}else{
				Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
			}


		}
	}




}
