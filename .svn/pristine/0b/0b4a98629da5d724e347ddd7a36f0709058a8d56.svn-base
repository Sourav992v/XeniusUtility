package radius.xenius.sems.feeder;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 *Created By Pratima Singh
 * 10-07-2018
 */
public class ItemFourFragment extends Fragment {

    Spinner spnr_discom;
    Spinner spnr_zone;
    Spinner spnr_circle;
    Spinner spnr_division;
    Spinner spnr_substation;
    Spinner spnr_feeder;
    Spinner spnr_outage_type;
    String discom_id = "";
    String zone_id = "";
    String circle_id = "";
    String division_id = "";
    String substation_id = "";
    String outage_type_id="";
    ProgressDialog progressDialog;
    LinkedHashMap<String, String> listdata;
    View view;
    View view2 = null;
    RelativeLayout relative_spnr_discom;
    RelativeLayout relative_spnr_zone;
    RelativeLayout relative_spnr_circle;
    RelativeLayout relative_spnr_division;
    TextView discomText;
    TextView zoneText;
    TextView circleText;
    TextView divisionText;
    String feeder_id="";

    private int day;
    private int month;
    private int year;
    ImageButton datebtn;
    EditText dateText;
    Button viewBtn;
    String date;

    public static ItemFourFragment newInstance() {
        ItemFourFragment fragment = new ItemFourFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item_four, container, false);
        discomText = (TextView) view.findViewById(R.id.discomText);
        relative_spnr_discom = (RelativeLayout) view.findViewById(R.id.relative_spnr_discom);
        spnr_discom = (Spinner) view.findViewById(R.id.spnr_discom);
        zoneText = (TextView) view.findViewById(R.id.zoneText);
        relative_spnr_zone = (RelativeLayout) view.findViewById(R.id.relative_spnr_zone);
        spnr_zone = (Spinner) view.findViewById(R.id.spnr_zone);
        circleText = (TextView) view.findViewById(R.id.circleText);
        relative_spnr_circle = (RelativeLayout) view.findViewById(R.id.relative_spnr_circle);
        spnr_circle = (Spinner) view.findViewById(R.id.spnr_circle);
        relative_spnr_division = (RelativeLayout) view.findViewById(R.id.relative_spnr_division);
        divisionText = (TextView) view.findViewById(R.id.divisionText);
        spnr_division = (Spinner) view.findViewById(R.id.spnr_division);
        spnr_substation = (Spinner) view.findViewById(R.id.spnr_substation);
        spnr_feeder = (Spinner) view.findViewById(R.id.spnr_feeder);
        spnr_outage_type = (Spinner) view.findViewById(R.id.spnr_outage_type);
        viewBtn = (Button) view.findViewById(R.id.btnView) ;
        listdata = new LinkedHashMap<String, String>();

        //---------------------date picker-------------------------
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        dateText = (EditText) view.findViewById(R.id.dateText);

