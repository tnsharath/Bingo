package com.vintile.bingo.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sharath TN on 2019-07-12.
 */
public class SharedPref {
    private final SharedPreferences pref;

    // Editor for Shared preferences
    private final SharedPreferences.Editor editor;

    // Sharedpref file name
    private static final String PREF_NAME = "Bingo";


    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_OPP_NAME = "opp_name";
    private static final String KEY_PLAYER_ID = "player_id";
    private static final String KEY_OPP_ID = "opp_id";
    private static final String KEY_IS_HOST = "is_host";
    private static final String KEY_TURN = "turn";
    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SharedPref(Context context){
        // Context
        // Shared pref mode
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setKeyRoomId(String roomId){
        editor.putString(KEY_ROOM_ID, roomId);
        editor.apply();
    }

    public String getRoomId(){
        return pref.getString(KEY_ROOM_ID, "");
    }


    public void setKeyPlayerName(String playerName){
        editor.putString(KEY_PLAYER_NAME, playerName);
        editor.apply();
    }

    public String getPlayerName(){
        return pref.getString(KEY_PLAYER_NAME, "Me");
    }

    public void setKeyOppName(String playerName){
        editor.putString(KEY_OPP_NAME, playerName);
        editor.apply();
    }

    public String getOppName(){
        return pref.getString(KEY_OPP_NAME, "Opponent");
    }

    public void setKeyPlayerID(String playerID){
        editor.putString(KEY_PLAYER_ID, playerID);
        editor.apply();
    }

    public String getKeyPlayerId(){
        return pref.getString(KEY_PLAYER_ID, "");
    }

    public void setKeyOppID(String playerID){
        editor.putString(KEY_OPP_ID, playerID);
        editor.apply();
    }

    public String getKeyOppId(){
        return pref.getString(KEY_OPP_ID, "");
    }

    public void setKeyIsHost(boolean isHost){
        editor.putBoolean(KEY_IS_HOST, isHost);
        editor.apply();
    }

    public boolean getKeyIsHost(){
        return pref.getBoolean(KEY_IS_HOST, false);
    }

    public void setKeyIsMyTurn(boolean isHost){
        editor.putBoolean(KEY_TURN, isHost);
        editor.apply();
    }

    public boolean isMyTurn(){
        return pref.getBoolean(KEY_TURN, false);
    }
}
