package com.example.q.camara;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fedora-2-jordi on 28/04/15.
 */
public class Tauler implements Serializable{
    private List<Cell> tauler;
    private int turn;  // si es 0 es el primer
    private String tirada1= null;
    private String tirada2=null;
    private Player serverPlayer = null;
    private Player clientPlayer = null;
    private Player winer;
    private boolean askWiner = false;
    public Tauler(List<Cell> list, int turn){
        this.tauler = list;
        this.turn = turn;
    }
    public void setMove(int pos){
        Cell c = tauler.get(pos);
        c.setClicked(true);
        if(turn == 0){
            tirada1 = c.getNumber();
        }else{
            tirada2 = c.getNumber();
        }
        turn ++;


        tauler.set(pos, c);
    }
    public void setPlayer(Player p){
         if(p.getRole().equals("server")){
             serverPlayer = p;
         }else{
             clientPlayer = p;
         }
    }

    public Player getPlayer(String role){
        if(serverPlayer.getRole().equals(role)){
            return serverPlayer;
        }else{
            return clientPlayer;
        }
    }

    public int getTurn(){
        return turn;
    }
    public void resetTurn(){
        turn = 0;
    }
    /*public char getMove1(){
        return tirada1;
    }*/
    public String getMove2() {
        return tirada2;
    }
    public List<Cell> getTauler() {
        return tauler;
    }
    public boolean compareMoves(){

        return tirada2 == tirada1;
    }
    public void setTauler(List<Cell> tauler) {
        this.tauler = tauler;
    }
    public void reset(){
        tirada1 = null;
        tirada2 = null;
        turn = 0;
    }
    public int getAllPoints(){
        if(serverPlayer != null && clientPlayer != null){
            return serverPlayer.getPoints() + clientPlayer.getPoints();
        }
        return 0;

    }
    public boolean getFirstAskWinner(){
      //  return this.askWiner;
        if(askWiner == false){
            askWiner = true;
            return askWiner;
        }else{
            return false;
        }
    }
    public String getWinner(){
        if(askWiner == false){
            if(serverPlayer.getPoints()>clientPlayer.getPoints()){
                winer = serverPlayer;
                return serverPlayer.getName();
            }
            else if(clientPlayer.getPoints() > serverPlayer.getPoints()){
                winer = clientPlayer;
                return clientPlayer.getName();
            }
            else{
                winer = null;
                return new String("empat");
            }
        }else{

            if(winer!= null){
                return winer.getName();
            }else{
                return new String ("empat");
            }
        }
    }

}