package main.java.controller;

import javafx.stage.Stage;
import main.java.view.factory.DisplayScene;

public class SceneManager{
    private static SceneManager instance;
    private Stage palco;

    private SceneManager(){}

    public static SceneManager getInstance(){
        if (instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage p){
        this.palco = p;
    }

    public void switchStageScene(DisplayScene cena){
        this.palco.setScene(cena.getScene());
    }
}