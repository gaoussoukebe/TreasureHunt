package btu.treasurehunt;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class CollectingcoinActivity extends ListActivity  {
    private CheckBox check ;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collecting_coins);

        SensorManager sManager = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
        List<Sensor> sList =  sManager.getSensorList(Sensor.TYPE_ALL);

        List<String> sNames = new ArrayList();

        for (int i = 0 ; i< sList.size() ; i++)
        {
            sNames.add(((Sensor) sList.get(i)).getName());

        }

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, sNames ));

        getListView().setTextFilterEnabled(true);
        listener();

    }

    public void listener ()
    {

        send =(Button) findViewById(R.id.sendInfo);

        send.setOnClickListener(new  View.OnClickListener() {
                                    @Override

                                    public void onClick (View v)
                                    {
                                        if(check.isChecked())
                                        {




                                        }
                                        else
                                        {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(CollectingcoinActivity.this);
                                            builder.setMessage("please select Gps before sending your data");
                                            builder.setCancelable(true);

                                            AlertDialog alert = builder.create();
                                            alert.setTitle("Error");
                                            alert.show();

                                        }

                                    }
                                }

        );
    }
}
