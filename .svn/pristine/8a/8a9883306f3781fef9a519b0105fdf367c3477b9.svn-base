package radius.xenius.sems.feeder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FeederStatusDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes;
    public TextView txt_dia;
    String s;

    public FeederStatusDialog(Activity a,String s) {
        super(a);
        this.c = a;
        this.s=s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feeder_status_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        txt_dia = (TextView) findViewById(R.id.txt_dia);
        txt_dia.setText(s);
        yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                break;

            default:
                break;
        }

    }


}
