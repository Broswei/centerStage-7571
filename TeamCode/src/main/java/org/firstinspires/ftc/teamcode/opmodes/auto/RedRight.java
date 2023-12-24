package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;

@Autonomous(group="norm autos")
public class RedRight extends BreadAutonomous {

    private int spikeMark;

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        boolean found = false;

        while (!isStarted()){

            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }

            spikeMark = pipeline.getSpikeMark();
            if (spikeMark != 0){
                found = true;
            }


            if (found){
                telemetry.addData("Spike Mark: ", spikeMark);
            }

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();
        }


        closeCameraAsync();
        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        if (this.spikeMark == 2) {
            driveDistance(12, 2000, opModeIsActive());
            bread.arm.setRotatorAngleDegrees(195);
            bread.arm.updateArm();
            bread.arm.setLowDepoPos();
            sleep(1500);
            bread.arm.setRightUnclamped();
            sleep(1000);
            bread.arm.setRightClamped();
            strafeDistance(-48,2000,opModeIsActive());
            bringHome();
            bread.arm.setRestPos();
            sleep(1500);
            bread.arm.setPickUpPos();
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            bread.arm.setLeftClamped();
        }
        else if (this.spikeMark == 1){

        }
        else if (this.spikeMark == 3){

        }
        else{
            telemetry.addLine("Current shitting the bed.");
        }


        while (opModeIsActive()){

        }

    }

    public void bringHome(){
        bread.arm.setRotatorAngleDegrees(0);
        bread.arm.updateArm();
        while (bread.arm.areRotatorsBusy()){

        }
    }
}
