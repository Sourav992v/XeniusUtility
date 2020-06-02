package radius.xenius.sems.feeder;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pratima Singh on 04-06-2018.
 */

public class OverviewArrayAdapterJE extends ArrayAdapter<Feeder_data> {
    private static final String TAG = "CardListActivity";
    private List<Feeder_data> cardList = new ArrayList<Feeder_data>();

    static class CardViewHolder {
        TextView feeder_nametxt;
        TextView lasttxt;
        TextView reading;
        TextView load;
        TextView rVoltage;
        TextView yVoltage;
        TextView bVoltage;
        TextView avgVontage;
        TextView rCurrent;
        TextView yCurrent;
        TextView bCurrent;
        TextView avgCurrent;
        TextView rPf;
        TextView yPf;
        TextView bPf;
        TextView cummPF;
        TextView md;
        TextView uom;
        TextView meterID;
        TextView modemNo;
        TextView meter_ct_mf;
        ImageButton incommerbtn;
        ImageButton cbtn;
    }

    public OverviewArrayAdapterJE(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Feeder_data object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Feeder_data getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.overview_listview_je, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.feeder_nametxt = (TextView) row.findViewById(R.id.feeder_nametxt);
            viewHolder.lasttxt = (TextView) row.findViewById(R.id.lasttxt);
            viewHolder.reading = (TextView) row.findViewById(R.id.reading);
            viewHolder.load = (TextView) row.findViewById(R.id.load);
            viewHolder.rVoltage = (TextView) row.findViewById(R.id.rVoltage);
            viewHolder.yVoltage = (TextView) row.findViewById(R.id.yVoltage);
            viewHolder.bVoltage = (TextView) row.findViewById(R.id.bVoltage);
            viewHolder.rCurrent = (TextView) row.findViewById(R.id.rCurrent);
            viewHolder.yCurrent = (TextView) row.findViewById(R.id.yCurrent);
            viewHolder.bCurrent = (TextView) row.findViewById(R.id.bCurrent);
            viewHolder.avgCurrent = (TextView) row.findViewById(R.id.avgCurrent);
            viewHolder.rPf = (TextView) row.findViewById(R.id.rPf);
            viewHolder.yPf = (TextView) row.findViewById(R.id.yPf);
            viewHolder.bPf = (TextView) row.findViewById(R.id.bPf);
            viewHolder.cummPF = (TextView) row.findViewById(R.id.cummPF);
            viewHolder.md = (TextView) row.findViewById(R.id.md);
            viewHolder.uom = (TextView) row.findViewById(R.id.uom);
            viewHolder.meterID = (TextView) row.findViewById(R.id.meterID);
            viewHolder.modemNo = (TextView) row.findViewById(R.id.modemNo);
            viewHolder.meter_ct_mf = (TextView) row.findViewById(R.id.meter_ct_mf);
            viewHolder.incommerbtn = (ImageButton) row.findViewById(R.id.incommerbtn);
            viewHolder.cbtn = (ImageButton) row.findViewById(R.id.cbtn);

            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Feeder_data feeder_data = getItem(position);
        viewHolder.feeder_nametxt.setText(feeder_data.getFeeder_name());
        viewHolder.lasttxt.setText(feeder_data.getLast_reading_updated());
        if ((feeder_data.getDigital_input3()==0 || feeder_data.getDigital_input2()==0) && (feeder_data.getHrs()>72)){
                viewHolder.reading.setText(Html.fromHtml("<font color='#FF0000'>Probe</font>"));
                viewHolder.load.setText(Html.fromHtml("<font color='#FF0000'>/Unplugged</font>"));
                viewHolder.rVoltage.setText("/485/232");
                viewHolder.rCurrent.setText("Disconnected");
                viewHolder.rPf.setText("/Removed");
        } else if((Math.abs(feeder_data.getR_Current())+Math.abs(feeder_data.getY_Current())+Math.abs(feeder_data.getB_Current()))==0 && (Math.abs(feeder_data.getR_Voltage())+Math.abs(feeder_data.getY_Voltage())+Math.abs(feeder_data.getB_Voltage()))==0
                && (Math.abs(feeder_data.getInstant_cum_KVA())==0) && (Math.abs(feeder_data.getMax_demand_KVA())==0)  && (feeder_data.getDigital_input3()==1 || feeder_data.getDigital_input2()==1) && feeder_data.getHrs()>72) {

            viewHolder.reading.setText(Html.fromHtml("<font color='#FF0000'>Probe</font>"));
            viewHolder.load.setText(Html.fromHtml("<font color='#FF0000'>/Unplugged</font>"));
            viewHolder.rVoltage.setText("/485/232");
            viewHolder.rCurrent.setText("Disconnected");
            viewHolder.rPf.setText("/Removed");
        } else{
            viewHolder.reading.setText(feeder_data.getKVAh());
            viewHolder.load.setText(String.valueOf(feeder_data.getInstant_cum_KVA()));
            viewHolder.rVoltage.setText(String.valueOf(feeder_data.getR_Voltage()));
            viewHolder.yVoltage.setText(String.valueOf(feeder_data.getY_Voltage()));
            viewHolder.bVoltage.setText(String.valueOf(feeder_data.getB_Voltage()));
            viewHolder.rCurrent.setText(String.valueOf(feeder_data.getR_Current()));
            viewHolder.yCurrent.setText(String.valueOf(feeder_data.getY_Current()));
            viewHolder.bCurrent.setText(String.valueOf(feeder_data.getB_Current()));
            viewHolder.rPf.setText(String.valueOf(feeder_data.getR_PF()));
            viewHolder.yPf.setText(String.valueOf(feeder_data.getY_PF()));
            viewHolder.bPf.setText(String.valueOf(feeder_data.getB_PF()));
            viewHolder.cummPF.setText(String.valueOf(feeder_data.getCumm_pf()+" Cumm"));
        }
            viewHolder.meter_ct_mf.setText(String.valueOf(feeder_data.getMeter_ct_mf()));
            viewHolder.md.setText(String.valueOf(feeder_data.getMax_demand_KVA()));
            viewHolder.uom.setText(feeder_data.getUom());
            viewHolder.meterID.setText(feeder_data.getSerial_no());
            viewHolder.modemNo.setText(feeder_data.getMC_UID());

        if (feeder_data.getDigital_input3()==1){
            viewHolder.cbtn.setImageResource(R.drawable.bullet_green);
        }else if(feeder_data.getDigital_input3()==0){
            viewHolder.cbtn.setImageResource(R.drawable.bullet_red);
        }else {
            viewHolder.cbtn.setImageResource(R.drawable.bullet_red);
        }

        if (feeder_data.getDigital_input2()==1){
            viewHolder.incommerbtn.setImageResource(R.drawable.bullet_green);
        }else if(feeder_data.getDigital_input2()==0){
            viewHolder.incommerbtn.setImageResource(R.drawable.bullet_red);
        }else {
            viewHolder.incommerbtn.setImageResource(R.drawable.bullet_red);
        }
        return row;
    }

}