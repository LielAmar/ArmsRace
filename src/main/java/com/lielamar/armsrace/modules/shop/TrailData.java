package com.lielamar.armsrace.modules.shop;

import lombok.Getter;

@Getter
public class TrailData {

    private final int red, green, blue;

    public TrailData(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

}
