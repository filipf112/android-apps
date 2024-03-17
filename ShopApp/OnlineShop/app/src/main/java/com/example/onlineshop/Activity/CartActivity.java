package com.example.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.onlineshop.Adapter.CartAdapter;
import com.example.onlineshop.Help.ChangeNumberItemsListener;
import com.example.onlineshop.Help.ManagmentCart;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityCartBinding;

//Odpowiada za wyświetlanie koszyka zakupów
public class CartActivity extends AppCompatActivity {

    private ManagmentCart managmentCart;
    double tax;

    ActivityCartBinding binding;
    //inicjuje cały widok
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        setVariable();
        initlist();
        statusBarColor();
        calculatorCart();
    }

    //kolor paska
    private void statusBarColor(){
        Window window = CartActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CartActivity.this,R.color.green));
    }

    //w zaleznosci od listy wyswietla odpowiedni koszyk (pusty lub pełny)
    private void initlist() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), () -> calculatorCart()));
    }

    //oblicza koszt
    private void calculatorCart(){
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round(managmentCart.getTotalFee() * percentTax *100)/100;

        double total = Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100;
        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);
    }

    //przycisk powrotu
    private void setVariable() {
        binding.backBtn.setOnClickListener((View v) -> {
            finish();
        });
    }
}