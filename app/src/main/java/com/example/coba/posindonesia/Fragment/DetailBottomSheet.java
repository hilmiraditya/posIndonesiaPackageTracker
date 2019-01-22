package com.example.coba.posindonesia.Fragment;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coba.posindonesia.R;
import com.example.coba.posindonesia.Adapter.RecyclerViewAdapter;
import com.example.coba.posindonesia.Session.SessionManager;

import org.w3c.dom.Text;

public class DetailBottomSheet extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_detail, container, true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext());

        recyclerView = view.findViewById(R.id.detil_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        SessionManager sessionManager = new SessionManager(getContext());

        TextView resiText = view.findViewById(R.id.resiPackage);
        resiText.setText(sessionManager.getResi());

        TextView yourText = view.findViewById(R.id.yoursPackage);
        Log.i("NUMM", sessionManager.getYours());
        yourText.setText(sessionManager.getYours());

        TextView sumText = view.findViewById(R.id.summaryPackage);
        sumText.setText(sessionManager.getSummary());

        return view;
    }

}