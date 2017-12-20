package btu.treasurehunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dez on 12/5/2017.
 */

public class Collectingcoins extends Fragment   {

    private Button send;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.collecting_coins, container, false);
    ListView list = (ListView) rootView.findViewById(R.id.list);
        SensorManager sManager = (SensorManager)this.getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        List<Sensor> sList =  sManager.getSensorList(Sensor.TYPE_ALL);

        send =(Button)rootView.findViewById(R.id.sendInfo);

        List<String> sNames = new ArrayList();
        for (int i = 0 ; i< sList.size() ; i++)
        {
            if(sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_LIGHT)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_PRESSURE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_GRAVITY))
                sNames.add(((Sensor) sList.get(i)).getType()==Sensor.TYPE_ACCELEROMETER?"Accelerometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_MAGNETIC_FIELD?"Magnetometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_GYROSCOPE?"Gyroscope":((Sensor) sList.get(i)).getType()==Sensor.TYPE_LIGHT?"Light Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_PRESSURE?"Barometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_PROXIMITY?"Proximity Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_AMBIENT_TEMPERATURE?"Thermometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_ROTATION_VECTOR?"Rotation Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_STEP_COUNTER?"Pedometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_GRAVITY?"Gravity Sensor":"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice, sNames );
       // setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice, sNames ));
        list.setAdapter(adapter);
      // getListView().setTextFilterEnabled(true);
        listener();

        return rootView;


    }

    public void listener ()
    {


        send.setOnClickListener(new  View.OnClickListener() {
                                    @Override

                                    public void onClick (View v)
                                    {


                                            Toast.makeText(getContext() , " Sending Data" , Toast.LENGTH_SHORT).show();



                                    }
                                }

        );
    }
}
