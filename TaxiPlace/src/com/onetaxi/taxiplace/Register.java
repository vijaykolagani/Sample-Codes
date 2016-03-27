package com.onetaxi.taxiplace;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.text.InputType;
import android.util.Config;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.onetaxi.validations.Field;
import com.onetaxi.validations.Form;
import com.onetaxi.validations.FormUtils;
import com.onetaxi.validations.InRange;
import com.onetaxi.validations.IsEmail;
import com.onetaxi.validations.NotEmpty;



public class Register extends Activity {

	EditText etEmail,etPswd,etRePswd,etDob,etPhone,etFirstName,etLastName;
	Button btRegister;
	Form mForm;
	private SimpleDateFormat dateFormatter;
	private DatePickerDialog dobDatePickerDialog;
	RadioGroup rgMF;
	ScrollView svMain;
	Profile profile;
	String gender = "m";
	String regId  ="";
	Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		/*WebView	wChart = (WebView)findViewById(R.id.webView1);
        wChart.getSettings().setJavaScriptEnabled(true);
        wChart.loadUrl("file:///android_asset/index.html");*/
		
		extras = getIntent().getExtras();
		

		
		etEmail = (EditText)findViewById(R.id.et_email);
		etPswd = (EditText)findViewById(R.id.et_password);
		etRePswd = (EditText)findViewById(R.id.et_repassword);
		etFirstName = (EditText)findViewById(R.id.et_firstname);
		etLastName = (EditText) findViewById(R.id.et_lastname);
		etDob = (EditText) findViewById(R.id.et_dob);
		etPhone = (EditText) findViewById(R.id.et_phone);
		btRegister = (Button)findViewById(R.id.bt_register);
		rgMF = (RadioGroup) findViewById(R.id.rg_mf);
		
		svMain = (ScrollView) findViewById(R.id.sv_main);

		etDob.setInputType(InputType.TYPE_NULL);

		dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
		initValidationForm();
		initCallbacks();
		

	}


	

	
	
	private void initValidationForm() {
		mForm = new Form(this);
		mForm.addField(Field.using(etEmail).validate(NotEmpty.build(this)).validate(IsEmail.build(this)));
		mForm.addField(Field.using(etPswd).validate(InRange.build(this, 6, 14)));
		mForm.addField(Field.using(etRePswd).validate(InRange.build(this, 6, 14))/*.validate(IsEquals.build(this,etPswd.getText().toString(),etRePswd.getText().toString()))*/);
		mForm.addField(Field.using(etFirstName).validate(NotEmpty.build(this)));
		mForm.addField(Field.using(etLastName).validate(NotEmpty.build(this)));
		mForm.addField(Field.using(etDob).validate(NotEmpty.build(this)));
		mForm.addField(Field.using(etPhone).validate(InRange.build(this, 10, 10)));

	}

	private void initCallbacks() {
		etPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
						submitReg();
					
					return true;
				}
				return false;
			}

		});


		Calendar newCalendar = Calendar.getInstance();
		dobDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				etDob.setText(dateFormatter.format(newDate.getTime()));
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		etDob.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					dobDatePickerDialog.show();

			}
		});
		etDob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dobDatePickerDialog.show();
			}
		});
		btRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isNetworkAvailable())
					submitReg();
				else
					Toast.makeText(getApplicationContext(), "No Internet", 1).show();
				
			}
		});
		rgMF.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override 
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected 
				if(checkedId == R.id.rb_female) {
					gender = "f";
					Log.d("gender",gender);
				} else if(checkedId == R.id.rb_male) {
					gender = "m";
					Log.d("gender",gender);
				} 
			} 
		}); 
	}

	private void submitReg() {
		FormUtils.hideKeyboard(Register.this, etPhone);
		if (mForm.isValid()) {
			if(etPswd.getText().toString().equals(etRePswd.getText().toString().toString())){

			

			}else{
				Toast.makeText(this, "Password mismatchhhhhh", Toast.LENGTH_LONG).show();
			}
		}
	}

	

	public  boolean isNetworkAvailable(){
		ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivity.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();

	}


}
