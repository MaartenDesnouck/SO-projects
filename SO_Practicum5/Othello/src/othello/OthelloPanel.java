/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author maartendesnouck
 */
public class OthelloPanel implements Serializable{
    
    private static final long serialVersionUID = 3L;
    private ObjectProperty<Integer> size = new SimpleObjectProperty<>();
    private OthelloPiece[][] veld;
    
    public OthelloPanel(int size){
        setSize(size);
        veld = new OthelloPiece[size][size];

        for(int i = 0; i< size;i++){
            for(int j=0;j<size;j++){
                veld[i][j] = new OthelloPiece();
            }
        }
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
        GridPane pane = new GridPane();
        for(int i=0;i<getSize();i++){
            for(int j=0;j<getSize();j++){
                pane.add(veld[i][j].getContent(), i, j);
            }
        }
        return pane;
    }
    
    
    
}
