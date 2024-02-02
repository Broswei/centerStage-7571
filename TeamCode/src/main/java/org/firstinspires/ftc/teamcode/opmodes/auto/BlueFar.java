package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

@Autonomous(group="norm autos")
public class BlueFar extends BreadAutonomous {

    private int spikeMark;

    private int aprilTag;

    @Override
    public void runOpMode() throws InterruptedException {

        setup(true);

        this.bread.vision.setDetectingBlueTSE(true);

        boolean found = false;

        while(!isStarted()) {

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

        closeCameraAsync();
        if (this.spikeMark == 2) {
            aprilTag = 2;
            strafeDistance(-6,2000,opModeIsActive());
            driveDistance(50, 1750, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
            driveDistance(4,2000,opModeIsActive());
            turnNoPID(70,3);
            driveDistance(80,2000,opModeIsActive());
        }
        else if (this.spikeMark == 1) {
            driveDistance(22, 2000, opModeIsActive());
            aprilTag = 1;
            turnNoPID(80,3);
            driveDistance(23, 2000, opModeIsActive());
            bread.drive.setPowers(0,0,0,0);
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            bread.arm.setLeftClamped();
        }
        else if (this.spikeMark == 3){
            aprilTag = 3;
            strafeDistance(-14,1750,opModeIsActive());
            driveDistance(48, 1500, opModeIsActive());
            bread.arm.setPickUpPos();
            sleep(1000);
            bread.arm.setLeftUnclamped();
            sleep(1000);
            bread.arm.setRestPos();
            sleep(1000);
            bread.arm.setLeftClamped();
            driveDistance(4,2000,opModeIsActive());
            turnNoPID(65,3);
            bread.drive.setPowers(0,0,0,0);
            driveDistance(100,2000,opModeIsActive());

        }

        bread.drive.setPowers(0,0,0,0);


        while (opModeIsActive()){

        }


    }

}
