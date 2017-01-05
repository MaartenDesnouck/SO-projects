/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.events;

import java.io.Serializable;

/**
 *
 * @author Sean
 */
public class GameInvitationEvent extends event.Event implements Serializable{
    
    private static final String TYPE_INVITE = "INVITE";
    
    public GameInvitationEvent(String game){
        super(TYPE_INVITE,game);
    }
    
}
