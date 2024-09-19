package com.example.afinal.APIService;

import com.example.afinal.Constructor.LoginRequest;
import com.example.afinal.Constructor.User;
import com.example.afinal.Constructor.UserProfileUpdateRequest;
import com.example.afinal.Response.LoginResponse;
import com.example.afinal.Response.RegisterResponse;
import com.example.afinal.Response.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @POST("register.php")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("login.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("user_profile.php")
    Call<UserProfileResponse> getUserProfile (@Query("email") String email);

    @POST("updateProfile.php")
    Call<Void> updateUserProfile(@Body UserProfileUpdateRequest request);

}
