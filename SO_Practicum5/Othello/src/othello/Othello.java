/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author maartendesnouck
 */
public class Othello implements game.GameInterface{
    
    private Pane root;
    
    public Othello(){
        
        root = new HBox();
        VBox vBox = new VBox();
        
        Counter blackCounter = new Counter(-1);
	Counter whiteCounter = new Counter(1);
	Counter emptyCounter = new Counter(0);
        OthelloPanel panel = new OthelloPanel(6);
        
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

    }
    
    @Override
    public Pane getGamePanel(){
        return root;
    }
    
}
