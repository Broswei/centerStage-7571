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
            driveDistance(7,2000,opModeIsActive());
            turnNoPID(80, 2);
            driveDistance(28, 2000, opModeIsActive());
            turnToPID(-85,2);
            strafeDistance(-20,2000,opModeIsActive());
            sleep(1000);
        }
        else{
            driveDistance(18, 2000, opModeIsActive());
            turnNoPID(80,2);
            bread.arm.setPickUpPos();
            if (this.spikeMark == 1){
                aprilTag = 1;
                driveDistance(22, 2000, opModeIsActive());
                strafeDistance(-8,2000,opModeIsActive());
                bread.arm.setLeftUnclamped();
                sleep(1000);
                bread.arm.setRestPos();
                bread.arm.setLeftClamped();
                turnToPID(-90,3);
                aimForYellow();
                sleep(3500);
                driveDistance(6,2000,opModeIsActive());
                strafeDistance(12,2000,opModeIsActive());
                turnToPID(-90,2);
                sleep(1000);
            }
            else if (this.spikeMark == 3){
                aprilTag = 3;
                driveDistance(4,2750,opModeIsActive());
                bread.arm.setLeftUnclamped();
                bread.drive.setPowers(0,0,0,0);
                sleep(1000);
                turnToPID(-90,2);
                driveDistance(22, 2000,opModeIsActive());
                bread.arm.setLeftClamped();
                aimForYellow();
                sleep(3500);
                turnToPID(-90,2);
                strafeDistance(-12,2000,opModeIsActive());
                turnToPID(-90,3);
                driveDistance(6,2000,opModeIsActive());
                sleep(1000);
            }
        }

        bread.arm.setRightUnclamped();
        sleep(1000);
        driveDistance(-8,2000,opModeIsActive());
        bread.arm.bringHome();
        bread.arm.setRightClamped();
        bread.arm.setRestPos();
        turnToPID(0,2);
        if (this.spikeMark == 3){
            driveDistance(32, 2000, opModeIsActive());
        }
        else if (this.spikeMark == 1){
            driveDistance(14, 2000, opModeIsActive());
        }
        else{
            driveDistance(21, 2000, opModeIsActive());
        }
        strafeDistance(-22, 2000, opModeIsActive());

        while (opModeIsActive()){

        }

    }

    public void aimForYellow(){
        bread.arm.setRotatorAngleDegrees(195);
        bread.arm.updateArm();
        bread.arm.setLowDepoPos();
    }
}
