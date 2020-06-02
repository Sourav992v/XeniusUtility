package radius.xenius.sems.feeder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class NCFeedersDetailsActivity extends AppCompatActivity {
    int tablerowcolor = Color.parseColor("#005c5c");
    JSONArray json_data1;
    View view;
    static ProgressDialog pd;
    String resulttoday;
    private int day;
    private int month;
    private int year;
    ImageButton datebtn;
    ImageButton month_yearbtn;
    EditText dateTxt;
    EditText year_monthTxt;
    String feeder_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncfeeders_details);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#044747")));
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        feeder_status();
    }


    public void feeder_status(){
        try{
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if (pd == null) {
                        pd = new ProgressDialog(NCFeedersDetailsActivity.this);
                        pd.setMessage("Please wait.");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                    }
                    pd.show();
                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        feeder_status = Util.getdata(API.FEEDER_STATUS+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                        //feeder_status = Util.getdata(API.FEEDER_STATUS);
                        System.out.println(feeder_status);
                        feeder_status = feeder_status.replaceAll("<[^>]*>", "");
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
                        }
                        pd = null;
                    }
                    try {
                        JSONObject reader = new JSONObject(feeder_status);
                        int rc = reader.getInt("rc");
                        if(rc==0) {
                            //----------------------------------------------
                            try {
                                json_data1 = reader.getJSONArray("resource");
                                System.out.println(json_data1);
                                TableLayout tl = (TableLayout) findViewById(R.id.nc_status);
                                //tl.setBackgroundColor(Color.parseColor("#007474"));
                                View v1 = new View(NCFeedersDetailsActivity.this);
                                v1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v1.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v1);

                                TableRow tr_head = new TableRow(NCFeedersDetailsActivity.this);
                                tr_head.setBackgroundColor(Color.parseColor("#015454"));
                                tr_head.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_name = new TextView(NCFeedersDetailsActivity.this);
                                if(LoginActivity.access_area.equalsIgnoreCase("UPPCL")) {
                                    label_name.setText("DISCOM");
                                }else{
                                    label_name.setText(LoginActivity.access_area + " ");
                                }
                                label_name.setPadding(8, 10, 30, 10);
                                label_name.setTextColor(Color.parseColor("#87cefa"));
                                label_name.setTextSize(16);
                                tr_head.addView(label_name);

                                TextView label_INSTALLED= new TextView(NCFeedersDetailsActivity.this);
                                label_INSTALLED.setText("INSTALLED");
                                label_INSTALLED.setPadding(50, 10, 0, 10);
                                label_INSTALLED.setTextColor(Color.parseColor("#87cefa"));
                                label_INSTALLED.setTextSize(16);
                                tr_head.addView(label_INSTALLED);

                                TextView label_UP = new TextView(NCFeedersDetailsActivity.this);
                                label_UP.setText("UP");
                                label_UP.setTextColor(Color.parseColor("#87cefa"));
                                label_UP.setPadding(50, 10, 50, 10);
                                label_UP.setTextSize(16);
                                tr_head.addView(label_UP);

                                TextView label_DN = new TextView(NCFeedersDetailsActivity.this);
                                label_DN.setText("DOWN");
                                label_DN.setTextColor(Color.parseColor("#87cefa"));
                                label_DN.setPadding(50, 10, 50, 10);
                                label_DN.setTextSize(16);
                                tr_head.addView(label_DN);

                                TextView label_NC = new TextView(NCFeedersDetailsActivity.this);
                                label_NC.setText("NC");
                                label_NC.setTextColor(Color.parseColor("#87cefa"));
                                label_NC.setPadding(50, 10, 50, 10);
                                label_NC.setTextSize(16);
                                tr_head.addView(label_NC);

                                TextView label_NP = new TextView(NCFeedersDetailsActivity.this);
                                label_NP.setText("NP");
                                label_NP.setTextColor(Color.parseColor("#87cefa"));
                                label_NP.setPadding(50, 10, 50, 10);
                                label_NP.setTextSize(16);
                                tr_head.addView(label_NP);

                                TextView label_TOTAL = new TextView(NCFeedersDetailsActivity.this);
                                label_TOTAL.setText("TOTAL");
                                label_TOTAL.setPadding(50, 10, 50, 10);
                                label_TOTAL.setTextColor(Color.parseColor("#87cefa"));
                                label_TOTAL.setTextSize(16);
                                tr_head.addView(label_TOTAL);

                                TextView label_MVAh = new TextView(NCFeedersDetailsActivity.this);
                                label_MVAh.setText("Energy Supplied "  + System.getProperty("line.separator") +"MVAh");
                                label_MVAh.setPadding(50, 10, 0, 10);
                                label_MVAh.setTextColor(Color.parseColor("#87cefa"));
                                label_MVAh.setTextSize(16);
                                tr_head.addView(label_MVAh);

                                TextView label_PF = new TextView(NCFeedersDetailsActivity.this);
                                label_PF.setText("Power Factor "  + System.getProperty("line.separator") +"PF<0.80");
                                label_PF.setPadding(50, 10, 0, 10);
                                label_PF.setTextColor(Color.parseColor("#87cefa"));
                                label_PF.setTextSize(16);
                                tr_head.addView(label_PF);

                                TextView label_MW = new TextView(NCFeedersDetailsActivity.this);
                                label_MW.setText("Instant Load "  + System.getProperty("line.separator") +"MW");
                                label_MW.setPadding(50, 10, 0, 10);
                                label_MW.setTextColor(Color.parseColor("#87cefa"));
                                label_MW.setTextSize(16);
                                tr_head.addView(label_MW);

                                TextView label_MVA = new TextView(NCFeedersDetailsActivity.this);
                                label_MVA.setText("Instant Load "  + System.getProperty("line.separator") +"MVA");
                                label_MVA.setPadding(50, 10, 100, 10);
                                label_MVA.setTextColor(Color.parseColor("#87cefa"));
                                label_MVA.setTextSize(16);
                                tr_head.addView(label_MVA);

                                tl.addView(tr_head, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                View v11 = new View(NCFeedersDetailsActivity.this);
                                v11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v11.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v11);
                                DecimalFormat df2 = new DecimalFormat("#.##");

                                for (int i = 0; i < json_data1.length(); i++) {
                                    int white = Color.parseColor("#ffffff");
                                    TableRow tr = new TableRow(NCFeedersDetailsActivity.this);

                                    JSONObject objects = json_data1.getJSONObject(i);
                                    String short_name = objects.getString("short_name");
                                    String installed = objects.getString("INSTALLED");
                                    String up = objects.getString("UP");
                                    String down = objects.getString("DN");
                                    String np = objects.getString("NP");
                                    String nc = objects.getString("NC");
                                    String total = objects.getString("TOTAL");
                                    String mvah = objects.getString("units_exported");
                                    String pf = objects.getString("low_perform_pf");
                                    Double mw = objects.getDouble("kw_load");
                                    Double mva = objects.getDouble("kva_load");

                                    tr.setLayoutParams(new TableRow.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    //tr.setBackgroundColor(white);
                                    tr.setBackgroundColor(tablerowcolor);
                                    View v = new View(NCFeedersDetailsActivity.this);
                                    v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                    v.setBackgroundColor(Color.parseColor("#a8a8a8"));

                                    TextView label_short_name = new TextView(NCFeedersDetailsActivity.this);
                                    label_short_name.setTextColor(Color.parseColor("#FFFF99"));
                                    label_short_name.setText(short_name);
                                    label_short_name.setBackgroundColor(tablerowcolor);
                                    label_short_name.setTextSize(12);
                                    label_short_name.setPadding(12, 12, 30, 12);
                                    tr.addView(label_short_name);

                                    TextView label_installed = new TextView(NCFeedersDetailsActivity.this);
                                    label_installed.setText(installed);
                                    label_installed.setTextColor(white);
                                    label_installed.setBackgroundColor(tablerowcolor);
                                    label_installed.setPadding(50, 12, 0, 12);
                                    label_installed.setTextSize(12);
                                    tr.addView(label_installed);

                                    TextView label_up = new TextView(NCFeedersDetailsActivity.this);
                                    label_up.setText(up);
                                    label_up.setTextColor(white);
                                    label_up.setBackgroundColor(tablerowcolor);
                                    label_up.setPadding(50, 12, 50, 12);
                                    label_up.setTextSize(12);
                                    tr.addView(label_up);

                                    TextView label_down = new TextView(NCFeedersDetailsActivity.this);
                                    label_down.setText(down);
                                    label_down.setPadding(50, 12, 50, 12);
                                    label_down.setTextSize(12);
                                    label_down.setBackgroundColor(tablerowcolor);
                                    label_down.setTextColor(white);
                                    tr.addView(label_down);

                                    TextView label_nc = new TextView(NCFeedersDetailsActivity.this);
                                    label_nc.setText(nc);
                                    label_nc.setPadding(50, 12, 50, 12);
                                    label_nc.setTextSize(12);
                                    label_nc.setBackgroundColor(tablerowcolor);
                                    label_nc.setTextColor(white);
                                    tr.addView(label_nc);


                                    TextView label_np = new TextView(NCFeedersDetailsActivity.this);
                                    label_np.setText(np);
                                    label_np.setPadding(50, 12, 50, 12);
                                    label_np.setTextSize(12);
                                    label_np.setBackgroundColor(tablerowcolor);
                                    label_np.setTextColor(white);
                                    tr.addView(label_np);

                                    TextView label_TOTALl = new TextView(NCFeedersDetailsActivity.this);
                                    label_TOTALl.setText(total);
                                    label_TOTALl.setPadding(50, 12, 50, 12);
                                    label_TOTALl.setTextSize(12);
                                    label_TOTALl.setBackgroundColor(tablerowcolor);
                                    label_TOTALl.setTextColor(white);
                                    tr.addView(label_TOTALl);

                                    TextView label_mvah = new TextView(NCFeedersDetailsActivity.this);
                                    label_mvah.setText(mvah);
                                    label_mvah.setPadding(50, 12, 0, 12);
                                    label_mvah.setTextSize(12);
                                    label_mvah.setBackgroundColor(tablerowcolor);
                                    label_mvah.setTextColor(white);
                                    tr.addView(label_mvah);

                                    TextView label_pf = new TextView(NCFeedersDetailsActivity.this);
                                    label_pf.setText(pf);
                                    label_pf.setPadding(50, 12, 0, 12);
                                    label_pf.setTextSize(12);
                                    label_pf.setBackgroundColor(tablerowcolor);
                                    label_pf.setTextColor(white);
                                    tr.addView(label_pf);

                                    TextView label_mw = new TextView(NCFeedersDetailsActivity.this);
                                    label_mw.setText(df2.format(mw)+"");
                                    label_mw.setPadding(50, 12, 0, 12);
                                    label_mw.setTextSize(12);
                                    label_mw.setBackgroundColor(tablerowcolor);
                                    label_mw.setTextColor(white);
                                    tr.addView(label_mw);

                                    TextView label_mva = new TextView(NCFeedersDetailsActivity.this);
                                    label_mva.setText(df2.format(mva)+"");
                                    label_mva.setPadding(50, 12, 150, 12);
                                    label_mva.setTextSize(12);
                                    label_mva.setBackgroundColor(tablerowcolor);
                                    label_mva.setTextColor(white);
                                    tr.addView(label_mva);

                                    tl.addView(tr, new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tl.addView(v);
                                    final  String ncs=label_nc.getText().toString();
                                    final  String nps=label_np.getText().toString();

                                    label_nc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(NCFeedersDetailsActivity.this, NCFeedersDetailsActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addCategory(Intent.CATEGORY_HOME);
                                            intent.putExtra("substation_name", "Test");
                                            intent.putExtra("feeder_name", "Test");
                                            intent.putExtra("feeder_id", "39eb3374-67f7-47bf-9755-82fb72bb311e");
                                            startActivity(intent);
                                            Toast.makeText(NCFeedersDetailsActivity.this,ncs, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    label_np.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(NCFeedersDetailsActivity.this, NPFeedersDetailsActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addCategory(Intent.CATEGORY_HOME);
                                            intent.putExtra("substation_name", "Test");
                                            intent.putExtra("feeder_name", "Test");
                                            intent.putExtra("feeder_id", "39eb3374-67f7-47bf-9755-82fb72bb311e");
                                            startActivity(intent);
                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            task.execute((Void[])null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
