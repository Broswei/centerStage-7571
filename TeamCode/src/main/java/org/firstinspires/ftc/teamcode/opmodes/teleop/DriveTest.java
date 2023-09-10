package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.bread.BreadOpMode;


@TeleOp(group="DriveTest")

public abstract class DriveTest extends BreadOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while(opModeIsActive()){


            telemetry.update();
        }
    }
}
