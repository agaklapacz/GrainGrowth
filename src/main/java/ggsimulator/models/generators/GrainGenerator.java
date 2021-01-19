package ggsimulator.models.generators;


import ggsimulator.models.Grain;

@FunctionalInterface
public interface GrainGenerator {
    Grain[][] generateGrains(Grain[][] grains, int width, int height, int[] parameters);
}
