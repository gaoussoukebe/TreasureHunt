package btu.treasurehunt;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import btu.treasurehunt.R;

public class internetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        displayAlert(this);


    }
    public void displayAlert(Context c)
    {
        new AlertDialog.Builder(c).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                .show();
    }
}
