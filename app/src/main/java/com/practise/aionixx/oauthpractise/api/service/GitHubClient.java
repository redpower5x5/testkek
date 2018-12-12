package com.practise.aionixx.oauthpractise.api.service;

import com.practise.aionixx.oauthpractise.MainActivity;
import com.practise.aionixx.oauthpractise.api.model.AccessToken;
import com.practise.aionixx.oauthpractise.api.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubClient {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccesToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );




    @GET("/user")
    Call<List<GitHubRepo>> userInfo(@Url String S);

    @GET("user")
    Call<GitHubRepo> userKek();

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
