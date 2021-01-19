package ggsimulator.models.generators;


import ggsimulator.models.Grain;

import java.util.Random;

public class RandomWithRadiusGrainGenerator implements GrainGenerator {

    @Override
    public Grain[][] generateGrains(Grain[][] grains, int width, int height, int[] parameters) {

        int N = parameters[0];
        int R = parameters[1];
        double distance;
        int securityLock = N*100;
        Random random = new Random();
        boolean canCreate;
        int x,y;
        while(N > 0 && securityLock > 0){
            x = random.nextInt(width-1);
            y = random.nextInt(height-1);
            canCreate = true;
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    distance = Math.sqrt(Math.pow(i-x,2)+Math.pow(j-y,2));
                    if(distance < R && grains[i][j].isAlive()){
                        canCreate = false;
                    }
                }
            }
            if(canCreate) {
                if (!grains[x][y].isAlive()) {
                    grains[x][y].revive();
                    N--;
                }
            }
            securityLock--;
        }

        return grains;
    }
}
