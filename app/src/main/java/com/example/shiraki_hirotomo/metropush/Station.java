package com.example.shiraki_hirotomo.metropush;

/**
 * Created by shiraki-hirotomo on 2014/09/23.
 */
public class Station {
    double x,y;
    String name, line, distance, prefecture;

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLine(String line){
        this.line = line;
    }
    public void setDistance(String distance){
        this.distance = distance;
    }
    public void setPrefecture(String prefecture){
        this.prefecture = prefecture;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public String getName(){
        return name;
    }
    public String getLine(){
        return line;
    }
    public String getDistance(){
        return distance;
    }
    public String getPrefecture(){
        return prefecture;
    }

}
