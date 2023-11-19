package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class ArmTest extends LinearOpMode {
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

        DcMotorEx leftRail = hardwareMap.get(DcMotorEx.class, "leftRail");
        DcMotorEx rightRail = hardwareMap.get(DcMotorEx.class, "rightRail");
        DcMotorEx rotator = hardwareMap.get(DcMotorEx.class, "rotator");

        leftRail.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRail.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Imu imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE); //might be wrong ones, i gotta tinker with this one


        double gyroOffset = 0.0;
        int scoringLevel = 1;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            gamepadEx1.updateControllerStates();

            boolean ascending = gamepadEx1.b_pressed;
            boolean descending = gamepadEx1.a_pressed;
            boolean toggleUp = gamepadEx1.dpad_up_pressed;
            boolean toggleDown = gamepadEx1.dpad_down_pressed;
            boolean outtaking = gamepadEx1.x_pressed;
            boolean intakeing = gamepadEx1.y_pressed;


            telemetry.addData("leftRail Position: ", leftRail.getCurrentPosition());
            telemetry.addData("rightRail Position: ", rightRail.getCurrentPosition());
            telemetry.update();
        }
    }
}
