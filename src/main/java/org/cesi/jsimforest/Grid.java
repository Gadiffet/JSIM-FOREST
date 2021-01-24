package org.cesi.jsimforest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    private int row;
    private int column;
    private Cell[][] matrix;

    /**
     * Grid Constructor
     *
     * @param row the number of row in the grid
     * @param column the number of column if the grid
     */
    public Grid(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new Cell[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++) {
                matrix[i][j] = new Cell(i,j,State.empty);
            }
        }
    }

    /**
     * Method to get a arraylist of the neightbors cells of one cell
     *
     * @return arrayList of Cells
     */
    public ArrayList<Cell> getNeighborsOfOneCell(int coordXCellTarget, int coordYCellTarget) {
        ArrayList<Cell> neighborsList = new ArrayList<>();
        for(int i=coordXCellTarget-1;i<=coordXCellTarget+1;i++){
            if(i >= 0 && i < this.getMatrix().length){
                for(int j=coordYCellTarget-1;j<=coordYCellTarget+1;j++) {
                    if(j >= 0 && j < this.getMatrix()[i].length && (i != coordXCellTarget || j != coordYCellTarget)) {
                        neighborsList.add(matrix[i][j]);
                    }
                }
            }
        }
        return neighborsList;
    }

    /**
     * Check if a number is sup or equal to 2 - throw Exception if not
     *
     * @param nbr integer
     * @return boolean
     */
    public boolean checkNbr(int nbr) {
        if(nbr <= 2) {
            return true;
        } else {
            throw new IllegalArgumentException("Nbr must be superior or equal to 2");
        }
    }

    public Cell[][] getMatrix() { return matrix; }

    public int getRow() { return row; }

    public void setRow(int row) {
        if (checkNbr(row)) {
            this.row = row;
        }
    }

    public int getColumn() { return column; }

    public void setColumn(int column) {
        if (checkNbr(column)) {
            this.column = column;
        }
    }
}
