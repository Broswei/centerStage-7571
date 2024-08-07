package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.bread.BreadDrive;

import org.firstinspires.ftc.teamcode.lib.util.Imu;

@TeleOp(name = "Static Heading")
public class StaticHeading extends BreadAutonomous {
    double integralSum = 0;

    double Kp = BreadConstants.IMU_P;
    double Ki = BreadConstants.IMU_I;
    double Kd = BreadConstants.IMU_D;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        double refrenceAngle = Math.toRadians(BreadConstants.IMU_TARGET);
        initialize(hardwareMap);
        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Target IMU Angle", refrenceAngle);
            telemetry.addData("Current IMU Angle", Math.toRadians(bread.imu.getAngleDegrees()));
            double power = PIDControl(refrenceAngle, Math.toRadians(bread.imu.getAngleDegrees()));
            bread.drive.setPowers(-0.83*power, -power, power, 0.83*power);
            telemetry.update();
        }

    }

    public double PIDControl(double refrence, double state) {
        double error = angleWrap(refrence - state);
        telemetry.addData("Error: ", Math.toDegrees(error));
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);
        return output;
    }
    public double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }


}
