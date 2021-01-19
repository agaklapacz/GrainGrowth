package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;

@FunctionalInterface
public interface Neighborhood {
    int[][] getNeighbors(Grain grain);
}
