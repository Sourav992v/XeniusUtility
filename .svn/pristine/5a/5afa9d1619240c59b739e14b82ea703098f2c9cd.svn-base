package radius.xenius.sems.feeder;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JeSdoOverviewFragment  extends Fragment {
    private static final String TAG = "CardListActivity";
    private OverviewArrayAdapterJE overviewArrayAdapterJE;
    ListView listView;
    String substation_id;
    Spinner spnr_substation;
    ProgressDialog pd;
    LinkedHashMap<String, String> listdata;
    public static JeSdoOverviewFragment newInstance() {
        JeSdoOverviewFragment fragment = new JeSdoOverviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_je_sdo_overview, container, false);
        listView = (ListView) view.findViewById(R.id.overviewListview);
        spnr_substation = (Spinner) view.findViewById(R.id.spnr_substation);
        listdata = new LinkedHashMap<String, String>();
        substation();

     spnr_substation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        substation_id=key;
                        setDataList();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        return view;
    }

    public void setDataList(){

        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));

        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
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
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.FEEDER_DATA+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area+"&substation_id="+substation_id);
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
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject resultt) {
                    if (pd != null && pd.isShowing()) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        pd = null;
                    }
                    try {
                        if(resultt.getInt("rc")==0 && "Success".equalsIgnoreCase(resultt.getString("message"))) {
                            String resourc = resultt.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = resultt.getJSONArray("resource");
                                System.out.println("Feeder jsonArray: "+jsonArray);
                                //listdata.put("select", "Select");

                                //-----------------------------------------------------------
                                overviewArrayAdapterJE = new OverviewArrayAdapterJE(getActivity(), R.layout.overview_listview_je);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    System.out.println("jsonArrayy : "+jsonArray);

                                    Feeder_data feeder_data = new Feeder_data();
                                    feeder_data.setFeeder_name("FEEDER:  "+jsonObject.getString("name"));
                                    feeder_data.setLast_reading_updated("LAST UPDATED:  "+jsonObject.getString("last_reading_updated"));
                                    feeder_data.setSerial_no(jsonObject.getString("serial_no"));
                                    feeder_data.setKVAh(jsonObject.getString("KVAh"));
                                    feeder_data.setGrid_load_sanctioned(jsonObject.getString("grid_load_sanctioned"));
                                    feeder_data.setInstant_cum_KVA(jsonObject.getDouble("instant_cum_KVA"));
                                    feeder_data.setR_Voltage(jsonObject.getDouble("R_Voltage"));
                                    feeder_data.setY_Voltage(jsonObject.getDouble("Y_Voltage"));
                                    feeder_data.setB_Voltage(jsonObject.getDouble("B_Voltage"));
                                    feeder_data.setR_Current(jsonObject.getDouble("R_Current"));
                                    feeder_data.setY_Current(jsonObject.getDouble("Y_Current"));
                                    feeder_data.setB_Current(jsonObject.getDouble("B_Current"));
                                    feeder_data.setR_PF(jsonObject.getDouble("R_PF"));
                                    feeder_data.setY_PF(jsonObject.getDouble("Y_PF"));
                                    feeder_data.setB_PF(jsonObject.getDouble("B_PF"));
                                    feeder_data.setCumm_pf(jsonObject.getDouble("cumm_pf"));
                                    feeder_data.setMax_demand_KVA(jsonObject.getDouble("max_demand_KVA"));
                                    feeder_data.setUom(jsonObject.getString("uom"));
                                    feeder_data.setMC_UID(jsonObject.getString("MC_UID"));
                                    feeder_data.setDigital_input2(jsonObject.getInt("digital_input2"));
                                    feeder_data.setDigital_input3(jsonObject.getInt("digital_input3"));
                                    feeder_data.setMeter_ct_mf(jsonObject.getInt("meter_ct_mf"));
                                    feeder_data.setHrs(jsonObject.getInt("Hrs"));
                                    overviewArrayAdapterJE.add(feeder_data);
                                }

                                listView.setAdapter(overviewArrayAdapterJE);
                                updateListViewHeight(listView);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error!!!",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (resultt==null){
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


    public void substation(){
        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
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
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
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
                        jsonObject = new JSONObject(resultt.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject resultt) {
                    if (pd != null && pd.isShowing()) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        pd = null;
                    }
                    try {
                        if(resultt.getInt("rc")==0 && "Success".equalsIgnoreCase(resultt.getString("message"))) {
                            String resourc = resultt.getString("resource");
                            if (!resourc.equalsIgnoreCase("null")) {
                                JSONArray jsonArray = resultt.getJSONArray("resource");
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
                                            view.setBackgroundColor(Color.parseColor("#015454"));
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
                            } else {
                                spnr_substation.setAdapter(null);
                                List<StringWithTag> itemList = new ArrayList<StringWithTag>();
                                itemList.add(new StringWithTag("Not found Substation !!!", "select"));
                                ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(getActivity(),
                                        R.layout.row, R.id.name, itemList){
                                    @Override
                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        if (position % 2 == 0) { // we're on an even row
                                            view.setBackgroundColor(Color.parseColor("#d3d3d3"));
                                        } else {
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        }
                                        return view;
                                    }};
                                spnr_substation.setAdapter(spinnerAdapter);
                            }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (resultt==null){
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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        // get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // Change Height of ListView
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight
                + (myListView.getDividerHeight() * (adapterCount)));
        myListView.setLayoutParams(params);
    }

}
