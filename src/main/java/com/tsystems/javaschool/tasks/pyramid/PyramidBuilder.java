package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    private int maxElements = 100;

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if(inputNumbers.contains(null)){
            throw new CannotBuildPyramidException();
        }
        
        
        if (inputNumbers.size() > maxElements) {
            throw new CannotBuildPyramidException();
        }

        int longitud = inputNumbers.size();
        int sumatoria = 1;
        int filas = 0;
        int columnas = 0;
        int mitad = 0;
        boolean firstZero=false;

        Collections.sort(inputNumbers);

        while (longitud > 0){
            longitud= longitud - sumatoria;
            sumatoria++;
            filas++;
        }

        columnas=2*filas-1;
        mitad = columnas/2;

        int [][] piramide = new int[filas][columnas];

        if (longitud==0){
            for (int i =0; i<filas; i++){
                for (int j =0; j<filas; j++){
                    piramide[i][j] = 0;
                }
            }
        }
        else{
            throw new CannotBuildPyramidException();
        }
        int index = 0;
        if(inputNumbers.get(index)==0){
            firstZero=true;
        }
        piramide[0][columnas/2]=inputNumbers.get(index);
        index++;
        int x,y;
        
        for (int i = 1; i < filas; i++){
            for(int j = 0; j < columnas-1; j++){
                if(piramide[i-1][j] != 0){
                    x = i-1;
                    y = j;
                    //IZQ
                    if(piramide[x+1][y-1] == 0){
                        piramide[x+1][y-1] = inputNumbers.get(index);
                        index++;     
                    }
                    //DER
                    piramide[x+1][y+1] = inputNumbers.get(index);
                    index++;
                }else if(firstZero==true){
                    if (i ==1){
                        x = 0;
                        y = columnas/2;
                        //IZQ
                        if(piramide[x+1][y-1] == 0){
                            piramide[x+1][y-1] = inputNumbers.get(index);
                            index++;     
                        }
                        //DER
                        piramide[x+1][y+1] = inputNumbers.get(index);
                        index++;
                    }
                }
        
            }
        }
        return piramide;
    }
}