package com.dr.galleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface {


    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static String[] urls;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperLayout);
        swipeRefreshLayout.setSize(50);

        if(checkConnection()) {
            fetchApi();
        } else {
            Toast.makeText(this, "Please Enable Internet Connection! & Restart Application !", Toast.LENGTH_LONG).show();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchApi();
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        Call<Post> call = jsonPlaceHolder.getPosts();


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.i("result : ", Integer.toString(response.code()));
                    return;
                }

                int l = response.body().getPhotos().getPhotosArrays().length;
                urls = new String[l];                                  // string of urls
                int i = 0;

                for (PhotosArray myPost : response.body().getPhotos().getPhotosArrays()) {
                    Log.i("post is ", myPost.getUrl_s());
                    Log.i("is family: ", "" + myPost.getIsfriend());

                    urls[i++] = myPost.getUrl_s();

                }

                recyclerAdapter = new RecyclerAdapter(MainActivity.this, urls, MainActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public boolean checkConnection() {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if(activeNetwork != null) {

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "Data Network Enabled", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            Toast.makeText(this, "NO connection !", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this, CurrentImageActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


}