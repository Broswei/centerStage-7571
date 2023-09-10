package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadConfig {
    public static class Hardware {
        // drive related hardware not included //

        // scoring mech motors

        // scoring mech servos

        public boolean complete(){   //make sure all hardware is configured (!null)
            return true;
        }
    }

    /**
     * @brief Loads hardware from hardwareMap into an BreadConfig.Hardware class
     *
     * This is where to change the names of imports in hardware.  these should be used in every class and opmode involving Bread
     *
     * @param hardwareMap
     * @return
     */
    public static BreadConfig.Hardware loadHardware(HardwareMap hardwareMap){
        BreadConfig.Hardware hardware = new BreadConfig.Hardware();

        return hardware;
    }
}