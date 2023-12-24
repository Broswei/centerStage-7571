package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;

@Autonomous
public class AutoTest extends BreadAutonomous {

    double kP = ImuPIDController.kP;
    double kI = ImuPIDController.kI;
    double kD = ImuPIDController.kD;

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

            turnPID(targetAngle);

            telemetry.addData("Target angle: ", targetAngle);
            telemetry.addData("Current Position: ", bread.imu.getAbsoluteAngleDegrees());
            telemetry.update();

        }

    }

    public void turnPID(double target){
        ImuPIDController pid = new ImuPIDController(target, this.kP, this.kI, this.kD);
        while (opModeIsActive() && Math.abs(target - bread.imu.getAbsoluteAngleDegrees()) > 1){
            double motorPower = pid.update(bread.imu.getAbsoluteAngleDegrees());
            bread.drive.setPowers(-0.83*motorPower, -motorPower, motorPower, 0.83*motorPower);
        }
        bread.drive.setPowers(0,0,0,0);
    }
}
