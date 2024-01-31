package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.lib.bread.BreadTeleOp;
import org.firstinspires.ftc.teamcode.lib.bread.BreadVision;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.ArrayList;

@TeleOp
public class VisionPortalTest extends BreadTeleOp {

    @Override
    public void runOpMode() {

        setup();
        this.bread.arm.setRestPos();
        this.bread.arm.setRotatorAngleDegrees(0);
        this.bread.launcher.putDown();
        this.bread.arm.setHandClamped();

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder().build();

        TSEDetectionProcessor tseProcessor = new TSEDetectionProcessor(false);

        BreadVision vision = new BreadVision(tagProcessor,tseProcessor, bread.webcamName);

        // TODO: make this a breadbot thing later
        vision.detectTSE();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            if (gamepad1.y) {
                gyroOffset = super.bread.imu.getAngleRadians();
            }

            if (gamepad1.right_bumper) {
                vision.detectAprilTags();
            }

            if (gamepad1.left_bumper) {
                vision.detectTSE();
            }

            if(vision.mode == BreadVision.Modes.TSE) {
                int spikeMark = vision.getSpikeMark();

                telemetry.addData("spike mark:", spikeMark);
            }

            if(vision.mode == BreadVision.Modes.APRIL_TAG) {
                int detections = vision.getDetections().size();

                telemetry.addData("detections:", detections);
            }



            double forward = Range.clip(-gamepad1.left_stick_y, -1, 1);
            double strafe = Range.clip(gamepad1.left_stick_x, -1, 1);
            double rotate = Range.clip(gamepad1.right_stick_x, -1, 1);

            double temp = strafe * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset);
            forward = -strafe * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset);
            strafe = temp;

            this.bread.drive.noRoadRunnerDriveFieldOriented(forward,strafe,rotate);


            telemetry.update();
        }

    }

}
