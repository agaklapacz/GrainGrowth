package ggsimulator.controllers;

import ggsimulator.models.neighborhoods.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ggsimulator.models.*;
import ggsimulator.models.boundaries.BoundaryConditions;
import ggsimulator.models.boundaries.NonPeriodicBoundaryConditions;
import ggsimulator.models.boundaries.PeriodicBoundaryConditions;
import ggsimulator.models.generators.GrainGenerator;
import ggsimulator.models.generators.RandomGrainGenerator;
import ggsimulator.models.generators.RandomWithRadiusGrainGenerator;
import ggsimulator.models.generators.UniformGrainGenerator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GrainGrowthProgramController implements Initializable {

    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private CheckBox PBCCheckBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button randomizeButton;
    @FXML
    private Button generateButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField randomAmountTextField;
    @FXML
    private TextField regularAmountTextField;
    @FXML
    private TextField randomWithRadiusAmountTextField;
    @FXML
    private TextField randomWithRadiusRadiusTextField;
    @FXML
    private CheckBox randomCheckBox;
    @FXML
    private CheckBox randomWithRadiusCheckBox;
    @FXML
    private CheckBox regularCheckBox;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea textArea;
    @FXML
    private Slider timeStepSlider;

    private GGSimulator nggSimulator;
    private GrainGrid grainGrid;
    private CanvasDrawer canvasDrawer;
    private Messenger guiMessenger;

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guiMessenger = new GUIMessenger(textArea);
        grainGrid = new GrainGrid();
        canvasDrawer = new CanvasDrawer(grainGrid);
        nggSimulator = new GGSimulator(grainGrid, canvasDrawer, guiMessenger);
        grainGrid.createGrains(DEFAULT_WIDTH, DEFAULT_HEIGHT, new NonPeriodicBoundaryConditions());
        canvasDrawer.createCanvas();
        canvasDrawer.draw();
        scrollPane.setContent(canvasDrawer.getCanvas());

        comboBox.getItems().addAll("1. Moore", "2. Von Neumann", "3. Hex Left", "4. Hex Right", "5. Hex Random", "6. Pentagonal Right", "7. Pentagonal Left", "8. Pentagonal Down", "9. Pentagonal Up", "10. With Radius");
        randomAmountTextField.setDisable(true);
        randomWithRadiusAmountTextField.setDisable(true);
        randomWithRadiusRadiusTextField.setDisable(true);
        regularAmountTextField.setDisable(true);
    }

    public void generateButtonOnAction(ActionEvent actionEvent) {
        try {
            int width = widthField == null || widthField.getText().isEmpty() ? 0 : Integer.parseInt(widthField.getText());
            int height = heightField == null || heightField.getText().isEmpty() ? 0 : Integer.parseInt(heightField.getText());
            if(width == 0 || height == 0){
                throw new Exception();
            }
            BoundaryConditions boundary;
            if(PBCCheckBox.isSelected()){
                boundary = new PeriodicBoundaryConditions();
                guiMessenger.showMessage("Board "+ width +"x"+ height +" with periodic boundary conditions has been created");
            }else{
                boundary = new NonPeriodicBoundaryConditions();
                guiMessenger.showMessage("Board "+ width +"x"+ height +" with non-periodic boundary conditions has been created");
            }
            grainGrid.createGrains(width, height, boundary);
            canvasDrawer.createCanvas();
            canvasDrawer.draw();
            scrollPane.setContent(canvasDrawer.getCanvas());
        }catch (Exception e){
            guiMessenger.showMessage("ERROR:   Board couldn't be created");
        }
    }

    private Neighborhood checkNeighborhood() throws Exception{
        SingleSelectionModel<String> neighborhoodSelectionModel = comboBox.getSelectionModel();
        switch (neighborhoodSelectionModel.getSelectedIndex()) {
            case 0:
                return new MooreNeighborhood();
            case 1:
                return new VonNeumannNeighborhood();
            case 2:
                return new HexLeftNeighborhood();
            case 3:
                return new HexRightNeighborhood();
            case 4:
                return new HexRandomNeighborhood();
            case 5:
                return new PentagonalRightNeighborhood();
            case 6:
                return new PentagonalLeftNeighborhood();
            case 7:
                return new PentagonalDownNeighborhood();
            case 8:
                return new PentagonalUpNeighborhood();
            case 9:
                return new NeighborhoodWithRadius();
            default: {
                throw new Exception();
            }
        }
    }

    public void randomizeButtonOnAction(ActionEvent actionEvent) {
        GrainGenerator generator = null;
        int[] generatorParameters = new int[2];
        if(randomCheckBox.isSelected()){
            generatorParameters[0] = randomAmountTextField == null || randomAmountTextField.getText().isEmpty() ? 0 : Integer.parseInt(randomAmountTextField.getText());
            generator = new RandomGrainGenerator();
        }
        if(regularCheckBox.isSelected()){
            generatorParameters[0] = regularAmountTextField == null || regularAmountTextField.getText().isEmpty() ? 0 : Integer.parseInt(regularAmountTextField.getText());
            generator = new UniformGrainGenerator();
        }
        if(randomWithRadiusCheckBox.isSelected()){
            generatorParameters[0] = randomWithRadiusAmountTextField == null || randomWithRadiusAmountTextField.getText().isEmpty() ? 0 : Integer.parseInt(randomWithRadiusAmountTextField.getText());
            generatorParameters[1] = randomWithRadiusRadiusTextField == null || randomWithRadiusRadiusTextField.getText().isEmpty() ? 0 : Integer.parseInt(randomWithRadiusRadiusTextField.getText());
            generator = new RandomWithRadiusGrainGenerator();
        }
        if(generator == null || generatorParameters[0] == 0){
            return;
        }
        grainGrid.generateGrains(generator, generatorParameters);
        canvasDrawer.draw();
    }

    public void startButtonOnAction(ActionEvent actionEvent) {
        try {
            double simulationTimeStep = timeStepSlider.getValue();
            nggSimulator.setNeighborhood(checkNeighborhood());
            nggSimulator.startSimulation(simulationTimeStep);
        }catch (Exception e){
            guiMessenger.showMessage("ERROR:   Neighborhood hasn't been chosen");
        }
    }

    public void stopButtonOnAction(ActionEvent actionEvent) {
        nggSimulator.stopSimulation();
    }

    public void resetButtonOnAction(ActionEvent actionEvent) {
        grainGrid.resetGrains();

        canvasDrawer.draw();
    }

   public void monteCarloButtonOnAction(ActionEvent actionEvent){
        grainGrid.MonteCarloMethod();
        canvasDrawer.draw();
    }

    public void DRXButtonOnAction(ActionEvent actionEvent) {
        grainGrid.DRX();
        canvasDrawer.draw();
    }

    public void DRXStartButtonOnAction() {
        grainGrid.DRX_Growth();
        canvasDrawer.draw();
    }

    public void DRXStart1ButtonOnAction() {
        grainGrid.DRX_start();
        canvasDrawer.draw();
    }

    public void randomCheckBoxOnAction(ActionEvent actionEvent) {
        if(randomCheckBox.isSelected()) {
            disableRandomOption(false);
            disableRegularOption(true);
            disableRandomWithRadiusOption(true);
        } else {
            disableRandomOption(true);
        }
    }

    public void regularCheckBoxOnAction(ActionEvent actionEvent) {
        if(regularCheckBox.isSelected()){
            disableRandomOption(true);
            disableRegularOption(false);
            disableRandomWithRadiusOption(true);
        } else {
            disableRegularOption(true);
        }
    }

    public void randomWithRadiusCheckBoxOnAction(ActionEvent actionEvent) {
        if(randomWithRadiusCheckBox.isSelected()){
            disableRandomOption(true);
            disableRegularOption(true);
            disableRandomWithRadiusOption(false);
        } else {
            disableRandomWithRadiusOption(true);
        }
    }

    private void disableRandomOption(boolean status){
        randomCheckBox.setSelected(!status);
        randomAmountTextField.setDisable(status);
    }

    private void disableRegularOption(boolean status){
        regularCheckBox.setSelected(!status);
        regularAmountTextField.setDisable(status);
    }

    private void disableRandomWithRadiusOption(boolean status){
        randomWithRadiusCheckBox.setSelected(!status);
        randomWithRadiusAmountTextField.setDisable(status);
        randomWithRadiusRadiusTextField.setDisable(status);
    }

}
