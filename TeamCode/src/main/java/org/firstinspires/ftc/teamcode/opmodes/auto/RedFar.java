package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;

@Autonomous(group="norm autos")
public class RedFar extends BreadAutonomous {

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

        closeCameraAsync();
        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        if (this.spikeMark == 2) {
            aprilTag = 2;
            driveDistance(48, 1750, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
        }
        else if (this.spikeMark == 1) {
            strafeDistance(15,1750,opModeIsActive());
            driveDistance(48, 1500, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
        }
        else if (this.spikeMark == 3){
            driveDistance(22, 2000, opModeIsActive());
            aprilTag = 1;
            turnNoPID(-80,3);
            driveDistance(20, 2000, opModeIsActive());
            bread.drive.setPowers(0,0,0,0);
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            bread.arm.setLeftClamped();
        }

        while (opModeIsActive()){

        }

    }

    public void aimForYellow(){
        bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_LOW_DEPO_ANG);
        bread.arm.updateArm();
        bread.arm.setLowDepoPos();
    }
}
