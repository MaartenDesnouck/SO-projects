/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import java.io.Serializable;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidMoveException;

/**
 *
 * @author maartendesnouck
 */
public class OthelloPanel implements Serializable{
    
    private static final long serialVersionUID = 3L;
    private ObjectProperty<Integer> size = new SimpleObjectProperty<>();
    private OthelloPiece[][] veld;
    private Counter blackCounter;
    private Counter whiteCounter;
    private Counter emptyCounter;
    private OthelloController controller;
    private OthelloModel model;
    
    public OthelloPanel(int size){
        setSize(size);
        veld = new OthelloPiece[size][size];

        for(int i = 0; i< size;i++){
            for(int j = 0;j<size;j++){
                OthelloPiece piece = new OthelloPiece();
                int x = j;
                int y = i;
                piece.getContent().setOnMouseEntered(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        try {
                            if(controller.isValidMove(x, y,true)){
                                piece.setState(model.getTurn());
                            }
                        } catch (BoardIndexOutOfBoundsException ex) {
                            ex.printStackTrace();
                        }
                    }
		});
                piece.getContent().setOnMouseExited(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        piece.setState(model.getState(x, y));
                    }
		});
                piece.getContent().setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        try {
                            controller.doMove(x, y, true);
                        } catch (BoardIndexOutOfBoundsException | InvalidMoveException ex) {
                            ex.printStackTrace();
                        }
                    }
		});
                veld[i][j] = piece;
            }
        }
        
        blackCounter = new Counter(-1);
	whiteCounter = new Counter(1);
	emptyCounter = new Counter(0);
        addListener(blackCounter);
        addListener(whiteCounter);
        addListener(emptyCounter);
        
        for(int i=0;i<Math.pow(getSize(),2);i++){
            emptyCounter.increment();
        }
    }
    
    public OthelloPanel(){
        blackCounter = new Counter(-1);
	whiteCounter = new Counter(1);
	emptyCounter = new Counter(0);
    }
    
    public void setController(OthelloController c){
        controller = c;
    }
    
    public void setModel(OthelloModel m){
        model = m;
        
        setSize(model.getSize());
        veld = new OthelloPiece[getSize()][getSize()];

        for(int i = 0; i< getSize();i++){
            for(int j = 0;j<getSize();j++){
                OthelloPiece piece = new OthelloPiece();
                int x = j;
                int y = i;
                piece.getContent().setOnMouseEntered(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        try {
                            if(controller.isValidMove(x, y,true)){
                                piece.setState(model.getTurn());
                            }
                        } catch (BoardIndexOutOfBoundsException ex) {
                        }
                    }
		});
                piece.getContent().setOnMouseExited(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        piece.setState(model.getState(x, y));
                    }
		});
                piece.getContent().setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        try {
                            controller.doMove(x, y, true);
                        } catch (BoardIndexOutOfBoundsException | InvalidMoveException ex) {
                        }
                    }
		});
                veld[i][j] = piece;
            }
        }
        
        for(int i=0;i<size.getValue();i++){
            for(int j=0;j<size.getValue();j++){
                veld[i][j].setState(model.getState(j, i));
                
                if(model.getState(j, i) == -1){
                    blackCounter.increment();
                }else if(model.getState(j, i) == 1){
                    whiteCounter.increment();
                }else{
                    emptyCounter.increment();
                }
            }
        }
        
        addListener(blackCounter);
        addListener(whiteCounter);
        addListener(emptyCounter);
        
        m.validProperty().addListener(new Handler());
    }
    
    public int getSize() {
        return size.getValue();
    }

    public void setSize(int size) {
        this.size.setValue(size);
    }
    
    public ObjectProperty<Integer> sizeProperty(){
        return size;
    }
    
    public void addListener(ChangeListener l){
        for(int i=0;i<getSize();i++){
            for(int j=0;j<getSize();j++){
                veld[i][j].stateProperty().addListener(l);
            }
        }
    }
    
    public Pane getContent(){
        
        HBox root = new HBox();
        VBox vBox = new VBox();
        
        GridPane pane = new GridPane();
        for(int i=0;i<getSize();i++){
            for(int j=0;j<getSize();j++){
                pane.add(veld[i][j].getContent(), i, j);
            }
        }
        
        vBox.getChildren().add(emptyCounter.getContent());
        vBox.getChildren().add(whiteCounter.getContent());
        vBox.getChildren().add(blackCounter.getContent());
        root.getChildren().add(pane);
        root.getChildren().add(vBox);
        
        return root;
    }
    
    public class Handler implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldB, Boolean newB){
            if(newB){
                for(int i=0;i<size.getValue();i++){
                    for(int j=0;j<size.getValue();j++){
                        veld[i][j].setState(model.getState(j, i));
                    }
                }
                if(controller.isFinished() != 0){
                    int result = controller.isFinished();
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run(){
                            Alert end = new Alert(AlertType.INFORMATION);
                            end.setTitle("Game Over!");
                            if(result == -1){
                                end.setHeaderText("Player Black has won!");
                            }else if(result == 1){
                                end.setHeaderText("Player White has won!");
                            }else {
                                end.setHeaderText("You have tied!");
                            }
                            end.setContentText("The game has ended.");
                            end.showAndWait();
                            model.reset();
                    
                            //reset veld
                            for(int i=0;i<size.getValue();i++){
                                for(int j=0;j<size.getValue();j++){
                                    veld[i][j].setState(model.getState(j, i));
                                }
                            }
                        }
                    });
                    
                }
                model.setValid(false);
            }
        }
    }   
}
