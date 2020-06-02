package radius.xenius.sems.feeder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class OTP extends AppCompatActivity implements OnClickListener{
	EditText optText;
	TextView  messageLabel;
	TextView  resend;
	Button submit;
	String loginID;
	private Context context;
	ProgressDialog pd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);
		context = this;

		getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
		View view =getSupportActionBar().getCustomView();


		optText = (EditText)findViewById(R.id.otpText);
		messageLabel = (TextView)findViewById(R.id.messageLable);
		resend = (TextView)findViewById(R.id.resend);
		Bundle gt=getIntent().getExtras();
		String message=gt.getString("message");
	    loginID=gt.getString("loginID");
		messageLabel.setText(message);
		submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
		resend.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ot, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		AsyncTask<Void, Void, Void> task;
		switch(v.getId()) {
		case R.id.submit: 
			// -------------------Submit Button-------------------------------------------------------------------------
			if(optText.getText().toString().equalsIgnoreCase("") || optText.getText().toString().equalsIgnoreCase(null)){
				Toast.makeText(getApplicationContext(), "Please Enter One Time Password !!!", Toast.LENGTH_SHORT).show();
				return;
			}		
			final String opt = optText.getText().toString();
			
			task = new AsyncTask<Void, Void, Void>() {			
				@Override
				protected void onPreExecute() {
					pd = new ProgressDialog(context);
					pd.setTitle("Loading...");
					pd.setMessage("Please wait");
					pd.setCancelable(false);
					pd.setIndeterminate(true);
					pd.show();
				}
					
				@Override
				protected Void doInBackground(Void... arg0) {				
					try {
					//	Thread.sleep(1000);
						String d =  Util.getdata(API.OTP_VARIFY+"login_id="+loginID+"&otp="+opt);
						JSONObject reader = new JSONObject(d);			
						int rc  = reader.getInt("rc");
						final String message = reader.getString("message");			
						if(rc == 0) {
							Intent intent = new Intent(context, ResetPasswordForgotActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.addCategory(Intent.CATEGORY_HOME);
							intent.putExtra("loginID", loginID);
							startActivity(intent);
							finish(); 
						} else {
							OTP.this.runOnUiThread(new Runnable()
							{
								public void run() {
							        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();	
							        optText.setText("");
							    }
							});
							
						}	
	                } catch (JSONException e) {
						e.printStackTrace();
					}
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					if (pd!=null) {
						pd.dismiss();
					}
				}
			};
			task.execute((Void[])null);
		
		break;
	  case R.id.resend:		
		
		/*-----------------------------Call API-------------------------*/
				
		task = new AsyncTask<Void, Void, Void>() {			
			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(context);
				pd.setTitle("Loading...");
				pd.setMessage("Please wait.");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}
				
			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					//Thread.sleep(1000);
					String data =  Util.getdata(API.RESEND_OTP+"login_id="+loginID);
					JSONObject reader = new JSONObject(data);			
					int rc  = reader.getInt("rc");
					final String message = reader.getString("message");			
					if(rc == 0) {
						OTP.this.runOnUiThread(new Runnable()
						{
							public void run() {
						        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();						        
						    }
						});
					} else {
						OTP.this.runOnUiThread(new Runnable()
						{
							public void run() {
						        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
						    }
						});
					}	
                } catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if (pd!=null) {
					pd.dismiss();
				}
			}
		};
		task.execute((Void[])null);
		break;
		
	  default: 
     	 Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show();
      break;
	}
				
	}

}
