/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm.robo;

import robocode.*;

/**
 *
 * @author Danimaetrix
 */
public class DanimaetrixDrone extends AdvancedRobot {

    @Override
    public void run() {

        while (true) {
            ahead(100);
            //turnGunRight(360);
            //back(100);
            //turnGunRight(360);
        }

    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {

        fire(1);

    }
}
