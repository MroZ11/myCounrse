package com.example.testjpa.model;

/**
 * Created by cloud on 2019/10/12.
 */
public enum RgbColor {

    RED(255,0,0),
    GREEN(0,255,0),
    BLUE(0,0,255),
    BLACK(0,0,0),
    WHITE(255,255,255)

    ;

    int r;
    int g;
    int b;

    RgbColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
