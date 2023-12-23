package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

public class  AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }

            if (!found){
                telemetry.addData("Spike Mark: ", getSpikeMark());
            }
            else{
                telemetry.addLine("Nothing is found :(");
            }
            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }
    }
}
