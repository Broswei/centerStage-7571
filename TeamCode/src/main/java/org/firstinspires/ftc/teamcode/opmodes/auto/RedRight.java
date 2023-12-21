package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

@Autonomous(group="park autos")
public class RedRight extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }

            if (!found){
                telemetry.addData("Spike Mark: ", showSpikeMark());
            }
            else{
                telemetry.addLine("Nothing is found :(");
            }
            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }
        closeCameraAsync();

        //hi - saeid

        driveDistance(-47, 750, opModeIsActive());
        this.bread.arm.setLeftUnclamped();
        sleep(500);
        strafeDistance(47, 750, opModeIsActive());
    }
}
