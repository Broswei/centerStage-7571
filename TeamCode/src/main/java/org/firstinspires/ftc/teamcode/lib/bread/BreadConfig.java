package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class BreadConfig {
    public static class Hardware {
        // drive related hardware not included //

        // goRail motors
        public DcMotorEx rightRail;
        public DcMotorEx leftRail;

        // arm motors
        public DcMotorEx leftRotator;
        public DcMotorEx rightRotator;

        // scoring mech servos
        public Servo angleAdjuster;
        public Servo launcher;

        //arm servos
        public Servo wristServo;
        public Servo leftClaw;
        public Servo rightClaw;

        public WebcamName webcamName;

        public boolean complete(){   //make sure all hardware is configured (!null)
            return this.rightRail != null && this.leftRail !=null && angleAdjuster != null && launcher !=null && leftRotator != null && rightRotator != null  && leftClaw != null && rightClaw != null && wristServo !=null;

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

        hardware.leftRotator = hardwareMap.get(DcMotorEx.class, "leftRotator");
        hardware.rightRotator = hardwareMap.get(DcMotorEx.class, "rightRotator");
        hardware.leftRotator.setDirection(DcMotorSimple.Direction.REVERSE);

        hardware.angleAdjuster = hardwareMap.get(Servo.class, "angleAdjuster");
        hardware.launcher = hardwareMap.get(Servo.class, "launcher");
        hardware.angleAdjuster.setDirection(Servo.Direction.REVERSE);
        hardware.launcher.setDirection(Servo.Direction.REVERSE);

        hardware.wristServo = hardwareMap.get(Servo.class, "wristServo");

        hardware.leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        hardware.leftClaw.setDirection(Servo.Direction.REVERSE);
        hardware.rightClaw = hardwareMap.get(Servo.class, "rightClaw");

        hardware.webcam = hardwareMap.get(WebcamName.class, "webcam");

        return hardware;
    }
}