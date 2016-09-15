package com.example.kushaalsingla.smartprix;

/**
 * Created by Kushaal Singla on 01-Jul-16.
 */
public class Store
{
    private String store_name="";
    private String store_url="";
    private String store_rating="";
    private String store_delivery="";
    private String name="";
    private String link="";
    private String price="";
    private String stock="";
    private String delivery="";
    private String shipping_cost="";
    private String pos="";
    private String logo="";

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = "Rs. "+price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_delivery() {
        return store_delivery;
    }

    public void setStore_delivery(String store_delivery) {
        this.store_delivery = store_delivery;
    }

    public String getStore_rating() {
        return store_rating;
    }

    public void setStore_rating(String store_rating) {
        this.store_rating = store_rating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(String shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
