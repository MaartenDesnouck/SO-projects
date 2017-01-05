
package othello;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidMoveException;


public class OthelloController {

    OthelloModel model;
    
    public OthelloController(OthelloModel model){
        this.model = model;
    }
    
    public boolean isValidMove(int x, int y) throws BoardIndexOutOfBoundsException {
        if(!model.inBounds(x, y)){
            throw new BoardIndexOutOfBoundsException();
        }
        if(model.getState(x, y) != 0){
            return false;
        }
        int[] directions = {-1,0,1};
        int color = model.getTurn();
        
        for(int i : directions){
            for(int j : directions){
                if(i != 0 || j != 0){
                    if(model.inBounds(x+i, y+j) && model.getState(x+i, y+j) == -1*color){
                        int xdir = i;
                        int ydir = j;
                        while(model.inBounds(x+xdir, y+ydir) && model.getState(x+xdir, y+ydir) == -1*color){
                            xdir+=i;
                            ydir+=j;
                        }
                        if(model.inBounds(x+xdir, y+ydir) && model.getState(x+xdir,y+ydir) == color){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        if(!isValidMove(x,y)){
            throw new InvalidMoveException();
        }
        
        model.setState(x, y,model.getTurn());
        
        int[] directions = {-1,0,1};
        int color = model.getTurn();
        
        for(int i : directions){
            for(int j : directions){
                if(i != 0 || j != 0){
                    if(model.inBounds(x+i, y+j) && model.getState(x+i, y+j) == -1*color){
                        int xdir = i;
                        int ydir = j;
                        while(model.inBounds(x+xdir, y+ydir) && model.getState(x+xdir, y+ydir) == -1*color){
                            xdir+=i;
                            ydir+=j;
                        }
                        if(model.inBounds(x+xdir, y+ydir) && model.getState(x+xdir,y+ydir) == color){
                            xdir-=i;
                            ydir-=j;
                            while(model.getState(x+xdir, y+ydir) == -1*color){
                                model.setState(x+xdir, y+ydir, color);
                                xdir-=i;
                                ydir-=j;
                            }
                        }
                    }
                }
            }
        }
        
        model.setTurn(model.getTurn()*-1);
        
        int canPlay = 0;
        for(int i=0; i<model.getSize();i++){
            for (int j=0; j<model.getSize();j++){
                try {
                    if(isValidMove(i,j)){
                        canPlay++;
                    }
                }catch(BoardIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        if(canPlay==0){
            model.setTurn(model.getTurn()*-1);
        }
        
        model.setValid(true);
    }
    
  
    public int isFinished(){
        Map<Integer,Integer> states = new HashMap<>();
        states.put(-1, 0);
        states.put(0, 0);
        states.put(1, 0);
        
        for(int i=0; i<model.getSize();i++){
            for (int j=0; j<model.getSize();j++){
                try {
                    if(isValidMove(i,j)){
                        return 0;
                    }
                    model.setTurn(model.getTurn()*-1);
                    if(isValidMove(i,j)){
                        model.setTurn(model.getTurn()*-1);
                        return 0;
                    }
                    model.setTurn(model.getTurn()*-1);
                    states.put(model.getState(i, j),(states.get(model.getState(i, j))+1));
                } catch (BoardIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(states.get(-1) > states.get(1)){
            return -1;
        }else if(states.get(1) > states.get(-1)){
            return 1;
        }else{
            return 2;
        }
    }
}
