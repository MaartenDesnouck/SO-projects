/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.exceptions;

/**
 *
 * @author Sean
 */
public class GameLoadException extends Exception{
    
    public GameLoadException(){
        super("The game could not be loaded.");
    }
    
}
