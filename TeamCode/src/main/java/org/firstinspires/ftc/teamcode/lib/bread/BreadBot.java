package org.firstinspires.ftc.teamcode.lib.bread;

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

    public BreadBot(HardwareMap hardwareMap){

        //load hardware
        BreadConfig.Hardware hardware = BreadConfig.loadHardware(hardwareMap);

        //load drive
        this.drive = new BreadDrive(hardwareMap);

        //load imu
        imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        //load motor
        PositionableMotor rotator = new PositionableMotor(hardware.rotator, BreadConstants.ROT_GEAR_RATIO, BreadConstants.ROT_TPR);

        //load servos
        Servo launcher = hardware.launcher;
        PositionableServo angleAdjuster = new PositionableServo(hardware.angleAdjuster);
        PositionableServo wristServo = new PositionableServo(hardware.wristServo);

        //load rails
        LinearRails towers = new LinearRails(hardware.leftRail, hardware.rightRail, BreadConstants.TOWERS_GEAR_RATIO, BreadConstants.TOWERS_TPR);

        //load hand
        hand = new BreadHand(wristServo, hardware.clawServo);
    }


}
