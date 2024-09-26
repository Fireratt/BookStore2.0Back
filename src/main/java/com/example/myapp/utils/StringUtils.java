package com.example.myapp.utils;
import java.util.Map;
import java.util.*;
// this class was used to form and analyze the string for kafka
// it will create the form string like 1,2,3;1,2,3, base the map 
public class StringUtils {
    // require the map tobe Map<String,String>[]
    public static String form(Object[] map){
        int len = map.length ;
        String result = "" ; 
        for(int i = 0 ; i < len ; i++){
            Iterator<String> itr = ((Map<String,String>)map[i]).values().iterator() ; 
            while(itr.hasNext()){
                result += itr.next() ; 
                if(itr.hasNext()){
                    result += "," ; 
                }
            }
            result += ";" ; 

        }
        return result ; 
    }
    public static List<List<String>> translate(String message){
        String[] all = message.split(";") ; 
        int len = all.length ; 
        ArrayList<List<String>> result = new ArrayList<List<String>>(len); 
        System.out.println("ReceiveMessage:"+ message + "\n all's length:" + len);
        for (int i = 0 ; i < len ; i++){
            result.add(Arrays.asList(all[i].split(","))) ; 
        }
        return result ; 
    }
}