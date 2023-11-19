package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(group = "Tests")
public class WristTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo wristServo = hardwareMap.get(Servo.class, "wristServo");
        wristServo.setDirection(Servo.Direction.REVERSE);

        wristServo.setPosition(0);
       /* PositionableServo wrist = new PositionableServo(wristServo, BreadConstants.WRIST_MAX_RANGE, Math.toRadians(BreadConstants.WRIST_ZERO_ANGLE));

        BreadHand hand = new BreadHand(wrist,clawServo);

        wristServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        wrist.rotateToDegrees(100);
*/
        waitForStart();

        while(!isStopRequested()){

            wristServo.setPosition(0.6);

            telemetry.update();
        }
    }
}
