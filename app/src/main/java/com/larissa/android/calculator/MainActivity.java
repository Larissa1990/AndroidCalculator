package com.larissa.android.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.larissa.android.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_HISTORY="history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(savedInstanceState==null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container_view,CalculateFragment.class,null).commit();
//        }
    }



}