package com.example.car_crashv3;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class Tile {
    private ImageView img;
    private int kind;





    private static int currentPlayerSkin;
    public static final int EMPTY = 0, CAR = 1 , STONE = 2, COIN = 3;


    public Tile() { }

    public Tile(ImageView img, int kind) {
        this.img = img;
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public Drawable getDrawable() {
        return img.getDrawable();
    }


    public void setPlayer(){
        kind=CAR;
        img.setImageResource(R.drawable.img_racing_car);
        img.setVisibility(View.VISIBLE);
    }

    public void setEmpty(){
        kind=EMPTY;
        img.setVisibility(View.INVISIBLE);
    }

    public void setStone(){
        kind = STONE;
        img.setImageResource(R.drawable.img_stone);
        img.setVisibility(View.VISIBLE);
    }

    public void setCoin(){
        kind=COIN;
        img.setImageResource(R.drawable.img_coin);
        img.setVisibility(View.VISIBLE);
    }


}
