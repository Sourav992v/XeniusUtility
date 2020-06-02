package radius.xenius.sems.feeder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pratima Singh on 10-04-2018.
 */

public class FeederPowerOutageReasonEdited extends ArrayAdapter<OutageReasonData> {

    private static final String TAG = "FeederPowerOutageReasonEdited";
    private List<OutageReasonData> cardList = new ArrayList<OutageReasonData>();
    FeederPowerOutageReasonEdited.CardViewHolder viewHolder;
    Context context;

    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
        TextView reason;
    }

    public FeederPowerOutageReasonEdited(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context=context;
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
        OutageReasonData feederCard = getItem(position);
        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_outage_reason_je_edited_list, parent, false);
            viewHolder = new FeederPowerOutageReasonEdited.CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            viewHolder.line3 = (TextView) row.findViewById(R.id.line3);
            viewHolder.reason = (TextView) row.findViewById(R.id.line4);
            row.setTag(viewHolder);
        } else {
            viewHolder = (FeederPowerOutageReasonEdited.CardViewHolder)row.getTag();
        }

        viewHolder.line1.setText(feederCard.getLine1());
        viewHolder.line2.setText(feederCard.getLine2());
        viewHolder.line3.setText(feederCard.getLine3());
        viewHolder.reason.setText(feederCard.getLine4());
        return row;
    }
}

