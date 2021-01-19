package ggsimulator.models;

import javafx.scene.paint.Color;

import javax.swing.*;

public class Grain {

    //================================ fields
    public static final int SIZE = 5;
    private boolean alive;
    public int coordX, coordY;
    private double ro;
    private boolean recrystallization;
    public int[][] neighborhood = new int[8][2];
    public Color color;
    boolean recristal; // zrekrystalizowny= true niezrekrystalizowany = false
    double densityDislocation;
    int state;


    //=============================== methods
    public Grain(){
        state = 0;
        alive = false;
        color = Color.DARKGREY;
        recristal = false;
        densityDislocation =0.0;
    }

    public Grain(int coordX, int coordY){
        alive = false;
        color = Color.DARKGREY;
        alive = false;
        color = Color.DARKGREY;
        this.coordX = coordX;
        this.coordY = coordY;

    }

    public Grain(int state, int x, int y, Color color,boolean recristal, double densityDislocation) {
        this.state = state;
        this.recristal = recristal;
        this.densityDislocation = densityDislocation;
        this.color = color;
        this.coordX = x;
        this.coordY = y;
    }

    public Grain(int state, int i, int y, Color color) {

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPosition(int x, int y){
        setCoordX(x);
        setCoordY(y);
    }

    public void revive(){
        alive = true;
        randomColor();
    }

    public void kill(){
        alive = false;
        color = Color.DARKGREY;
        densityDislocation =0;
        recristal = false;

    }

    public void randomColor(){
        if(color == null || color == Color.DARKGREY){
            do{
                this.color = Color.color(Math.random(), Math.random(), Math.random());
            } while (this.color == Color.DARKGREY);
        }
    }

    public Grain deepClone(){
        Grain grain = new Grain();
        grain.setAlive(this.alive);
        grain.setCoordX(this.coordX);
        grain.setCoordY(this.coordY);
        grain.setNeighborhood(this.neighborhood);
        grain.setColor(this.color);
        return grain;
    }


    // ================================== accessors
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getCoordX() {
        return coordX;
    }

    public double getRo() {return ro;}


    public void setRo(double ro) {this.ro = ro;}

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[][] getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(int[][] neighborhood) {
        this.neighborhood = neighborhood;
    }

    public boolean isRecristal() {
        return recristal;
    }

    public void setRecristal(boolean recristal) {
        this.recristal = recristal;
    }

    public double getDensdislocation() {
        return densityDislocation;
    }

    public void setDensdislocation(double densityDislocation) {
        this.densityDislocation = densityDislocation;
    }
}
