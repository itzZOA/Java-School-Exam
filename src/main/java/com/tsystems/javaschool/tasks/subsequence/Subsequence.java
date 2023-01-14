package com.tsystems.javaschool.tasks.subsequence;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) throws IllegalArgumentException {
        if(x == null || y == null){
            throw new IllegalArgumentException(" ");
        }
        
        if(x.size() == 0){
            return true;
        }
    
      
        
        if(x.size() > y.size()){
            return false;
        }else{
            int i=0;
            int j=0;

            System.out.println(x.toString());
            System.out.println(y.toString());
            while (i < x.size() && j < y.size()){
                System.out.println("Index_i: " + i);
                System.out.println("Index_j: " + j);
                System.out.println(x.get(i) + " != " + y.get(j) );
                if (x.get(i)!=y.get(j)){
                    y.remove(y.get(j));
                    System.out.println("Elimino");
                    System.out.println(x.toString());
                    System.out.println(y.toString());
                }
                else{
                    i++;
                    j++;
                }
            }
            System.out.println("Eliminarlos los ultimos de Y");
            if(i == x.size()){ 
                for(int k = i; k < y.size();k++){
                    y.remove(k);
                }
            }
            
            System.out.println("Cadenas al final.");
            System.out.println(x.toString());
            System.out.println(y.toString());
            if(x.size() == y.size()){
                if(x.containsAll(y)){
                    return true;
                }
            }
            return false;
            
        }
    }
}