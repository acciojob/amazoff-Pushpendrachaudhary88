package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(){

    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id =id;
        String[] str = deliveryTime.split(";");
        int time = Integer.parseInt(str[0])*60 +Integer.parseInt(str[1]);
        this.deliveryTime=time;
    }


    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public static String getDeliveryTimeString(int  timeInt){
        int hours =timeInt/60;
        int min =timeInt%60;
        String hrStr="";
        String minStr ="";

        if(hours<10){
            hrStr="0"+hours;
        }
        else{
            hrStr=""+hours;
        }
        if(min<10){
            minStr=""+min;
        }
        else{
            minStr =""+min;
        }
        return hrStr+":"+minStr;
    }
    public static int getDeliveryTimeAsInt(String timeString){
        return (Integer.parseInt(timeString.substring(0,2))*60)+Integer.parseInt(timeString.substring(3));
    }

}
