package com.example.coba.posindonesia;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DetailBottomSheet extends BottomSheetDialogFragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> dataSet = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_detail, container, false);
        setDataSet();
        recyclerView = new RecyclerView(getContext());
        recyclerView.findViewById(R.id.detil_recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setDataSet(){
        dataSet.add(0,"Jancuk");
        dataSet.add(1,"Jancuk");
        dataSet.add(2,"Jancuk");
        dataSet.add(3,"Jancuk");
        dataSet.add(4,"Jancuk");
    }
}