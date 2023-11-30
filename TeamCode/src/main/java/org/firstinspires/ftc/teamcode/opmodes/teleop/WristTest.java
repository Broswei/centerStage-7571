package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(group = "Tests")
public class WristTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo wristServo = hardwareMap.get(Servo.class, "wristServo");

        wristServo.setPosition(0);

        waitForStart();

        while(!isStopRequested()){

            if (gamepad1.b){
                wristServo.setPosition(0.6);
            }
            else{
                wristServo.setPosition(0);
            }

            telemetry.update();
        }
    }
}
