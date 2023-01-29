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
    private String TAG = "so47492459";
    int i;
    LatLng departure;
    LatLng destination;

    public MapFragment() {
        this.departure = null;
        this.destination = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listPoints = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_map, vg, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapId);
        supportMapFragment.getMapAsync(this);
        SearchView departureEditText = view.findViewById(R.id.search_departure);
        SearchView destinationEditText = view.findViewById(R.id.search_destination);
        i=0;

        Button button = view.findViewById(R.id.searchbutton);
        Button buttonOrder = view.findViewById(R.id.orderbutton);
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

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

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


                        //Execute Directions API request
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


//        departureEditText.setOnQueryTextListener(new departureEditText.OnQueryTextListener() {
//            public boolean onQueryTextSubmit(String query) {
//                String location = departureEditText.getQuery().toString();
//                List<Address> addressList = null;
//                if (location != null || !location.equals("")) {
//                    Geocoder geocoder = new Geocoder(view.getContext());
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//        });
//    }


//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//    }

    private String getRequestUrl(LatLng origin, LatLng destination) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + ", " + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + destination.latitude + ", " + destination.longitude;
        //Set value enable the sensor
        String sensor = "sensor-false";
        String mode = "mode=driving";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/directions/" + output + "?" + param;
        return url;

    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);

            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    /**
     * KAda je mapa spremna mozemo da radimo sa njom.
     * Mozemo reagovati na razne dogadjaje dodavanje markera, pomeranje markera,klik na mapu,...
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        Location location = null;

        boolean successs = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));

        if (provider == null) {
            Log.i("ASD", "Onmapre");

            showLocatonDialog();
        } else {
            if (checkLocationPermission()) {
                Log.i("ASD", "str" + provider);

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //Request location updates:
                    location = locationManager.getLastKnownLocation(provider);
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //Request location updates:
                    location = map.getMyLocation();
                }
            }
        }

        //ako zelimo da rucno postavljamo markere to radimo
        //dodavajuci click listener
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (listPoints.size() == 2) {
                    listPoints.clear();
                    map.clear();
                }

                listPoints.add(latLng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listPoints.size() == 1) {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng).zoom(14).build();

                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng).zoom(14).build();

                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                if(i<2) {
                    map.addMarker(markerOptions);
                    i += 1;
                }else{
                    map.clear();
                    i=0;
                    map.addMarker(markerOptions);
                    i+=1;
                }

                if (listPoints.size() == 2) {
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                }
            }
        });

        //ako zelmo da reagujemo na klik markera koristimo marker click listener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("ASD", "ASDASDASDSA");
            }
        });

        //ako je potrebno da reagujemo na pomeranje markera koristimo marker drag listener
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


    /**
     * Kada zelmo da dobijamo informacije o lokaciji potrebno je da specificiramo
     * po kom kriterijumu zelimo da dobijamo informacije GSP, MOBILNO(WIFI, MObilni internet), GPS+MOBILNO
     **/
    private void createMapFragmentAndInflate() {

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mMapFragment).commit();
        mMapFragment.getMapAsync(this);
    }

    private void showLocatonDialog() {
        if (dialog == null) {
            dialog = new LocationDialog(getActivity()).prepareDialog();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog.show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        createMapFragmentAndInflate();

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("gps", String.valueOf(gps));
        Log.i("wifi", String.valueOf(wifi));
        if (!gps && !wifi) {
            Log.i("resumemap", "Resume Map");
            showLocatonDialog();
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

    /**
     * Svaki put kada uredjaj dobijee novu informaciju o lokaciji ova metoda se poziva
     * i prosledjuje joj se nova informacija o kordinatamad
     */
//    @Override
//    public void onLocationChanged(Location location) {
//        Toast.makeText(getActivity(), "NEW LOCATION", Toast.LENGTH_SHORT).show();
//        if (map != null) {
//            addMarker(location);
//        }
//    }
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

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
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
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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

    /**
     * Rad sa lokacja izuzetno trosi bateriju.Obavezno osloboditi kada vise ne koristmo
     */
    @Override
    public void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }



}