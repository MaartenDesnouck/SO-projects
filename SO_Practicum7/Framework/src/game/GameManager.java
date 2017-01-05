/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import event.Event;
import game.exceptions.GameLoadException;
import game.exceptions.NoGameLoadedException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Sean
 */
public class GameManager extends event.EventPublisher implements event.EventListener{
    
    private GameInterface loadedGame;
    
    public GameManager(){
        event.EventBroker.getEventBroker().addEventListener(this);
    }
    
    public void loadGame(File gameFile) throws GameLoadException{
        if(gameFile != null){
            try{
                //create loader
                URLClassLoader loader = new URLClassLoader(new URL[]{gameFile.toURI().toURL()});
                
                //get name
                String className = gameFile.getName().substring(0, gameFile.getName().lastIndexOf('.'));
                
                //load class
                Class gameClass = loader.loadClass(className.toLowerCase()+"."+className);
                
                //create instance of class
                Object game = gameClass.newInstance();
                
                if(game instanceof GameInterface){
                    loadedGame = (GameInterface) game;
                }else{
                    throw new game.exceptions.GameLoadException();
                }
            } catch (ClassNotFoundException | MalformedURLException | InstantiationException | IllegalAccessException ex) {
                throw new game.exceptions.GameLoadException();
            }
        }else{
            throw new game.exceptions.GameLoadException();
        }
    }
    
    public GameInterface getLoadedGame(){
        return loadedGame;
    }
    
    @Override
    public void handleEvent(Event event){
        if(event.getType().equals("INVITE")){
            
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    Alert confirmation = new Alert(AlertType.CONFIRMATION);
                    confirmation.setTitle("Game Invitation");
                    confirmation.setHeaderText("Would you like to play "+event.getMessage()+"?\nYou will be player Black.");
                    confirmation.setContentText("Choose your answer.");
                    confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> result = confirmation.showAndWait();

                    if(result.get() == ButtonType.YES){
                        if((loadedGame != null) && (loadedGame.getGameName().equals(event.getMessage()))){
                            acceptInvitation();
                            loadedGame.startGame(-1);
                        }else{
                            Alert error = new Alert(AlertType.ERROR);
                            error.setTitle("Game Not Loaded!");
                            error.setHeaderText("You cannot play "+event.getMessage()+", since the game is not loaded.");
                            error.setContentText("Load the game first.");
                            error.showAndWait();
                            declineInvitation();
                        }
                    }else{
                        declineInvitation();
                    }
                }
            });
            
        }else if(event.getType().equals("ACCEPTED")){
            
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    Alert accepted = new Alert(AlertType.INFORMATION);
                    accepted.setTitle("Game Invitation Accepted");
                    accepted.setHeaderText("Your game invitation has been accepted.");
                    accepted.setContentText("You are player White and can start the game.");
                    accepted.showAndWait();
                    loadedGame.startGame(1);
                }
            });
            
        }else if(event.getType().equals("DECLINED")){
            
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    Alert declined = new Alert(AlertType.ERROR);
                    declined.setTitle("Game Invitation Declined");
                    declined.setHeaderText("Your game invitation has been declined.");
                    declined.setContentText(null);
                    declined.showAndWait();
                }
            });
            
        }
    }
    
    public void sendInvitation() throws NoGameLoadedException{
        if(loadedGame != null){
            publishEvent(new game.events.GameInvitationEvent(loadedGame.getGameName()));
        }else{
            throw new game.exceptions.NoGameLoadedException();
        }
    }
    
    public void acceptInvitation(){
        publishEvent(new game.events.GameInviteAcceptedEvent());
    }
    
    public void declineInvitation(){
        publishEvent(new game.events.GameInviteDeclinedEvent());
    }
}
