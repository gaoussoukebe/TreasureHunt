package btu.treasurehunt;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
            if(sList.get(i)==sManager.getDefaultSensor(1)||sList.get(i)==sManager.getDefaultSensor(2)||sList.get(i)==sManager.getDefaultSensor(4)||sList.get(i)==sManager.getDefaultSensor(5)||sList.get(i)==sManager.getDefaultSensor(6)||sList.get(i)==sManager.getDefaultSensor(8)||sList.get(i)==sManager.getDefaultSensor(13))
                sNames.add(((Sensor) sList.get(i)).getType()==1?"Accelerometer":((Sensor) sList.get(i)).getType()==2?"Magnetometer":((Sensor) sList.get(i)).getType()==4?"Gyroscope":((Sensor) sList.get(i)).getType()==5?"Light Sensor":((Sensor) sList.get(i)).getType()==6?"Barometer":((Sensor) sList.get(i)).getType()==8?"Proximity Sensor":((Sensor) sList.get(i)).getType()==13?"Thermometer":"");
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
