/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author maartendesnouck
 */
public class ChatPanel {
    
    private AnchorPane content;
    private ChatModel model;
    private ChatController chatController;
    
    public static ChatPanel createChatPanel(){
        ChatModel model = new ChatModel();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ChatPanel.class.getResource("ChatPane.fxml"));
        AnchorPane content = new AnchorPane();
        try{
            content = (AnchorPane) loader.load();
        }catch(IOException e){    
        }
        
        ChatController chatController = loader.getController();
        chatController.setModel(model);
        
        ChatPanel panel = new ChatPanel();
        panel.setChatController(chatController);
        panel.setContent(content);
        panel.setChatModel(model);
        
        return panel;
    }
    
    public void setContent(AnchorPane content){
        this.content = content;
    }
    
    public AnchorPane getContent(){
        return content;
    }
    
    public void setChatModel(ChatModel model){
        this.model = model;
    }
    
    public ChatModel getChatModel(){
        return model;
    }
    
    public void setChatController(ChatController chatController){
        this.chatController = chatController;
    }
    
    public ChatController getChatController(){
        return chatController;
    }
    
}
