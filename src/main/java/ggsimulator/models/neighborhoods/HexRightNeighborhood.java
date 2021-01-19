package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;
public class HexRightNeighborhood implements Neighborhood {

    @Override
    public int[][] getNeighbors(Grain grain) {

        int[][] originalNeighbors = grain.getNeighborhood();
        int[][] newNeighbors = new int[6][2];

        newNeighbors[0][0] = originalNeighbors[0][0];
        newNeighbors[0][1] = originalNeighbors[0][1];
        newNeighbors[1][0] = originalNeighbors[1][0];
        newNeighbors[1][1] = originalNeighbors[1][1];
        newNeighbors[2][0] = originalNeighbors[2][0];
        newNeighbors[2][1] = originalNeighbors[2][1];
        newNeighbors[3][0] = originalNeighbors[4][0];
        newNeighbors[3][1] = originalNeighbors[4][1];
        newNeighbors[4][0] = originalNeighbors[5][0];
        newNeighbors[4][1] = originalNeighbors[5][1];
        newNeighbors[5][0] = originalNeighbors[6][0];
        newNeighbors[5][1] = originalNeighbors[6][1];

        return newNeighbors;
    }
}