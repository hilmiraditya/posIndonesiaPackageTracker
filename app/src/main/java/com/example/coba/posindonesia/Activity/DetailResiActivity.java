package com.example.coba.posindonesia.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
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
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.TileSet;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailResiActivity extends AppCompatActivity{
    private Map map = null;
    MapsF mapsF  = new MapsF();
    private MapObject mapObject;
    BaseUrl baseUrl = new BaseUrl();
    View v;


    ///nyoba mapbox//
        private MapView mapView;
    /// end//

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Mapbox.getInstance(this,
                "pk.eyJ1IjoiaGlsbWlyYWRpdHlhIiwiYSI6ImNqcjh2eTJlczAxeDg0M2xoNDU4eWxlMDMifQ.8A34sWyzuIKrsioK-zrAqg");
        Mapbox.setAccessToken("pk.eyJ1IjoiaGlsbWlyYWRpdHlhIiwiYSI6ImNqcjh2eTJlczAxeDg0M2xoNDU4eWxlMDMifQ.8A34sWyzuIKrsioK-zrAqg");


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

//        mapsF.setMapFragment((MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment));
//
//        mapsF.getMapFragment().init(new OnEngineInitListener() {
//            @Override
//            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
//                if (error == OnEngineInitListener.Error.NONE) {
//                    map = mapsF.getMapFragment().getMap();
//                    map.setCenter(new GeoCoordinate(-6.874230, 107.616400), Map.Animation.NONE);
//                    map.setZoomLevel(map.getMaxZoomLevel());
//
//
//                }else{
//                    Log.i("HERE ERR", error.getDetails());
//                }
//            }
//        });


        ////mappp////
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Log.i("BISA","MAPSNYA KEREN YA WKWWK");
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(-6.8983035, 107.619494))
                                .zoom(10)
                                .tilt(20)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-6.8983035,107.619494))
                                .title("Lokasi Kurir")
                                .snippet("Fariz Putra Dandi")
                        );
                    }
                });
            }
        });


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
//                mapView.onDestroy();
            }
        });

    }

    protected void getDetailResi(final String noResi, String lon, String lat){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<Resi> resiCall = requestAPI.getResi(noResi, lon, lat);
        resiCall.enqueue(new Callback<Resi>() {
            @Override
            public void onResponse(Call<Resi> call, Response<Resi> response) {
                Log.i("succ", response.message());

                if (response.body() == null){
                    Log.i("succ", "null");
                    Toast.makeText(DetailResiActivity.this,"Resi tidak ditemukan", Toast.LENGTH_LONG).show();
                    //finish();
                }else{
                    TextView etaResi = findViewById(R.id.ETA);

                    View dsV = new DetailBottomSheet().getView();
                    SessionManager sessionManager = new SessionManager(DetailResiActivity.this);
                    String summ = response.body().getMessage().getSum_packet_delivered() + "/" + response.body().getMessage().getTotal_packet();
                    String eta = response.body().getMessage().getEstimation_time();
                    int maxLength = (eta.length() < 3)?eta.length():3;
                    String cutString = eta.substring(0, maxLength) + " Minutes";
                    etaResi.setText(cutString);
                    sessionManager.setSummary(summ);
                    int numm  = Integer.parseInt(response.body().getMessage().getYour_packet());
                    sessionManager.setYours(response.body().getMessage().getYour_packet() + getOrdinalFor(numm));
                    sessionManager.setResi(noResi);
                    sessionManager.setLatitude(response.body().getMessage().getLatitude());
                    sessionManager.setLongitude(response.body().getMessage().getLongitude());
                }
            }
            @Override
            public void onFailure(Call<Resi> call, Throwable t) {
                Log.i("errr", t.getMessage());
                Toast.makeText(DetailResiActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                //finish();

            }
        });

    }


    public static String getOrdinalFor(int value) {
        int tenRemainder = value % 10;
        switch (tenRemainder) {
            case 1:
                return " st";
            case 2:
                return " nd";
            case 3:
                return " rd";
            default:
                return " th";
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