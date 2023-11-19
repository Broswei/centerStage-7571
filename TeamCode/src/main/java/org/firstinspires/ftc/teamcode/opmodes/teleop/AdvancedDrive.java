package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.bread.BreadTeleOp;
import org.firstinspires.ftc.teamcode.lib.bread.BreadOpMode;
import org.firstinspires.ftc.teamcode.lib.util.Imu;


@TeleOp(group="Comp")

public class AdvancedDrive extends BreadTeleOp {

    @Override
    public void runOpMode() throws InterruptedException {

        setup();
        this.bread.angleAdjuster.setPosition(0.08);
        this.bread.launcher.setPosition(0);
        this.bread.wristServo.setPosition(0);
        this.bread.hand.clamp();

        this.bread.rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            update();

            telemetry.addData("Towers Position: ", this.bread.towers.getRawPosition());
            telemetry.addData("Rotator Angle: ", this.bread.rotator.getAngleDegrees());
            telemetry.addData("Wrist Position: ", this.bread.wristServo.getPosition());
            telemetry.update();
        }
    }
}
