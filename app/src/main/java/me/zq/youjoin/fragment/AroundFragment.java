package me.zq.youjoin.fragment;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import me.zq.youjoin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AroundFragment extends BaseFragment {

    private LocationManager locationManager;
    private String provider;

    public AroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_around, container, false);
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        getLocation();
    }

    private void getLocation(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        List<String> providerList = locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        } else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        } else{
            Toast.makeText(getActivity(), "No Location Provider to Use", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            Location location = locationManager.getLastKnownLocation(provider);
            if(location != null){
                onGetLocation(location);
            }
            locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //更新当前设备位置信息
            onGetLocation(location);
        }
    };

    private void onGetLocation(Location location){
        // TODO: 2016/5/8 成功获取到当前经纬度坐标后的处理逻辑
        String currentLocation = "latitude is : " + location.getLatitude() + "\n"
                + "longitude is : " + location.getLongitude();
        Log.d(TAG, currentLocation);
    }

}