package radius.xenius.sems.feeder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity implements OnClickListener {
	EditText login_id;
	EditText mobile_no;
	Button submit;
	private Context context;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
        /* Initialize buttons */
		context = this;

		getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
		View view =getSupportActionBar().getCustomView();

		/*ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.host = API.LOGINURL;;
				startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
				finish();
			}
		});
*/
		login_id = (EditText)findViewById(R.id.login_ID);
		login_id.requestFocus();
		mobile_no = (EditText)findViewById(R.id.mobile_no);
		submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		AsyncTask<Void, Void, Void> task;
		if(login_id.getText().toString().equalsIgnoreCase("") || login_id.getText().toString().equalsIgnoreCase(null)){
			Toast.makeText(getApplicationContext(), "Field can't be empty !!!", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (mobile_no.getText().toString().equalsIgnoreCase("") || mobile_no.getText().toString().equalsIgnoreCase(null)) {
			Toast.makeText(getApplicationContext(), "Field can't be empty !!!", Toast.LENGTH_SHORT).show();
			return;
		} 
		/*/Call API */
		
		task = new AsyncTask<Void, Void, Void>() {			
			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(context);
				pd.setMessage("Please wait.");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}				
			@SuppressWarnings("WrongThread")
			@Override
			protected Void doInBackground(Void... arg0) {				
				try {
					//Thread.sleep(1000);
					String loginID = login_id.getText().toString();
					String m_no = mobile_no.getText().toString();
					String d =  Util.getdata(API.FORGOT_PASSWORD+"login_id="+loginID+"&mobile_no="+m_no);
										
					JSONObject reader = new JSONObject(d);			
					int rc  = reader.getInt("rc");
					final String message = reader.getString("message");			
					if(rc == 0) {
						ForgotPassword.this.runOnUiThread(new Runnable()
						{
							public void run() {
						        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
						    }
						});
						Intent intent = new Intent(context, OTP.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.putExtra("loginID", loginID);
						intent.putExtra("message", message);
						startActivity(intent);
						finish(); 
					} else {
						ForgotPassword.this.runOnUiThread(new Runnable()
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
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				startActivity(new Intent(ForgotPassword.this, MainActivity.class));
				finish();
				return true;
			case R.id.logout:
				CustomDialogClass cdd=new CustomDialogClass(this);
				cdd.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
		finish();
	}

}
