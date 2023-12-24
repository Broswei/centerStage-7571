package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;

@Autonomous
public class AutoTest extends BreadAutonomous {

    double kP = BreadConstants.IMU_P;
    double kI = BreadConstants.IMU_I;
    double kD = BreadConstants.IMU_D;

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
