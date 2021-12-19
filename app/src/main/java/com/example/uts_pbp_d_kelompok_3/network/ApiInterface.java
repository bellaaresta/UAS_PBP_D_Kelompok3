package com.example.uts_pbp_d_kelompok_3.network;

import com.example.uts_pbp_d_kelompok_3.model.Delivery;
import com.example.uts_pbp_d_kelompok_3.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register.php")
    Call<Response<String>> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<Response<User>> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<Response<String>> update(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("deliveries/list.php")
    Call<Response<ArrayList<Delivery>>> getDeliveries(
            @Field("user_id") int userId
    );

    @FormUrlEncoded
    @POST("deliveries/create.php")
    Call<Response<String>> createDelivery(
            @Field("user_id") int userId,
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") int weight,
            @Field("price") int price
    );

    @FormUrlEncoded
    @POST("deliveries/detail.php")
    Call<Response<Delivery>> detailDelivery(
            @Field("resi") String resi
    );
}
