package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.bread.BreadTeleOp;
import org.firstinspires.ftc.teamcode.lib.bread.BreadVision;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@TeleOp
public class VisionPortalTest extends BreadTeleOp {

    @Override
    public void runOpMode() {

        setup();
        this.bread.arm.setRestPos();
        this.bread.arm.setRotatorAngleDegrees(0);
        this.bread.launcher.putDown();
        this.bread.arm.setHandClamped();

        this.bread.vision.setDetectingBlueTSE(false);

        // TODO: make this a breadbot thing later
        bread.vision.detectAprilTags();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        bread.vision.setExposureMode(ExposureControl.Mode.Manual);
        bread.vision.setExposure(6, TimeUnit.MILLISECONDS);
        bread.vision.setGain(250);

        while(!isStopRequested() && opModeIsActive()) {
            if (gamepad1.y) {
                gyroOffset = super.bread.imu.getAngleRadians();
            }

            if (gamepad1.right_bumper) {
                bread.vision.detectAprilTags();
            }

            if (gamepad1.left_bumper) {
                bread.vision.detectTSE();
            }

            if(bread.vision.mode == BreadVision.Modes.TSE) {
                int spikeMark = bread.vision.getSpikeMark();

                telemetry.addData("spike mark:", spikeMark);
            }

            if(bread.vision.mode == BreadVision.Modes.APRIL_TAG) {
                int detections = bread.vision.getDetections().size();

                if(gamepad1.y) {
                    moveTowardAprilTag(BreadConstants.DESIRED_DISTANCE);
                }

                telemetry.addData("detections:", detections);


            }


            if(!gamepad1.y) {
                double forward = Range.clip(-gamepad1.left_stick_y, -1, 1);
                double strafe = Range.clip(gamepad1.left_stick_x, -1, 1);
                double rotate = Range.clip(gamepad1.right_stick_x, -1, 1);

                double temp = strafe * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset);
                forward = -strafe * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset);
                strafe = temp;

                this.bread.drive.noRoadRunnerDriveFieldOriented(forward, strafe, rotate);
            }

            telemetry.update();
        }

    }

}
