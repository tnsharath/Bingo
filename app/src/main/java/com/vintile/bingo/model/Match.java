package com.vintile.bingo.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sharath on 2020/04/07
 **/
@IgnoreExtraProperties
public class Match {


    public String player1;
    public String player2;
    public String winner;
    public String loser;
    public boolean toss;

    public Match() {
    }

    public Match(String player1, String player2, String winner, String loser, boolean toss) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.loser = loser;
        this.toss = toss;
    }

    public Match( String player2) {
        this.player2 = player2;;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }


    public boolean getToss() {
        return toss;
    }

    public void setToss(boolean toss) {
        this.toss = toss;
    }
}
