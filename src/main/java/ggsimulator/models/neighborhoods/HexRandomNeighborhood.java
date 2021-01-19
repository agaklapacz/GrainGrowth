package ggsimulator.models.neighborhoods;

import ggsimulator.models.Grain;

public class HexRandomNeighborhood implements Neighborhood {

    @Override
    public int[][] getNeighbors(Grain grain) {
        int x;
        Neighborhood hexL = new HexLeftNeighborhood();
        Neighborhood hexR = new HexRightNeighborhood();
        int[][] newNeighbors;

        x = (int)(Math.random()*2+1);
        if(x%2 == 0){
            newNeighbors = hexL.getNeighbors(grain);
        }else {
            newNeighbors = hexR.getNeighbors(grain);
        }
        return newNeighbors;
    }
}

