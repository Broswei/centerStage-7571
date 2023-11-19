package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadConfig {
    public static class Hardware {
        // drive related hardware not included //

        // goRail motors
        public DcMotorEx rightRail;
        public DcMotorEx leftRail;

        // arm motor
        public DcMotorEx rotator;

        // scoring mech servos
        public Servo angleAdjuster;
        public Servo launcher;
        public CRServo leftIntake;
        public CRServo rightIntake;

        //arm servos
        public Servo wristServo;
        public Servo clawServo;

        public boolean complete(){   //make sure all hardware is configured (!null)
            return this.rightRail != null && this.leftRail !=null && angleAdjuster != null && launcher !=null && rotator != null  && clawServo != null; //&& wristServo !=null;

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

        hardware.leftRail = hardwareMap.get(DcMotorEx.class, "leftRail");
        hardware.rightRail = hardwareMap.get(DcMotorEx.class, "rightRail");

        hardware.leftRail.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.rightRail.setDirection(DcMotorSimple.Direction.FORWARD);

        hardware.rotator = hardwareMap.get(DcMotorEx.class, "rotator");

        hardware.angleAdjuster = hardwareMap.get(Servo.class, "angleAdjuster");
        hardware.launcher = hardwareMap.get(Servo.class, "launcher");
        hardware.angleAdjuster.setDirection(Servo.Direction.REVERSE);
        hardware.launcher.setDirection(Servo.Direction.REVERSE);

        //hardware.wristServo = hardwareMap.get(Servo.class, "wristServo");
        hardware.clawServo = hardwareMap.get(Servo.class, "clawServo");
        hardware.clawServo.setDirection(Servo.Direction.REVERSE);
        //hardware.wristServo.setDirection(Servo.Direction.REVERSE);

        return hardware;
    }
}