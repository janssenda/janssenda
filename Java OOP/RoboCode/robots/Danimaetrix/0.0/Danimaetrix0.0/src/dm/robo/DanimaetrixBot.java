/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm.robo;

import java.util.Random;
import robocode.*;

/**
 *
 * @author Danimaetrix
 */
public class DanimaetrixBot extends AdvancedRobot {

    @Override
    public void run() {
        Random r = new Random();
        while (true) {

            
            turnRight(r.nextInt(100));
            
            ahead(45);
            

            try {
                Thread.sleep(500);
            } catch (Exception e) {

            }
            //turnGunRight(360);
//            ahead(100);
//            turnGunRight(360);
//            back(100);
//            turnGunRight(360);
        }

    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
          
          turnRight(e.getBearing());
          fire(1);
    }

}
