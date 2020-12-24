package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rhexgomez.typer.roboto.TyperRoboto;
import java.util.ArrayList;
import java.util.List;

public class newactivity extends AppCompatActivity
{

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    RecyclerView recyclerView;
    private boolean appBarExpanded = true;
    ReAdapter reAdapter;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);
        recyclerView = findViewById(R.id.newre);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("LET`S GET STARTED");
        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbar.setCollapsedTitleTypeface(TyperRoboto.ROBOTO_BOLD());
        collapsingToolbar.setExpandedTitleTypeface(TyperRoboto.ROBOTO_REGULAR());
        settingrecycler();

    }

    public void settingrecycler()
    {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Storage Access", "Please Grant Permission for Storing and Retriving Data from your Device"));
        movieList.add(new Movie("Location Access", "Please Grant Permission for the Location Service"));
        movieList.add(new Movie("Battery Optimization", "Please Grant Permission for Running the App while you travel"));
        movieList.add(new Movie("Camera Access", "Please Grant Permission for Taking moments"));
         reAdapter = new ReAdapter(newactivity.this, movieList, appBarLayout);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            reAdapter.buttonclick();
    }
}
