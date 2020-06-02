/*
 * Contributors:
 * Pratima Singh
 */

package radius.xenius.sems.feeder;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ItemTwoFragment extends Fragment {
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
    String substation_name="";
    String feeder_id="";
    String feeder_name="";
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
    Button addRemarkBtn;
    Button viewRemarkBtn;
    String remark;

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_two, container, false);
        discomText= (TextView) view.findViewById(R.id.discomText);
        relative_spnr_discom = (RelativeLayout) view.findViewById(R.id.relative_spnr_discom);
        spnr_discom = (Spinner) view.findViewById(R.id.spnr_discom);
        zoneText= (TextView) view.findViewById(R.id.zoneText);
        relative_spnr_zone = (RelativeLayout) view.findViewById(R.id.relative_spnr_zone);
        spnr_zone = (Spinner) view.findViewById(R.id.spnr_zone);
        circleText= (TextView) view.findViewById(R.id.circleText);
        relative_spnr_circle=(RelativeLayout) view.findViewById(R.id.relative_spnr_circle);
        spnr_circle = (Spinner) view.findViewById(R.id.spnr_circle);
        relative_spnr_division=(RelativeLayout) view.findViewById(R.id.relative_spnr_division);
        divisionText= (TextView) view.findViewById(R.id.divisionText);
        spnr_division = (Spinner) view.findViewById(R.id.spnr_division);
        spnr_substation = (Spinner) view.findViewById(R.id.spnr_substation);
        spnr_feeder = (Spinner) view.findViewById(R.id.spnr_feeder);
        listdata = new LinkedHashMap<String, String>();

        switch(LoginActivity.access_area){
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
               // substation("5a72dc9e552605.85388859");
                break;
            default:
                discom();
                break;
        }
        viewRemarkBtn=(Button) view.findViewById(R.id.viewRemarkBtn);
        addRemarkBtn=(Button) view.findViewById(R.id.addRemarkBtn);

        viewRemarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!feeder_id.equalsIgnoreCase("")){
                    viewRemark();
                } else{
                    FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), "Please select Feeder!!!");
                    dialog.show();
                }
            }
        });

        addRemarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!feeder_id.equalsIgnoreCase("")){
                    addRemark();
                } else{
                    FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), "Please select Feeder!!!");
                    dialog.show();
                }
            }
        });

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
                        substation_name=type;
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
                        feeder_name=type;
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        String key = (String) swt.tag;
                        feeder_id=key;
                        if(!feeder_id.equalsIgnoreCase("select")) {
                            chart(feeder_id);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        return view;
    }

    public void viewRemark(){
        Intent intent = new Intent(getActivity(), ViewRemarkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.putExtra("substation_name", substation_name);
        intent.putExtra("feeder_name", feeder_name);
        intent.putExtra("feeder_id", feeder_id);
        startActivity(intent);
    }

    public void addRemark(){
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.activity_add_remark, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(promptsView);
        final Button okBtn = (Button) promptsView.findViewById(R.id.btnOK);
        final Button cancleBtn = (Button) promptsView.findViewById(R.id.btnCancle);
        final EditText remarkTxt= (EditText) promptsView.findViewById(R.id.remarkText);
        final AlertDialog dialog = builder.create();
        dialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remark= remarkTxt.getText().toString();
                if(!remark.equalsIgnoreCase("")) {
                    save_remark();
                } else{
                    FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), "Please Write Remark!!!");
                    dialog.show();
                }
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void save_remark(){
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
                        resultt = Util.getdata(API.ADD_REMARK+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area+"&sensor_id="+feeder_id+"&remark="+remark+"&profile_id="+LoginActivity.login_id);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject=new JSONObject(resultt.toString());
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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))){
                            FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), result.getString("message"));
                            dialog.show();
                        }else{
                            FeederStatusDialog dialog = new FeederStatusDialog(getActivity(), result.getString("message"));
                            dialog.show();                        }

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
                        resultt = Util.getdata(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject=new JSONObject(resultt.toString());
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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))){
                            JSONArray jsonArray= result.getJSONArray("resource");
                            ArrayList<String> items = new ArrayList<String>();
                            //listdata.put("select","Select");
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
                                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#ffffff"));
                                    } else {
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                                        TextView name=  (TextView) view.findViewById(R.id.name);
                                        name.setTextColor(Color.parseColor("#000000"));
                                    }
                                    return view;
                                }
                            };
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
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
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
                        if(LoginActivity.access_area.equalsIgnoreCase("ZONE" )){
                            resultt = Util.getdata(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.ZONES+"discom_id="+discom_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject=new JSONObject(resultt.toString());

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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))){
                            String resourc= result.getString("resource");
                            if(!resourc.equalsIgnoreCase("null")) {
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
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
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
                        if(LoginActivity.access_area.equalsIgnoreCase("CIRCLE" )){
                            resultt = Util.getdata(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.CIRCLE+"zone_id="+zone_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject=new JSONObject(resultt.toString());

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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))) {
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
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
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
                        if(LoginActivity.access_area.equalsIgnoreCase("DIVISION")){
                            resultt = Util.getdata(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.DIVISION+"circle_id="+circle_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject=new JSONObject(resultt.toString());

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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))) {
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
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#000000"));
                    }
                    return view;
                }
            };
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
                        if(LoginActivity.access_area.equalsIgnoreCase("SUBSTATION")){
                            resultt = Util.getdata(API.ACCESS_LEVEL+"login_id="+LoginActivity.login_id+"&password="+LoginActivity.password+"&access_level="+LoginActivity.access_area);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        } else {
                            resultt = Util.getdata(API.SUBSTAION+"division_id="+division_id);
                            resultt = resultt.replaceAll("<[^>]*>", "");
                        }
                        jsonObject=new JSONObject(resultt.toString());
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
                        if(result.getInt("rc")==0 && "Success".equalsIgnoreCase(result.getString("message"))) {
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
                        view.setBackgroundColor(Color.parseColor("#003f3f"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
                        name.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView name=  (TextView) view.findViewById(R.id.name);
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
                        progressDialog.setMessage("Please wait.");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                    }
                }

                @Override
                protected JSONObject doInBackground(String... arg0) {
                    JSONObject jsonObject = null;
                    String resultt="";
                    try {
                        resultt = Util.getdata(API.FEEDER + "substation_id=" + substation_id);
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
                                    LinearLayout main = (LinearLayout) view.findViewById(R.id.feeder_detailsLayout);
                                    if(view2!=null) {
                                        main.removeView(view2);
                                        view2=null;
                                    }

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

    public void chart(final  String feeder_id){
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
                        resultt = Util.getdata(API.FEEDER_DETAILS+"login_id="+LoginActivity.login_id+"&password="+ LoginActivity.password+"&access_level="+LoginActivity.access_area+"&feeder_id="+feeder_id);
                        resultt = resultt.replaceAll("<[^>]*>", "");
                        jsonObject=new JSONObject(resultt.toString());
                       // HttpPost request = new HttpPost(API.FEEDER_DETAILS+"substation_id="+feeder_id);
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
                        if(resultt.getInt("rc")==0){
                            //--------------------------Feeder UP Down---------------------------------------------

                            LinearLayout main = (LinearLayout) view.findViewById(R.id.feeder_detailsLayout);
                            if(view2==null) {
                                view2 = getActivity().getLayoutInflater().inflate(R.layout.layout, main, false);
                                main.addView(view2);
                            }
                            ScrollView scrollview=(ScrollView) view.findViewById(R.id.scroll3);
                            scrollview.setFocusable(true);
                            scrollview.requestFocus();
                            JSONObject resource=resultt.getJSONObject("resource");
                            JSONObject cb_status= resource.getJSONObject("cb_status");
                            int incomer=cb_status.getInt("incomer");
                            int cb=cb_status.getInt("cb");
                            String meter_no=cb_status.getString("meter_no");
                            String modem_no=cb_status.getString("modem_no");

                            String last_reading_updated=cb_status.getString("last_reading_updated");
                            TextView last_reading_updatedtxt=(TextView) view.findViewById(R.id.last_updated_time);
                            last_reading_updatedtxt.setText("Last Updated : "+last_reading_updated);

                            ImageButton img_incommer = (ImageButton) view.findViewById(R.id.img_incommer);
                            ImageButton img_cb = (ImageButton) view.findViewById(R.id.img_cb);

                            if (incomer==1){
                                img_incommer.setImageResource(R.drawable.bullet_green);
                            }else if(incomer==0){
                                img_incommer.setImageResource(R.drawable.bullet_red);
                            }else {
                                img_incommer.setImageResource(R.drawable.bullet_red);
                            }
                            if (cb==1){
                                img_cb.setImageResource(R.drawable.bullet_green);
                            }else if(cb==0){
                                img_cb.setImageResource(R.drawable.bullet_red);
                            }else {
                                img_cb.setImageResource(R.drawable.bullet_red);
                            }

                            int csq= cb_status.getInt("csq");

                            TextView meter_noTxt=(TextView) view.findViewById(R.id.meter_no);
                            meter_noTxt.setText(meter_no);
                            TextView modamNoTxt=(TextView) view.findViewById(R.id.modamNo);
                            modamNoTxt.setText(modem_no);
                            ImageButton img_signal_strenght = (ImageButton) view.findViewById(R.id.img_signal_strenght);
                            TextView signaltxt=(TextView) view.findViewById(R.id.signal);
                            if (0<=csq && csq<=7){
                                signaltxt.setText(Html.fromHtml("Signal Strength <font color='#DAF7A6'>(Bed-"+csq+")</font>"));
                                img_signal_strenght.setImageResource(R.drawable.noconnectionxxl);
                            }else if(8<=csq && csq<=14){
                                signaltxt.setText(Html.fromHtml("Signal Strength <font color='#DAF7A6'>(Poor-"+csq+")</font>"));
                                img_signal_strenght.setImageResource(R.drawable.lowconnectionxxl);
                            }else if(15<=csq && csq<=22){
                                signaltxt.setText(Html.fromHtml("Signal Strength <font color='#DAF7A6'>(Good-"+csq+")</font>"));
                                img_signal_strenght.setImageResource(R.drawable.mediumconnectionxxl);
                            }else if(23<=csq && csq<=31){
                                signaltxt.setText(Html.fromHtml("Signal Strength <font color='#DAF7A6'>(Excellent-"+csq+")</font>"));
                                img_signal_strenght.setImageResource(R.drawable.highconnectionxxl);
                            }

                            JSONObject chartdatajsonobj= resource.getJSONObject("chart_data");
                            JSONObject contact_detailsObj = resource.getJSONObject("contact_details");
                            JSONObject today_interruptionObj= resource.getJSONObject("today_interruption");
                            JSONObject monthly_interruptionObj= resource.getJSONObject("monthly_interruption");
                            //--------------------------Chart-----------------------------------------
                            JSONArray date=chartdatajsonobj.getJSONArray("date");
                            JSONArray KVAh=chartdatajsonobj.getJSONArray("KVAh");
                            JSONArray KWh=chartdatajsonobj.getJSONArray("KWh");
                            String uom=chartdatajsonobj.getString("uom");

                            Map<String, String> params = new HashMap<String, String>(4);
                            params.put("%KVAH%", (KVAh.toString()));
                            params.put("%KWH%", (KWh.toString()));
                            params.put("%DATE%", date.toString());
                            params.put("%UOM%", uom+" Units");
                            params.put("%PATH%", "https://code.highcharts.com/highcharts.js");
                            String htmContent;
                            try {
                                WebView webview;
                                webview = (WebView) view.findViewById(R.id.webView1);
                                htmContent = Util.readFile(getActivity().getAssets().open("bar.htm"), params);
                                params.clear();
                                webview.getSettings().setJavaScriptEnabled(true);
                                webview.setWebChromeClient(new WebChromeClient());
                                webview.getSettings().setDomStorageEnabled(true);
                                webview.setBackgroundColor(Color.parseColor("#005c5c"));
                                webview.loadDataWithBaseURL(null, htmContent, "text/html", "utf-8", null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //------------------------------------------------------------------
                            contact_details(contact_detailsObj, today_interruptionObj,monthly_interruptionObj);
                        }else {
                            LinearLayout main = (LinearLayout) view.findViewById(R.id.feeder_detailsLayout);
                            if(view2!=null) {
                                main.removeView(view2);
                                view2=null;
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

    public void contact_details(JSONObject contact_detailsObj, JSONObject today_interruptionObj, JSONObject monthly_interruptionObj){
        try{
            String no_of_interuption= today_interruptionObj.getString("no_of_interuption");
            //int outage_duration_min= today_interruptionObj.getInt("outage_duration_min");
            String outage_duration_min= today_interruptionObj.getString("outage_duration_min");
           // int hrs = (int) TimeUnit.MILLISECONDS.toHours(TimeUnit.MINUTES.toMillis(outage_duration_min)) % 24;
          //  int min = (int) TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MINUTES.toMillis(outage_duration_min)) % 60;
           // int sec = (int) TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MINUTES.toMillis(outage_duration_min)) % 60;
           // String duration= String.format("%02d:%02d:%02d", hrs, min, sec);
            String duration= outage_duration_min;

            //---------------------------Today's Interruption-------------------------------------------

            TextView counttxt=(TextView) view.findViewById(R.id.count) ;
            TextView durationtxt=(TextView) view.findViewById(R.id.duration) ;
            counttxt.setText(no_of_interuption);
            durationtxt.setText(duration);

            //---------------------------Monthly Interruption-------------------------------------------
            String monthly_no_of_interuption= monthly_interruptionObj.getString("no_of_interuption");
            String outagelessthan5min= monthly_interruptionObj.getString("outagelessthan5min");
            String outage5to30min= monthly_interruptionObj.getString("outageduration5to30min");
            String outagemorethan30min= monthly_interruptionObj.getString("outagemorethan30min");
            TableLayout monthly_inteceptiontable = (TableLayout) view.findViewById(R.id.monthly_interruption_table);
            monthly_inteceptiontable.removeAllViewsInLayout();
            TextView count_monthtxt=(TextView) view.findViewById(R.id.count_month) ;
            TextView lessthan5txt=(TextView) view.findViewById(R.id.lessthan5) ;
            TextView lessthan5to30txt=(TextView) view.findViewById(R.id.lessthan5to30) ;
            TextView morethan30txt=(TextView) view.findViewById(R.id.morethan30) ;
            count_monthtxt.setText(monthly_no_of_interuption);
            lessthan5txt.setText(outagelessthan5min);
            lessthan5to30txt.setText(outage5to30min);
            morethan30txt.setText(outagemorethan30min);

            ///-daily----------------------------------------------------------------------

             /*  TableLayout daily_inteceptiontable = (TableLayout) view.findViewById(R.id.inteceptiontable);
            daily_inteceptiontable.removeAllViewsInLayout();

            TableRow tr_heada = new TableRow(getActivity());
            tr_heada.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView label_weight_kg = new TextView(getActivity());
            label_weight_kg.setText("Count");
            label_weight_kg.setPadding(7, 0, 0, 0);
            label_weight_kg.setHeight(16);

            label_weight_kg.setTextColor(Color.parseColor("#000000"));
            tr_heada.setMinimumHeight(50);
            tr_heada.addView(label_weight_kg);

            TextView label_s = new TextView(getActivity());
            label_s.setText(":  "+no_of_interuption);
            label_s.setTextColor(Color.parseColor("#000000"));
            label_s.setPadding(7, 0, 0, 0);
            tr_heada.addView(label_s);

            daily_inteceptiontable.addView(tr_heada, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TableRow tr_row = new TableRow(getActivity());
            tr_row.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tr_row.setMinimumHeight(50);
            TextView label_count = new TextView(getActivity());
            label_count.setText("Duration");
            label_count.setTextColor(Color.parseColor("#000000"));
            label_count.setPadding(7, 0, 0, 0);
            tr_row.addView(label_count);

            TextView label_dur = new TextView(getActivity());
            label_dur.setText(":  "+duration);
            label_dur.setTextColor(Color.parseColor("#000000"));
            label_dur.setPadding(7, 0, 0, 0);
            tr_row.addView(label_dur);

            daily_inteceptiontable.addView(tr_row, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));*/
//-------------monthly-------------------------------------------------

          /*  TableRow tr_headm = new TableRow(getActivity());
            //tr_headm.setBackgroundColor(Color.parseColor("#47a1d8"));
            tr_headm.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView label_monthly1 = new TextView(getActivity());
            label_monthly1.setText("Count");
            label_monthly1.setPadding(7, 0, 0, 0);
            label_monthly1.setTextColor(Color.parseColor("#000000"));
            tr_headm.addView(label_monthly1);

            TextView label_monthly12 = new TextView(getActivity());
            label_monthly12.setText(monthly_no_of_interuption);
            label_monthly12.setTextColor(Color.parseColor("#000000"));
            label_monthly12.setPadding(7, 0, 0, 0);
            tr_headm.addView(label_monthly12);

            monthly_inteceptiontable.addView(tr_headm, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            TableRow tr_rowm2 = new TableRow(getActivity());
            tr_rowm2.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView label_countm2 = new TextView(getActivity());
            label_countm2.setText("Less Than 5 min");
            label_countm2.setTextColor(Color.parseColor("#000000"));
            label_countm2.setPadding(7, 0, 0, 0);
            tr_rowm2.addView(label_countm2);

            TextView label_dur_m = new TextView(getActivity());
            label_dur_m.setText(outagelessthan5min);
            label_dur_m.setPadding(7, 0, 0, 0);
            label_dur_m.setTextColor(Color.parseColor("#000000"));
            tr_rowm2.addView(label_dur_m);

            monthly_inteceptiontable.addView(tr_rowm2, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TableRow tr_rowm3 = new TableRow(getActivity());
            tr_rowm3.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView label_countm3 = new TextView(getActivity());
            label_countm3.setText("Less Than 5 to 30 Min");
            label_countm3.setTextColor(Color.parseColor("#000000"));
            label_countm3.setPadding(7, 0, 0, 0);
            tr_rowm3.addView(label_countm3);

            TextView label_dur_m3 = new TextView(getActivity());
            label_dur_m3.setText(outage5to30min);
            label_dur_m3.setPadding(7, 0, 0, 0);
            label_dur_m3.setTextColor(Color.parseColor("#000000"));
            tr_rowm3.addView(label_dur_m3);

            monthly_inteceptiontable.addView(tr_rowm3, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TableRow tr_rowm4 = new TableRow(getActivity());
            tr_rowm4.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView label_countm4 = new TextView(getActivity());
            label_countm4.setText("More Than 30 Min");
            label_countm4.setTextColor(Color.parseColor("#000000"));
            label_countm4.setPadding(7, 0, 0, 0);
            tr_rowm4.addView(label_countm4);

            TextView label_dur_m4 = new TextView(getActivity());
            label_dur_m4.setText(outagemorethan30min);
            label_dur_m4.setPadding(7, 0, 0, 0);
            label_dur_m4.setTextColor(Color.parseColor("#000000"));
            tr_rowm4.addView(label_dur_m4);

            monthly_inteceptiontable.addView(tr_rowm4, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));*/


            //-----------------------FEEDER CONTACT DETAILS------------------------------------

            System.out.println("sdo_namePra:"+contact_detailsObj.getString("sdo_name"));
            String sdo_desi=contact_detailsObj.getString("sdo_designation");
            String sdo_name=contact_detailsObj.getString("sdo_name");
            String sdo_mob=contact_detailsObj.getString("sdo_mob");
            String sdo_email=contact_detailsObj.getString("sdo_email");

            String je_designation=contact_detailsObj.getString("je_designation");
            String je_name=contact_detailsObj.getString("je_name");
            String je_mob=contact_detailsObj.getString("je_mob");
            String je_email=contact_detailsObj.getString("je_email");

            String ee_designation=contact_detailsObj.getString("ee_designation");
            String ee_name=contact_detailsObj.getString("ee_name");
            String ee_mob=contact_detailsObj.getString("ee_mob");
            String ee_email=contact_detailsObj.getString("ee_email");

            String sso_designation=contact_detailsObj.getString("sso_designation");
            String sso_name=contact_detailsObj.getString("sso_name");
            String sso_mob=contact_detailsObj.getString("sso_mob");
            String sso_email=contact_detailsObj.getString("sso_email");

            TextView ee_desitxt = (TextView) view.findViewById(R.id.ee_desi);
            TextView ee_nametxt = (TextView) view.findViewById(R.id.ee_name);
            TextView ee_mnotxt = (TextView) view.findViewById(R.id.ee_mno);
            TextView ee_emailtxt = (TextView) view.findViewById(R.id.ee_email);

            if (ee_designation.equalsIgnoreCase("null")) {
                ee_desitxt.setText("");
            } else {
                ee_desitxt.setText("Design. ("+ee_designation+")");
            }

            if (ee_name.equalsIgnoreCase("null")) {
                ee_nametxt.setText("");
            } else {
                ee_nametxt.setText(ee_name);
            }

            if (ee_mob.equalsIgnoreCase("null")) {
                ee_mnotxt.setText("");
            } else {
                ee_mnotxt.setText(ee_mob);
            }

            if (ee_email.equalsIgnoreCase("null")) {
                ee_emailtxt.setText("");
            } else {
                ee_emailtxt.setText(ee_email);
            }

            TextView sdo_desitxt = (TextView) view.findViewById(R.id.sdo_desi);
            TextView sdo_nametxt = (TextView) view.findViewById(R.id.sdo_name);
            TextView sdo_mnotxt = (TextView) view.findViewById(R.id.sdo_mno);
            TextView sdo_emailtxt = (TextView) view.findViewById(R.id.sdo_email);
            if (sdo_desi.equalsIgnoreCase("null")) {
                sdo_desitxt.setText("");
            } else {
                sdo_desitxt.setText("Design. ("+sdo_desi+")");
            }

            if (sdo_name.equalsIgnoreCase("null")) {
                sdo_nametxt.setText("");
            } else {
                sdo_nametxt.setText(sdo_name);
            }

            if (sdo_mob.equalsIgnoreCase("null")) {
                sdo_mnotxt.setText("");
            } else {
                sdo_mnotxt.setText(sdo_mob);
            }

            if (sdo_email.equalsIgnoreCase("null")) {
                sdo_emailtxt.setText("");
            } else {
                sdo_emailtxt.setText(sdo_email);
            }

            TextView je_desitxt = (TextView) view.findViewById(R.id.je_desi);
            TextView je_nametxt = (TextView) view.findViewById(R.id.je_name);
            TextView je_mnotxt = (TextView) view.findViewById(R.id.je_mno);
            TextView je_emailtxt = (TextView) view.findViewById(R.id.je_email);
            if (je_designation.equalsIgnoreCase("null")) {
                je_desitxt.setText("");
            } else {
                je_desitxt.setText("Design. ("+je_designation+")");
            }

            if (je_name.equalsIgnoreCase("null")) {
                je_nametxt.setText("");
            } else {
                je_nametxt.setText(je_name);
            }

            if (je_mob.equalsIgnoreCase("null")) {
                je_mnotxt.setText("");
            } else {
                je_mnotxt.setText(je_mob);
            }

            if (je_email.equalsIgnoreCase("null")) {
                je_emailtxt.setText("");
            } else {
                je_emailtxt.setText(je_email);
            }

            TextView sso_desitxt = (TextView) view.findViewById(R.id.sso_desi);
            TextView sso_nametxt = (TextView) view.findViewById(R.id.sso_name);
            TextView sso_mnotxt = (TextView) view.findViewById(R.id.sso_mno);
            TextView sso_emailtxt = (TextView) view.findViewById(R.id.sso_email);
            if (sso_designation.equalsIgnoreCase("null")) {
                sso_desitxt.setText("");
            } else {
                sso_desitxt.setText("Design. ("+sso_designation+")");
            }

            if (sso_name.equalsIgnoreCase("null")) {
                sso_nametxt.setText("");
            } else {
                sso_nametxt.setText(sso_name);
            }

            if (sso_mob.equalsIgnoreCase("null")) {
                sso_mnotxt.setText("");
            } else {
                sso_mnotxt.setText(sso_mob);
            }

            if (sso_email.equalsIgnoreCase("null")) {
                sso_emailtxt.setText("");
            } else {
                sso_emailtxt.setText(sso_email);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        //---------------------------FEEDER CONTACT DETAILS-------------------------------------------
    }

}