        dateText.setText(day + "-" + (month + 1) + "-" + year);

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
                                dateText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                //today_statistics(dateTxt.getText().toString());
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        outage_type_list();

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outage_details();
            }
        });

        switch (LoginActivity.access_area) {
            case "ZONE":
                relative_spnr_discom.setVisibility(View.GONE);
                discomText.setVisibility(View.GONE);
                spnr_discom.setVisibility(View.GONE);
                zone(discom_id);
                break;

            case "CIRCLE":
                relative_spnr_discom.setVisibility(View.GONE);
                discomText.setVisibility(View.GONE);
                spnr_discom.setVisibility(View.GONE);
                relative_spnr_zone.setVisibility(View.GONE);
                zoneText.setVisibility(View.GONE);
                spnr_zone.setVisibility(View.GONE);
                circle(zone_id);
                break;

            case "DIVISION":
                relative_spnr_discom.setVisibility(View.GONE);
                discomText.setVisibility(View.GONE);
                spnr_discom.setVisibility(View.GONE);
                relative_spnr_zone.setVisibility(View.GONE);
                zoneText.setVisibility(View.GONE);
                spnr_zone.setVisibility(View.GONE);
                relative_spnr_circle.setVisibility(View.GONE);
                circleText.setVisibility(View.GONE);
                spnr_circle.setVisibility(View.GONE);
                division(circle_id);
                break;

            case "SUBSTATION":
                relative_spnr_discom.setVisibility(View.GONE);
                discomText.setVisibility(View.GONE);
                spnr_discom.setVisibility(View.GONE);
                relative_spnr_zone.setVisibility(View.GONE);
                zoneText.setVisibility(View.GONE);
                spnr_zone.setVisibility(View.GONE);
                relative_spnr_circle.setVisibility(View.GONE);
                circleText.setVisibility(View.GONE);
                spnr_circle.setVisibility(View.GONE);
                relative_spnr_division.setVisibility(View.GONE);
                divisionText.setVisibility(View.GONE);
                spnr_division.setVisibility(View.GONE);
                substation(division_id);
                break;
            default:
                discom();
        }

        spnr_discom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        discom_id = key;
                        zone(key);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_zone.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        zone_id = key;
                        circle(key);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_circle.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        circle_id = key;
                        division(key);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_division.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        division_id = key;
                        substation(key);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_substation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        substation_id = key;
                        feeder(substation_id);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_feeder.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        feeder_id = key;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        spnr_outage_type.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        outage_type_id = key;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        return  view;
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public void discom() {
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait.");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        resultt = Util.getdata(API.ACCESS_LEVEL + "login_id=" + LoginActivity.login_id + "&password=" + LoginActivity.password + "&access_level=" + LoginActivity.access_area);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                        if (result.getInt("rc") == 0 && "Success".equalsIgnoreCase(result.getString("message"))) {
                            JSONArray jsonArray = result.getJSONArray("resource");
                            ArrayList<String> items = new ArrayList<String>();
                            //listdata.put("select","Select");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_data = jsonArray.getJSONObject(i);
                                listdata.put(json_data.getString("id"), json_data.getString("name"));
                            }

                            for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                itemList.add(new StringWithTag(value, key));
                            }

                            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                    R.layout.row, R.id.name, itemList) {
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                                        TextView name = (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name = (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }
                            };
                            spnr_discom.setAdapter(spinnerAdapter);
                            spnr_discom.setPrompt("Select");
                            listdata.clear();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        Toast.makeText(getActivity(), " Network Problem Occured", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
            //----------------------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zone(final String discom_id) {
        if (discom_id.equalsIgnoreCase("select") || discom_id.equalsIgnoreCase("")) {
            spnr_zone.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Discom !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
            spnr_zone.setAdapter(spinnerAdapter);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        if (LoginActivity.access_area.equalsIgnoreCase("ZONE")) {
                            resultt = Util.getdata(API.ACCESS_LEVEL + "login_id=" + LoginActivity.login_id + "&password=" + LoginActivity.password + "&access_level=" + LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.ZONES + "discom_id=" + discom_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject = new JSONObject(resultt.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (result.getInt("rc") == 0 && "Success".equalsIgnoreCase(result.getString("message"))) {
                            String resourc = result.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = result.getJSONArray("resource");
                                //listdata.put("select", "Select");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                    listdata.put(json_data.getString("id"), json_data.getString("name"));
                                }
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    itemList.add(new StringWithTag(value, key));
                                }
                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList) {
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#003f3f"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }
                                };
                                spnr_zone.setAdapter(spinnerAdapter);
                                spnr_zone.setPrompt("Select");
                                listdata.clear();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
            //----------------------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void circle(final String zone_id) {
        if (zone_id.equalsIgnoreCase("select") || zone_id.equalsIgnoreCase("")) {
            spnr_circle.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Zone !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
            spnr_circle.setAdapter(spinnerAdapter);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        if (LoginActivity.access_area.equalsIgnoreCase("CIRCLE")) {
                            resultt = Util.getdata(API.ACCESS_LEVEL + "login_id=" + LoginActivity.login_id + "&password=" + LoginActivity.password + "&access_level=" + LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.CIRCLE + "zone_id=" + zone_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject = new JSONObject(resultt.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (result.getInt("rc") == 0 && "Success".equalsIgnoreCase(result.getString("message"))) {
                            String resourc = result.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = result.getJSONArray("resource");
                                // listdata.put("select", "Select");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                    listdata.put(json_data.getString("id"), json_data.getString("name"));
                                }
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    itemList.add(new StringWithTag(value, key));
                                }

                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList) {
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#003f3f"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }
                                };
                                spnr_circle.setAdapter(spinnerAdapter);
                                spnr_circle.setPrompt("Select");
                                listdata.clear();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void division(final String circle_id) {
        if (circle_id.equalsIgnoreCase("select") || circle_id.equalsIgnoreCase("")) {
            spnr_division.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Circle !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
            spnr_division.setAdapter(spinnerAdapter);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        if (LoginActivity.access_area.equalsIgnoreCase("DIVISION")) {
                            resultt = Util.getdata(API.ACCESS_LEVEL + "login_id=" + LoginActivity.login_id + "&password=" + LoginActivity.password + "&access_level=" + LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.DIVISION + "circle_id=" + circle_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject = new JSONObject(resultt.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (result.getInt("rc") == 0 && "Success".equalsIgnoreCase(result.getString("message"))) {
                            String resourc = result.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = result.getJSONArray("resource");
                                //listdata.put("select", "Select");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                    listdata.put(json_data.getString("id"), json_data.getString("name"));
                                }
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    itemList.add(new StringWithTag(value, key));
                                }
                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList) {
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#003f3f"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }
                                };
                                spnr_division.setAdapter(spinnerAdapter);
                                spnr_division.setPrompt("Select");
                                listdata.clear();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured !!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void substation(final String division_id) {
        if (division_id.equalsIgnoreCase("select") || division_id.equalsIgnoreCase("")) {
            spnr_substation.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Division !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
            spnr_substation.setAdapter(spinnerAdapter);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        if (LoginActivity.access_area.equalsIgnoreCase("SUBSTATION")) {
                            resultt = Util.getdata(API.ACCESS_LEVEL + "login_id=" + LoginActivity.login_id + "&password=" + LoginActivity.password + "&access_level=" + LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.SUBSTAION + "division_id=" + division_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }

                    try {
                        if (result.getInt("rc") == 0 && "Success".equalsIgnoreCase(result.getString("message"))) {
                            String resourc = result.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = result.getJSONArray("resource");
                                //listdata.put("select", "Select");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                    listdata.put(json_data.getString("id"), json_data.getString("name"));
                                }
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    itemList.add(new StringWithTag(value, key));
                                }

                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList) {
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#003f3f"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }
                                };
                                spnr_substation.setAdapter(spinnerAdapter);
                                spnr_substation.setPrompt("Select");
                                listdata.clear();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured !!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void feeder(final String substation_id) {
        if (substation_id.equalsIgnoreCase("select") || substation_id.equalsIgnoreCase("")) {
            spnr_feeder.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Division !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
            spnr_feeder.setAdapter(spinnerAdapter);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        resultt = Util.getdata(API.FEEDER + "substation_id=" + substation_id);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject resultt) {
                    if (progressDialog!=null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (resultt.getInt("rc") == 0) {
                            String resourc = resultt.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {

                                JSONArray jsonArray = resultt.getJSONArray("resource");
                                if (jsonArray.length() > 0) {
                                    // listdata.put("select", "Select");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_data = jsonArray.getJSONObject(i);
                                        listdata.put(json_data.getString("id"), json_data.getString("name"));
                                    }
                                    List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                    for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                        String key = entry.getKey();
                                        String value = entry.getValue();
                                        itemList.add(new StringWithTag(value, key));
                                    }
                                    System.out.println("itemList" + itemList);

                                    ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                            R.layout.row, R.id.name, itemList) {
                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            if (position % 2 == 0) { // we're on an even row
                                                view.setBackgroundColor(Color.parseColor("#003f3f"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#ffffff"));
                                            } else {
                                                view.setBackgroundColor(Color.parseColor("#ffffff"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#000000"));
                                            }
                                            return view;
                                        }
                                    };
                                    spnr_feeder.setAdapter(spinnerAdapter);
                                    spnr_feeder.setPrompt("Select");
                                    listdata.clear();
                                } else {
                                    spnr_feeder.setAdapter(null);
                                    List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                    itemList.add(new StringWithTag("Not found feeder for selected Substation !!!", "select"));
                                    ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                            R.layout.row, R.id.name, itemList) {
                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            if (position % 2 == 0) { // we're on an even row
                                                view.setBackgroundColor(Color.parseColor("#003f3f"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#ffffff"));
                                            } else {
                                                view.setBackgroundColor(Color.parseColor("#ffffff"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#000000"));
                                            }
                                            return view;
                                        }
                                    };
                                    spnr_feeder.setAdapter(spinnerAdapter);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (resultt == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured !!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outage_type_list() {
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt = "";
                    try {
                        resultt = Util.getdata(API.AVG_OUTAGE_TYPE_LIST);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject resultt) {
                    try {
                        if (resultt.getInt("rc") == 0) {
                            String resourc = resultt.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {

                                JSONArray jsonArray = resultt.getJSONArray("resource");
                                if (jsonArray.length() > 0) {
                                    // listdata.put("select", "Select");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_data = jsonArray.getJSONObject(i);
                                        listdata.put(json_data.getString("key"), json_data.getString("value"));
                                    }
                                    List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                    for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                        String key = entry.getKey();
                                        String value = entry.getValue();
                                        itemList.add(new StringWithTag(value, key));
                                    }
                                    System.out.println("itemList" + itemList);

                                    ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                            R.layout.row, R.id.name, itemList) {
                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            if (position % 2 == 0) { // we're on an even row
                                                view.setBackgroundColor(Color.parseColor("#003f3f"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#ffffff"));
                                            } else {
                                                view.setBackgroundColor(Color.parseColor("#ffffff"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#000000"));
                                            }
                                            return view;
                                        }
                                    };
                                    spnr_outage_type.setAdapter(spinnerAdapter);
                                    spnr_outage_type.setPrompt("Select");
                                    listdata.clear();
                                } else {
                                    spnr_outage_type.setAdapter(null);
                                    List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                    itemList.add(new StringWithTag("Not Found Outage Type", "select"));
                                    ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                            R.layout.row, R.id.name, itemList) {
                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            if (position % 2 == 0) { // we're on an even row
                                                view.setBackgroundColor(Color.parseColor("#003f3f"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#ffffff"));
                                            } else {
                                                view.setBackgroundColor(Color.parseColor("#ffffff"));
                                                TextView name = (TextView) view.findViewById(R.id.name);
                                                name.setTextColor(Color.parseColor("#000000"));
                                            }
                                            return view;
                                        }
                                    };
                                    spnr_feeder.setAdapter(spinnerAdapter);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (resultt == null) {
                        Toast.makeText(getActivity(), "Network Problem Occured !!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void outage_details(){
        date=dateText.getText().toString();
        ScrollView scrollview=(ScrollView) view.findViewById(R.id.scroll);
        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }

        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    if(progressDialog ==null) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait.");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    String resultt="";
                    try {
                        String[] parts = date.split("-");
                        String day = parts[0];
                        String month = parts[1];
                        String year = parts[2];
                        String datee=year+"-"+month+"-"+day;

                        resultt = Util.getdata(API.AVG_OUTAGE_DETAILS+"login_id="+LoginActivity.login_id+"&password="+ LoginActivity.password+"&access_level="+LoginActivity.access_area+"&feeder_id="+feeder_id+"&FromDate="+datee+"&ToDate="+datee+"&outageType="+outage_type_id);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject=new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject resultt) {
                    if (progressDialog!=null) {
                        progressDialog.dismiss();                    }

                    try {

                        if(resultt.getInt("rc")==0) {
                            //--------------------------Feeder UP Down---------------------------------------------

                            LinearLayout main = (LinearLayout) view.findViewById(R.id.avg_outageLayout);
                            if(view2==null) {
                                view2 = getActivity().getLayoutInflater().inflate(R.layout.uppcl_access_level_outage, main, false);
                                main.addView(view2);
                            }
                            TextView event_dateTxt = (TextView) view.findViewById(R.id.event_date);
                            TextView feeder_countTxt = (TextView) view.findViewById(R.id.feeder_count);
                            TextView outage_countTxt = (TextView) view.findViewById(R.id.outage_count);
                            TextView total_Outage_DurationTxt = (TextView) view.findViewById(R.id.outage_duration);
                            JSONArray resource=resultt.getJSONArray("resource");
                            if(resource.length()>0) {
                                JSONObject resourcee = resource.getJSONObject(0);
                                String event_date = resourcee.getString("event_date");
                                String feeder_count = resourcee.getString("feeder_count");
                                String outage_count = resourcee.getString("Outage_count");
                                String total_Outage_Duration = resourcee.getString("Total_Outage_Duration");

                                //----------------------set text in text fields-------------------------------------------------
                                event_dateTxt.setText(event_date);
                                feeder_countTxt.setText(feeder_count);
                                outage_countTxt.setText(outage_count);
                                total_Outage_DurationTxt.setText(total_Outage_Duration);

                            } else {
                                feeder_countTxt.setText("0");
                                event_dateTxt.setText("00:00:00");
                                outage_countTxt.setText("0");
                                total_Outage_DurationTxt.setText("00:00:00");
                            }
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (resultt==null){
                        Toast.makeText(getActivity(),"Network Problem Occured",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
            //----------------------------------------------------------------------------------------
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
