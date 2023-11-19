package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class OnlyGoRailTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx leftRail = hardwareMap.get(DcMotorEx.class, "leftRail");
        DcMotorEx rightRail = hardwareMap.get(DcMotorEx.class, "rightRail");

        leftRail.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRail.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightRail.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            if (gamepad1.b){
                leftRail.setPower(1);
                rightRail.setPower(-1);
            }else{
                leftRail.setPower(0);
                rightRail.setPower(0);
            }
        }


        }
    }

