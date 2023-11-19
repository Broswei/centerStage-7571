package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group = "Tests")
public class ClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            if (gamepad1.right_trigger > 0.01){
                clawServo.setPosition(0);
            }
            else{
                clawServo.setPosition(1);
            }

            telemetry.addData("servo position: ", clawServo.getPosition());
            telemetry.update();
        }
    }
}
