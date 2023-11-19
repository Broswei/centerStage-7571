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
public class GoRailTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //load gamepads
        GamepadEx gamepadEx1 = new GamepadEx (gamepad1);

        DcMotorEx leftRail = hardwareMap.get(DcMotorEx.class, "leftRail");
        DcMotorEx rightRail = hardwareMap.get(DcMotorEx.class, "rightRail");

        leftRail.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRail.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            gamepadEx1.updateControllerStates();

            boolean ascending = gamepadEx1.b_pressed;
            boolean descending = gamepadEx1.a_pressed;
            boolean resetDown = gamepadEx1.y_pressed;

            if (ascending){
                leftRail.setTargetPosition(10280);
                rightRail.setTargetPosition(10280);
                leftRail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightRail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftRail.setVelocity(2000);
                rightRail.setVelocity(2000);
                while (leftRail.isBusy() && rightRail.isBusy()){}
            }

            if (descending){
                leftRail.setTargetPosition(0);
                rightRail.setTargetPosition(0);
                leftRail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightRail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftRail.setVelocity(2000);
                rightRail.setVelocity(2000);
                while (leftRail.isBusy() && rightRail.isBusy()){}
            }

            telemetry.addData("leftRail Position: ", leftRail.getCurrentPosition());
            telemetry.addData("rightRail Position: ", rightRail.getCurrentPosition());
            telemetry.update();
        }
    }
}
