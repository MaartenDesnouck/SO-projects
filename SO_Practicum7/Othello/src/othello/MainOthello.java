/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import othello.exception.InvalidBoardSizeException;

/**
 *
 * @author Sean
 */
public class MainOthello extends Application{
    
   public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage){
        
        stage.setTitle("MainOthello");
        
        OthelloModel model = null;
        try {
            model = new OthelloModel(6);
        } catch (InvalidBoardSizeException ex) {
            ex.printStackTrace();
        }
        OthelloController controller = new OthelloController(model);
        OthelloPanel panel = new OthelloPanel();
        panel.setModel(model);
        panel.setController(controller);
        
        stage.setScene(new Scene(panel.getContent()));
	stage.sizeToScene();
        stage.show();
    }
    
}
