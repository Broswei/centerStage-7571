package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class LauncherTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        GamepadEx gamepadEx1 = new GamepadEx (gamepad1);

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

        Servo angleAdjuster = hardwareMap.get(Servo.class, "angleAdjuster");
        angleAdjuster.setDirection(Servo.Direction.REVERSE);

        Servo launcher = hardwareMap.get(Servo.class, "launcher");
        launcher.setDirection(Servo.Direction.REVERSE);


        double gyroOffset = 0.0;
        angleAdjuster.setPosition(0);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            gamepadEx1.updateControllerStates();
            boolean rising = gamepadEx1.b_pressed;
            boolean descending = gamepadEx1.a_pressed;

            if (rising){
                angleAdjuster.setPosition(0.2);

            }
            if (descending) {
                angleAdjuster.setPosition(0.08);
            }

            if (gamepad1.right_trigger > 0.01){
                launcher.setPosition(0.5);
            }
            else{
                launcher.setPosition(0);
            }

            telemetry.addData("Angle Position: ", angleAdjuster.getPosition());
            telemetry.update();
        }
    }
}
