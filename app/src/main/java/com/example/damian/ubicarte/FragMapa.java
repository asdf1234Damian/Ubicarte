package com.example.damian.ubicarte;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragMapa extends Fragment {
    private GoogleMap googleMap;
    private MapView mMapView;
    private Marker marker;
    private CameraPosition cameraPosition;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frag_mapa, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
                cameraPosition = new CameraPosition.Builder().target(new LatLng(19.4326,-99.1332)).zoom(10).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot Vehicles = dataSnapshot.child("Vehicles");
                for (DataSnapshot ds: Vehicles.getChildren()){
                    if (ds.child("Propietario").getValue().toString().equals(Global.id)){
                        Global.vehiculos.add(new Vehicle());
                        marker.setTitle(ds.child("Modelo").getValue().toString());
                        marker.setSnippet(ds.child("Placas").getValue().toString());
                        double lat = Double.valueOf(ds.child("lat").getValue().toString());
                        double lng = Double.valueOf(ds.child("lng").getValue().toString());
                        marker.setPosition(new LatLng(lat,lng));
                        //TODO a ver si se puede hacer que la camara siga el marcador :c
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}