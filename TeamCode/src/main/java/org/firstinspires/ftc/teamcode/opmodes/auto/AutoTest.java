package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

public class  AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup();

        this.bread.hand.unclamp();
        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
                this.bread.hand.clamp();
            }
            //insert camera recongition

            telemetry.addData("Status: ", "Initialized");

        }

        this.bread.drive.getRoadrunnerDrive().driveDistance(47, 750, opModeIsActive());

        this.bread.drive.getRoadrunnerDrive().strafeDistance( 94, 750, opModeIsActive());




    }
}
