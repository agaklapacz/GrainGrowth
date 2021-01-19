module nggsimulator{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens ggsimulator;
    opens ggsimulator.controllers to javafx.fxml;
}