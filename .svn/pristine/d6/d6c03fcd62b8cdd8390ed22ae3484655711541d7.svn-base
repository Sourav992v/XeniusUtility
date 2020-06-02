package radius.xenius.sems.feeder;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Pratima Singh 01-08-2018
 */
public class OutageReasonJEFragment extends Fragment implements FeederCustomButtonListener{

    View view;
    Button editBtn;
    View view2 = null;
    Button editedBtn;
    ListView listView;
    String substation_id;
    Spinner spnr_substation;
    ProgressDialog pd;
    LinkedHashMap<String, String> listdata;
    JSONArray jsonArray;
    String power_outage_id;
    String power_outage_reasonn;
    TextView titalText;

    FeederPowerOutageReasonEdit feederPowerOutageReasonEdit;
    List<StringWithTag> reasonlist;
    FeederPowerOutageReasonEdited feederPowerOutageReasonEdited;


    public static OutageReasonJEFragment newInstance() {
        OutageReasonJEFragment fragment = new OutageReasonJEFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outage_reason_je, container, false);

        editBtn = (Button) view.findViewById(R.id.edit);
        editedBtn = (Button) view.findViewById(R.id.edited);
        titalText = (TextView) view.findViewById(R.id.titalText);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        editedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edited();
            }
        });
        return view;
    }

    public void edit(){
        LinearLayout main = (LinearLayout) view.findViewById(R.id.outage_reasonLayout);
        if(view2!=null) {
            main.removeView(view2);
            view2=null;
        }

        if(view2==null) {
            view2 = getActivity().getLayoutInflater().inflate(R.layout.fragment_outage_reason_je_list_inflater, main, false);
            main.addView(view2);
        }
        titalText.setText("Edit");
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
                        //setDataList();
                        powerOutageList();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        listView = (ListView)view.findViewById(R.id.card_listView);
    }

    public void edited(){
        LinearLayout main = (LinearLayout) view.findViewById(R.id.outage_reasonLayout);
        if(view2!=null) {
            main.removeView(view2);
            view2=null;
        }
        if(view2==null) {
            view2 = getActivity().getLayoutInflater().inflate(R.layout.fragment_outage_reason_je_list_inflater, main, false);
            main.addView(view2);
        }
        titalText.setText("Edited");
        spnr_substation = (Spinner) view.findViewById(R.id.spnr_substation);
        listdata = new LinkedHashMap<String, String>();
        substation();
        listView = (ListView)view.findViewById(R.id.card_listView);
        spnr_substation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
                        String type = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        substation_id=key;
                        powerOutageListEdited();
                       // setDataList();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
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


    public void powerOutageList(){
        final String TAG = "feederPowerOutageReasonEdit";
        listView = (ListView) view.findViewById(R.id.card_listView);
        listdata = new  LinkedHashMap<String, String>();
        reasonlist = new ArrayList<StringWithTag>();

        if(!isNetworkConnected()){
            Toast.makeText(getActivity(), "No Internet Connection!!" , Toast.LENGTH_LONG).show();
            return;
        }
        try{
            //-------------------------------------------------------------------------------------
            @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject=null;
                    try {
                        //String site_id="5a797e637e0e30.85310117";
                        String site_id=substation_id;
                        String status="N";
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        HttpPost request = new HttpPost(API.POWER_OUTAGE_DETAILS+"site_id="+site_id+"&status="+status);
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));
                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println("Power Outage Reason List : "+result.toString());
                        jsonObject=new JSONObject(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
                @Override
                protected void onPostExecute(JSONObject result1) {
                    try {
                        if(result1.getInt("status")==200 && "true".equals(result1.getString("data_status"))){
                            jsonArray= result1.getJSONArray("data");
                            feederPowerOutageReasonEdit = new FeederPowerOutageReasonEdit(getActivity(), R.layout.fragment_outage_reason_je_edit_list);
                            //--------------------Power Outage List in View Holder-----------------------------

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
                                            HttpGet request = new HttpGet(API.OUTAGE_OPTIONS);
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
                                        try {

                                            if(result.getInt("status")==200 && "true".equals(result.getString("data_status"))){
                                                JSONArray jsonArray1= result.getJSONArray("data");
                                                listdata.put("select", "Select");
                                                for(int i=0; i < jsonArray1.length() ; i++) {
                                                    JSONObject json_data = jsonArray1.getJSONObject(i);
                                                    listdata.put(json_data.getString("id"), json_data.getString("reason"));
                                                }

                                                for (Map.Entry<String, String> entry : listdata.entrySet()) {
                                                    String key = entry.getKey();
                                                    String value = entry.getValue();
                                                    reasonlist.add(new StringWithTag(value, key));
                                                }

                                                //--------------------Power Outage Data List in View Holder--------------------------------
                                                for(int i=0; i < jsonArray.length() ; i++) {

                                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                                    String outage_id=json_data.getString("id");
                                                    long from_time=json_data.getLong("from_time");
                                                    Calendar calendar = Calendar.getInstance();
                                                    calendar.setTimeInMillis(from_time);
                                                    String dateFormat = "dd-MM-yyyy hh:mm:ss";
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                                                    String from_timee = simpleDateFormat.format(calendar.getTime());
                                                    long to_time=json_data.getLong("to_time");
                                                    calendar.setTimeInMillis(to_time);
                                                    String to_timee = simpleDateFormat.format(calendar.getTime());
                                                    String name=json_data.getString("name");
                                                    OutageReasonData card = new OutageReasonData(name, from_timee ,to_timee, reasonlist, outage_id);
                                                    feederPowerOutageReasonEdit.add(card);
                                                    feederPowerOutageReasonEdit.setCustomButtonListner(OutageReasonJEFragment.this);
                                                }

                                                listView.setAdapter(feederPowerOutageReasonEdit);
                                                listView.setFastScrollEnabled(true);
                                                updateListViewHeight(listView);
                                                //------------------------------------------
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
                            //----------------------------------------------------------------------------------------------
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result1==null){
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

    @Override
    public void onButtonClickListner(String key,  String power_outage_reason,String outage_id) {
        //site_id="0702eb80-109a-11e8-8559-028ddc03ab22";
        power_outage_id=outage_id;
        power_outage_reasonn=power_outage_reason;
        if(power_outage_reasonn.equalsIgnoreCase("Select")) {
            Toast.makeText(getActivity(), "Please Select reason!!!" ,
                    Toast.LENGTH_LONG).show();
            return;
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
                        String url=API.UPDATE_POWER_OUTAGE_REASON+"id="+power_outage_id+"&reason="+power_outage_reasonn+"&status=P&login_id="+LoginActivity.login_id;

                        url=url.replaceAll(" ","%20");
                        HttpPost request = new HttpPost(url);
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
                    if (pd != null && pd.isShowing()) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        pd = null;
                    }
                    try {
                        if(result.getInt("status")==200){
                            if(result.getInt("rc")==0) {
                                FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), result.getString("message"));
                                dialog.show();
                            } else{
                                FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), "Network Problem Occured");
                                dialog.show();
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result==null){
                        FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), "Network Problem Occured");
                        dialog.show();
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

    public void powerOutageListEdited(){
        final String TAG = "FeederPowerOutageReasonEdited";
        /* Initialize buttons */
        listView = (ListView) view.findViewById(R.id.card_listView);

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
                        String site_id=substation_id;
                        //String site_id="5a797e637e0e30.85310117";
                        String status="P";
                        HttpParams httpParams=new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpParams,30000);
                        HttpClient client = new DefaultHttpClient(httpParams);
                        System.out.println("URL : "+API.POWER_OUTAGE_DETAILS+"site_id="+site_id+"&status="+status);
                        HttpPost request = new HttpPost(API.POWER_OUTAGE_DETAILS+"site_id="+site_id+"&status="+status);
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
                protected void onPostExecute(JSONObject result1) {
                    try {
                        if(result1.getInt("status")==200 && "true".equals(result1.getString("data_status"))){
                            jsonArray= result1.getJSONArray("data");
                            System.out.println("Power Outage Reason List Pratima: "+jsonArray);
                            feederPowerOutageReasonEdited = new FeederPowerOutageReasonEdited(getActivity(), R.layout.fragment_outage_reason_je_edited_list);
                            //--------------------Power Outage List in View Holder-----------------------------
                            for(int i=0; i < jsonArray.length() ; i++) {
                                JSONObject json_data = jsonArray.getJSONObject(i);
                                long from_time=json_data.getLong("from_time");
                                System.out.println(from_time);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(from_time);
                                String dateFormat = "dd-MM-yyyy hh:mm:ss";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                                String from_timee = simpleDateFormat.format(calendar.getTime());

                                long to_time=json_data.getLong("to_time");
                                calendar.setTimeInMillis(to_time);
                                String to_timee = simpleDateFormat.format(calendar.getTime());
                                String name=json_data.getString("name");
                                String reason=json_data.getString("reason");
                                System.out.println("Power Outage List "+i+" : "+from_time +"    "+to_time);
                                OutageReasonData outageReasonData = new OutageReasonData(name, from_timee ,to_timee, reason);
                                feederPowerOutageReasonEdited.add(outageReasonData);
                            }
                            listView.setAdapter(feederPowerOutageReasonEdited);
                            listView.setFastScrollEnabled(true);
                            updateListViewHeight(listView);
                            //------------------------------------------
                            listdata.clear();

                            //----------------------------------------------------------------------------------------------
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (result1==null){
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

}
