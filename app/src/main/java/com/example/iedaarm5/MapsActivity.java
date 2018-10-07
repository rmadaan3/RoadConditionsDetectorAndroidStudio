package com.example.iedaarm5;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private DatabaseReference mDatabase;
    private DatabaseReference refDatabase;
    public ArrayList<WeightedLatLng> ll = new ArrayList<>();
    public ArrayList<WeightedLatLng> ln = new ArrayList<>();
    public int[] color = {Color.rgb(0,0,255)};
    public float[] startPoints= {0.2f};
    public Gradient gradient = new Gradient(color,startPoints);
    public int[] color1 = {Color.rgb(255,0,0)};
    public Gradient gradient1 = new Gradient(color1,startPoints);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        refDatabase = mDatabase.child("logs");
        refDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String tes = child.getValue(String.class);
                    String test = tes;
                    String array1[]= test.split(",");
                    LatLng C5 = new LatLng(Float.valueOf(array1[1]), Float.valueOf(array1[2]));
                    float intens = Float.valueOf(array1[0]);
                    if (intens>=0) {
                        WeightedLatLng data5 = new WeightedLatLng(C5, intens);
                        ll.add(data5);
                    }
                    else{
                        WeightedLatLng data5 = new WeightedLatLng(C5, Math.abs(intens));
                        ln.add(data5);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(mMap==null) return;

                LatLng View = new LatLng(28.5455096,77.2723653);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String tes = child.getValue(String.class);
                    String test = tes;
                    String array1[]= test.split(",");
                    LatLng C5 = new LatLng(Float.valueOf(array1[1]), Float.valueOf(array1[2]));
                    float intens = Float.valueOf(array1[0]);
                    if (intens>=0) {
                        WeightedLatLng data5 = new WeightedLatLng(C5, intens);
                        ll.add(data5);
                    }
                    else{
                        WeightedLatLng data5 = new WeightedLatLng(C5, Math.abs(intens));
                        ln.add(data5);
                    }
                    View = C5;
                }
                mProvider = new HeatmapTileProvider.Builder().weightedData(ll).gradient(gradient1).build();
                mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                mProvider = new HeatmapTileProvider.Builder().weightedData(ln).gradient(gradient).build();
                mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(View,25));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addHeatMap();
        LocationManager locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        LatLng iiitdelhi = new LatLng(28.5455096,77.2723653);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iiitdelhi,13));
    }

    public void addHeatMap()
    {
        if(mMap == null) return;

        LatLng C1 = new LatLng(28.545139, 77.272703);
        LatLng C2 = new LatLng(28.546117, 77.272901);
        LatLng C3 = new LatLng(28.544938, 77.271444);
        WeightedLatLng data1 = new WeightedLatLng(C1, 3);
        WeightedLatLng data2 = new WeightedLatLng(C2, 2);
        WeightedLatLng data3 = new WeightedLatLng(C3, 2.5);
        LatLng C9 = new LatLng(28.542300, 77.272800);
        WeightedLatLng data9 = new WeightedLatLng(C9, 3);
        ll.add(data1);
        ll.add(data2);
        ll.add(data3);
        ln.add(data9);
        mProvider = new HeatmapTileProvider.Builder().weightedData(ll).gradient(gradient1).build();
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        mProvider = new HeatmapTileProvider.Builder().weightedData(ln).gradient(gradient).build();
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}