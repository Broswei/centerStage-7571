package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;
import org.firstinspires.ftc.teamcode.lib.util.Imu;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadBot {

    public final BreadDrive drive;
    public Imu imu;

    public BreadBot(HardwareMap hardwareMap){

        //load hardware
        BreadConfig.Hardware hardware = BreadConfig.loadHardware(hardwareMap);

        //load drive
        this.drive = new BreadDrive(hardwareMap);

        //load imu
        imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        //load motors
        PositionableMotor leftTower = new PositionableMotor(hardware.leftRail, BreadConstants.TOWERS_GEAR_RATIO, BreadConstants.TOWERS_TPR, BreadConstants.TOWERS_BASE_LENGTH_MMS, BreadConstants.TOWERS_MAX_EXTENSION_MMS);
        PositionableMotor rightTower = new PositionableMotor(hardware.leftRail, BreadConstants.TOWERS_GEAR_RATIO, BreadConstants.TOWERS_TPR, BreadConstants.TOWERS_BASE_LENGTH_MMS, BreadConstants.TOWERS_MAX_EXTENSION_MMS);
    }


}
