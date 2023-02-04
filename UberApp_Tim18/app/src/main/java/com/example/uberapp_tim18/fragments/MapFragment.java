


package com.example.uberapp_tim18.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.dialog.LocationDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import DTO.LocationDTO;
import DTO.LocationSetDTO;


public class MapFragment extends Fragment implements LocationListener, OnMapReadyCallback {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private LocationManager locationManager;
    private String provider;
    private SupportMapFragment mMapFragment;
    private AlertDialog dialog;
    private Marker home;
    private GoogleMap map;
    ArrayList<LatLng> listPoints;
    private LatLng departure;
    private LatLng destination;
    private String TAG = "so47492459";
    private int i;

    private RelativeLayout l;

    private Button button;
    private Button buttonOrder;

    private SearchView departureEditText;
    private SearchView destinationEditText;
    private View rootView;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private SupportMapFragment supportMapFragment;
    private int flag;

    public MapFragment() {
        this.departure = null;
        this.destination = null;
        this.flag = 1;
    }

    public MapFragment(int flag) {
        this.departure = null;
        this.destination = null;
        this.flag = flag;
    }

    public MapFragment(LatLng departure,LatLng destination) {
        this.departure = departure;
        this.destination = destination;
        this.flag = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listPoints = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        initGui(inflater, vg);
        return rootView;
    }

