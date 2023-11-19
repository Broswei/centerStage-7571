package org.firstinspires.ftc.teamcode.lib.bread;

import android.icu.text.Transliterator;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.motion.LinearRails;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableServo;
import org.firstinspires.ftc.teamcode.lib.util.Imu;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadBot {

    public final BreadDrive drive;
    public Imu imu;
    public BreadHand hand;
    public LinearRails towers;
    public BreadArm arm;
    public Servo launcher;
    public PositionableMotor rotator;
    public Servo angleAdjuster;

    public Servo wristServo;

    public BreadBot(HardwareMap hardwareMap){

        //load hardware
        BreadConfig.Hardware hardware = BreadConfig.loadHardware(hardwareMap);

        //load drive
        drive = new BreadDrive(hardwareMap);

        //load imu
        imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        //load motor
        rotator = new PositionableMotor(hardware.rotator, BreadConstants.ROT_GEAR_RATIO, BreadConstants.ROT_TPR);

        //load servos
        launcher = hardware.launcher;
        angleAdjuster = hardware.angleAdjuster;
        wristServo = hardware.wristServo;
        PositionableServo wrist = new PositionableServo(hardware.wristServo);

        //load rails
        towers = new LinearRails(hardware.leftRail, hardware.rightRail, BreadConstants.TOWERS_GEAR_RATIO, BreadConstants.TOWERS_TPR);

        //load hand
        hand = new BreadHand(wrist, hardware.clawServo);

        //load arm
        this.arm = new BreadArm(rotator, towers, hand);
    }


}
