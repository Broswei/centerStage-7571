package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class PlainFODTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // load motors
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "br");

        Imu imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE); //might be wrong ones, i gotta tinker with this one

        double gyroOffset = 0.0;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            if(gamepad1.y){
                gyroOffset = imu.getAngleRadians();
            }

            // get more input space out of controller; objectively better

            double forward = Range.clip(-gamepad1.left_stick_y, -1, 1) * 0.8;
            double strafe  = Range.clip( gamepad1.left_stick_x, -1, 1);
            double rotate  = Range.clip(gamepad1.right_stick_x, -1, 1) * 0.5;

            double temp = strafe*Math.cos(imu.getAngleRadians()-gyroOffset)+forward*Math.sin(imu.getAngleRadians()-gyroOffset);
            forward = -strafe*Math.sin(imu.getAngleRadians()-gyroOffset)+forward*Math.cos(imu.getAngleRadians()-gyroOffset);
            strafe = temp;

            // * tried and tested method

            // double fl = forward + strafe + rotate;
            // double fr = forward - strafe - rotate;
            // double bl = forward - strafe + rotate;
            // double br = forward + strafe - rotate; 

            // * that wack method that Ahmed found
            // * cred at Gavin Ford: https://www.youtube.com/watch?v=gnSW2QpkGXQ

            double theta = Math.atan2(forward, strafe);
            double power = Math.hypot(forward, strafe);

            double sin = Math.sin(theta - Math.PI * 0.25d);
            double cos = Math.cos(theta - Math.PI * 0.25d);

            // to save divisions at expense of readability

            double inverseScaledPower = 1 / Math.max(Math.abs(sin), Math.abs(cos));

            double fl = power * cos * inverseScaledPower + rotate;
            double fr = power * sin * inverseScaledPower - rotate;
            double bl = power * sin * inverseScaledPower + rotate;
            double br = power * cos * inverseScaledPower - rotate;
            
            // ? maybe change this to actual max output? max ( all motors )
            // TODO: try this on actual bot

            double maxOutput = Math.abs(power + rotate);

            if (maxOutput > 1) {
                fl /= maxOutput;
                fr /= maxOutput;
                bl /= maxOutput;
                br /= maxOutput;
            }

            frontLeft.setPower(fl);
            frontRight.setPower(fr);
            backLeft.setPower(bl);
            backRight.setPower(br);

            telemetry.addData("Gyro Rotation: ", imu.getAngleRadians());
            telemetry.addData("Gyro offset: ", gyroOffset);
            telemetry.update();
        }
    }
}
