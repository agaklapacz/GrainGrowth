package ggsimulator.models;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ggsimulator.models.neighborhoods.Neighborhood;

public class GGSimulator {
    private final GrainGrid grainGrid;
    private final CanvasDrawer canvasDrawer;
    private final Messenger messenger;

    private Neighborhood neighborhood;
    private Timeline timeline;

    public GGSimulator(GrainGrid grainGrid, CanvasDrawer canvasDrawer, Messenger messenger){
        this.grainGrid = grainGrid;
        this.canvasDrawer = canvasDrawer;
        this.messenger = messenger;
    }

    public void startSimulation(double duration){
        if(timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING)){
            messenger.showMessage("Simulation is running");
            return;
        }
        messenger.showMessage("Grains growth simulation has been launched");
        timeline = new Timeline(simulateFrame(duration));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private KeyFrame simulateFrame(double duration) {
        return new KeyFrame(Duration.seconds(duration), e->{
            boolean hasNextStep = grainGrid.calculateGrainsGrowth(neighborhood);
            canvasDrawer.draw();
            if(!hasNextStep){
                messenger.showMessage("Simulation has been completed");
                timeline.stop();
            }
        });
    }

    public void stopSimulation(){
        if(timeline == null || timeline.getStatus().equals(Animation.Status.STOPPED)){
            return;
        }
        messenger.showMessage("Simulation has been stopped");
        timeline.stop();
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }
}
