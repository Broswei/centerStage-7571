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

        // load

        DcMotorEx rotator = hardwareMap.get(DcMotorEx.class, "rotator");

        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotator.setTargetPosition(0);
        rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rotator.setVelocity(250);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Rotator Position: ", rotator.getCurrentPosition());

        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            rotator.setTargetPosition((int)(BreadConstants.ROT_TPR * PositionableMotor.degreesToRotations(180)));
            rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rotator.setVelocity(500);


            telemetry.addData("Rotator Position: ", rotator.getCurrentPosition());
            telemetry.addData("Target Position: ", rotator.getTargetPosition());
            telemetry.update();
        }
    }
}