    public void initGui(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        departureEditText = rootView.findViewById(R.id.search_departure);
        destinationEditText = rootView.findViewById(R.id.search_destination);
        RelativeLayout l = rootView.findViewById(R.id.relativeZ);
        button = rootView.findViewById(R.id.searchbutton);
        buttonOrder = rootView.findViewById(R.id.orderbutton);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mMapFragment).commit();
        mMapFragment.getMapAsync(this);
        i=0;
        if(flag == 1){
            l.setTranslationZ(1000);
        }else{
            drawRoute();
            createRide();
        }

    }
    public void createRide(){
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                String locationDeparture = departureEditText.getQuery().toString();
                String locationDestination = destinationEditText.getQuery().toString();
                LocationSetDTO locationSetDTO = new LocationSetDTO();
                MarkerOptions markerOptions = new MarkerOptions();

                List<Address> addressList = null;
                if (locationDeparture != null || !locationDeparture.equals("")) {
                    Geocoder geocoder = new Geocoder(view.getContext());
                    try {
                        addressList = geocoder.getFromLocationName(locationDeparture, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address addressDeparture = addressList.get(0);
                    LatLng latLngDeparture = new LatLng(addressDeparture.getLatitude(), addressDeparture.getLongitude());
                    LocationDTO locationDTODeparture = new LocationDTO(addressDeparture.getAddressLine(0),latLngDeparture.latitude,latLngDeparture.longitude);
                    locationSetDTO.setDeparture(locationDTODeparture);
                    if (locationDestination != null || !locationDestination.equals("")) {
                        geocoder = new Geocoder(view.getContext());
                        try {
                            addressList = geocoder.getFromLocationName(locationDestination, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address addressDestination = addressList.get(0);
                        LatLng latLngDestination = new LatLng(addressDestination.getLatitude(), addressDestination.getLongitude());
                        LocationDTO locationDTODestination = new LocationDTO(addressDestination.getAddressLine(0),latLngDestination.latitude,latLngDestination.longitude);
                        locationSetDTO.setDestination(locationDTODestination);
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(locationSetDTO);
                editor.putString("object", json);
                editor.apply();
                CreateRide newFragment = new CreateRide();
                fragmentTransaction.replace(R.id.fragment_container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);

        Location location = null;
        boolean successs = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(departure!= null){
            drawCurrentRide(this.departure,this.destination);
        } else if (location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("ASD", "ASDASDASDSA");
            }
        });

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(getActivity(), "Drag started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Toast.makeText(getActivity(), "Dragging", Toast.LENGTH_SHORT).show();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getActivity(), "Drag ended", Toast.LENGTH_SHORT).show();
            }
        });

        if (location != null) {

        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("gps", String.valueOf(gps));
        Log.i("wifi", String.valueOf(wifi));
        if (!gps && !wifi) {
            Log.i("resumemap", "Resume Map");
        } else {
            if (checkLocationPermission()) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 2000L, 0, this);
                    Toast.makeText(getContext(), "ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 2000, 0, this);
                    Toast.makeText(getContext(), "ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
    }
    public void drawCurrentRide(LatLng departure,LatLng destination){
        MarkerOptions markerOptions = new MarkerOptions();
        if(i<2){
            markerOptions.position(departure);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            map.addMarker(markerOptions);
            i+=1;
        }else{
            map.clear();
            i=0;
            markerOptions.position(departure);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            map.addMarker(markerOptions);
        }
        markerOptions.position(destination);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(markerOptions);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 15));
        i+=1;

        List<LatLng> path = new ArrayList();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDYRxUrFDTe31EDYIsyG8q5QnRo4VY0zWc")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, departure.latitude + "," + departure.longitude,
                destination.latitude + "," + destination.longitude);
        LatLng zoom = new LatLng((departure.latitude+destination.latitude)/2,(departure.longitude+destination.longitude)/2);

        try {
            DirectionsResult res = req.await();
            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }

        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            map.addPolyline(opts);
        }
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 13));
    }
    public void drawRoute(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationDeparture = departureEditText.getQuery().toString();
                String locationDestination = destinationEditText.getQuery().toString();
                MarkerOptions markerOptions = new MarkerOptions();

                List<Address> addressList = null;
                if (locationDeparture != null || !locationDeparture.equals("")) {
                    Geocoder geocoder = new Geocoder(view.getContext());
                    try {
                        addressList = geocoder.getFromLocationName(locationDeparture, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address addressDeparture = addressList.get(0);
                    LatLng latLngDeparture = new LatLng(addressDeparture.getLatitude(), addressDeparture.getLongitude());
                    if(i<2){
                        markerOptions.position(latLngDeparture);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        map.addMarker(markerOptions);
                        i+=1;
                    }else{
                        map.clear();
                        i=0;
                        markerOptions.position(latLngDeparture);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        map.addMarker(markerOptions);
                    }

                    if (locationDestination != null || !locationDestination.equals("")) {
                        geocoder = new Geocoder(view.getContext());
                        try {
                            addressList = geocoder.getFromLocationName(locationDestination, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address addressDestination = addressList.get(0);
                        LatLng latLngDestination = new LatLng(addressDestination.getLatitude(), addressDestination.getLongitude());
                        markerOptions.position(latLngDestination);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        map.addMarker(markerOptions);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngDestination, 15));
                        i+=1;
                        System.out.println(latLngDeparture + "    " + latLngDestination);
                        List<LatLng> path = new ArrayList();

                        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey("AIzaSyDYRxUrFDTe31EDYIsyG8q5QnRo4VY0zWc")
                                .build();
                        DirectionsApiRequest req = DirectionsApi.getDirections(context, latLngDeparture.latitude + "," + latLngDeparture.longitude,
                                latLngDestination.latitude + "," + latLngDestination.longitude);
                        LatLng zoom = new LatLng((latLngDeparture.latitude+latLngDestination.latitude)/2,(latLngDeparture.longitude+latLngDestination.longitude)/2);

                        try {
                            DirectionsResult res = req.await();
                            //Loop through legs and steps to get encoded polylines of each step
                            if (res.routes != null && res.routes.length > 0) {
                                DirectionsRoute route = res.routes[0];

                                if (route.legs != null) {
                                    for (int i = 0; i < route.legs.length; i++) {
                                        DirectionsLeg leg = route.legs[i];
                                        if (leg.steps != null) {
                                            for (int j = 0; j < leg.steps.length; j++) {
                                                DirectionsStep step = leg.steps[j];
                                                if (step.steps != null && step.steps.length > 0) {
                                                    for (int k = 0; k < step.steps.length; k++) {
                                                        DirectionsStep step1 = step.steps[k];
                                                        EncodedPolyline points1 = step1.polyline;
                                                        if (points1 != null) {
                                                            //Decode polyline and add points to list of route coordinates
                                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    EncodedPolyline points = step.polyline;
                                                    if (points != null) {
                                                        //Decode polyline and add points to list of route coordinates
                                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                                        for (com.google.maps.model.LatLng coord : coords) {
                                                            path.add(new LatLng(coord.lat, coord.lng));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, ex.getLocalizedMessage());
                        }

                        //Draw the polyline
                        if (path.size() > 0) {
                            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                            map.addPolyline(opts);
                        }

                        map.getUiSettings().setZoomControlsEnabled(true);

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 13));
                    }
                }
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow user location")
                        .setMessage("To continue working we need your locations....Allow now?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);}
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(provider, 0, 0, this);
                    }
                } else if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(provider, 0, 0, this);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }





}
