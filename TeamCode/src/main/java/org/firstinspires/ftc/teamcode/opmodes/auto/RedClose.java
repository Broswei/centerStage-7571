package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;

@Autonomous(group="norm autos")
public class RedClose extends BreadAutonomous {

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
        bread.vision.stopProcessors(); // TODO: change to detectAprilTag ltr;
        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        if (this.spikeMark == 2) {
            aprilTag = 5;
            driveDistance(36, 2000, opModeIsActive());
            sleep(1000);
            turnNoPID(-85,2);
            driveDistance(8,2000,opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setRightUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            bread.arm.setRightClamped();
            aimForYellow();
            sleep(3500);
            aimForYellow();
            strafeDistance(-15,2000,opModeIsActive());
            driveDistance(15,2000,opModeIsActive());
        }
        else{
            driveDistance(21, 2000, opModeIsActive());
            turnNoPID(-85,3);
            bread.arm.setPickUpPos();
            if (this.spikeMark == 1){
                aprilTag = 4;
                driveDistance(3,2750,opModeIsActive());
                bread.arm.setRightUnclamped();
                bread.drive.setPowers(0,0,0,0);
                sleep(1000);
                driveDistance(24, 2000,opModeIsActive());
                bread.arm.setRightClamped();
                aimForYellow();
                sleep(3500);
            }
            else if (this.spikeMark == 3){
                aprilTag = 6;
                driveDistance(24, 2000, opModeIsActive());
                strafeDistance(8,2000,opModeIsActive());
                bread.arm.setRightUnclamped();
                bread.drive.setPowers(0,0,0,0);
                sleep(1000);
                bread.arm.setRestPos();
                bread.arm.setRightClamped();
                aimForYellow();
                sleep(3500);
                strafeDistance(-30,2000,opModeIsActive());
                driveDistance(4,2000,opModeIsActive());
                sleep(1000);
            }
        }

        bread.drive.setPowers(0,0,0,0);
        bread.arm.setLeftUnclamped();
        bread.drive.setPowers(0,0,0,0);
        sleep(1000);
        driveDistance(-8,2000,opModeIsActive());
        bread.arm.bringHome();
        bread.arm.setLeftClamped();
        bread.arm.setRestPos();
        turnNoPID(-90,2);
        if (this.spikeMark == 3){
            driveDistance(13, 2000, opModeIsActive());
        }
        else if (this.spikeMark == 1){
            driveDistance(30, 2000, opModeIsActive());
        }
        else{
            driveDistance(22, 2000, opModeIsActive());
        }
        strafeDistance(20, 2000, opModeIsActive());
        driveDistance(6,2000,opModeIsActive());

        while (opModeIsActive()){

        }

    }



}
