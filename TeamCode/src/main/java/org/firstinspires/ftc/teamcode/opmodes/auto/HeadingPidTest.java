package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
@Autonomous
public class HeadingPidTest extends BreadAutonomous {

    int spikeMark ;
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




        while(!isStopRequested() && opModeIsActive()) {
            turnToPID(90,10);
            sleep(500);
            turnToPID(0,10);
            sleep(500);

            telemetry.addData("Error: ", pid.error);

            telemetry.update();
        }



    }

}
