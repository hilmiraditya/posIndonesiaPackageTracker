package com.example.coba.posindonesia.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coba.posindonesia.R;

public class MainActivity extends AppCompatActivity {

    DetailResiActivity da;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent goToDetailResi = new Intent(MainActivity.this, DetailResiActivity.class);
                goToDetailResi.putExtra("NoResi", noresi.getText().toString());

                Pair<View, String> p1 = Pair.create(vAppLogo, "AppLogo");
                Pair<View, String> p2 = Pair.create(vAppTitle, "AppTitle");
                Pair<View, String> p3 = Pair.create(vCardBody, "CardBody");
                Pair<View, String> p4 = Pair.create(vButtonS, "ButtonS");
                Pair<View, String> p5 = Pair.create(vmapView, "mapBody");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p1, p2, p3, p4, p5);

                startActivity(goToDetailResi, activityOptions.toBundle());
            }
        });
    }

}
