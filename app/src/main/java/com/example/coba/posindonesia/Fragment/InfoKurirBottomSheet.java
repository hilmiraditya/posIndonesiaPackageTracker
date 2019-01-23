package com.example.coba.posindonesia.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.coba.posindonesia.Activity.MainActivity;
import com.example.coba.posindonesia.R;

public class InfoKurirBottomSheet extends BottomSheetDialogFragment {

    Button call,sms;
    String phoneNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_infokurir, container, true);
        call = view.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callForwardString = "087855857881";
                Intent intentCallForward = new Intent(Intent.ACTION_DIAL); // ACTION_CALL
                Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                intentCallForward.setData(uri2);
                startActivity(intentCallForward);
            }
        });
        sms = view.findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = "087855857881";
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.putExtra("address"  , phoneNo);
                startActivity(smsIntent);
            }
        });
        return view;
    }
}
