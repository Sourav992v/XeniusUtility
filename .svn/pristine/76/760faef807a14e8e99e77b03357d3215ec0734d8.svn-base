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
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordForgotActivity extends AppCompatActivity implements OnClickListener{
	EditText confirm_password;
	EditText new_password;
	Button submit;
	ImageButton refresh;
	private Context context;
	String loginID;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_reset_password_forgot);
		context=this;

		getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
		View view =getSupportActionBar().getCustomView();

/* Initialize buttons */
		
		new_password = (EditText)findViewById(R.id.new_password);
		confirm_password = (EditText)findViewById(R.id.confirm_password);
		new_password.requestFocus();
		submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
		Bundle gt=getIntent().getExtras();
		loginID = gt.getString("loginID");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset_password_forgot, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		AsyncTask<Void, Void, Void> task;
				
			if(new_password.getText().toString().equalsIgnoreCase("") || new_password.getText().toString().equalsIgnoreCase(null)){
				Toast.makeText(getApplicationContext(), "Field can't be empty !!!", Toast.LENGTH_SHORT).show();
				return;
			}
			if (confirm_password.getText().toString().equalsIgnoreCase("") || confirm_password.getText().toString().equalsIgnoreCase(null)) {
				Toast.makeText(getApplicationContext(), "Field can't be empty !!!", Toast.LENGTH_SHORT).show();
				return;
			} 
			if(new_password.getText().toString().contains(" ")) {
				Toast.makeText(getApplicationContext(), "New Password is not allowed white spaces", Toast.LENGTH_SHORT).show();
				return;
			} 
			if(confirm_password.getText().toString().contains(" ")) {
				Toast.makeText(getApplicationContext(), "Confirm Password is not allowed white spaces", Toast.LENGTH_SHORT).show();
				return;
			}
			String new_pass = new_password.getText().toString();
			final String confirm_pass = confirm_password.getText().toString();
			if(new_pass.equals(confirm_pass)) {					
			/*--------------------- Call API--------------------------------------- */
				task = new AsyncTask<Void, Void, Void>() {			
					@Override
					protected void onPreExecute() {
						pd = new ProgressDialog(context);
						pd.setMessage("Please wait.");
						pd.setCancelable(false);
						pd.setIndeterminate(true);
						pd.show();
					}
						
					@Override
					protected Void doInBackground(Void... arg0) {				
						try {
						//	Thread.sleep(1000);
							String d =  Util.getdata(API.PASSWORD_CHANGE+"login_id="+loginID+"&password="+confirm_pass);
							JSONObject reader = new JSONObject(d);
							int resource  = reader.getInt("rc");
							final String message= reader.getString("message");
							System.out.println(message);
							if(resource == 0) {
								//switch on login page
								ResetPasswordForgotActivity.this.runOnUiThread(new Runnable()
								{
									public void run() {
								        Toast.makeText(context, "Password Successfully Reset !! Please Login Again !!", Toast.LENGTH_LONG).show();
								    }
								});
								
						    	Intent intent = new Intent(context, MainActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.addCategory(Intent.CATEGORY_HOME);
								startActivity(intent);
								finish();
							}
							else {
								ResetPasswordForgotActivity.this.runOnUiThread(new Runnable()
								{
									public void run() {
								        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
								        new_password.setText("");
										confirm_password.setText("");
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
						
			} else {
				Toast.makeText(getApplicationContext(), "New Password and Confirm Password are not same !! Please Re-Enter", Toast.LENGTH_SHORT).show();
				new_password.setText("");
				confirm_password.setText("");
				return;
			}				
						
		
				
	}
	
	
	public void showToast(final String toast)
	{
	    runOnUiThread(new Runnable() {
	        public void run()
	        {
	            Toast.makeText(ResetPasswordForgotActivity.this, toast, Toast.LENGTH_SHORT).show();
	        }
	    });
	}

}
