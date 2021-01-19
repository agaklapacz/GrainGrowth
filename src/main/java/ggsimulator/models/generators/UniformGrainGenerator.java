package ggsimulator.models.generators;


import ggsimulator.models.Grain;

public class UniformGrainGenerator implements GrainGenerator {


    @Override
    public Grain[][] generateGrains(Grain[][] grains, int width, int height, int[] parameters) {

        int numberOfLivingGrains = parameters[0];

        double numberOfGrainsInARow = Math.sqrt(numberOfLivingGrains);
        double distance = width/(numberOfGrainsInARow + 1);

        int x,y;

        x =  (int)(width/(numberOfGrainsInARow +1));
        y =  (int)(width/(numberOfGrainsInARow +1));

        int tmp1 = x;
        int tmp2 = y;
        while (numberOfLivingGrains>0){
            if(x>width-5){
                y+=distance;
                x = tmp1;
            }
            if(y>height-5){
                tmp1++;
                x = tmp1;
                y = tmp2;
            }
            if(tmp1 == 100){
                tmp1 = 0;
                x = tmp1;
                tmp2++;
            }

            if(!grains[x][y].isAlive()){
                grains[x][y].revive();
                numberOfLivingGrains--;
            }
            x+=distance;
        }

        return  grains;
    }

}
