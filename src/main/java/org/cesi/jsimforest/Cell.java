package org.cesi.jsimforest;

import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.EnumMap;

public class Cell implements CRUDInterface {

    private int coordX;
    private int coordY;
    private State state;
    private int age;

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
     * isEvolving - Method to find the new state of a cell
     *
     * @param neighborsStatesNumbers - EnumMap that gives the number of each neighbors states
     * @return State - the future new cell's state
     */
    public State isEvolving(EnumMap<State, Integer> neighborsStatesNumbers) {
        State actualState = getState();
        State newState = actualState;
        int probability = (int) Math.floor(Math.random() * 100);
        int age = getAge();
        int treeCount = neighborsStatesNumbers.get(State.tree);
        int bushCount = neighborsStatesNumbers.get(State.bush);
        int burningCount = neighborsStatesNumbers.get(State.burning);
        int infectedCount = neighborsStatesNumbers.get(State.burning);
        switch (actualState) {
            case empty:
                if (treeCount >= 2 || bushCount >= 3 || (treeCount == 1 && bushCount == 2)) {
                    newState = State.youngTree;
                }
                break;
            case youngTree:
                if ((treeCount + bushCount) <= 3) {
                    newState = State.bush;
                }
                if (burningCount >= 1) {
                    if(probability <= 25) {
                        newState = State.burning;
                    }
                }
                if (infectedCount >= 1) {
                    if(probability <= 75) {
                        newState = State.burning;
                    }
                }
                break;
            case bush:
                if(age >= 2) {
                    newState = State.tree;
                }
                if (burningCount >= 1) {
                    if(probability <= 50) {
                        newState = State.burning;
                    }
                }
                if (infectedCount >= 1) {
                    if(probability <= 50) {
                        newState = State.burning;
                    }
                }
                break;
            case tree:
                if (burningCount >= 1) {
                    if(probability <= 75) {
                        newState = State.burning;
                    }
                }
                if (infectedCount >= 1) {
                    if(probability <= 25) {
                        newState = State.burning;
                    }
                }
                break;
            case burning:
                if (age >= 1 ) {
                    newState = State.ashes;
                }
                break;
            case infected:
            case ashes:
                if (age >= 1) {
                    newState = State.empty;
                }
                break;
        }
        return newState;
    }

    /**
     * infoCell - Method to get informations (id, coordinates, state) on the cell
     *
     * @return String - infos about one cell (memory adress instance, X, Y, State, age)
     */
    public String infoCell() {
        String infos = "Cell : " + toString() + "| CoordX : " + getCoordX() + "| CoordY : " + getCoordY() + "| State : " + getState() + "| Age :" + getAge();
        return infos;
    }

    /**
     * checkNbr - Method to check if a number is sup or equal to 0 - throw exception if not
     *
     * @param nbr integer - number to test
     * @return boolean - number is equal or superior to 0
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

    /**
     * saveCell - Method to store Cell data in DB
     *
     * @param idGrid - id of the grid that the cell belongs to
     */
    public void saveCell(int idGrid) {
        String state = "'" + this.getState().toString() + "'";
        String req = MessageFormat.format("INSERT INTO cells (coordX, coordY, state, age, ID_grid) VALUES ({0}, {1}, {2}, {3}, {4})", this.getCoordX(), this.getCoordY(), state, this.getAge(), idGrid);
        create(req);
    }

    public ResultSet readOneCell(int id) {
        String req = MessageFormat.format("SELECT * FROM cells WHERE ID = {0}", id);
        return read(req);
    }


    public void updateOneCell(int idGrid, int coordX, int coordY) {
        String req = MessageFormat.format("UPDATE FROM cells SET state = {0}, age = {1} WHERE ID_GRID = {2} AND coordX = {3} AND coordY = {4}", this.getState().toString(), this.getAge(), idGrid, coordX, coordY);
    }

    public void deleteAllCellsInOneGrid(int idGrid){
        String req = MessageFormat.format("DELETE FROM cells WHERE ID_GRID = {0}", idGrid);
    }



}
