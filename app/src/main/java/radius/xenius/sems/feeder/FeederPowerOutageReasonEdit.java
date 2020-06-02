package radius.xenius.sems.feeder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class FeederPowerOutageReasonEdit extends ArrayAdapter<OutageReasonData> {

    private static final String TAG = "feederPowerOutageReasonEdit";
    private List<OutageReasonData> cardList = new ArrayList<OutageReasonData>();
    FeederPowerOutageReasonEdit.CardViewHolder viewHolder;
    ArrayAdapter<StringWithTag> spinnerAdapter;;
    Context context;
    String key;

    private FeederCustomButtonListener customListner;
    String power_outage_reason;

    String str;

    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
        Button btn;
        Spinner reason;
        String str;
    }

    public FeederPowerOutageReasonEdit(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context=context;
    }

    public void setCustomButtonListner(FeederCustomButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public void add(OutageReasonData object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public OutageReasonData getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_outage_reason_je_edit_list, parent, false);
            viewHolder = new FeederPowerOutageReasonEdit.CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            viewHolder.line3 = (TextView) row.findViewById(R.id.line3);
            viewHolder.btn = (Button) row.findViewById(R.id.save);
            viewHolder.reason = (Spinner) row.findViewById(R.id.spnr_reason);
            row.setTag(viewHolder);
        } else {
            viewHolder = (FeederPowerOutageReasonEdit.CardViewHolder)row.getTag();
        }
        final OutageReasonData feederCard = getItem(position);
        ///Toast.makeText(context, getItem(position).toString(), Toast.LENGTH_SHORT).show();

        spinnerAdapter = new ArrayAdapter<StringWithTag>(row.getContext(),
                R.layout.row, R.id.name, feederCard.itemList){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if (position % 2 == 0) { // we're on an even row
                    view.setBackgroundColor(Color.parseColor("#b2c5c5"));
                    TextView name = (TextView) view.findViewById(R.id.name);
                    name.setTextColor(Color.parseColor("#000000"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                    TextView name = (TextView) view.findViewById(R.id.name);
                    name.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        viewHolder.line1.setText(feederCard.getLine1());
        viewHolder.line2.setText(feederCard.getLine2());
        viewHolder.line3.setText(feederCard.getLine3());
        viewHolder.reason.setAdapter(spinnerAdapter);
        final String outage_id=feederCard.getOutage_id();
        str =viewHolder.reason.getSelectedItem().toString();
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.reason.setAdapter(null);
                viewHolder.reason.setAdapter(spinnerAdapter);
                // String str =viewHolder.reason.getSelectedItem().toString();
                if (customListner != null)
                    customListner.onButtonClickListner(key, power_outage_reason, outage_id);
            }
        });

        viewHolder.reason.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {

                        power_outage_reason = parent.getItemAtPosition(position).toString();
                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                        key = (String) swt.tag;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        return row;
    }



}
