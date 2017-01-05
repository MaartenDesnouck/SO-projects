
package othello.exception;


public class BoardIndexOutOfBoundsException extends Exception {

    public BoardIndexOutOfBoundsException(){
        super("The index is out of bounds of the game board.");
    }
}
