package com.example.coba.posindonesia;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailResiActivity extends AppCompatActivity{

//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<String> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resi);

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

        Button button = (Button) findViewById(R.id.detailResiBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottom = new DetailBottomSheet();
                bottom.show(getSupportFragmentManager(),bottom.getTag());
            }
        });


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

}
