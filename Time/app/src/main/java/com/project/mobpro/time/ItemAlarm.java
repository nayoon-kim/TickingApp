package com.project.mobpro.time;

public class ItemAlarm {
    String index;
    String name;
    public String getIndex(){
        return index;
    }
    public String getName(){
        return name;
    }
    public void setIndex(String index){
        this.index= index;
    }
    public void setName(String name){
        this.name = name;
    }
    public ItemAlarm(String index, String name){
        this.index= index;
        this.name = name;
    }
}
