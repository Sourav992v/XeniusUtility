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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JeSdoAvgOutageFragment extends Fragment {

    Spinner spnr_discom;
    Spinner spnr_zone;
    Spinner spnr_circle;
    Spinner spnr_division;
    Spinner spnr_substation;
    Spinner spnr_feeder;
    String discom_id="";
    String zone_id="";
    String circle_id="";
    String division_id="";
    String substation_id="";
    String feeder_id="";
    ProgressDialog progressDialog;
    LinkedHashMap<String, String> listdata;
    private int day;
    private int month;
    private int year;
    ImageButton datebtn;
    EditText dateTxt;
    View view;

    public static JeSdoAvgOutageFragment newInstance() {
        JeSdoAvgOutageFragment fragment = new JeSdoAvgOutageFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_je_sdo_avg_outage, container, false);

        spnr_discom = (Spinner) view.findViewById(R.id.spnr_discom);
        spnr_zone = (Spinner) view.findViewById(R.id.spnr_zone);
        spnr_circle = (Spinner) view.findViewById(R.id.spnr_circle);
        spnr_division = (Spinner) view.findViewById(R.id.spnr_division);
        spnr_substation = (Spinner) view.findViewById(R.id.spnr_substation);
        spnr_feeder = (Spinner) view.findViewById(R.id.spnr_feeder);
        listdata = new LinkedHashMap<String, String>();

        //---------------------date picker-------------------------
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dateTxt = (EditText) view.findViewById(R.id.dateText);
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
                                //today_statistics(dateTxt.getText().toString());
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        //-----------------call discom api----------------
        discom();

        spnr_discom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        discom_id=key;
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
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        zone_id=key;
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
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        circle_id=key;
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
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        division_id=key;
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
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        substation_id=key;
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
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        feeder_id=key;

                        if(!feeder_id.equalsIgnoreCase("select")) {
                            //chart(feeder_id);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void discom(){
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait.");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        System.out.println("API.DISCOMS: "+API.DISCOMS);
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpGet request = new HttpGet(API.DISCOMS);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog!=null) {
                        progressDialog.dismiss();
                    }
                    try {
                        List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                        if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                            JSONArray jsonArray= result.getJSONArray("data");
                            ArrayList<String> items = new ArrayList<String>();
                            listdata.put("select","Select");
                            for(int i=0; i < jsonArray.length() ; i++) {
                                JSONObject json_data = jsonArray.getJSONObject(i);
                                listdata.put(json_data.getString("id"), json_data.getString("name"));
                            }

                            for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                itemList.add(new StringWithTag(value, key));
                            }

                            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                    R.layout.row, R.id.name, itemList){
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }};
                            spnr_discom.setAdapter(spinnerAdapter);
                            spnr_discom.setPrompt("Select");
                            listdata.clear();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
                        Toast.makeText(getActivity()," Network Problem Occured",Toast.LENGTH_LONG).show();
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

    public void zone(final String discom_id){
        if (discom_id.equalsIgnoreCase("select") || discom_id.equalsIgnoreCase("")){
            spnr_zone.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Discom !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }};;
            spnr_zone.setAdapter(spinnerAdapter);
        }
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait.");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.ZONES+"discom_id="+discom_id);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println(result.toString());
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result) {
                    if (progressDialog!=null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                            JSONArray jsonArray= result.getJSONArray("data");
                            listdata.put("select","Select");
                            for(int i=0; i < jsonArray.length() ; i++) {
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
                                    R.layout.row, R.id.name, itemList){
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }};
                            spnr_zone.setAdapter(spinnerAdapter);
                            spnr_zone.setPrompt("Select");
                            listdata.clear();

                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
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

    public void circle(final String zone_id){
        if (zone_id.equalsIgnoreCase("select") || zone_id.equalsIgnoreCase("")){
            spnr_circle.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Zone !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }};;
            spnr_circle.setAdapter(spinnerAdapter);
        }
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                }
                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.CIRCLE+"zone_id="+zone_id);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println(result.toString());
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result) {
                    try {
                        if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                            JSONArray jsonArray= result.getJSONArray("data");
                            listdata.put("select","Select");
                            for(int i=0; i < jsonArray.length() ; i++) {
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
                                    R.layout.row, R.id.name, itemList){
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }};
                            spnr_circle.setAdapter(spinnerAdapter);
                            spnr_circle.setPrompt("Select");
                            listdata.clear();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
                        Toast.makeText(getActivity(),"Network Problem Occured",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void division(final String circle_id){
        if (circle_id.equalsIgnoreCase("select") || circle_id.equalsIgnoreCase("")){
            spnr_division.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Circle !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }};
            spnr_division.setAdapter(spinnerAdapter);
        }
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.DIVISION+"circle_id="+circle_id);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println(result.toString());
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result) {
                    try {
                        if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                            JSONArray jsonArray= result.getJSONArray("data");
                            listdata.put("select","Select");
                            for(int i=0; i < jsonArray.length() ; i++) {
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
                                    R.layout.row, R.id.name, itemList){
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }};;
                            spnr_division.setAdapter(spinnerAdapter);
                            spnr_division.setPrompt("Select");
                            listdata.clear();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
                        Toast.makeText(getActivity(),"Network Problem Occured !!!",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void substation(final String division_id){
        if (division_id.equalsIgnoreCase("select") || division_id.equalsIgnoreCase("")){
            spnr_substation.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Division !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }};;
            spnr_substation.setAdapter(spinnerAdapter);
        }
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.SUBSTAION+"division_id="+division_id);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println(result.toString());
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result) {
                    try {
                        if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                            JSONArray jsonArray= result.getJSONArray("data");
                            listdata.put("select","Select");
                            for(int i=0; i < jsonArray.length() ; i++) {
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
                                    R.layout.row, R.id.name, itemList){
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    if (position % 2 == 0) { // we're on an even row
                                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }};;
                            spnr_substation.setAdapter(spinnerAdapter);
                            spnr_substation.setPrompt("Select");
                            listdata.clear();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
                        Toast.makeText(getActivity(),"Network Problem Occured !!!",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            };
            task.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void feeder(final String substation_id) {
        if (substation_id.equalsIgnoreCase("select") || substation_id.equalsIgnoreCase("")) {
            spnr_feeder.setAdapter(null);
            List<StringWithTag> itemList = new ArrayList<StringWithTag>();
            itemList.add(new StringWithTag("Please Select Division !!!", "select"));
            ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                    R.layout.row, R.id.name, itemList){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.parseColor("#2a82b2"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }};;
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
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    try {
                        HttpParams httpParams = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.FEEDER + "substation_id=" + substation_id);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println(result.toString());
                        String resultt = (result.toString()).replaceAll("<[^>]*>", "");
                        jsonObject = new JSONObject(resultt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }

                @Override
                protected void onPostExecute(JSONObject resultt) {
                    try {
                        if (resultt.getInt("rc") == 0) {
                            JSONArray jsonArray = resultt.getJSONArray("feeder_list");
                            if(jsonArray.length()> 0){
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
                                System.out.println("itemList"+itemList);

                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList){
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                            TextView name=  (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name=  (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }};;
                                spnr_feeder.setAdapter(spinnerAdapter);
                                spnr_feeder.setPrompt("Select");
                                listdata.clear();
                            } else {
                                spnr_feeder.setAdapter(null);
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                itemList.add(new StringWithTag("Not found feeder for selected Substation !!!", "select"));
                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList){
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#2a82b2"));
                                            TextView name=  (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#ffffff"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                            TextView name=  (TextView) view.findViewById(R.id.name);
                                            name.setTextColor(Color.parseColor("#000000"));
                                        }
                                        return view;
                                    }};;
                                spnr_feeder.setAdapter(spinnerAdapter);
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

}
