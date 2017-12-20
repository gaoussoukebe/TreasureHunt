package btu.treasurehunt;


import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Dez on 12/5/2017.
 */

public class Collectingcoins extends  Fragment implements SensorEventListener {
    ArrayList<String> checked = new ArrayList<>();
    private Button send;
    List<String> sNames;
    List<Sensor> sList;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ListView list;
    SensorManager sManager;
    float valueLight,valueThermometer,valueStepCounter , valueProximity, valueBarometer,
            valueXaccelerometer , valueYaccelerometer , valueZaccelerometer,
            valueXmagnetometer, valueYMagnetometer , valueZmagnetometer,
            valueXgyroscope , valueYgyroscope, valueZgyroscope,
            valueXgravity , valueYgravity, valueZgravity,
            valueXrotation , valueYrotation , valueZrotation;

    Sensor mlight , mTemperature , mPedometer , mAccelerometer , mMagnetometer , mGyroscope,mProximity ,mGravity , mRotation ,mBarometer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.collecting_coins, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        sManager = (SensorManager)this.getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        sList =  sManager.getSensorList(Sensor.TYPE_ALL);
        send =(Button)rootView.findViewById(R.id.sendInfo);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mlight= sManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTemperature = sManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mPedometer = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mAccelerometer =sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer= sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope= sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mProximity =sManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mGravity =sManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mRotation =sManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mBarometer=sManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sNames = new ArrayList();
        for (int i = 0 ; i< sList.size() ; i++)
        {
            if(sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_LIGHT)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_PRESSURE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)||sList.get(i)==sManager.getDefaultSensor(Sensor.TYPE_GRAVITY))
                sNames.add(((Sensor) sList.get(i)).getType()==Sensor.TYPE_ACCELEROMETER?"Accelerometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_MAGNETIC_FIELD?"Magnetometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_GYROSCOPE?"Gyroscope":((Sensor) sList.get(i)).getType()==Sensor.TYPE_LIGHT?"Light Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_PRESSURE?"Barometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_PROXIMITY?"Proximity Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_AMBIENT_TEMPERATURE?"Thermometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_ROTATION_VECTOR?"Rotation Sensor":((Sensor) sList.get(i)).getType()==Sensor.TYPE_STEP_COUNTER?"Pedometer":((Sensor) sList.get(i)).getType()==Sensor.TYPE_GRAVITY?"Gravity Sensor":"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice, sNames );
        // setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice, sNames ));
        list.setAdapter(adapter);
        // getListView().setTextFilterEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent , View view , int position , long id)
            {
                String selectedItem = ((TextView) view).getText().toString();

                if(checked.contains(selectedItem))
                {
                    checked.remove(selectedItem);
                }
                else
                    checked.add(selectedItem);
            }

        } );
        listener();

        return rootView;


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor , int accuracy)
    {

    }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
       /* valueLight = event.values[0];
        valueThermometer = event.values[0];
        valueStepCounter=event.values[0];
*/

        int sensorType = event.sensor.getType();


        if (sensorType == Sensor.TYPE_LIGHT)
        {
            valueLight= event.values[0];
        }

        if (sensorType == Sensor.TYPE_STEP_COUNTER)
        {
            valueStepCounter= event.values[0];
        }
        if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE)
        {
            valueThermometer= event.values[0];
        }
        if(sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            valueXaccelerometer = event.values[0];
            valueYaccelerometer = event.values[1];
            valueZaccelerometer = event.values[2];
        }
        if(sensorType == Sensor.TYPE_MAGNETIC_FIELD)
        {
            valueXmagnetometer = event.values[0];
            valueYMagnetometer = event.values[1];
            valueZmagnetometer = event.values[2];
        }
        if(sensorType == Sensor.TYPE_GYROSCOPE)
        {
            valueXgyroscope = event.values[0];
            valueYgyroscope = event.values[1];
            valueZgyroscope = event.values[2];
        }
        if (sensorType == Sensor.TYPE_PROXIMITY)
        {
            valueProximity= event.values[0];
        }
        if(sensorType == Sensor.TYPE_GRAVITY)
        {
            valueXgravity = event.values[0];
            valueYgravity = event.values[1];
            valueZgravity = event.values[2];
        }

        if(sensorType == Sensor.TYPE_ROTATION_VECTOR)
        {
            valueXrotation = event.values[0];
            valueYrotation = event.values[1];
            valueZgravity = event.values[2];
        }

        if (sensorType == Sensor.TYPE_PRESSURE)
        {
            valueProximity= event.values[0];
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        sManager.registerListener(this,mlight, SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mTemperature,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mPedometer,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mProximity,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mGravity,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,mRotation, SensorManager.SENSOR_DELAY_NORMAL );
        sManager.registerListener(this,mBarometer , SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        sManager.unregisterListener(this);
    }

    public void listener ()
    {


        send.setOnClickListener(new  View.OnClickListener() {
                                    @Override

                                    public void onClick (View v)
                                    {
                                        String items ="";
                                        for(String item:checked)
                                        {
                                            items+= "-"+ item + "\n";
                                        }

                                        Toast.makeText(getContext(),"you have selected \n" + items, Toast.LENGTH_LONG ).show();

                                        String itemcheck ;
                                        for(int i = 0 ; i<checked.size() ; i++)
                                        {
                                            if(checked.get(i) == "Accelerometer")
                                            {
                                                float valuex = valueXaccelerometer;
                                                float valuey =valueYaccelerometer;
                                                float valuez = valueZaccelerometer;
                                                Toast.makeText(getContext(), "value of accelerometer is : \n x: " + valuex + "\n y: " + valuey + "\n z: " + valuez  , Toast.LENGTH_LONG).show();

                                            }

                                            if(checked.get(i) == "Magnetometer")
                                            {
                                                float valuex = valueXmagnetometer;
                                                float valuey =valueYMagnetometer;
                                                float valuez = valueZmagnetometer;
                                                Toast.makeText(getContext(), "value of magnetometer is : \n x: " + valuex + "\n y: " + valuey + "\n z: " + valuez  , Toast.LENGTH_LONG).show();


                                            }
                                            if(checked.get(i) == "Gyroscope")
                                            {
                                                float valuex = valueXgyroscope;
                                                float valuey =valueXgyroscope;
                                                float valuez = valueZgyroscope;
                                                Toast.makeText(getContext(), "value of gyroscope is : \n x: " + valuex + "\n y: " + valuey + "\n z: " + valuez  , Toast.LENGTH_LONG).show();

                                            }

                                            if(checked.get(i) == "Proximity Sensor")
                                            {
                                                float Value = valueProximity ;
                                                Toast.makeText(getContext(), "value of Proximity Sensor is : " + Value , Toast.LENGTH_SHORT).show();

                                            }
                                            if(checked.get(i) == "Light Sensor")
                                            {


                                                float Value = valueLight ;
                                                Toast.makeText(getContext(), "value of Light Sensor is : " + Value , Toast.LENGTH_SHORT).show();

                                            }

                                            if(checked.get(i) == "Gravity Sensor")
                                            {
                                                float valuex = valueXgravity;
                                                float valuey =valueYgravity;
                                                float valuez = valueZgravity;
                                                Toast.makeText(getContext(), "value of gravity is : \n x: " + valuex + "\n y: " + valuey + "\n z: " + valuez  , Toast.LENGTH_LONG).show();

                                            }

                                            if(checked.get(i) == "Rotation Sensor")
                                            {
                                                float valuex = valueXrotation;
                                                float valuey =valueYrotation;
                                                float valuez = valueZrotation;
                                                Toast.makeText(getContext(), "value of Rotation is : \n x: " + valuex + "\n y: " + valuey + "\n z: " + valuez  , Toast.LENGTH_LONG).show();


                                            }
                                            if(checked.get(i) == "Pedometer")
                                            {
                                                float Value = valueStepCounter ;
                                                Toast.makeText(getContext(), "value of Pedometer is : " + Value , Toast.LENGTH_SHORT).show();

                                            }
                                            if(checked.get(i) == "Barometer")
                                            {
                                                float Value = valueBarometer ;
                                                Toast.makeText(getContext(), "value of Barometer is : " + Value , Toast.LENGTH_SHORT).show();

                                            }

                                            if(checked.get(i) == "Thermometer")
                                            {
                                                float Value = valueThermometer ;
                                                Toast.makeText(getContext(), "value of Thermometer is : " + Value , Toast.LENGTH_SHORT).show();

                                            }
                                        }

               /* send.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                Toast.makeText(getContext() , " Sending Data" , Toast.LENGTH_SHORT).show();


                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus < 100) {
                            progressStatus += 1;
                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                   // textView.setText(progressStatus+"/"+progressBar.getMax());
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

*/
                                    }
                                }

        );
    }
}

