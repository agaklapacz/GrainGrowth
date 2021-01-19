package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;

public class MooreNeighborhood implements Neighborhood {

    @Override
    public int[][] getNeighbors(Grain grain) {

        return grain.getNeighborhood();
    }
}

