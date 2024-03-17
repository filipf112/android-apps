package com.example.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;

import android.view.Window;


import com.example.onlineshop.Adapter.PopularAdapter;
import com.example.onlineshop.Domain.PopularDomain;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.example.onlineshop.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    //inicjuje caly widok
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        statusBarColor();
        initRecyclerView();
        bottomNavigation();
        profileInit();

    }

    //nawigacja do Koszyka
    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
    }

    //nawigacja do profilu
    private void profileInit()
    {

        binding.profileBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ProfileActivity.class)));
    }

    private void statusBarColor(){
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));
    }

    //lista popularnych produktów (PopularDomain),
    // konfiguruje RecyclerView oraz ustawia PopularAdapter do wyświetlania elementów

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Koszulka","item_1", 15,4,50,"test"));
        items.add(new PopularDomain("Zegarek","item_2", 10,4,50,"test1"));
        items.add(new PopularDomain("Koszulka","item_3", 15,4,50,"test2"));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.PopularView.setAdapter(new PopularAdapter(items));

    }
}