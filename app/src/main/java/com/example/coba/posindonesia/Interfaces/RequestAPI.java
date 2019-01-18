package com.example.coba.posindonesia.Interfaces;

import com.example.coba.posindonesia.Model.Resi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestAPI {

    @FormUrlEncoded
    @POST("getinfo")
    Call<Resi> getResi(
            @Field("no_resi") String no_resi,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
    );

}
