package com.example.onlineshop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.onlineshop.Activity.DetailActivity;
import com.example.onlineshop.Domain.PopularDomain;
import com.example.onlineshop.Help.ChangeNumberItemsListener;
import com.example.onlineshop.Help.ManagmentCart;
import com.example.onlineshop.databinding.ViewholderCartBinding;
import com.example.onlineshop.databinding.ViewholderPupListBinding;

import java.util.ArrayList;

//Adapter RecyclerView, który obsługuje wyświetlanie elementów koszyka zakupowego w liście

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder>{
    ArrayList<PopularDomain> items;
    Context context;
    ViewholderCartBinding binding;

    ChangeNumberItemsListener changeNumberItemsListener;
    ManagmentCart managmentCart;
    //lista elementow koszyka, nasluchuje zmiane liczby elementow koszyka
    public CartAdapter(ArrayList<PopularDomain> items, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;

        this.changeNumberItemsListener = changeNumberItemsListener;
    }


    //tworzy nowe widoki elementów z listy
    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        managmentCart=new ManagmentCart(context);
        return new Viewholder(binding);
    }

    //ustawia dane dla widoku elementu na podstawie pozycji na liście
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        binding.titleTxt.setText(items.get(position).getTitle());
        binding.feeEachItem.setText("$"+items.get(position).getPrice());
        binding.totalEach.setText("$"+Math.round(items.get(position).getNumberInCart()*items.get(position).getPrice()));
        binding.numberItemsTxt.setText(String.valueOf(items.get(position).getNumberInCart()));

        int drawableResourced=holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(binding.pic);

        binding.plusCartBtn.setOnClickListener(v -> managmentCart.plusNumberItem(items, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        binding.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.minusNumberItem(items, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

    }

    //zwraca rozmiar listy
    @Override
    public int getItemCount() {
        return items.size();
    }

    //pojedyncyz element w liscie
    public class Viewholder extends RecyclerView.ViewHolder{
        public Viewholder(ViewholderCartBinding binding){
            super(binding.getRoot());
        }
    }
}
