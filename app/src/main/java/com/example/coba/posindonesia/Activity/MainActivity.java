package com.example.coba.posindonesia.Activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coba.posindonesia.Maps.MapsF;
import com.example.coba.posindonesia.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.here.android.mpa.mapping.MapFragment;

public class MainActivity extends AppCompatActivity {
    View v;
    private static final int REQUEST_CODE_PERMISSION = 1;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Double latitude, longitude;
    MapsF mapsF = new MapsF();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        getWindow().setAllowReturnTransitionOverlap(false);
        detailResiTransition();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPASU", "oused");
        if (mapsF.getMapFragment() != null) mapsF.getMapFragment().onDestroy();

    }

    @Override
    protected void onResume() {
        Log.i("onRESMU", "RESUME");
        if (mapsF.getMapFragment() != null) mapsF.getMapFragment().onDestroyView();

        super.onResume();
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    private void detailResiTransition(){
        final View vAppLogo = this.findViewById(R.id.imageView);
        final View vAppTitle = this.findViewById(R.id.textView);
        final View vCardBody = this.findViewById(R.id.resiCard);
        final View vButtonS = this.findViewById(R.id.searchBtn);
        final View vmapView = this.findViewById(R.id.mapCard);
        final EditText noresi = this.findViewById(R.id.noResi);


        Button button = this.findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((isEmpty(noresi))){
                    Toast.makeText(MainActivity.this,"Receipt Number is Empty",Toast.LENGTH_LONG).show();
                } else {
                    Intent goToDetailResi = new Intent(MainActivity.this, DetailResiActivity.class);
                    goToDetailResi.putExtra("NoResi", noresi.getText().toString());
                    goToDetailResi.putExtra("latitude", latitude.toString());
                    goToDetailResi.putExtra("longitude", longitude.toString());
                    Pair<View, String> p1 = Pair.create(vAppLogo, "AppLogo");
                    Pair<View, String> p2 = Pair.create(vAppTitle, "AppTitle");
                    Pair<View, String> p3 = Pair.create(vCardBody, "CardBody");
                    Pair<View, String> p4 = Pair.create(vButtonS, "ButtonS");
                    Pair<View, String> p5 = Pair.create(vmapView, "mapBody");
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p1, p2, p3, p4, p5);
                    startActivity(goToDetailResi, activityOptions.toBundle());
                }
            }
        });
    }
    private void permission(){
        if(Build.VERSION.SDK_INT>= 23) {
            if (checkSelfPermission(mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{mPermission,
                        }, REQUEST_CODE_PERMISSION);
                return;
            } else {
                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
                mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("BERHASIL LAT",String.valueOf(latitude));
                            Log.i("BERHASIL LONG",String.valueOf(longitude));
                        }
                    }
                });
            }
        }
    }

}
