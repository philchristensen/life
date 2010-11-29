/*
 * @(#)Life.java    1.0 01/08/16
 * 
 * Game of Life
 * Copyright (C) 2004 Phil Christensen
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of version 2 of the GNU General Public
 * License as published by the Free Software Foundation.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.util.*;

public class Life{
    protected boolean[][] lifegrid;
    protected int cols, rows;
    
    public Life(int cols, int rows){
        this.lifegrid = new boolean[cols][rows];
        this.cols = cols;
        this.rows = rows;
    }
    
    public int[] getSize(){
        return new int[]{this.cols, this.rows};
    }
    
    public boolean[][] getGrid(){
        boolean[][] result = new boolean[cols][rows];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                result[i][j] = lifegrid[i][j];
            }
        }
        return result;
    }
    
    public void populate(int entities){
        int randomCol, randomRow;
        Random rand = new Random();
        for(int i = 0; i < entities; i++){      
            randomCol = Math.abs(rand.nextInt() % cols);
            randomRow = Math.abs(rand.nextInt() % rows);
            lifegrid[randomCol][randomRow] = (!lifegrid[randomCol][randomRow]);
            //System.out.println("c: " + randomCol + " r: " + randomRow);
        }
    }
    
    public void iterate(){
        boolean[][] newgrid = new boolean[cols][rows];
        for(int i = 0; i < newgrid.length; i++){
            for(int j = 0; j < newgrid[i].length; j++){
                int mortality = 0;
                if(i - 1 >= 0){
                    mortality += this.calculate(i-1, j, true);
                }
                mortality += this.calculate(i, j, false);
                if(i + 1 < newgrid.length){
                    mortality += this.calculate(i+1, j, true);
                }
                if(lifegrid[i][j]){
                    if(mortality == 2 || mortality == 3)
                        newgrid[i][j] = true;
                    else
                        newgrid[i][j] = false;
                }
                if(! lifegrid[i][j]){
                    if(mortality == 3)
                        newgrid[i][j] = true;
                    else
                        newgrid[i][j] = false;
                }
            }
        }
        lifegrid = newgrid;
    }
    
    protected int calculate(int col, int row, boolean side){
        int mortality = 0;
        if(row - 1 >= 0)
            mortality += (lifegrid[col][row-1] ? 1 : 0);
        if(side)
            mortality += (lifegrid[col][row] ? 1 : 0);
        if(row + 1 < lifegrid[col].length)
            mortality += (lifegrid[col][row+1] ? 1 : 0);
        return mortality;
    }
    
    public void print(){
        for(int i = 0; i < lifegrid.length; i++){
            for(int j = 0; j < lifegrid[i].length; j++){
                System.out.print(lifegrid[i][j] ? "O" : ".");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    
    public static void main(String[] args){
        Life life = new Life(10,10);
        life.populate(20);
        life.print();
        life.iterate();
        life.print();
    }
}
