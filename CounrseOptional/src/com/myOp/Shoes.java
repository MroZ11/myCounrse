package com.myOp;

/**
 * Created by cloud on 2019/02/13.
 */
public class Shoes {

    private String band;

    private Double price;

    public Shoes() {
    }

    public Shoes(String band, Double price) {
        this.band = band;
        this.price = price;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
