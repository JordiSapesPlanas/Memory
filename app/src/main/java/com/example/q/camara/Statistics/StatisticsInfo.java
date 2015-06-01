package com.example.q.camara.Statistics;

/**
 * Created by q on 27/05/15.
 */
public class StatisticsInfo {

    private String opponent;
    private String data;
    private Boolean isWon;

    public StatisticsInfo(String opponent, String data, Boolean isWon) {
        this.opponent = opponent;
        this.data = data;
        this.isWon = isWon;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getData() {
        return data;
    }

    public Boolean getIsWon() {
        return isWon;
    }
}