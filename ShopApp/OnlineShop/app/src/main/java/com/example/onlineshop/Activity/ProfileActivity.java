package com.example.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.onlineshop.Adapter.PopularAdapter;
import com.example.onlineshop.Domain.PopularDomain;
import com.example.onlineshop.databinding.ActivityCartBinding;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityProfileBinding;

import java.util.ArrayList;

//odpowiada za wyświetlanie profilu użytkownika.

public class ProfileActivity extends AppCompatActivity{

    //inicjuje widok
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }



}
