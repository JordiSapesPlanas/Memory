package com.example.q.camara;

import java.io.Serializable;

/**
 * Created by jordi on 23/03/2015.
 */
public class Player implements Serializable{
    private int points;
    private String name;
    private boolean isMyTurn;
    private String role;
    public Player(String role, String name) {
        this.points = 0;
        this.name = name;
        this.role = role;
        if(role.equals("client")) {
            isMyTurn = true;

        }
        else {
            isMyTurn = false;
        }
        /*this.tirada1 = tirada1;
        this.tirada2 = tirada2;*/
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public boolean compareMove(char c1, char c2 ){
        return c1 == c2;
    }
    public void addPoint(){
        this.points ++;
    }
    public boolean getIsMyTurn() {
        return isMyTurn;
    }
    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }
}