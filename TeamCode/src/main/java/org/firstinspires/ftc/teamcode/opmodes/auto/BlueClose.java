package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

@Autonomous(group="norm autos")
public class BlueClose extends BreadAutonomous {

    private int spikeMark;

    private int aprilTag;

    @Override
    public void runOpMode() throws InterruptedException {

        setup(true);

        this.bread.vision.setDetectingBlueTSE(true);

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
        bread.vision.stopProcessors();
        //hi - saeid (random 13406 member that definitely isn't the captain and definitely did not type "hi" when I wasn't looking)
        if (this.spikeMark == 2) {
            aprilTag = 2;
            driveDistance(11, 2000, opModeIsActive());
            aimForYellow();
            sleep(3500);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setLeftClamped();
            driveDistance(9,2000,opModeIsActive());
            turnNoPID(80,3);
            driveDistance(28, 2000, opModeIsActive());
            sleep(1000);
            strafeDistance(-15,2000,opModeIsActive());
            driveDistance(4,2000,opModeIsActive());
        }
        else{
            driveDistance(20, 2000, opModeIsActive());
            turnNoPID(80,3);
            bread.arm.setPickUpPos();
            if (this.spikeMark == 3){
                aprilTag = 4;
                driveDistance(3,2750,opModeIsActive());
                bread.arm.setLeftUnclamped();
                bread.drive.setPowers(0,0,0,0);
                sleep(1000);
                driveDistance(19.5, 2000,opModeIsActive());
                bread.arm.setLeftClamped();
                aimForYellow();
                sleep(3500);
                strafeDistance(-27,2000,opModeIsActive());
                driveDistance(4,2000,opModeIsActive());
            }
            else if (this.spikeMark == 1){
                aprilTag = 6;
                driveDistance(24, 2000, opModeIsActive());
                strafeDistance(-6,2000,opModeIsActive());
                bread.arm.setLeftUnclamped();
                bread.drive.setPowers(0,0,0,0);
                sleep(1000);
                bread.arm.setRestPos();
                bread.arm.setLeftClamped();
                aimForYellow();
                sleep(3500);
                strafeDistance(-6,2000,opModeIsActive());
                driveDistance(4,2000,opModeIsActive());
                sleep(1000);
            }
        }

        bread.drive.setPowers(0,0,0,0);
        bread.arm.setRightUnclamped();
        bread.drive.setPowers(0,0,0,0);
        sleep(1000);
        driveDistance(-6,2000,opModeIsActive());
        bread.arm.bringHome();
        bread.arm.setRightClamped();
        bread.arm.setRestPos();
        turnNoPID(80,2);
        if (this.spikeMark == 1){
            driveDistance(20, 2000, opModeIsActive());
        }
        else if (this.spikeMark == 3){
            driveDistance(32, 2000, opModeIsActive());
        }
        else{
            driveDistance(19, 2000, opModeIsActive());
        }
        strafeDistance(-22, 2000, opModeIsActive());
        driveDistance(6,2000,opModeIsActive());

        while (opModeIsActive()){

        }

    }

}
