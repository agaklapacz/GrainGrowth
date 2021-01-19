package ggsimulator.models;

import ggsimulator.models.boundaries.BoundaryConditions;
import ggsimulator.models.boundaries.PeriodicBoundaryConditions;
import ggsimulator.models.generators.GrainGenerator;
import ggsimulator.models.neighborhoods.Neighborhood;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrainGrid {

    private int width, height;
    private BoundaryConditions boundaryConditions;
    private Grain[][] grains;
    private Grain[][] grains1;


    final double k = 0.1;
    final double t = -6;
    Random random = new Random();

    public void createGrains(int width, int height, BoundaryConditions boundaryConditions){
        this.width = width;
        this.height = height;
        this.boundaryConditions = boundaryConditions;

        grains = new Grain[width][height];
        grains1 = new Grain[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                grains[i][j] = new Grain(i,j);
                grains[i][j].setAlive(false);
                grains[i][j] = boundaryConditions.calculateBoundary(grains[i][j], width, height);
            }
        }
    }

    public boolean calculateGrainsGrowth(Neighborhood neighborhood){
        Grain[][] tmp = new Grain[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tmp[i][j] = grains[i][j].deepClone();
            }
        }

        int[][] neighbors;
        boolean hasNextStep = false;

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(grains[i][j].isAlive()){
                    neighbors = neighborhood.getNeighbors(grains[i][j]);

                    for (int[] neighbor : neighbors) {
                        int x = neighbor[0];
                        if (x < 0) continue;
                        int y = neighbor[1];
                        if (y < 0) continue;

                        if (!tmp[x][y].isAlive()) {
                            tmp[x][y].setAlive(true);
                            tmp[x][y].setColor(grains[i][j].getColor());
                            hasNextStep = true;
                        }
                    }
                }
            }
        }
        grains = tmp;
        return hasNextStep;
    }

    public void generateGrains(GrainGenerator grainGenerator, int[] generatorParameters){
        grains = grainGenerator.generateGrains(grains, width, height, generatorParameters);
    }

    public void resetGrains(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grains[i][j].kill();
            }
        }
    }

    public void MonteCarloMethod(){

        Grain[][] tmpboard = this.grains;

        int[][] neighbors = new int[8][2];
        List<Grain> listAllCells = new ArrayList<>();
        List<Color> listAllColours = new ArrayList<>();
        for(int i = 0; i < height; i ++){
            for (int j = 0; j<width; j++){
               // neighbors = grains[i][j].neighborhood;
                listAllCells.add(grains[i][j]);
                listAllColours.add(grains[i][j].getColor());
            }
        }

        for (int i = 0; i < listAllCells.size(); i ++){
            int find = random.nextInt(listAllCells.size());

            int x = listAllCells.get(find).getCoordX();
            int y = listAllCells.get(find).getCoordY();
            neighbors = grains[x][y].neighborhood;

            int energyBefore = 0;
            int energyAfter = 0;
            List<Color> col = new ArrayList<>();

            for (int[] neighbor : neighbors) {
                int xx = neighbor[0];
                int yy = neighbor[1];
                if ((grains[xx][yy].getColor())!=(grains[x][y].getColor())) {
                    energyBefore ++;
                }
                //List<Color> col = new ArrayList<>();
                 col.add(grains[xx][yy].getColor());
                //col[neighbors.length] = grains[xx][yy].getColor();
            }

            int choice = random.nextInt(col.size());

            for (int[] neighbor : neighbors) {
                int xx = neighbor[0];
                int yy = neighbor[1];
                if ((grains[xx][yy].getColor())!=(col.get(choice))) {
                    energyAfter ++;
                }
            }

            int energyChange = energyAfter - energyBefore;

            double p = 0;
            if (energyChange <= 0)
                p = 1;
            else
                p = Math.exp(-(energyChange/(k*t)));
            if (p == 1)
                grains[x][y].color = col.get(choice);

            listAllCells.remove(find);
        }
        this.grains = tmpboard;

    }

    //zmienne do DRX//
    double A= 86710969050178.5;
    double B= 9.41268203527779;
    double ro_after;
    double ro_before = 0;
    double delta_ro;
    double time = 0;
    boolean growth;
    boolean rekr;
    boolean stop;
    int zz;
    int kz;
    /////////////////
    public void DRX() {

        time += 0.001;
        ro_after = A / B + (1 - A / B) * Math.exp(-B*time);  //gestosc dyslokacji
        delta_ro = ro_after - ro_before;
        ro_before = ro_after;
        List<Grain> listAllCells = new ArrayList<>();
        List<Color> listAllColours = new ArrayList<>();

        double pack = delta_ro / (width * height);

        int neighbor = 0;
        int numberOfCellsOnTheEdge = 0;
        int numberOfCellsInside = 0;

        boolean[][] onTheEdge = new boolean[width][height]; //tworze tablice o rozmairze siatki, która przechowuje true jeśli komórka jest na krawędzi, nie jeśli nie jest

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                onTheEdge[i][j] = false;
                listAllCells.add(grains[i][j]);
                listAllColours.add(grains[i][j].getColor());
                //grains[i][j].isRecristal() = false; // jak ustwaić wsyztskim komórka recristal na false?
                grains[i][j].setRecristal(false);

            }
        }

        //przechodze po kazdej komórce i sprawdzam jej sąsiedztwo
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++){
                neighbor = 0;
                for (int z = i - 1, z1 = 0; z1 < 3; z++, z1++){
                    for (int k = j - 1, k1 = 0; k1 < 3; k++, k1++){
                        //trzeb auwzględnić warunki brzegowe periodycze nie periodyczne
                        //
                        //
                        if (k >= 0 && k < width && z >= 0 && z < width){
                            if(grains[i][j].getColor() != grains[z][k].getColor())
                                neighbor ++;
                        }
                    }
                }
                if (neighbor > 0){
                    //grains[i][j].setDensdislocation(0.7 * pack); //jeśli ma sasaidów czyli jest na krawedzi to gestosc dyslokacji * 0,7
                    grains[i][j].densityDislocation += pack* 0.7;
                    numberOfCellsOnTheEdge ++;
                    onTheEdge[i][j] = true;
                }else {
                   // grains[i][j].setDensdislocation(0.3 * pack); //jeśli jest w środku gestosc dyslokacji * 0,3
                    grains[i][j].densityDislocation += pack* 0.3;
                    numberOfCellsInside ++;
                }
            }
        }
        double sum = (numberOfCellsOnTheEdge * 0.3 * pack) + (numberOfCellsInside * 0.7 * pack);
        double smallPack = sum / 100;              //  rest / (wiersze * kolumny)

        double sum1 = 0.8 * sum;
        double sum2 = 0.2 * sum;
        while (sum1 > 0 || sum2 > 0) {
            int choice = random.nextInt(listAllCells.size());
            int x = listAllCells.get(choice).getCoordX();
            int y = listAllCells.get(choice).getCoordY();


            if (onTheEdge[x][y] == true && sum1 > 0) {
                grains[x][y].densityDislocation += smallPack;   //tuta to musze dodawać do wczesniejszej sumy Densdislocation ?+=?
                sum1 -= smallPack;
            }
            if (onTheEdge[x][y] == false && sum2 > 0) {
                grains[x][y].densityDislocation += smallPack;   //tuta to musze dodawać do wczesniejszej sumy Densdislocation
                sum2 -= smallPack;
            }
        }
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                if (grains[i][j].getDensdislocation() >= 4215840142323.42/(width * height) && onTheEdge[i][j] == true && grains[i][j].isRecristal() == false && ro_after> 4215840142323.42){
                    grains[i][j].color = generateColor();
                    grains[i][j].setRecristal(true);
                    grains[i][j].setDensdislocation(0);
                }
            }
        }
    }

    public void DRX_start() {
        stop = false;
        while (stop == false) {
            DRX();
            DRX_Growth();
        }
    }


    public void DRX_Growth(){

        for (int i = 0; i < width; i++){
            for (int j = 0; j <height; j++){

                grains1[i][j] = new Grain();
                if (grains[i][j].isRecristal() == false){
                    //potrzbuje 2 zmienne aby sprawdzić czy któyś sąsiad jest zrekrystalizoway i czy sąsiad ma miejszcze gestości niż ty
                    growth = true;
                    rekr = false;
                    //przechodze po sąsiadach
                    for (int z = i - 1, z1 = 0; z1 < 3; z++, z1++){
                        for (int k = j - 1, k1 = 0; k1 < 3; k++, k1++){
                            //tutaj uwzglednic warunki brzegowe
                            //
                            //
                            if (z >= 0 && k >= 0 && z < width && k < height) {
                                if (!((z1 == 2 && k1 == 0) || (z1 == 0 && k1 == 2) || (z1 == 2 && k1 == 0) || (z1 == 2 && k1 == 2))){
                                    if(grains[i][j].getDensdislocation() < grains[z][k].getDensdislocation()) {
                                        growth = false;
                                    }
                                    if (grains[z][k].isRecristal() == true){
                                        rekr = true;
                                        //zapisuje sb indeksy tego sąsiada
                                        zz = z;
                                        kz = k;
                                    }
                                }
                            }
                        }
                    }
                    if (rekr && growth)
                    {
                        grains[i][j].color = grains[zz][kz].color;  //zmieniam kolor komórki na kolor sąsaida
                        grains1[i][j].setRecristal(true);  //
                        grains1[i][j].setDensdislocation(0); //
                    }
                }
            }
        }
        int count = 0;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                grains[i][j].recristal = grains1[i][j].isRecristal();
                grains[i][j].densityDislocation = grains1[i][j].getDensdislocation();

              /*  grains[i][j].setRecristal(grains1[i][j].isRecristal());
                grains[i][j].setDensdislocation(grains1[i][j].getDensdislocation());*/
            }
        }
        if (count == (width * height)){
            stop = true;
        }
    }

    public javafx.scene.paint.Color generateColor(){

        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B= (int)(Math.random()*256);
        Color color = Color.rgb(R, G, B);
        return color;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BoundaryConditions getBoundaryConditions() {
        return boundaryConditions;
    }

    public Grain[][] getGrains() {
        return grains;
    }
}
