package com.practise.aionixx.oauthpractise;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.practise.aionixx.oauthpractise.adapter.GitHubAdapter;
import com.practise.aionixx.oauthpractise.api.model.AccessToken;
import com.practise.aionixx.oauthpractise.api.model.GitHubRepo;
import com.practise.aionixx.oauthpractise.api.service.GitHubClient;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private  ListView listView;
    private  Button buttonLogin;
    private  Button buttonGet;
    private  Button buttonMap;


    private  String clientId = "51f25ac4b3eb41c9d94e";
    private  String clientSecret = "7b15bf276f2e415ac12984be54ef797d020db479";
    private  String redirectUri = "people1205://callback";
    public   String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.kek);
        buttonLogin= (Button) findViewById(R.id.button3);
        buttonGet = (Button) findViewById(R.id.button);
        buttonMap = (Button) findViewById(R.id.mapa);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMaps();
            }
        });





        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize"+ "?client_id=" + clientId + "&scope=repo&redirect_uri="+redirectUri));
                startActivity(intent);
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetShit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if(uri != null && uri.toString().startsWith(redirectUri))
        {
            String code = uri.getQueryParameter("code");

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://github.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            GitHubClient client = retrofit.create(GitHubClient.class);
            Call<AccessToken> accessTokenCall = client.getAccesToken(
                    clientId,
                    clientSecret,
                    code
            );
            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    assert response.body() != null;
                    token = response.body().getAccessToken();
                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    Log.d("token", "onResponse: "+ token);


                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });



        }
    }

    private void GetShit()
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> call =  client.userInfo("https://api.github.com/?access_token="+token);

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();
                listView.setAdapter(new GitHubAdapter(MainActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMaps()
    {
        Intent kok = new Intent(this, MapsActivity.class);
        startActivity(kok);
    }
}
