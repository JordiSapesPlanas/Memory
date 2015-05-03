package com.example.q.camara.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by q on 29/04/15.
 */

@Entity
public class PlayRecord {

    @Id
    Long id;

    @Index
    private String playIp;

    @Index
    private String regId;
    // you can add more fields...

    @Index
    private Integer numberCells;
    // you can add more fields...


    public PlayRecord() {
    }

    public String getPlayIp() {
        return playIp;
    }

    public void setPlayIp(String playIp) {
        this.playIp = playIp;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setNumberCells(Integer numberCells) {
        this.numberCells = numberCells;
    }

    public Integer getNumberCells() {
        return numberCells;
    }
}
