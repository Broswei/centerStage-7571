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

public abstract class AdvancedDrive extends BreadTeleOp {

    @Override
    public void runOpMode() throws InterruptedException {

        setup();
        this.bread.angleAdjuster.setPosition(0);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested()){

            update();

            telemetry.update();
        }
    }
}
