package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

public class ParkRedRight extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        //this.bread.hand.unclamp();
        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }
            //insert camera recongition

            telemetry.addData("Status: ", "Initialized");

        }

        driveDistance(-48,2000,opModeIsActive());
        bread.arm.setPickUpPos();
        sleep(1000);
        bread.arm.setHandUnclamped();
        bread.arm.setRestPos();

        while (opModeIsActive()){

        }





    }
}
