/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.io.Serializable;

/**
 *
 * @author maartendesnouck
 */
public class ChatMessage extends event.Event implements Serializable{
    
    private String sender;
    private static final String TYPE_CHAT = "CHAT";
    
    public ChatMessage(String sender, String message){
        super(TYPE_CHAT,message);
        this.sender = sender;    
    }
    
    public String getSender(){
        return sender;
    }
    
}
