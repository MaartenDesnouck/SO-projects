
package othello;

import event.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidMoveException;


public class OthelloController extends event.EventPublisher implements event.EventListener{

    private OthelloModel model;
    private int role;
    
    public OthelloController(OthelloModel model){
        this.model = model;
        role = 0;
        event.EventBroker.getEventBroker().addEventListener(this);
    }
    
    public boolean isValidMove(int x, int y, boolean checkRole) throws BoardIndexOutOfBoundsException {
        if(checkRole && model.getTurn() != role){
            return false;
        }
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
    
    public void doMove(int x, int y, boolean thisPlayer) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        if(!isValidMove(x,y,thisPlayer)){
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
        
        //send turn over network
        if(thisPlayer){
            publishEvent(new game.events.GameMoveEvent(x+","+y));
        }
        
        model.setTurn(model.getTurn()*-1);
        
        int canPlay = 0;
        for(int i=0; i<model.getSize();i++){
            for (int j=0; j<model.getSize();j++){
                try {
                    if(isValidMove(i,j,false)){
                        canPlay++;
                    }
                }catch(BoardIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        //if other player can't play, this player plays again
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
                    if(isValidMove(i,j,false)){
                        return 0;
                    }
                    model.setTurn(model.getTurn()*-1);
                    if(isValidMove(i,j,false)){
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
    
    public void setRole(int role){
        this.role = role;
    }

    @Override
    public void handleEvent(Event event) {
        if(event.getType().equals("MOVE")){
            String move = event.getMessage();
            String[] coords = move.split(",");
            try {
                doMove(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]), false);
            } catch (InvalidMoveException | BoardIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
    }
}
