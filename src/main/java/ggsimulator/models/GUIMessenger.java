package ggsimulator.models;

import javafx.scene.control.TextArea;

public class GUIMessenger implements Messenger {

    private final TextArea textArea;

    public GUIMessenger(TextArea textArea) {
        this.textArea = textArea;
        textArea.setEditable(false);
    }

    @Override
    public void showMessage(String message){
        textArea.appendText("\n"+message);
    }
}

