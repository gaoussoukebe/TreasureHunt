package btu.treasurehunt;

/**
 * Created by Dez on 1/8/2018.
 */
import android.app.Application;
import android.content.Context;
import android.location.Location;

public class MyApplication extends Application {


    private static MyApplication mInstance;
    private Location currentlocation;
    private boolean TreasureFound;
    private Context MainActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public Location getCurrentLocation() {
        return currentlocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentlocation = currentLocation;
    }
    public boolean getTreasureFound() {
        return TreasureFound;
    }

    public void setTreasureFound(boolean TreasureFound) {
        this.TreasureFound = TreasureFound;
    }
    public Context getMainActivity() {
        return MainActivity;
    }

    public void setMainActivity(Context MainActivity) {
        this.MainActivity = MainActivity;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
