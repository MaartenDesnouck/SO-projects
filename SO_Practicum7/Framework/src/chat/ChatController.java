/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author maartendesnouck
 */
final public class ChatController extends event.EventPublisher{
    
    private ChatModel model;
    private ChatEventHandler handler;
    
    @FXML
    private Button sendButton;
    
    @FXML
    private TextField chatField;
    
    @FXML
    private TextArea chatArea;
    
    
    @FXML
    private void initialize(){
        handler = new ChatEventHandler();
        event.EventBroker.getEventBroker().addEventListener(handler);
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                chatArea.textProperty().bind(model.chatTextProperty());
            }
        });
    }
    
    public void setModel(ChatModel model){
        this.model = model;
    }
    
    public void handle(ActionEvent e){
        sendMessage(chatField.getText());
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                chatField.clear();
            }
        });
    }
    
    public void sendMessage(String message){
        publishEvent(new ChatMessage(model.getName(),message));
    }
    
    private class ChatEventHandler implements event.EventListener{
        
        @Override
        public void handleEvent(event.Event e){
            if(e.getType().equals("CHAT")){
                model.addMessage((ChatMessage) e);
            }
        }
        
    }
}
