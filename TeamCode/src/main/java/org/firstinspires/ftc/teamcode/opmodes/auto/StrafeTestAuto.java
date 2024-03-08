package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;

@Autonomous(group="test")
public class StrafeTestAuto extends BreadAutonomous {

    private int spikeMark;

    private int aprilTag;

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        this.bread.vision.setDetectingBlueTSE(false);

        boolean found = false;

        while (!isStarted()){

            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }

            spikeMark = bread.vision.getSpikeMark();
            if (spikeMark != 0){
                found = true;
            }


            if (found){
                telemetry.addData("Spike Mark: ", spikeMark);
            }

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();
        }

//        closeCameraAsync();
        bread.vision.stopProcessors(); // change to detectAprilTags later

        aimForYellow();

        sleep (3000);

        strafeDistance(15, 1750, opModeIsActive());



    }

    public void aimForYellow(){
        bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_LOW_DEPO_ANG);
        bread.arm.updateArm();
        bread.arm.setLowDepoPos();
    }
}
