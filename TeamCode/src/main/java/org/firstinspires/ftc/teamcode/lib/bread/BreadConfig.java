package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadConfig {
    public static class Hardware {
        // drive related hardware not included //

       /* // goRail motors
        public DcMotorEx rightRail;
        public DcMotorEx leftRail;

        // scoring mech servos
        public Servo droneServo;
        public Servo intakeServo1;
        public Servo intakeServo2;

        //arm servos
        public Servo wristServo;
        public Servo clawServo; */

        public boolean complete(){   //make sure all hardware is configured (!null)
            return true; //this.rightRail != null && this.leftRail !=null && droneServo != null && intakeServo1 !=null && intakeServo2 != null && wristServo !=null && clawServo != null;

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

       /* hardware.leftRail = hardwareMap.get(DcMotorEx.class, "leftRail");
        hardware.rightRail = hardwareMap.get(DcMotorEx.class, "rightRail");

        hardware.leftRail.setDirection(DcMotorSimple.Direction.REVERSE);
        hardware.rightRail.setDirection(DcMotorSimple.Direction.REVERSE);

        hardware.intakeServo1 = hardwareMap.get(Servo.class, "intake1");
        hardware.intakeServo2 = hardwareMap.get(Servo.class, "intake2");
        hardware.droneServo = hardwareMap.get(Servo.class, "droneServo");

        hardware.wristServo = hardwareMap.get(Servo.class, "wristServo");
        hardware.clawServo = hardwareMap.get(Servo.class, "clawServo");
        */
        return hardware;
    }
}