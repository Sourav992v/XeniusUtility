package radius.xenius.sems.feeder;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class LoginActivity extends Activity implements OnClickListener{

    EditText loginID;
    EditText pass;
    Button btnRegister;
    static String login_id = null;
    static String password = null;
    static String access_area="";
    static Context context;
    static String device_token = null;
    static ProgressDialog pd;
    Boolean isInternetPresent = false;
    public static final String PREFS_NAME = "preferences";
    public static final String PREFS_LOGIN_ID = "Username";
    public static final String PREFS_PASSWORD = "Password";
    public static String project_id="";
    public static String profile_name="";
    private final String Defaultlogin_id = null;
    private final String Defaultpassword = null;
    private String username=null;
    private String userpass=null;
    private String device_app_ver="1";
    String resultt ;
    JSONObject reader;
    int rc;
    String message;
    ConnectionDetector cd;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setVisibility(View.GONE);
        loginID = (EditText) findViewById(R.id.loginId);
        pass = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection. !!!", Toast.LENGTH_SHORT).show();
        } else {
            btnRegister.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            ///----------------SIGN IN----------------------------------------------
            case R.id.btnRegister:
                data();
                break;

            default:
                Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void data(){
        login_id = loginID.getText().toString();
        password = pass.getText().toString();
        if(login_id.equalsIgnoreCase("") || login_id.equalsIgnoreCase(null)) {
            Toast.makeText(getApplicationContext(), "Login-id can't be empty !!!", Toast.LENGTH_SHORT).show();
        } else if(password.equalsIgnoreCase("") || password.equalsIgnoreCase(null)) {
            Toast.makeText(getApplicationContext(), "Password can't be empty !!!", Toast.LENGTH_SHORT).show();
        } else if(login_id.contains(" ")) {
            Toast.makeText(getApplicationContext(), "Login-id is not allowed white spaces", Toast.LENGTH_SHORT).show();
        } else if(password.contains(" ")) {
            Toast.makeText(getApplicationContext(), "Password is not allowed white spaces", Toast.LENGTH_SHORT).show();
        } else {
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if (pd == null) {
                        pd = new ProgressDialog(context);
                        pd.setMessage("Please wait.");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                    }
                    pd.show();
                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        System.out.println(API.LOGIN+"login_id="+login_id+"&password="+password+"&device_token=" + device_token + "&device_OS=ANDROID&device_app_ver="+device_app_ver);
                        resultt = Util.getdata(API.LOGIN+"login_id="+login_id+"&password="+password+"&device_token=" + device_token + "&device_OS=ANDROID&device_app_ver="+device_app_ver);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    ///--------------------------------------------------------
                    if (pd != null && pd.isShowing()) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                            btnRegister.setEnabled(false);
                        }
                        pd = null;
                    }
                    try {
                        reader = new JSONObject(resultt);
                        rc  = reader.getInt("rc");
                        message = reader.getString("message");
                        if(rc == 0) {
                            JSONObject resource = reader.getJSONObject("resource");
                            String application = resource.getString("application");
                            if(application.equalsIgnoreCase("FEDR")){
                                //-------------------host from login data------------------------
                                project_id=resource.getString("project_id");
                                profile_name=resource.getString("profile_name");
                                access_area=resource.getString("access_area");
                                Intent intent;
                                if(access_area.equalsIgnoreCase("UPPCL") || access_area.equalsIgnoreCase("ZONE") || access_area.equalsIgnoreCase("CIRCLE") || access_area.equalsIgnoreCase("DIVISION")){
                                    intent = new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    startActivity(intent);
                                    finish();
                                } else if(access_area.equalsIgnoreCase("SUBSTATION")){
                                    intent = new Intent(context, JeSdoMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    btnRegister.setEnabled(true);
                                    Toast.makeText(LoginActivity.this,"Invalid Access Area",Toast.LENGTH_LONG).show();;
                                    //intent = new Intent(context, JeSdoMainActivity.class);
                                }

                            } else{
                                btnRegister.setEnabled(true);
                                Toast.makeText(LoginActivity.this,"Invalid Appilcation Type",Toast.LENGTH_LONG).show();
                            }
                        } else{
                            btnRegister.setEnabled(true);
                            Toast.makeText(LoginActivity.this,reader.getString("message"),Toast.LENGTH_LONG).show();;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            task.execute((Void[])null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(pd != null && pd.isShowing())
            pd .dismiss();
        pd = null;
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        username = loginID.getText().toString();
        userpass = pass.getText().toString();
        System.out.println("onPause save name: " + username);
        System.out.println("onPause save password: " + userpass);
        editor.putString(PREFS_LOGIN_ID, username);
        editor.putString(PREFS_PASSWORD, userpass);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        username = settings.getString(PREFS_LOGIN_ID, Defaultlogin_id);
        userpass = settings.getString(PREFS_PASSWORD, Defaultpassword);
        btnRegister.setEnabled(true);

        if(username!=null && userpass!=null){
            loginID.setText(username);
            pass.setText(userpass);
            System.out.println("onResume load name: " + username);
            System.out.println("onResume load password: " + userpass);
            data();
        }
    }

    public  void googlePlayUpdateOption(){
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public void forgotPassword(View v){
        Intent intent = new Intent(context, ForgotPassword.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
    }

}
