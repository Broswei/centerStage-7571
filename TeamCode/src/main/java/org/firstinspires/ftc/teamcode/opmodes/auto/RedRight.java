package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;

@Autonomous(group="park autos")
public class RedRight extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        boolean found = false;

        while (!isStarted()){

            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }

            telemetry.addData("Spike Mark: ", getSpikeMark());
            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }


        closeCameraAsync();
        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        this.bread.arm.setRotatorAngleDegrees(200);
        this.bread.arm.updateArm();
        driveDistance(12.5, 750,opModeIsActive());
        this.bread.arm.setRightUnclamped();
        sleep(500);
        this.bread.arm.setRightClamped();

        while (opModeIsActive()){

        }

    }
}
