package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

@Autonomous(group="norm autos")
public class BlueFar extends BreadAutonomous {

    private int spikeMark;

    private int aprilTag;

    @Override
    public void runOpMode() throws InterruptedException {

        setup(true);

        boolean found = false;

        while(!isStarted()) {

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
        if (this.spikeMark == 2) {
            aprilTag = 2;
            driveDistance(48, 2000, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
            turnToPID(86, 3);
            driveDistance(77, 2000, opModeIsActive());
            aimForYellow();
            strafeDistance(10,2000,opModeIsActive());
            turnToPID(86,2);
            sleep(3500);
            driveDistance(4,2000,opModeIsActive());
        }
        else if (this.spikeMark == 1) {
            driveDistance(18, 2000, opModeIsActive());
            turnToPID(90, 2);
            aprilTag = 1;
            driveDistance(3, 2750, opModeIsActive());
            bread.arm.setRightUnclamped();
            sleep(1000);
            driveDistance(22, 2000, opModeIsActive());
            bread.arm.setRightClamped();
            aimForYellow();
            sleep(3500);
            turnToPID(-90, 2);
            strafeDistance(14, 2000, opModeIsActive());

        }
        else if (this.spikeMark == 3){
            aprilTag = 3;
            strafeDistance(-12,2000,opModeIsActive());
            driveDistance(48, 1500, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
            turnToPID(80, 3);
            driveDistance(82, 2000, opModeIsActive());
            aimForYellow();
            strafeDistance(7,2000,opModeIsActive());
            turnToPID(80,2);
            sleep(3500);
            driveDistance(4,2000,opModeIsActive());
        }


        bread.arm.setRightUnclamped();
        sleep(1000);
        driveDistance(-8,2000,opModeIsActive());
        bread.arm.bringHome();
        bread.arm.setRightClamped();
        bread.arm.setRestPos();
        turnToPID(180,2);
        if (this.spikeMark == 3){
            driveDistance(-13, 2000, opModeIsActive());
        }
        else if (this.spikeMark == 1){
            driveDistance(-32, 2000, opModeIsActive());
        }
        else{
            driveDistance(-19, 2000, opModeIsActive());
        }
        strafeDistance(-20, 2000, opModeIsActive());

        while (opModeIsActive()){

        }


    }

}
