package com.example.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.onlineshop.Domain.PopularDomain;
import com.example.onlineshop.Help.ManagmentCart;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityDetailBinding;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;

//odpowiedzialna za wyświetlanie szczegółów wybranego produktu w sklepie internetowym i dodawanie go do koszyka

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private PopularDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        managmentCart  = new ManagmentCart(this);
        statusBarColor();
    }

    private void statusBarColor(){
        Window window = DetailActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(DetailActivity.this, R.color.green));
    }

    //Pobiera dane przekazane przez Intent, wyświetla szczegóły produktu w widoku,
    // obsługuje dodawanie produktu do koszyka oraz obsługuje działanie przycisku powrotu
    private void getBundles() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPicUrl(),"drawable",this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(binding.itemPic);

        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.descriptionTxt.setText(object.getDescription());
        binding.reviewTxt.setText(object.getReview()+"");
        binding.ratingTxt.setText(object.getScore()+"");

        binding.addToCartBtn.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managmentCart.insertFood(object);
        });

        binding.backBtn.setOnClickListener(v -> finish());


    }
}