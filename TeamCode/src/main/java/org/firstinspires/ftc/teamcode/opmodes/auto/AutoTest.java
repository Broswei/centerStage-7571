package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;

@Autonomous
public class AutoTest extends BreadAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {

        setup(false);

        while (!isStarted()){

            telemetry.addData("Status: ", "Initialized");
            telemetry.update();

        }

        closeCameraAsync();

        turnToPID(90);
        sleep(1000);
        turnToPID(180);

        while (opModeIsActive()){

        }


    }

}
