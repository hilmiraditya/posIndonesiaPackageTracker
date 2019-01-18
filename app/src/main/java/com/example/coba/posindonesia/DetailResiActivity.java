package com.example.coba.posindonesia;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.directions.route.Routing;
import com.google.android.gms.maps.GoogleMap;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.OnMapRenderListener;
import com.here.android.mpa.routing.RouteManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DetailResiActivity extends AppCompatActivity{

//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<String> dataSet;

//    @Override
//    public void onMapReady(Map map) {
//        map.setCenter(new GeoCoordinate(37.7397, -121.4252, 0.0), Map.Animation.NONE);
//        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
//    }

    private Map map = null;
    private MapFragment mapFragment = null;
    private MapObject mapObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resi);
        final OnMapReadyCallback onMapReadyCallback = null;
        TextView noDetailResi = this.findViewById(R.id.noDetailResi);
        Log.i("SRRRR", getIntent().getStringExtra("NoResi"));
        noDetailResi.setText(getIntent().getStringExtra("NoResi"));

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        getWindow().setAllowReturnTransitionOverlap(false);

//        detailResiBtn.findViewById(R.id.detailResiBtn);
//        detailResiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottom = new DetailBottomSheet();
//                bottom.show(getSupportFragmentManager(), bottom.getTag());
//            }
//        });

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    map = mapFragment.getMap();
                    map.setCenter(new GeoCoordinate(-6.874230, 107.616400), Map.Animation.NONE);
                    map.setZoomLevel(map.getMaxZoomLevel());
                    

                }else{
                    Log.i("HERE ERR", error.getDetails());
                }
            }
        });

        Button button = (Button) findViewById(R.id.detailResiBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottom = new DetailBottomSheet();
                bottom.show(getSupportFragmentManager(),bottom.getTag());
            }
        });

//        FloatingActionButton refresh_button = (FloatingActionButton) findViewById(R.id.refresh_button);
//        refresh_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //do here
//            }
//        });

    }






//        ///adapter///
//        dataSet = new ArrayList<>();
//        setDataSet();
//        recyclerView = new RecyclerView(DetailResiActivity.this);
//        recyclerView.findViewById(R.id.detil_recycleview);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerViewAdapter(dataSet);
//        recyclerView.setAdapter(adapter);
//        ////
    }

//    private void setDataSet(){
//        dataSet.add("HALO SATU DUA TIGA");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//        dataSet.add("hehehe");
//    }


