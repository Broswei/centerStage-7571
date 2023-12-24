package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;

@TeleOp
public class IMUTuning extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);


        double targetAngle = 90;

        while (!isStarted()){

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }

        closeCameraAsync();

        while (opModeIsActive()){
            turnToPID(targetAngle);

            telemetry.addData("target: ", targetAngle);
            telemetry.addData("current: ", bread.imu.getAbsoluteAngleDegrees());
            telemetry.update();
        }

    }

}
