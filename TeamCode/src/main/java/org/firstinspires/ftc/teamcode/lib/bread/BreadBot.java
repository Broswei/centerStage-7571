package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class BreadBot {

    public final BreadDrive drive;

    public BreadBot(HardwareMap hardwareMap){

        //load hardware
        BreadConfig.Hardware hardware = BreadConfig.loadHardware(hardwareMap);

        //load drive
        this.drive = new BreadDrive(hardwareMap);



    }


}
