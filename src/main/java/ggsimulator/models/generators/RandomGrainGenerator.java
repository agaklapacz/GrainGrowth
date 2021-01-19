package ggsimulator.models.generators;

import ggsimulator.models.Grain;

import java.util.Random;

public class RandomGrainGenerator implements GrainGenerator {

    @Override
    public Grain[][] generateGrains(Grain[][] grains, int width, int height, int[] parameters) {
        int numberOfLivingGrains = parameters[0];
        int securityLock = numberOfLivingGrains*10;
        Random random = new Random();
        int x, y;
        while(numberOfLivingGrains > 0 && securityLock > 0){
            x = random.nextInt(width-1);
            y = random.nextInt(height-1);
            if(!grains[x][y].isAlive()){
                grains[x][y].revive();
                numberOfLivingGrains--;
            }
            securityLock--;
        }

        return grains;
    }
}
