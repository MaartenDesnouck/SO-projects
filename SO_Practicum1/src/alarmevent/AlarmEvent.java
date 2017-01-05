/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmevent;

/**
 *
 * @author maartendesnouck
 */
public class AlarmEvent extends eventbroker.Event{
    
    protected String location;
    
    public AlarmEvent(String type, String location) {
        super(type,"ALARM! "+type+" at "+location);
        this.location=location;
    }
    
    public String getLocation() {
        return location;
    }
}
