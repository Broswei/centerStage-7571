package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

@Autonomous(group="park autos")
public class ParkBlueLeft extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(true);
        this.bread.arm.setHandUnclamped();

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
