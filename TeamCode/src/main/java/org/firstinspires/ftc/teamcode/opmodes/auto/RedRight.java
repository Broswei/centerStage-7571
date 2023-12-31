package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;

@Autonomous(group="norm autos")
public class RedRight extends BreadAutonomous {

    private int spikeMark;

    private int aprilTag;

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

        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        if (this.spikeMark == 2) {
            aprilTag = 5;
            driveDistance(18, 2000, opModeIsActive());
            aimForYellow();
            sleep(3500);
            bread.arm.setRightUnclamped();
            sleep(1000);
            bread.arm.setRightClamped();
            turnToPID(-90);
            driveDistance(24, 2000, opModeIsActive());
        }
        else{
            driveDistance(24, 2000, opModeIsActive());
            turnToPID(-90);
            bread.arm.setPickUpPos();
            if (this.spikeMark == 1){
                aprilTag = 4;
                driveDistance(-3, 2000,opModeIsActive());
                bread.arm.setRightUnclamped();
                sleep(1000);
                driveDistance(12, 2000,opModeIsActive());
                bread.arm.setRightClamped();
                aimForYellow();
            }
            else if (this.spikeMark == 3){
                aprilTag = 6;
                driveDistance(12, 2000, opModeIsActive());
                bread.arm.setRightUnclamped();
                sleep(1000);
                bread.arm.setRestPos();
                bread.arm.setRightClamped();
                turnToPID(280);
                aimForYellow();


            }
        }
        bread.arm.setLeftUnclamped();
        sleep(1000);
        bringHome();
        bread.arm.setLeftClamped();
        bread.arm.setRestPos();
        turnToPID(180);
        if (this.spikeMark == 3){
            driveDistance(12, 2000, opModeIsActive());
        }
        else if (this.spikeMark == 1){
            driveDistance(36, 2000, opModeIsActive());
        }
        else{
            driveDistance(24, 2000, opModeIsActive());
        }
        strafeDistance(6, 2000, opModeIsActive());

        while (opModeIsActive()){

        }

    }

    public void bringHome(){
        bread.arm.setRotatorAngleDegrees(0);
        bread.arm.updateArm();
    }

    public void aimForYellow(){
        bread.arm.setRotatorAngleDegrees(195);
        bread.arm.updateArm();
        bread.arm.setLowDepoPos();
    }
}
