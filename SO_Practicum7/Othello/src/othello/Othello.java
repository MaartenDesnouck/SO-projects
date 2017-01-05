/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import javafx.scene.layout.Pane;
import othello.exception.InvalidBoardSizeException;

/**
 *
 * @author maartendesnouck
 */
public class Othello implements game.GameInterface{
    
    private OthelloModel model;
    private OthelloController controller;
    private OthelloPanel panel;
    
    public Othello(){
        
        try {
            model = new OthelloModel(4);
        } catch (InvalidBoardSizeException ex) {
            ex.printStackTrace();
        }
        controller = new OthelloController(model);
        panel = new OthelloPanel();
        panel.setModel(model);
        panel.setController(controller);
    }
    
    @Override
    public Pane getGamePanel(){
        return panel.getContent();
    }

    @Override
    public String getGameName() {
        return "Othello";
    }

    @Override
    public void startGame(int role) {
        controller.setRole(role);
    }
    
}
