package com.milier.wowgallery.bean;

import java.io.Serializable;

/**
 * Created by jly on 2016/12/23.
 */
public class ComboBean implements Serializable {
    private String titile;
    private String price;
    private String productId;

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ComboBean() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ComboBean(String titile, String price, String productId) {
        this.titile = titile;
        this.price = price;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ComboBean{" +
                "titile='" + titile + '\'' +
                ", price='" + price + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
