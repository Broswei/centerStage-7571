package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;
import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class RotatorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //load gamepads
        GamepadEx gamepadEx1 = new GamepadEx (gamepad1);
        GamepadEx gampadEx2 = new GamepadEx (gamepad2);

        // load motors
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "br");

        PositionableMotor rotator = new PositionableMotor(hardwareMap.get(DcMotorEx.class, "rotator"), BreadConstants.ROT_GEAR_RATIO, BreadConstants.ROT_TPR);

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

            gamepadEx1.updateControllerStates();

            boolean ascending = gamepadEx1.b_pressed;
            boolean descending = gamepadEx1.a_pressed;

            if(gamepad1.y){
                gyroOffset = imu.getAngleRadians();
            }

            double forward = Range.clip(-gamepad1.left_stick_y, -0.6, 0.6);
            double strafe = Range.clip(gamepad1.left_stick_x, -0.6, 0.6);
            double rotate = Range.clip(gamepad1.right_stick_x, -0.4, 0.4);

            double temp = strafe*Math.cos(imu.getAngleRadians()-gyroOffset)+forward*Math.sin(imu.getAngleRadians()-gyroOffset);
            forward = -strafe*Math.sin(imu.getAngleRadians()-gyroOffset)+forward*Math.cos(imu.getAngleRadians()-gyroOffset);
            strafe = temp;

            double fl = forward + strafe + rotate;
            double fr = forward - strafe - rotate;
            double bl = forward - strafe + rotate;
            double br = forward + strafe - rotate; //could be wrong here as well, gotta tinker

            frontLeft.setPower(fl);
            frontRight.setPower(fr);
            backLeft.setPower(bl);
            backRight.setPower(br);

            rotator.rotateToDegrees(180,1000);
            sleep(5000);
            rotator.rotateToDegrees(0,1000);




            telemetry.addData("Gyro Rotation: ", imu.getAngleRadians());
            telemetry.addData("Gyro offset: ", gyroOffset);
            telemetry.update();
        }
    }
}
