/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarm;

import java.util.Random;

/**
 *
 * @author maartendesnouck
 */
public class FireDepartment implements alarm.AlarmListener {

    Random random = new Random();
    
    @Override
    public void alarm(alarm.Alarm alarm) {
        System.out.println("Fire Department "+random.nextInt(10)+" is checking out the "+alarm.getType()+" at "+alarm.getLocation());
    }
    
}
