package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;

public class  AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(true);

        this.bread.arm.setHandUnclamped();
        boolean found = false;

        while (!isStarted()){
            if (gamepad1.a || gamepad2.a){
                this.bread.arm.setHandClamped();
            }
            //insert camera recongition

            telemetry.addData("Status: ", "Initialized");

        }

        //Leave this body empty after testing

    }
}
