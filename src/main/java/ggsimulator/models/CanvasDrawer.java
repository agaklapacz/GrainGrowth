package ggsimulator.models;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasDrawer {
    public final static int GRAIN_SIZE = 5;
    private final GrainGrid grainGrid;
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    public CanvasDrawer(GrainGrid grainGrid){
        this.grainGrid = grainGrid;
    }

    public void createCanvas(){
        canvas = new Canvas(GRAIN_SIZE * grainGrid.getWidth(),GRAIN_SIZE * grainGrid.getHeight());
        canvas.setOnMouseClicked(mouseEvent -> {
            int x = ((int) mouseEvent.getX())/GRAIN_SIZE;
            int y = ((int) mouseEvent.getY())/GRAIN_SIZE;
            if(!grainGrid.getGrains()[x][y].isAlive()){
                grainGrid.getGrains()[x][y].revive();
            } else {
                grainGrid.getGrains()[x][y].kill();
            }
            drawSingleCell(grainGrid.getGrains()[x][y]);
        });
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void draw(){
        for(int i = 0 ; i < grainGrid.getWidth(); i++){
            for(int j = 0 ;j < grainGrid.getHeight(); j++){
                graphicsContext.setFill(grainGrid.getGrains()[i][j].getColor());
                int x = grainGrid.getGrains()[i][j].getCoordX()*Grain.SIZE;
                int y = grainGrid.getGrains()[i][j].getCoordY()*Grain.SIZE;
                graphicsContext.fillRect(x, y, GRAIN_SIZE, GRAIN_SIZE);
            }
        }
    }

    public void drawSingleCell(Grain grain){
        for(int i = 0; i < grainGrid.getWidth(); i++){
            for(int j = 0; j < grainGrid.getHeight(); j++){
                graphicsContext.setFill(grainGrid.getGrains()[i][j].getColor());
                int x = grainGrid.getGrains()[i][j].getCoordX()*GRAIN_SIZE;
                int y = grainGrid.getGrains()[i][j].getCoordY()*GRAIN_SIZE;
                graphicsContext.fillRect(x, y, GRAIN_SIZE, GRAIN_SIZE);
            }
        }
    }

    public void resetCanvas(){
        graphicsContext.setFill(Color.DARKGREY);
        graphicsContext.fillRect(0,0, grainGrid.getWidth()*GRAIN_SIZE, grainGrid.getWidth()*GRAIN_SIZE);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
