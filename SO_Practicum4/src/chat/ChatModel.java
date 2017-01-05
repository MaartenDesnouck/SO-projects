/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author maartendesnouck
 */
public class ChatModel {
    
    private StringProperty chatTextProperty;
    private String name;
    private List<ChatMessage> messages;
    
    public ChatModel(){
        messages = new ArrayList<>();
        chatTextProperty = new SimpleStringProperty("");
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void addMessage(ChatMessage message){
        messages.add(message);
        String text = chatTextProperty.getValue();
        text = text+message.getSender()+": "+message.getMessage()+"\n";
        chatTextProperty.setValue(text);
    }
    
    public List<ChatMessage> getMessages(){
        return messages;
    }
    
    public StringProperty chatTextProperty(){
        return chatTextProperty;
    }
}
