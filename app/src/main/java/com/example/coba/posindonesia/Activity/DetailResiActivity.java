package com.example.coba.posindonesia.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coba.posindonesia.Fragment.DetailBottomSheet;
import com.example.coba.posindonesia.Fragment.InfoKurirBottomSheet;
import com.example.coba.posindonesia.Interfaces.RequestAPI;
import com.example.coba.posindonesia.Maps.MapsF;
import com.example.coba.posindonesia.Model.Resi;
import com.example.coba.posindonesia.R;
import com.example.coba.posindonesia.Session.SessionManager;
import com.example.coba.posindonesia.Url.BaseUrl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.here.android.mpa.mapping.MapCircle;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.MapObject;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.style.sources.TileSet;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.round;
import static java.util.Objects.*;

public class DetailResiActivity extends AppCompatActivity{
    private Map map = null;
    MapsF mapsF  = new MapsF();
    private MapObject mapObject;
    BaseUrl baseUrl = new BaseUrl();
    View v;
    private Marker marker_inter;
    private MapView mapView;
    public Double kurirLong, kurirLat;
    public Bundle tempBundlel;
    private BuildingPlugin buildingPlugin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Mapbox.getInstance(this,
                "pk.eyJ1IjoiaGlsbWlyYWRpdHlhIiwiYSI6ImNqcjh2eTJlczAxeDg0M2xoNDU4eWxlMDMifQ.8A34sWyzuIKrsioK-zrAqg");
        Mapbox.setAccessToken("pk.eyJ1IjoiaGlsbWlyYWRpdHlhIiwiYSI6ImNqcjh2eTJlczAxeDg0M2xoNDU4eWxlMDMifQ.8A34sWyzuIKrsioK-zrAqg");


        sessionManager = new SessionManager(DetailResiActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resi);
        load_maps_view();
        final OnMapReadyCallback onMapReadyCallback = null;
        TextView noDetailResi = this.findViewById(R.id.noDetailResi);
        Log.i("SRRRR", getIntent().getStringExtra("NoResi"));
        noDetailResi.setText(getIntent().getStringExtra("NoResi"));

        Log.i("DARI MAIN LAT", getIntent().getStringExtra("latitude"));
        Log.i("DARI MAIN LONG", getIntent().getStringExtra("longitude"));

        getDetailResi(getIntent().getStringExtra("NoResi"),getIntent().getStringExtra("longitude"),getIntent().getStringExtra("latitude"));

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        getWindow().setAllowReturnTransitionOverlap(false);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
        if (kurirLat == null){
            Toast.makeText(DetailResiActivity.this,"Slow Connection",Toast.LENGTH_LONG).show();
            finish();

        }else{
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(kurirLat, kurirLong))
                                .zoom(10)
                                .tilt(20)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
                        marker_inter = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(kurirLat,kurirLong))
                                .title("Lokasi Kurir")
                                .snippet("Fariz Putra Dandi")
                        );
                    }
                });
            }
        });
        }
            }
        }, 300000);

        ///end///

        Button button = (Button) findViewById(R.id.detailResiBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottom = new DetailBottomSheet();
                bottom.show(getSupportFragmentManager(),bottom.getTag());
            }
        });

        Button button2 = (Button) findViewById(R.id.infokurirBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottom = new InfoKurirBottomSheet();
                bottom.show(getSupportFragmentManager(),bottom.getTag());
            }
        });

        FloatingActionButton refresh_button = (FloatingActionButton) findViewById(R.id.refresh_button);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker_inter.remove();
                getDetailResi(getIntent().getStringExtra("NoResi"),getIntent().getStringExtra("longitude"),getIntent().getStringExtra("latitude"));
            }
        });



    }

    protected void loadMap(final Double newLat, final Double newLong){
                Toast.makeText(DetailResiActivity.this,newLat + " , " + newLong, Toast.LENGTH_LONG).show();
                mapView = (MapView) findViewById(R.id.mapView);
                mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                CameraPosition position = new CameraPosition.Builder()
                                        .target(new LatLng(newLat, newLong))
                                        .zoom(10)
                                        .tilt(20)
                                        .build();
                                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
                                marker_inter = mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(newLat, newLong))
                                        .title("Lokasi Kurir")
                                        .snippet("Fariz Putra Dandi")
                                );
                            }
                        });
                    }
        });

    }


    private String timeFormatHelper(Double tot_seconds){
        Double p1 = tot_seconds % 60;
        Double p2 = tot_seconds / 60;
        Double p3 = p2 % 60;
        p2 = p2 / 60;
        return String.format("Estimation : %02d Hour %02d Minutes", Math.round(p2), Math.round(p3));
    }

    protected void getDetailResi(final String noResi, String lon, String lat){


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<Resi> resiCall = requestAPI.getResi(noResi, lon, lat);
        resiCall.enqueue(new Callback<Resi>() {
            @Override
            public void onResponse(Call<Resi> call, Response<Resi> response) {
                Log.i("succ", response.message());

                if(response.body() == null){
                    Log.i("succ", "null");
                    Toast.makeText(DetailResiActivity.this,"Server Error", Toast.LENGTH_LONG).show();
                    finish();
                }else if (response.body().getError().equals("3")){
                    Log.i("succ", "null");
                    Toast.makeText(DetailResiActivity.this,"Receipt Not Found", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    TextView etaResi = findViewById(R.id.ETA);

                    View dsV = new DetailBottomSheet().getView();

                    String summ = response.body().getMessage().getSum_packet_delivered() + "/" + response.body().getMessage().getTotal_packet();
                    Double eta = response.body().getMessage().getEstimation_time();
                    etaResi.setText(timeFormatHelper(eta));
                    sessionManager.setSummary(summ);
                    int numm  = Integer.parseInt(response.body().getMessage().getYour_packet());
                    sessionManager.setYours(response.body().getMessage().getYour_packet() + getOrdinalFor(numm));
                    sessionManager.setResi(noResi);
                    sessionManager.setLatitude(response.body().getMessage().getLatitude().toString());
                    sessionManager.setLongitude(response.body().getMessage().getLongitude().toString());
                    kurirLat = response.body().getMessage().getLatitude();
                    kurirLong = response.body().getMessage().getLongitude();
                    loadMap(response.body().getMessage().getLatitude(), response.body().getMessage().getLongitude());
                }
            }
            @Override
            public void onFailure(Call<Resi> call, Throwable t) {
                Log.i("errr", t.getMessage());
                Toast.makeText(DetailResiActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    public static String getOrdinalFor(int value) {
        int tenRemainder = value % 10;
        switch (tenRemainder) {
            case 1:
                return "ST";
            case 2:
                return "ND";
            case 3:
                return "RD";
            default:
                return "TH";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void load_maps_view(){
        ViewStub stub = (ViewStub) findViewById(R.id.MAP_ETA);
        stub.setLayoutResource(R.layout.maps_eta);
        v = stub.inflate();
    }

}

