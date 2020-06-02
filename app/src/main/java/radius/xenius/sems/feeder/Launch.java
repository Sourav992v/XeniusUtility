package radius.xenius.sems.feeder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Launch extends Activity {
	Boolean isInternetPresent = false;
    ConnectionDetector cd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
        	showAlertDialog(Launch.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        else {
        	Thread logoTimer = new Thread() {
    			public void run(){
    				try{
    					int logoTimer = 0;
    					while(logoTimer < 5000){
    						sleep(50);
    						logoTimer = logoTimer +100;
    					};
    					startActivity(new Intent("com.e.CLEARSCREENFEEDER"));
    				}
    				catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    				
    				finally{
    					finish();
    				}
    			}
    		};
    		logoTimer.start();
        }
    }
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        // Setting alert dialog icon
        //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	finish();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
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
	

	@SuppressWarnings("deprecation")
	public void showAlertDialog1(String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		alertDialog.show();
	}


}
