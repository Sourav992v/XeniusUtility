
package radius.xenius.sems.feeder;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ItemOneFragment extends Fragment {
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
    int tablerowcolor = Color.parseColor("#005c5c");
    //int tablerowcolor = Color.parseColor("#007474");
    int white = Color.parseColor("#ffffff");

    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_item_one, container, false);

        //---------------------date picker-------------------------
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        TextView projectIDText = (TextView) view.findViewById(R.id.project_idTxt);
        TextView profile_nameText = (TextView) view.findViewById(R.id.consumerNameTxt);
        projectIDText.setText("Project :  "+LoginActivity.project_id);
        profile_nameText.setText("Welcome, "+LoginActivity.profile_name);
        dateTxt = (EditText) view.findViewById(R.id.dateText);
        year_monthTxt  = (EditText) view.findViewById(R.id.month_yearText);

        year_monthTxt.setText((month + 1) + "-" + year);
        dateTxt.setText(day + "-" + (month + 1) + "-" + year);


        datebtn=(ImageButton) view.findViewById(R.id.datebtn);
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateTxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                today_statistics(dateTxt.getText().toString());
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        month_yearbtn=(ImageButton) view.findViewById(R.id.month_yearbtn);
        month_yearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                year_monthTxt.setText((monthOfYear + 1) + "-" + year);
                                monthly_statistics(year_monthTxt.getText().toString());
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        feeder_status();
        String date= dateTxt.getText().toString();
        String year_month= year_monthTxt.getText().toString();
        today_statistics(date);
        monthly_statistics(year_month);
        return view;
    }


    public void feeder_status(){
        try{
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if (pd == null) {
                        pd = new ProgressDialog(getContext());
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
                                TableLayout tl = (TableLayout) view.findViewById(R.id.feeder_status);
                                //tl.setBackgroundColor(Color.parseColor("#007474"));
                                View v1 = new View(getActivity());
                                v1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v1.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v1);

                                TableRow tr_head = new TableRow(getActivity());
                                tr_head.setBackgroundColor(Color.parseColor("#015454"));
                                tr_head.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_name = new TextView(getActivity());
                                if(LoginActivity.access_area.equalsIgnoreCase("UPPCL")) {
                                    label_name.setText("DISCOM");
                                }else{
                                    label_name.setText(LoginActivity.access_area + " ");
                                }
                                label_name.setPadding(8, 10, 30, 10);
                                label_name.setTextColor(Color.parseColor("#87cefa"));
                                label_name.setTextSize(16);
                                tr_head.addView(label_name);

                                TextView label_INSTALLED= new TextView(getActivity());
                                label_INSTALLED.setText("INSTALLED");
                                label_INSTALLED.setPadding(50, 10, 0, 10);
                                label_INSTALLED.setTextColor(Color.parseColor("#87cefa"));
                                label_INSTALLED.setTextSize(16);
                                tr_head.addView(label_INSTALLED);

                                TextView label_UP = new TextView(getActivity());
                                label_UP.setText("UP");
                                label_UP.setTextColor(Color.parseColor("#87cefa"));
                                label_UP.setPadding(50, 10, 50, 10);
                                label_UP.setTextSize(16);
                                tr_head.addView(label_UP);

                                TextView label_DN = new TextView(getActivity());
                                label_DN.setText("DOWN");
                                label_DN.setTextColor(Color.parseColor("#87cefa"));
                                label_DN.setPadding(50, 10, 50, 10);
                                label_DN.setTextSize(16);
                                tr_head.addView(label_DN);

                                TextView label_NC = new TextView(getActivity());
                                label_NC.setText("NC");
                                label_NC.setTextColor(Color.parseColor("#87cefa"));
                                label_NC.setPadding(50, 10, 50, 10);
                                label_NC.setTextSize(16);
                                tr_head.addView(label_NC);

                                TextView label_NP = new TextView(getActivity());
                                label_NP.setText("NP");
                                label_NP.setTextColor(Color.parseColor("#87cefa"));
                                label_NP.setPadding(50, 10, 50, 10);
                                label_NP.setTextSize(16);
                                tr_head.addView(label_NP);

                                TextView label_TOTAL = new TextView(getActivity());
                                label_TOTAL.setText("TOTAL");
                                label_TOTAL.setPadding(50, 10, 50, 10);
                                label_TOTAL.setTextColor(Color.parseColor("#87cefa"));
                                label_TOTAL.setTextSize(16);
                                tr_head.addView(label_TOTAL);

                                TextView label_MVAh = new TextView(getActivity());
                                label_MVAh.setText("Energy Supplied "  + System.getProperty("line.separator") +"MVAh");
                                label_MVAh.setPadding(50, 10, 0, 10);
                                label_MVAh.setTextColor(Color.parseColor("#87cefa"));
                                label_MVAh.setTextSize(16);
                                tr_head.addView(label_MVAh);

                                TextView label_PF = new TextView(getActivity());
                                label_PF.setText("Power Factor "  + System.getProperty("line.separator") +"PF<0.80");
                                label_PF.setPadding(50, 10, 0, 10);
                                label_PF.setTextColor(Color.parseColor("#87cefa"));
                                label_PF.setTextSize(16);
                                tr_head.addView(label_PF);

                                TextView label_MW = new TextView(getActivity());
                                label_MW.setText("Instant Load "  + System.getProperty("line.separator") +"MW");
                                label_MW.setPadding(50, 10, 0, 10);
                                label_MW.setTextColor(Color.parseColor("#87cefa"));
                                label_MW.setTextSize(16);
                                tr_head.addView(label_MW);

                                TextView label_MVA = new TextView(getActivity());
                                label_MVA.setText("Instant Load "  + System.getProperty("line.separator") +"MVA");
                                label_MVA.setPadding(50, 10, 100, 10);
                                label_MVA.setTextColor(Color.parseColor("#87cefa"));
                                label_MVA.setTextSize(16);
                                tr_head.addView(label_MVA);

                                tl.addView(tr_head, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                View v11 = new View(getActivity());
                                v11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v11.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v11);
                                DecimalFormat df2 = new DecimalFormat("#.##");

                                int totalinstalled = 0;
                                int totalup = 0;
                                int totaldown = 0;
                                int totalnp = 0;
                                int totalnc = 0;
                                int totaltotal = 0;
                                int totalpf = 0;
                                Double totalmvah = 0.0;
                                Double totalmw = 0.0;
                                Double totalmva = 0.0;

                                for (int i = 0; i < json_data1.length(); i++) {
                                    int white = Color.parseColor("#ffffff");
                                    TableRow tr = new TableRow(getContext());

                                    JSONObject objects = json_data1.getJSONObject(i);
                                    String short_name = objects.getString("short_name");
                                    int installed = objects.getInt("INSTALLED");
                                    int up = objects.getInt("UP");
                                    int down = objects.getInt("DN");
                                    int np = objects.getInt("NP");
                                    int nc = objects.getInt("NC");
                                    int total = objects.getInt("TOTAL");
                                    int pf = objects.getInt("low_perform_pf");
                                    Double mvah = objects.getDouble("units_exported");
                                    Double mw = objects.getDouble("kw_load");
                                    Double mva = objects.getDouble("kva_load");

                                     totalinstalled = totalinstalled+installed;
                                     totalup = totalup+up;
                                     totaldown = totaldown+down;
                                     totalnp = totalnp+np;
                                     totalnc = totalnc+nc;
                                     totaltotal = totaltotal+total;
                                     totalmvah = totalmvah+mvah;
                                     totalpf = totalpf+pf;
                                     totalmw = totalmw+mw;
                                     totalmva = totalmva+mva;


                                    tr.setLayoutParams(new TableRow.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    //tr.setBackgroundColor(white);
                                    tr.setBackgroundColor(tablerowcolor);
                                    View v = new View(getContext());
                                    v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                    v.setBackgroundColor(Color.parseColor("#a8a8a8"));

                                    TextView label_short_name = new TextView(getContext());
                                    label_short_name.setTextColor(Color.parseColor("#FFFF99"));
                                    label_short_name.setText(short_name);
                                    label_short_name.setBackgroundColor(tablerowcolor);
                                    label_short_name.setTextSize(14);
                                    label_short_name.setPadding(12, 12, 30, 12);
                                    tr.addView(label_short_name);

                                    TextView label_installed = new TextView(getContext());
                                    label_installed.setText(installed+"");
                                    label_installed.setTextColor(white);
                                    label_installed.setBackgroundColor(tablerowcolor);
                                    label_installed.setPadding(50, 12, 0, 12);
                                    label_installed.setTextSize(14);
                                    tr.addView(label_installed);

                                    TextView label_up = new TextView(getContext());
                                    label_up.setText(up+"");
                                    label_up.setTextColor(white);
                                    label_up.setBackgroundColor(tablerowcolor);
                                    label_up.setPadding(50, 12, 50, 12);
                                    label_up.setTextSize(14);
                                    tr.addView(label_up);

                                    TextView label_down = new TextView(getContext());
                                    label_down.setText(down+"");
                                    label_down.setPadding(50, 12, 50, 12);
                                    label_down.setTextSize(14);
                                    label_down.setBackgroundColor(tablerowcolor);
                                    label_down.setTextColor(white);
                                    tr.addView(label_down);

                                    TextView label_nc = new TextView(getContext());
                                    label_nc.setText(nc+"");
                                    label_nc.setPadding(50, 12, 50, 12);
                                    label_nc.setTextSize(14);
                                    label_nc.setBackgroundColor(tablerowcolor);
                                    label_nc.setTextColor(white);
                                    tr.addView(label_nc);


                                    TextView label_np = new TextView(getContext());
                                    label_np.setText(np+"");
                                    label_np.setPadding(50, 12, 50, 12);
                                    label_np.setTextSize(14);
                                    label_np.setBackgroundColor(tablerowcolor);
                                    label_np.setTextColor(white);
                                    tr.addView(label_np);

                                    TextView label_TOTALl = new TextView(getContext());
                                    label_TOTALl.setText(total+"");
                                    label_TOTALl.setPadding(50, 12, 50, 12);
                                    label_TOTALl.setTextSize(14);
                                    label_TOTALl.setBackgroundColor(tablerowcolor);
                                    label_TOTALl.setTextColor(white);
                                    tr.addView(label_TOTALl);

                                    TextView label_mvah = new TextView(getContext());
                                    label_mvah.setText(df2.format(mvah)+"");
                                    label_mvah.setPadding(50, 12, 0, 12);
                                    label_mvah.setTextSize(14);
                                    label_mvah.setBackgroundColor(tablerowcolor);
                                    label_mvah.setTextColor(white);
                                    tr.addView(label_mvah);

                                    TextView label_pf = new TextView(getContext());
                                    label_pf.setText(pf+"");
                                    label_pf.setPadding(50, 12, 0, 12);
                                    label_pf.setTextSize(14);
                                    label_pf.setBackgroundColor(tablerowcolor);
                                    label_pf.setTextColor(white);
                                    tr.addView(label_pf);

                                    TextView label_mw = new TextView(getContext());
                                    label_mw.setText(df2.format(mw)+"");
                                    label_mw.setPadding(50, 12, 0, 12);
                                    label_mw.setTextSize(14);
                                    label_mw.setBackgroundColor(tablerowcolor);
                                    label_mw.setTextColor(white);
                                    tr.addView(label_mw);

                                    TextView label_mva = new TextView(getContext());
                                    label_mva.setText(df2.format(mva)+"");
                                    label_mva.setPadding(50, 12, 150, 12);
                                    label_mva.setTextSize(14);
                                    label_mva.setBackgroundColor(tablerowcolor);
                                    label_mva.setTextColor(white);
                                    tr.addView(label_mva);

                                    tl.addView(tr, new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tl.addView(v);
                                    final  String ncs=label_nc.getText().toString();
                                    final  String nps=label_np.getText().toString();

                                   /* label_nc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getActivity(), NCFeedersDetailsActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addCategory(Intent.CATEGORY_HOME);
                                            intent.putExtra("substation_name", "Test");
                                            intent.putExtra("feeder_name", "Test");
                                            intent.putExtra("feeder_id", "39eb3374-67f7-47bf-9755-82fb72bb311e");
                                            startActivity(intent);
                                            Toast.makeText(getActivity(),ncs, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    label_np.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getActivity(), NPFeedersDetailsActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addCategory(Intent.CATEGORY_HOME);
                                            intent.putExtra("substation_name", "Test");
                                            intent.putExtra("feeder_name", "Test");
                                            intent.putExtra("feeder_id", "39eb3374-67f7-47bf-9755-82fb72bb311e");
                                            startActivity(intent);
                                            Toast.makeText(getActivity(),ncs, Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getActivity(),nps, Toast.LENGTH_SHORT).show();
                                        }
                                    });*/

                                }
                                //-----------------------------------TOTAL DATA-----------------------------------------------
                                TableRow tr = new TableRow(getContext());


                                tr.setLayoutParams(new TableRow.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                //tr.setBackgroundColor(white);
                                tr.setBackgroundColor(tablerowcolor);
                                View v = new View(getContext());
                                v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v.setBackgroundColor(Color.parseColor("#a8a8a8"));

                                TextView label_short_name = new TextView(getContext());
                                label_short_name.setTextColor(Color.parseColor("#FFFF99"));
                                label_short_name.setText("TOTAL");
                                label_short_name.setBackgroundColor(tablerowcolor);
                                label_short_name.setTextSize(14);
                                label_short_name.setPadding(12, 12, 30, 12);
                                tr.addView(label_short_name);

                                TextView label_installed = new TextView(getContext());
                                label_installed.setText(totalinstalled+"");
                                label_installed.setTextColor(white);
                                label_installed.setBackgroundColor(tablerowcolor);
                                label_installed.setPadding(50, 12, 0, 12);
                                label_installed.setTextSize(14);
                                tr.addView(label_installed);

                                TextView label_up = new TextView(getContext());
                                label_up.setText(totalup+"");
                                label_up.setTextColor(white);
                                label_up.setBackgroundColor(tablerowcolor);
                                label_up.setPadding(50, 12, 50, 12);
                                label_up.setTextSize(14);
                                tr.addView(label_up);

                                TextView label_down = new TextView(getContext());
                                label_down.setText(totaldown+"");
                                label_down.setPadding(50, 12, 50, 12);
                                label_down.setTextSize(14);
                                label_down.setBackgroundColor(tablerowcolor);
                                label_down.setTextColor(white);
                                tr.addView(label_down);

                                TextView label_nc = new TextView(getContext());
                                label_nc.setText(totalnc+"");
                                label_nc.setPadding(50, 12, 50, 12);
                                label_nc.setTextSize(14);
                                label_nc.setBackgroundColor(tablerowcolor);
                                label_nc.setTextColor(white);
                                tr.addView(label_nc);

                                TextView label_np = new TextView(getContext());
                                label_np.setText(totalnp+"");
                                label_np.setPadding(50, 12, 50, 12);
                                label_np.setTextSize(14);
                                label_np.setBackgroundColor(tablerowcolor);
                                label_np.setTextColor(white);
                                tr.addView(label_np);

                                TextView label_TOTALl = new TextView(getContext());
                                label_TOTALl.setText(totaltotal+"");
                                label_TOTALl.setPadding(50, 12, 50, 12);
                                label_TOTALl.setTextSize(14);
                                label_TOTALl.setBackgroundColor(tablerowcolor);
                                label_TOTALl.setTextColor(white);
                                tr.addView(label_TOTALl);

                                TextView label_mvah = new TextView(getContext());
                                label_mvah.setText(df2.format(totalmvah)+"");
                                label_mvah.setPadding(50, 12, 0, 12);
                                label_mvah.setTextSize(14);
                                label_mvah.setBackgroundColor(tablerowcolor);
                                label_mvah.setTextColor(white);
                                tr.addView(label_mvah);

                                TextView label_pf = new TextView(getContext());
                                label_pf.setText(totalpf+"");
                                label_pf.setPadding(50, 12, 0, 12);
                                label_pf.setTextSize(14);
                                label_pf.setBackgroundColor(tablerowcolor);
                                label_pf.setTextColor(white);
                                tr.addView(label_pf);

                                TextView label_mw = new TextView(getContext());
                                label_mw.setText(df2.format(totalmw)+"");
                                label_mw.setPadding(50, 12, 0, 12);
                                label_mw.setTextSize(14);
                                label_mw.setBackgroundColor(tablerowcolor);
                                label_mw.setTextColor(white);
                                tr.addView(label_mw);

                                TextView label_mva = new TextView(getContext());
                                label_mva.setText(df2.format(totalmva)+"");
                                label_mva.setPadding(50, 12, 150, 12);
                                label_mva.setTextSize(14);
                                label_mva.setBackgroundColor(tablerowcolor);
                                label_mva.setTextColor(white);
                                tr.addView(label_mva);

                                tl.addView(tr, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                tl.addView(v);

                                //-----------------------------------------------------------------------------------
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

    public void today_statistics(final String date){
        try{
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if (pd == null) {
                        pd = new ProgressDialog(getContext());
                        pd.setMessage("Please wait.");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                    }
                    pd.show();
                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        //Thread.sleep(1000);
                        String[] parts = date.split("-");
                        String day = parts[0];
                        String month = parts[1]; // 004
                        String year = parts[2];
                        String datee=year+"-"+month+"-"+day;
                        resulttoday = Util.getdata(API.VITAL_STATISTICS+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area+"&type=DAILY&data_access_date="+datee);
                        resulttoday = resulttoday.replaceAll("<[^>]*>", "");
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
                        JSONObject reader = new JSONObject(resulttoday);
                        int rc = reader.getInt("rc");

                        //------------------------------Table----------------------------------------
                        TableLayout tl = (TableLayout) view.findViewById(R.id.today);

                       // super.setContentView(sv);

                        tl.removeAllViews();
                        if (rc == 0) {
                            String daily_month_data = reader.getString("resource");
                            JSONObject daily_month_datsa= new JSONObject(daily_month_data);
                            JSONArray main= daily_month_datsa.getJSONArray("main");
                            try {
                                //---------------------------Table Header-------------------------------------------

                                View v11 = new View(getActivity());
                                v11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v11.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v11);

                                TableRow tr_head = new TableRow(getActivity());
                                tr_head.setBackgroundColor(Color.parseColor("#004f4f"));
                                tr_head.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_name = new TextView(getActivity());
                                if(LoginActivity.access_area.equalsIgnoreCase("UPPCL")) {
                                    label_name.setText("DISCOM" + System.getProperty("line.separator") + "");
                                }else {
                                    label_name.setText(LoginActivity.access_area + System.getProperty("line.separator") + "");
                                }
                                label_name.setPadding(8, 10, 50, 10);
                                label_name.setTextColor(Color.parseColor("#87cefa"));
                                label_name.setTextSize(16);
                                tr_head.addView(label_name);

                                TextView label_inter_count = new TextView(getActivity());
                                label_inter_count.setText("Interruption "+  System.getProperty("line.separator") +"Count");
                                label_inter_count.setTextColor(Color.parseColor("#87cefa"));
                                label_inter_count.setPadding(50, 10, 0, 10);
                                label_inter_count.setTextSize(16);
                                tr_head.addView(label_inter_count);

                                TextView label_inter_duration = new TextView(getActivity());
                                label_inter_duration.setText("Interruption "+  System.getProperty("line.separator") +"Duration");
                                label_inter_duration.setTextColor(Color.parseColor("#87cefa"));
                                label_inter_duration.setTextSize(16);
                                label_inter_duration.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_inter_duration);

                                TextView label_total = new TextView(getActivity());
                                label_total.setText("Energy Unit Losses"+  System.getProperty("line.separator") +"Total (MVAh)");
                                label_total.setTextColor(Color.parseColor("#87cefa"));
                                label_total.setTextSize(16);
                                label_total.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_total);

                                TextView label_total1 = new TextView(getActivity());
                                label_total1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"Amount (RS.)");
                                label_total1.setTextColor(Color.parseColor("#87cefa"));
                                label_total1.setTextSize(16);
                                label_total1.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_total1);

                                TextView labelEUL1= new TextView(getActivity());
                                labelEUL1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"+15% (MVAh)");
                                labelEUL1.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL1.setTextSize(16);
                                labelEUL1.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL1);

                                TextView labelEULAMT1= new TextView(getActivity());
                                labelEULAMT1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"+15% Amount");
                                labelEULAMT1.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT1.setTextSize(16);
                                labelEULAMT1.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEULAMT1);

                                TextView labelEUL2= new TextView(getActivity());
                                labelEUL2.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"0% (MVAh)");
                                labelEUL2.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL2.setTextSize(16);
                                labelEUL2.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL2);

                                TextView labelEULAMT2= new TextView(getActivity());
                                labelEULAMT2.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"0% Amount");
                                labelEULAMT2.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT2.setTextSize(16);
                                labelEULAMT2.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEULAMT2);

                                TextView labelEUL3= new TextView(getActivity());
                                labelEUL3.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"-15% (MVAh)");
                                labelEUL3.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL3.setTextSize(16);
                                labelEUL3.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL3);

                                TextView labelEULAMT3= new TextView(getActivity());
                                labelEULAMT3.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"-15% Amount");
                                labelEULAMT3.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT3.setTextSize(16);
                                labelEULAMT3.setPadding(50, 10, 100, 10);
                                tr_head.addView(labelEULAMT3);

                                tl.addView(tr_head, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));


                                View v1 = new View(getActivity());
                                v1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v1.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v1);


                                for(int i=0; i<main.length(); i++) {
                                    //---------------------------Table Data Row-------------------------------------------
                                    TableRow tr = new TableRow(getContext());

                                    JSONObject objects = main.getJSONObject(i);
                                    String short_name=objects.getString("short_name");
                                    String total_feeder_interuruption=objects.getString("total_feeder_interuruption");
                                    String total_feeder_interuruption_duration=objects.getString("total_feeder_interuruption_duration");
                                    String total_eul_unit=objects.getString("total_eul_unit");
                                    String total_eul_amount=objects.getString("total_eul_amount");
                                    String EUL1=objects.getString("EUL1");
                                    String EUL2=objects.getString("EUL2");
                                    String EUL3=objects.getString("EUL3");
                                    String EUL1_AMOUNT=objects.getString("EUL1_AMOUNT");
                                    String EUL2_AMOUNT=objects.getString("EUL2_AMOUNT");
                                    String EUL3_AMOUNT=objects.getString("EUL3_AMOUNT");

                                    tr.setLayoutParams(new TableRow.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tr.setBackgroundColor(tablerowcolor);
                                    View v = new View(getContext());
                                    v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                    v.setBackgroundColor(Color.parseColor("#a8a8a8"));

                                    TextView labelName = new TextView(getContext());
                                    labelName.setText(short_name);
                                    labelName.setPadding(12, 12, 10, 12);
                                    labelName.setTextSize(14);
                                    labelName.setTextColor(Color.parseColor("#FFFF99"));
                                    tr.addView(labelName);

                                    TextView labelinterruptionCount = new TextView(getContext());
                                    labelinterruptionCount.setText(total_feeder_interuruption);
                                    labelinterruptionCount.setPadding(50, 12, 0, 12);
                                    labelinterruptionCount.setTextSize(14);
                                    labelinterruptionCount.setTextColor(white);
                                    tr.addView(labelinterruptionCount);

                                    TextView labellabelinterruptionDur = new TextView(getContext());
                                    labellabelinterruptionDur.setText(total_feeder_interuruption_duration);
                                    labellabelinterruptionDur.setPadding(50, 12, 0, 12);
                                    labellabelinterruptionDur.setTextSize(14);
                                    labellabelinterruptionDur.setTextColor(white);
                                    tr.addView(labellabelinterruptionDur);

                                    TextView labeltotal_eul_unit = new TextView(getContext());
                                    labeltotal_eul_unit.setText(total_eul_unit);
                                    labeltotal_eul_unit.setTextSize(14);
                                    labeltotal_eul_unit.setPadding(50, 12, 0, 12);
                                    labeltotal_eul_unit.setTextColor(white);
                                    tr.addView(labeltotal_eul_unit);

                                    TextView labeltotal_eul_rs = new TextView(getContext());
                                    labeltotal_eul_rs.setText(total_eul_amount);
                                    labeltotal_eul_rs.setTextSize(14);
                                    labeltotal_eul_rs.setPadding(50, 12, 0, 12);
                                    labeltotal_eul_rs.setTextColor(white);
                                    tr.addView(labeltotal_eul_rs);

                                    TextView labelEUL11 = new TextView(getContext());
                                    labelEUL11.setText(EUL1);
                                    labelEUL11.setTextSize(12);
                                    labelEUL11.setPadding(50, 12, 0, 12);
                                    labelEUL11.setTextColor(white);
                                    tr.addView(labelEUL11);

                                    TextView labelEULAMT11 = new TextView(getContext());
                                    labelEULAMT11.setText(EUL1_AMOUNT);
                                    labelEULAMT11.setTextSize(14);
                                    labelEULAMT11.setPadding(50, 12, 0, 12);
                                    labelEULAMT11.setTextColor(white);
                                    tr.addView(labelEULAMT11);

                                    TextView labelEUL12 = new TextView(getContext());
                                    labelEUL12.setText(EUL2);
                                    labelEUL12.setTextSize(14);
                                    labelEUL12.setPadding(50, 12, 0, 12);
                                    labelEUL12.setTextColor(white);
                                    tr.addView(labelEUL12);

                                    TextView labelEUL112 = new TextView(getContext());
                                    labelEUL112.setText(EUL2_AMOUNT);
                                    labelEUL112.setTextSize(14);
                                    labelEUL112.setPadding(50, 12, 0, 12);
                                    labelEUL112.setTextColor(white);
                                    tr.addView(labelEUL112);

                                    TextView labelEUL13 = new TextView(getContext());
                                    labelEUL13.setText(EUL3);
                                    labelEUL13.setTextSize(14);
                                    labelEUL13.setPadding(50, 12, 0, 12);
                                    labelEUL13.setTextColor(white);
                                    tr.addView(labelEUL13);

                                    TextView labelEUL113 = new TextView(getContext());
                                    labelEUL113.setText(EUL3_AMOUNT);
                                    labelEUL113.setTextSize(14);
                                    labelEUL113.setPadding(50, 12, 100, 12);
                                    labelEUL113.setTextColor(white);
                                    tr.addView(labelEUL113);

                                    tl.addView(tr, new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tl.addView(v);
                                }
                                //---------------Total ROW-----------------------------------
                                JSONObject total= daily_month_datsa.getJSONObject("total");
                                TableRow tr_total = new TableRow(getActivity());
                                tr_total.setBackgroundColor(tablerowcolor);
                                tr_total.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_totall = new TextView(getActivity());
                                label_totall.setText("TOTAL   ");
                                label_totall.setPadding(12, 12, 10, 12);
                                label_totall.setTextSize(14);
                                label_totall.setTextColor(Color.parseColor("#FFFF99"));
                                tr_total.addView(label_totall);

                                TextView label_totalInterupt = new TextView(getActivity());
                                label_totalInterupt.setText(total.getString("totalInterupt"));
                                label_totalInterupt.setPadding(50, 12, 0, 12);
                                label_totalInterupt.setTextSize(14);
                                label_totalInterupt.setTextColor(white);
                                tr_total.addView(label_totalInterupt);

                                TextView label_total3 = new TextView(getActivity());
                                label_total3.setText(total.getString("totalInteruptDuration"));
                                label_total3.setTextColor(white);
                                label_total3.setPadding(50, 12, 0, 12);
                                label_total3.setTextSize(14);
                                tr_total.addView(label_total3);

                                TextView label_toaleul = new TextView(getActivity());
                                label_toaleul.setText(total.getString("toaleul"));
                                label_toaleul.setTextColor(white);
                                label_toaleul.setPadding(50, 12, 0, 12);
                                label_toaleul.setTextSize(14);
                                tr_total.addView(label_toaleul);


                                TextView label_toaleul_amount = new TextView(getActivity());
                                label_toaleul_amount.setText(total.getString("toaleul_amount"));
                                label_toaleul_amount.setTextColor(white);
                                label_toaleul_amount.setPadding(50, 12, 0, 12);
                                label_toaleul_amount.setTextSize(14);
                                tr_total.addView(label_toaleul_amount);

                                TextView label_EUL1 = new TextView(getActivity());
                                label_EUL1.setText(total.getString("EUL1"));
                                label_EUL1.setTextColor(white);
                                label_EUL1.setPadding(50, 12, 0, 12);
                                label_EUL1.setTextSize(14);
                                tr_total.addView(label_EUL1);

                                TextView label_EUL1_AMOUNT = new TextView(getActivity());
                                label_EUL1_AMOUNT.setText(total.getString("EUL1_AMOUNT"));
                                label_EUL1_AMOUNT.setTextColor(white);
                                label_EUL1_AMOUNT.setPadding(50, 12, 0, 12);
                                label_EUL1_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL1_AMOUNT);

                                TextView label_EUL2 = new TextView(getActivity());
                                label_EUL2.setText(total.getString("EUL2"));
                                label_EUL2.setTextColor(white);
                                label_EUL2.setPadding(50, 12, 0, 12);
                                label_EUL2.setTextSize(14);
                                tr_total.addView(label_EUL2);

                                TextView label_EUL2_AMOUNT = new TextView(getActivity());
                                label_EUL2_AMOUNT.setText(total.getString("EUL2_AMOUNT"));
                                label_EUL2_AMOUNT.setTextColor(white);
                                label_EUL2_AMOUNT.setPadding(50, 12, 0, 12);
                                label_EUL2_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL2_AMOUNT);

                                TextView label_EUL3 = new TextView(getActivity());
                                label_EUL3.setText(total.getString("EUL3"));
                                label_EUL3.setTextColor(white);
                                label_EUL3.setPadding(50, 12, 0, 12);
                                label_EUL3.setTextSize(14);
                                tr_total.addView(label_EUL3);

                                TextView label_EUL3_AMOUNT = new TextView(getActivity());
                                label_EUL3_AMOUNT.setText(total.getString("EUL3_AMOUNT"));
                                label_EUL3_AMOUNT.setTextColor(white);
                                label_EUL3_AMOUNT.setPadding(50, 12, 100, 12);
                                label_EUL3_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL3_AMOUNT);


                                tl.addView(tr_total, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                View v111 = new View(getActivity());
                                v111.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v111.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v111);
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
        //-------------------------------------------------
    }

    public void monthly_statistics(final String month_year){
        try{
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if (pd == null) {
                        pd = new ProgressDialog(getContext());
                        pd.setMessage("Please wait.");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                    }
                    pd.show();
                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    try {
                        //Thread.sleep(1000);
                        String[] parts = month_year.split("-");
                        String month = parts[0]; // 004
                        String year = parts[1];
                        String datee=year+"-"+month;
                        resulttoday = Util.getdata(API.VITAL_STATISTICS+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area+"&type=MONTHLY&data_access_date="+datee);
                        resulttoday = resulttoday.replaceAll("<[^>]*>", "");
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
                        JSONObject reader = new JSONObject(resulttoday);
                        int rc = reader.getInt("rc");
                        //------------------------------Table----------------------------------------
                        TableLayout tl = (TableLayout) view.findViewById(R.id.monthly);
                        tl.removeAllViews();
                        if (rc == 0) {
                            String daily_month_data = reader.getString("resource");
                            JSONObject daily_month_datsa= new JSONObject(daily_month_data);
                            JSONArray main= daily_month_datsa.getJSONArray("main");
                            try {
                                //---------------------------Table Header-------------------------------------------
                                View v1 = new View(getActivity());
                                v1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v1.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v1);

                                TableRow tr_head = new TableRow(getActivity());
                                tr_head.setBackgroundColor(Color.parseColor("#004f4f"));
                                tr_head.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_name = new TextView(getActivity());
                                if(LoginActivity.access_area.equalsIgnoreCase("UPPCL")) {
                                    label_name.setText("DISCOM" + System.getProperty("line.separator") + "");
                                }else {
                                    label_name.setText(LoginActivity.access_area + System.getProperty("line.separator") + "");
                                }
                                label_name.setPadding(8, 10, 50, 10);
                                label_name.setTextColor(Color.parseColor("#87cefa"));
                                label_name.setTextSize(16);
                                tr_head.addView(label_name);

                                TextView label_inter_count = new TextView(getActivity());
                                label_inter_count.setText("Interruption "+  System.getProperty("line.separator") +"Count");
                                label_inter_count.setTextColor(Color.parseColor("#87cefa"));
                                label_inter_count.setPadding(50, 10, 0, 10);
                                label_inter_count.setTextSize(16);
                                tr_head.addView(label_inter_count);

                                TextView label_inter_duration = new TextView(getActivity());
                                label_inter_duration.setText("Interruption "+  System.getProperty("line.separator") +"Duration");
                                label_inter_duration.setTextColor(Color.parseColor("#87cefa"));
                                label_inter_duration.setTextSize(16);
                                label_inter_duration.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_inter_duration);

                                TextView label_total = new TextView(getActivity());
                                label_total.setText("Energy Unit Losses"+  System.getProperty("line.separator") +"Total (MVAh)");
                                label_total.setTextColor(Color.parseColor("#87cefa"));
                                label_total.setTextSize(16);
                                label_total.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_total);

                                TextView label_total1 = new TextView(getActivity());
                                label_total1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"Amount (RS.)");
                                label_total1.setTextColor(Color.parseColor("#87cefa"));
                                label_total1.setTextSize(16);
                                label_total1.setPadding(50, 10, 0, 10);
                                tr_head.addView(label_total1);

                                TextView labelEUL1= new TextView(getActivity());
                                labelEUL1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"+15% (MVAh)");
                                labelEUL1.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL1.setTextSize(16);
                                labelEUL1.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL1);

                                TextView labelEULAMT1= new TextView(getActivity());
                                labelEULAMT1.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"+15% Amount");
                                labelEULAMT1.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT1.setTextSize(16);
                                labelEULAMT1.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEULAMT1);

                                TextView labelEUL2= new TextView(getActivity());
                                labelEUL2.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"0% (MVAh)");
                                labelEUL2.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL2.setTextSize(16);
                                labelEUL2.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL2);

                                TextView labelEULAMT2= new TextView(getActivity());
                                labelEULAMT2.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"0% Amount");
                                labelEULAMT2.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT2.setTextSize(16);
                                labelEULAMT2.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEULAMT2);

                                TextView labelEUL3= new TextView(getActivity());
                                labelEUL3.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"-15% (MVAh)");
                                labelEUL3.setTextColor(Color.parseColor("#87cefa"));
                                labelEUL3.setTextSize(16);
                                labelEUL3.setPadding(50, 10, 0, 10);
                                tr_head.addView(labelEUL3);

                                TextView labelEULAMT3= new TextView(getActivity());
                                labelEULAMT3.setText("Energy Unit Losses "+  System.getProperty("line.separator") +"-15% Amount");
                                labelEULAMT3.setTextColor(Color.parseColor("#87cefa"));
                                labelEULAMT3.setTextSize(16);
                                labelEULAMT3.setPadding(50, 10, 100, 10);
                                tr_head.addView(labelEULAMT3);

                                tl.addView(tr_head, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                View v11 = new View(getActivity());
                                v11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v11.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v11);

                                for(int i=0; i<main.length(); i++) {
                                    //---------------------------Table Data Row-------------------------------------------
                                    int white = Color.parseColor("#ffffff");
                                    TableRow tr = new TableRow(getContext());

                                    JSONObject objects = main.getJSONObject(i);
                                    String short_name=objects.getString("short_name");
                                    String total_feeder_interuruption=objects.getString("total_feeder_interuruption");
                                    String total_feeder_interuruption_duration=objects.getString("total_feeder_interuruption_duration");
                                    String total_eul_unit=objects.getString("total_eul_unit");
                                    String total_eul_amount=objects.getString("total_eul_amount");
                                    String EUL1=objects.getString("EUL1");
                                    String EUL2=objects.getString("EUL2");
                                    String EUL3=objects.getString("EUL3");
                                    String EUL1_AMOUNT=objects.getString("EUL1_AMOUNT");
                                    String EUL2_AMOUNT=objects.getString("EUL2_AMOUNT");
                                    String EUL3_AMOUNT=objects.getString("EUL3_AMOUNT");

                                    tr.setLayoutParams(new TableRow.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tr.setBackgroundColor(tablerowcolor);

                                    View v = new View(getContext());
                                    v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                    v.setBackgroundColor(Color.parseColor("#a8a8a8"));

                                    TextView labelName = new TextView(getContext());
                                    labelName.setText(short_name);
                                    labelName.setPadding(12, 12, 10, 12);
                                    labelName.setTextSize(14);
                                    labelName.setTextColor(Color.parseColor("#FFFF99"));
                                    tr.addView(labelName);

                                    TextView labelinterruptionCount = new TextView(getContext());
                                    labelinterruptionCount.setText(total_feeder_interuruption);
                                    labelinterruptionCount.setPadding(50, 12, 0, 12);
                                    labelinterruptionCount.setTextSize(14);
                                    labelinterruptionCount.setTextColor(white);
                                    tr.addView(labelinterruptionCount);

                                    TextView labellabelinterruptionDur = new TextView(getContext());
                                    labellabelinterruptionDur.setText(total_feeder_interuruption_duration);
                                    labellabelinterruptionDur.setPadding(50, 12, 0, 12);
                                    labellabelinterruptionDur.setTextSize(14);
                                    labellabelinterruptionDur.setTextColor(white);
                                    tr.addView(labellabelinterruptionDur);

                                    TextView labeltotal_eul_unit = new TextView(getContext());
                                    labeltotal_eul_unit.setText(total_eul_unit);
                                    labeltotal_eul_unit.setTextSize(14);
                                    labeltotal_eul_unit.setPadding(50, 12, 0, 12);
                                    labeltotal_eul_unit.setTextColor(white);
                                    tr.addView(labeltotal_eul_unit);

                                    TextView labeltotal_eul_rs = new TextView(getContext());
                                    labeltotal_eul_rs.setText(total_eul_amount);
                                    labeltotal_eul_rs.setTextSize(14);
                                    labeltotal_eul_rs.setPadding(50, 12, 0, 12);
                                    labeltotal_eul_rs.setTextColor(white);
                                    tr.addView(labeltotal_eul_rs);

                                    TextView labelEUL11 = new TextView(getContext());
                                    labelEUL11.setText(EUL1);
                                    labelEUL11.setTextSize(14);
                                    labelEUL11.setPadding(50, 12, 0, 12);
                                    labelEUL11.setTextColor(white);
                                    tr.addView(labelEUL11);

                                    TextView labelEULAMT11 = new TextView(getContext());
                                    labelEULAMT11.setText(EUL1_AMOUNT);
                                    labelEULAMT11.setTextSize(14);
                                    labelEULAMT11.setPadding(50, 12, 0, 12);
                                    labelEULAMT11.setTextColor(white);
                                    tr.addView(labelEULAMT11);

                                    TextView labelEUL12 = new TextView(getContext());
                                    labelEUL12.setText(EUL2);
                                    labelEUL12.setTextSize(14);
                                    labelEUL12.setPadding(50, 12, 0, 12);
                                    labelEUL12.setTextColor(white);
                                    tr.addView(labelEUL12);

                                    TextView labelEUL112 = new TextView(getContext());
                                    labelEUL112.setText(EUL2_AMOUNT);
                                    labelEUL112.setTextSize(14);
                                    labelEUL112.setPadding(50, 12, 0, 12);
                                    labelEUL112.setTextColor(white);
                                    tr.addView(labelEUL112);

                                    TextView labelEUL13 = new TextView(getContext());
                                    labelEUL13.setText(EUL3);
                                    labelEUL13.setTextSize(14);
                                    labelEUL13.setPadding(50, 12, 0, 12);
                                    labelEUL13.setTextColor(white);
                                    tr.addView(labelEUL13);

                                    TextView labelEUL113 = new TextView(getContext());
                                    labelEUL113.setText(EUL3_AMOUNT);
                                    labelEUL113.setTextSize(14);
                                    labelEUL113.setPadding(50, 12, 100, 12);
                                    labelEUL113.setTextColor(white);
                                    tr.addView(labelEUL113);

                                    tl.addView(tr, new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.FILL_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tl.addView(v);
                                }
                                //---------------Total ROW-----------------------------------
                                JSONObject total= daily_month_datsa.getJSONObject("total");
                                TableRow tr_total = new TableRow(getActivity());

                                tr_total.setBackgroundColor(tablerowcolor);
                                tr_total.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                                TextView label_total41 = new TextView(getActivity());
                                label_total41.setText("TOTAL   ");
                                label_total41.setPadding(8, 12, 50, 12);
                                label_total41.setTextSize(14);
                                label_total41.setTextColor(Color.parseColor("#FFFF99"));
                                tr_total.addView(label_total41);

                                TextView label_total2 = new TextView(getActivity());
                                label_total2.setText(total.getString("totalInterupt"));
                                label_total2.setPadding(50, 12, 0, 12);
                                label_total2.setTextSize(14);
                                label_total2.setTextColor(white);
                                tr_total.addView(label_total2);

                                TextView label_total3 = new TextView(getActivity());
                                label_total3.setText(total.getString("totalInteruptDuration"));
                                label_total3.setTextColor(white);
                                label_total3.setPadding(50, 12, 0, 12);
                                label_total3.setTextSize(14);
                                tr_total.addView(label_total3);

                                TextView label_total4 = new TextView(getActivity());
                                label_total4.setText(total.getString("toaleul"));
                                label_total4.setTextColor(white);
                                label_total4.setPadding(50, 12, 0, 12);
                                label_total4.setTextSize(14);

                                TextView label_toaleul_amount = new TextView(getActivity());
                                label_toaleul_amount.setText(total.getString("toaleul_amount"));
                                label_toaleul_amount.setTextColor(white);
                                label_toaleul_amount.setPadding(50, 12, 0, 12);
                                label_toaleul_amount.setTextSize(14);
                                tr_total.addView(label_toaleul_amount);

                                TextView label_EUL1 = new TextView(getActivity());
                                label_EUL1.setText(total.getString("EUL1"));
                                label_EUL1.setTextColor(white);
                                label_EUL1.setPadding(50, 12, 0, 12);
                                label_EUL1.setTextSize(14);
                                tr_total.addView(label_EUL1);

                                TextView label_EUL1_AMOUNT = new TextView(getActivity());
                                label_EUL1_AMOUNT.setText(total.getString("EUL1_AMOUNT"));
                                label_EUL1_AMOUNT.setTextColor(white);
                                label_EUL1_AMOUNT.setPadding(50, 12, 0, 12);
                                label_EUL1_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL1_AMOUNT);

                                TextView label_EUL2 = new TextView(getActivity());
                                label_EUL2.setText(total.getString("EUL2"));
                                label_EUL2.setTextColor(white);
                                label_EUL2.setPadding(50, 12, 0, 12);
                                label_EUL2.setTextSize(14);
                                tr_total.addView(label_EUL2);

                                TextView label_EUL2_AMOUNT = new TextView(getActivity());
                                label_EUL2_AMOUNT.setText(total.getString("EUL2_AMOUNT"));
                                label_EUL2_AMOUNT.setTextColor(white);
                                label_EUL2_AMOUNT.setPadding(50, 12, 0, 12);
                                label_EUL2_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL2_AMOUNT);

                                TextView label_EUL3 = new TextView(getActivity());
                                label_EUL3.setText(total.getString("EUL3"));
                                label_EUL3.setTextColor(white);
                                label_EUL3.setPadding(50, 12, 0, 12);
                                label_EUL3.setTextSize(14);
                                tr_total.addView(label_EUL3);

                                TextView label_EUL3_AMOUNT = new TextView(getActivity());
                                label_EUL3_AMOUNT.setText(total.getString("EUL3_AMOUNT"));
                                label_EUL3_AMOUNT.setTextColor(white);
                                label_EUL3_AMOUNT.setPadding(50, 12, 100, 12);
                                label_EUL3_AMOUNT.setTextSize(14);
                                tr_total.addView(label_EUL3_AMOUNT);

                                tr_total.addView(label_total4);
                                tl.addView(tr_total, new TableLayout.LayoutParams(
                                        ViewGroup.LayoutParams.FILL_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                View v12 = new View(getActivity());
                                v12.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                                v12.setBackgroundColor(Color.parseColor("#a8a8a8"));
                                tl.addView(v12);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            task.execute((Void[])null);
        }catch (Exception e){
            e.printStackTrace();
        }
        //-------------------------------------------------
    }

}
