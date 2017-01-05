
package othello;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;


public class OthelloModel {

    // the game board
    private int[][] board;
    // who's turn it is (-1/1)
    private int turn;
    
    private BooleanProperty valid = new SimpleBooleanProperty();
    
    public OthelloModel(int size) throws InvalidBoardSizeException {
       if(size < 4 || size%2 != 0){
           throw new InvalidBoardSizeException();
       }
       
       board = new int[size][size];
       for(int i=0;i<size;i++){
           for(int j=0;j<size;j++){
               board[i][j]=0;
           }
       }
       board[(size/2)-1][(size/2)-1] = 1;
       board[(size/2)-1][size/2] = -1;
       board[size/2][(size/2)-1] = -1;
       board[size/2][size/2] = 1;
       
       turn = -1;
    }
    
    public int getState(int x, int y) {
        return board[x][y];
    }
    
    public void setState(int x, int y, int state) throws BoardIndexOutOfBoundsException {
        if(!inBounds(x,y)){
            throw new BoardIndexOutOfBoundsException();
        }
        board[x][y]=state;
    }
    
    public boolean inBounds(int x, int y){
        return (x>=0 && x < getSize() && y>=0 && y < getSize());
    }
    
    public int getTurn(){
        return turn;
    }
    
    public void setTurn(int turn){
        this.turn = turn;
    }
    
    public int getSize(){
        return board.length;
    }
    
    public String toString(){
        String s = "";
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                s+="\t"+board[i][j];
            }
            s+="\n";
        }
        return s;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o.getClass() != OthelloModel.class){
            return false;
        }
        if(getSize() != ((OthelloModel)o).getSize()){
            return false;
        }
        if(getTurn() != ((OthelloModel)o).getTurn()){
            return false;
        }
        for(int i=0;i<getSize();i++){
            for(int j=0;j<getSize();j++){
                if(getState(i,j) != ((OthelloModel)o).getState(i,j)){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isValid(){
        return valid.getValue();
    }
    
    public void setValid(boolean v){
        valid.set(v);
    }
    
    public BooleanProperty validProperty(){
        return valid;
    }
    
}
