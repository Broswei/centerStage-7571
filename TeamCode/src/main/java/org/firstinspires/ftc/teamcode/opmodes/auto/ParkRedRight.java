package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
@Autonomous(group="park autos")
public class ParkRedRight extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup();

        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
              //  this.bread.hand.clamp();
            }
            //insert camera recongition

            telemetry.addData("Status: ", "Initialized");

        }


        while (opModeIsActive()) {
            this.bread.drive.getRoadrunnerDrive().strafeDistance(-47, 750, opModeIsActive());
        }



    }
}
