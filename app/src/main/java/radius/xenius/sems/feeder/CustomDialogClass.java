package radius.xenius.sems.feeder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


/**
 * Created by Pratima Singh on 09-03-2017.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
             SharedPreferences settings = getContext().getSharedPreferences(LoginActivity.PREFS_NAME,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                dismiss();
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }

    }
}