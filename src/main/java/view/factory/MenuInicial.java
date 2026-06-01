package main.java.view.factory;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MenuInicial implements DisplayScene {

    @Override
    public Scene getScene() {
        return new Scene(new Pane(), 800, 600);
    }

}