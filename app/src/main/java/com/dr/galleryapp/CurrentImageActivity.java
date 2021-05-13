package com.dr.galleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class CurrentImageActivity extends AppCompatActivity {

    ViewPager2 viewPager2;

    String[] arr = MainActivity.urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_image);

        viewPager2 = (ViewPager2) findViewById(R.id.viewPagerImageSlider);
        Intent intent = getIntent();
        String curr = intent.getStringExtra("url");
        List<SliderItem> sliderItems = new ArrayList<>();

        for(String url: arr) {
            sliderItems.add(new SliderItem(url));
        }

        viewPager2.setAdapter(new SliderAdapter(sliderItems, new SliderItem(curr), viewPager2, this));


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);


//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.loading_layout)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
    }
}