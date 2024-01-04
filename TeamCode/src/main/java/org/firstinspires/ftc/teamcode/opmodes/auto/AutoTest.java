package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        while (!isStarted()) {

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }


        driveDistance(12,2000,opModeIsActive());
        turnToPID(-90,3);



        while (opModeIsActive()) {

        }


    }

}
