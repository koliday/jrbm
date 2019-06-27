package com.jrsportsgame.jrbm.util;

public class DateTimeUtil {

    //将秒转为 xx分：yy秒
    public static String secondsToTime(Long seconds){
        Long minute=seconds / 60;
        Long second=seconds % 60;
        String time="";
        if(minute == 0){
            time+="00:";
        }else if(minute<10){
            time+="0"+minute+":";
        }else{
            time+=minute+":";
        }
        if(second == 0){
            time+="00";
        }else if(second<10){
            time+="0"+second;
        }else{
            time+=second;
        }
        return time;
    }

    public static void main(String[] args) {
        System.out.println(secondsToTime(0l));
        System.out.println(secondsToTime(5l));
        System.out.println(secondsToTime(35l));
        System.out.println(secondsToTime(60l));
        System.out.println(secondsToTime(80l));
        System.out.println(secondsToTime(3405l));
    }
}
