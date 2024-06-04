package com.example.myapp.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageUtils {
    public static <T> Page<T> toPage(T[] array , Pageable pageStatus , long total)
    {
        // int pageSize = pageStatus.getPageSize() ; 
        // int pageNumber = pageStatus.getPageNumber() ; 
        ArrayList<T> list = new ArrayList<>(Arrays.asList(array)) ; 
        return new PageImpl<T>(list , pageStatus ,total) ; 
    }

    private static <T> ArrayList<T> divideList(ArrayList<T> list , int start , int end)
    {
        if(end - start < 0)
        {
            return null ; 
        }
        ArrayList<T> retList = new ArrayList<>(end-start +1) ; 
        for(int i = 0 ; i < end-start+1 ; i++)
        {
            retList.add(i, list.get(i + start));
        }
        return retList ; 
    }
}
