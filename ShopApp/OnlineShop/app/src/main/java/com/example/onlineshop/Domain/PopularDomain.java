package com.example.onlineshop.Domain;

import java.io.Serializable;

// reprezentującą dane produktu popularnego w sklepie internetowym.
public class PopularDomain implements Serializable {

        //tytuł
        private String title;
        //adres obrazka (grafiki)
        private String picUrl;
        //liczba recenzji
        private int review;
        //ocena
        private double score;
        //ilosc produktow w koszyku
        private int numberInCart;
        //cena
        private double price;
        //opis
        private String description;

        public PopularDomain(String title, String picUrl, int review, double score, double price, String description){
            this.title = title;
            this.picUrl = picUrl;
            this.review = review;
            this.score = score;
            this.price = price;
            this.description = description;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTitle(String titile){
            this.title = title;
        }

        public String getPicUrl(){
            return picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getReview() {
            return review;
        }

        public void setReview(int review) {
            this.review = review;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getNumberInCart() {
            return numberInCart;
        }

        public void setNumberInCart(int numberInCart) {
            this.numberInCart = numberInCart;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
}




