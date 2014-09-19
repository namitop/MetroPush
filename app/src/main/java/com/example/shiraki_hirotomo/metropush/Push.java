package com.example.shiraki_hirotomo.metropush;

/**
 * Created by shiraki-hirotomo on 2014/09/18.
 */
public class Push {
    String type = null;//出発：departure、到着：arrival、各駅：everystops
    boolean isEnable;
    //int isEnable;

    public Push(String type, boolean isEnable){
        this.type = type;
        this.isEnable = isEnable;
    }

    public String getType(){
        return type;
    }

    public boolean getIsEnable(){
        return isEnable;
    }
}
