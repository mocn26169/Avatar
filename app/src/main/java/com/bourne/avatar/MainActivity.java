package com.bourne.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bourne.avatar.GalleryFinal.GalleryFinalActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryFinal(null);
    }

    public void galleryFinal(View view) {
        Intent intent = new Intent(this, GalleryFinalActivity.class);
        startActivity(intent);
    }


}

