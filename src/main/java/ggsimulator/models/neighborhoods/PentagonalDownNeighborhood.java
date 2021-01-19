package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;

public class PentagonalDownNeighborhood implements Neighborhood {

    @Override
    public int[][] getNeighbors(Grain grain) {

        int x;
        int[][] originalNeighbors = grain.getNeighborhood();
        int[][] newNeighbors = new int[5][2];

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

        return newNeighbors;
    }
}
