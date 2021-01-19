package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;

public class PentagonalRandomNeighborhood implements Neighborhood {

    @Override
    public int[][] getNeighbors(Grain grain) {

        int x;
        int[][] originalNeighbors = grain.getNeighborhood();
        int[][] newNeighbors = new int[5][2];
        x = (int)(Math.random()*4+1);

        switch (x){
            //right
            case 1: {
                newNeighbors[0][0] = originalNeighbors[0][0];
                newNeighbors[0][1] = originalNeighbors[0][1];
                newNeighbors[1][0] = originalNeighbors[1][0];
                newNeighbors[1][1] = originalNeighbors[1][1];
                newNeighbors[2][0] = originalNeighbors[2][0];
                newNeighbors[2][1] = originalNeighbors[2][1];
                newNeighbors[3][0] = originalNeighbors[3][0];
                newNeighbors[3][1] = originalNeighbors[3][1];
                newNeighbors[4][0] = originalNeighbors[4][0];
                newNeighbors[4][1] = originalNeighbors[4][1];
                break;
            }
            //down
            case 2: {
                newNeighbors[0][0] = originalNeighbors[2][0];
                newNeighbors[0][1] = originalNeighbors[2][1];
                newNeighbors[1][0] = originalNeighbors[3][0];
                newNeighbors[1][1] = originalNeighbors[3][1];
                newNeighbors[2][0] = originalNeighbors[4][0];
                newNeighbors[2][1] = originalNeighbors[4][1];
                newNeighbors[3][0] = originalNeighbors[5][0];
                newNeighbors[3][1] = originalNeighbors[5][1];
                newNeighbors[4][0] = originalNeighbors[6][0];
                newNeighbors[4][1] = originalNeighbors[6][1];
                break;
            }
            //left
            case 3: {
                newNeighbors[0][0] = originalNeighbors[4][0];
                newNeighbors[0][1] = originalNeighbors[4][1];
                newNeighbors[1][0] = originalNeighbors[5][0];
                newNeighbors[1][1] = originalNeighbors[5][1];
                newNeighbors[2][0] = originalNeighbors[6][0];
                newNeighbors[2][1] = originalNeighbors[6][1];
                newNeighbors[3][0] = originalNeighbors[7][0];
                newNeighbors[3][1] = originalNeighbors[7][1];
                newNeighbors[4][0] = originalNeighbors[0][0];
                newNeighbors[4][1] = originalNeighbors[0][1];
                break;
            }
            //up
            case 4: {
                newNeighbors[0][0] = originalNeighbors[6][0];
                newNeighbors[0][1] = originalNeighbors[6][1];
                newNeighbors[1][0] = originalNeighbors[7][0];
                newNeighbors[1][1] = originalNeighbors[7][1];
                newNeighbors[2][0] = originalNeighbors[0][0];
                newNeighbors[2][1] = originalNeighbors[0][1];
                newNeighbors[3][0] = originalNeighbors[1][0];
                newNeighbors[3][1] = originalNeighbors[1][1];
                newNeighbors[4][0] = originalNeighbors[2][0];
                newNeighbors[4][1] = originalNeighbors[2][1];
                break;
            }
        }
        return newNeighbors;
    }
}