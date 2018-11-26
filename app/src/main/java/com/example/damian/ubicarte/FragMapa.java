package com.example.damian.ubicarte;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragMapa extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private DatabaseReference mDatabase;
    private String Car="Aqui va el modelo", plates="Aqui van las placas";
    private LatLng currentPos= new LatLng(0,0);
    private MarkerOptions marker;
    LatLng Emilianoano= new LatLng(19.608236333333334, -99.24984083333334);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frag_mapa, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        marker = new MarkerOptions();
        marker.position(currentPos);
        marker.title(Car);
        marker.snippet(plates);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        ValueEventListener positionListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicle v =dataSnapshot.getValue(Vehicle.class);
                //Toast.makeText(getActivity(), v.getModelo()+v.getPlacas(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };


        mDatabase= FirebaseDatabase.getInstance().getReference();
        /*
        mDatabase.addValueEventListener(positionListener);*/


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.addMarker(marker);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPos).zoom(17).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });





        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.getKey();
        marker.position(Emilianoano);
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