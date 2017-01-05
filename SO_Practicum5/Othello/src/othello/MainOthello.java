/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author maartendesnouck
 */
public class MainOthello extends Application{
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage){
        
        stage.setTitle("MainOthello");
        
        HBox root = new HBox();
        VBox vBox = new VBox();
        
        Counter blackCounter = new Counter(-1);
	Counter whiteCounter = new Counter(1);
	Counter emptyCounter = new Counter(0);
        OthelloPanel panel = new OthelloPanel(4);
        
        panel.addListener(blackCounter);
        panel.addListener(whiteCounter);
        panel.addListener(emptyCounter);
        
        for(int i=0;i<Math.pow(panel.getSize(),2);i++){
            emptyCounter.increment();
        }
        
        vBox.getChildren().add(emptyCounter.getContent());
        vBox.getChildren().add(whiteCounter.getContent());
        vBox.getChildren().add(blackCounter.getContent());
        root.getChildren().add(panel.getContent());
        root.getChildren().add(vBox);
        
        stage.setScene(new Scene(root));
	stage.sizeToScene();
        stage.show();
    }
}
