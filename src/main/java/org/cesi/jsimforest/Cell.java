package org.cesi.jsimforest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.EnumMap;

public class Cell {

    private int coordX;
    private int coordY;
    private State state;
    private int age;

    /**
     * Cell Constructor
     *
     * @param coordX Coordinate of X axis
     * @param coordY Coordinate of Y axis
     */
    public Cell(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.state = State.empty;
        this.age = 0;
    }

    /**
     * Cell Constructor - Overload - can set the cell's state
     *
     * @param coordX Coordinate of X axis
     * @param coordY Coordinate of Y axis
     * @param state State of the Cell - enum
     */
    public Cell(int coordX, int coordY, State state) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.state = state;
        this.age = 0;
    }

    /**
     * Method to try to evolve the cell to a new state
     *
     * @param neighborsStatesNumbers - the number of each neighbors cell's state
     */
    public State isEvolving(EnumMap<State, Integer> neighborsStatesNumbers) {
        State actualState = getState();
        State newState = actualState;
        int age = getAge();
        int treeCount = neighborsStatesNumbers.get(State.tree);
        int bushCount = neighborsStatesNumbers.get(State.bush);
        switch (actualState) {
            case empty:
                if (treeCount >= 2 || bushCount >= 3 || (treeCount == 1 && bushCount == 2)) {
                    newState = State.youngTree;
                }
                break;
            case youngTree:
                if (treeCount <= 3 && bushCount <= 3) {
                    newState = State.bush;
                }
                break;
            case bush:
                if(age >= 2) {
                    newState = State.tree;
                }
                break;
        }
        return newState;
    }


    /**
     * Method to get informations (id, coordinates, state) on the cell
     *
     * @return String
     */
    public String infoCell() {
        String infos = "Cell : " + toString() + "| CoordX : " + getCoordX() + "| CoordY : " + getCoordY() + "| State : " + getState() + "| Age :" + getAge();
        return infos;
    }

    /**
     * Method to check if a number is sup or equal to 0 - throw exception if not
     *
     * @param nbr integer
     * @return boolean
     */
    public boolean checkNbr(int nbr) {
        if(nbr >= 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Nbr must be superior or equal to 0");
        }
    }

    public int getCoordX() { return coordX; }

    public void setCoordX(int coordX) {
        if (checkNbr(coordX)) {
            this.coordX = coordX;
        }
    }

    public int getCoordY() { return coordY; }

    public void setCoordY(int coordY) {
        if (checkNbr(coordY)) {
            this.coordY = coordY;
        }
    }

    public State getState() { return state; }

    public void setState(State state) {
        this.state = state;
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        if (checkNbr(age)) {
            this.age = age;
        }
    }

}