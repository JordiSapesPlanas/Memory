package com.example.q.camara;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by jordi on 24/03/2015.
 */
import java.io.Serializable;

/**
 * Created by jordi on 24/03/2015.
 */
public class Cell implements Serializable {
    private boolean isClicked;
    private String number;
    private Player p;
    Cell(String number) {
        this.number = number;
        this.isClicked = false;
        this.p = null;
    }

    public String getNumber() {
        if (!isClicked){
            return "?";
        }else{
            return this.number;

        }
    }
    public String getHiddenNumber(){
        return this.number;
    }
    public boolean isClicked(){
        return this.isClicked;
    }
    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
    public void setPlayer(Player p){
        this.p = p;
    }
    public int getColor(){
        if(this.p == null){
            return Color.WHITE;
        }
        if(p.getRole().equals("server")){
            return Color.GREEN;
        }if(p.getRole().equals("client")){
            return Color.CYAN;
        }
        return 0;


    }
    public Player getPlayer(){
        return this.p;
    }
}
