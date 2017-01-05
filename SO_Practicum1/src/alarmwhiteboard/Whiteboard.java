/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmwhiteboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author maartendesnouck
 */
public class Whiteboard {
    
    protected Set<alarm.AlarmListener> listeners = new HashSet<>();
    protected final static Whiteboard board = new Whiteboard();
    protected Map<String,Set<alarm.AlarmListener>> listenermap= new HashMap<>();

    private Whiteboard() {
    }

    public static Whiteboard getWhiteboard() {
        return board;
    }
    
    public void addAlarmListener(alarm.AlarmListener s) {
        listeners.add(s);
    }

    public void removeAlarmListener(alarm.AlarmListener s) {
        listeners.remove(s);
    }
    
    public void addAlarmListener(String type, alarm.AlarmListener s) {
        if (!listenermap.containsKey(type)){
            listenermap.put(type, new HashSet<alarm.AlarmListener>());
        }
        listenermap.get(type).add(s);
    }
    
    public void removeAlarmListener(String type, alarm.AlarmListener s){
        if(listenermap.containsKey(type)){
            if(listenermap.get(type).contains(s)){
                listenermap.get(type).remove(s);
            }
            if(listenermap.get(type).isEmpty()){
                listenermap.remove(type);
            }
        }
    }
    
    public Set<alarm.AlarmListener> getAlarmListeners(String type){
        Set<alarm.AlarmListener> result = new HashSet<>();
        result.addAll(listeners);
        if(listenermap.containsKey(type)){
            result.addAll(listenermap.get(type));
        }
        return result;
    }
    
    
}
