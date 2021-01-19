package ggsimulator.models.boundaries;

import ggsimulator.models.Grain;

public class NonPeriodicBoundaryConditions implements BoundaryConditions {

    @Override
    public Grain calculateBoundary(Grain grain, int width, int height) {
        int x = grain.getCoordX();
        int y = grain.getCoordY();

        int[][] neighborhood = grain.getNeighborhood();

        neighborhood[0][0] = x;
        neighborhood[0][1] = y - 1;
        neighborhood[1][0] = x - 1;
        neighborhood[1][1] = y - 1;
        neighborhood[2][0] = x - 1;
        neighborhood[2][1] = y;
        neighborhood[3][0] = x - 1;
        neighborhood[3][1] = y + 1;
        neighborhood[4][0] = x;
        neighborhood[4][1] = y + 1;
        neighborhood[5][0] = x + 1;
        neighborhood[5][1] = y + 1;
        neighborhood[6][0] = x + 1;
        neighborhood[6][1] = y;
        neighborhood[7][0] = x + 1;
        neighborhood[7][1] = y - 1;

        for(int i = 0; i < 8; i++){
            if(neighborhood[i][0] < 0){
                neighborhood[i][0] = -1;
                neighborhood[i][1] = -1;
            }
            if(neighborhood[i][0] > (width-1)){
                neighborhood[i][0] = -1;
                neighborhood[i][1] = -1;
            }
            if(neighborhood[i][1] < 0){
                neighborhood[i][0] = -1;
                neighborhood[i][1] = -1;
            }
            if(neighborhood[i][1] > (height-1)){
                neighborhood[i][0] = -1;
                neighborhood[i][1] = -1;
            }
        }

        grain.setNeighborhood(neighborhood);
        return grain;
    }
}
