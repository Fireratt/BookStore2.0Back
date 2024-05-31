package com.example.myapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class ByteUtils {
    public static byte[] toByteArray(Object obj)
    {
        if(obj == null)
        {
            System.out.println("To ByteArray Return NULL");
            return null ; 
        }
        byte[] bytes = null ; 
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ; 
        
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    

    }

    public static String byteArray2Base64(byte[] ba)
    {
        final Base64.Encoder encoder = Base64.getEncoder() ; 
        return "" ; 
    }

    
    public static long getObjectSize(Object obj) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            return baos.toByteArray().length;
        } catch (IOException e) {
            throw new RuntimeException("Unable to get object size", e);
        }
    }
}
