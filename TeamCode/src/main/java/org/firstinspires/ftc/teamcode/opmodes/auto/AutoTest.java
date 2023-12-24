package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;

@Autonomous
public class AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        double kP = ImuPIDController.kP;
        double kI = ImuPIDController.kI;
        double kD = ImuPIDController.kD;

        double targetAngle = 90;

        while (!isStarted()){

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }

        while (opModeIsActive()){

            ImuPIDController pid = new ImuPIDController(targetAngle, kP, kI, kD);
            while (opModeIsActive() && Math.abs(targetAngle - bread.imu.getAbsoluteAngleDegrees()) > 1){
                double motorPower = pid.update(bread.imu.getAbsoluteAngleDegrees());
                bread.drive.setPowers(-0.83*motorPower, -motorPower, motorPower, 0.83*motorPower);
            }

            bread.drive.setPowers(0,0,0,0);
            telemetry.addData("Target angle: ", targetAngle);
            telemetry.addData("Current Position: ", bread.imu.getAbsoluteAngleDegrees());
            telemetry.update();

        }


    }
}
