package ggsimulator.models.boundaries;


import ggsimulator.models.Grain;

@FunctionalInterface
public interface BoundaryConditions {
    Grain calculateBoundary(Grain grain, int width, int height);
}
