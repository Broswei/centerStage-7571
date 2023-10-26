package org.firstinspires.ftc.teamcode.opmodes.teleop;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import org.firstinspires.ftc.vision.VisionPortal;



@TeleOp(group = "Tests")
public class NewAprilTagTest extends LinearOpMode {
    
    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
            .setDrawAxis(true)
            .setDrawcubeProjection(true)
            .setDrawTagID(true)
            .setDrawTagOutline(true)
            .build();
        
        VisionPortal visionPortal = new VisionPortal.Builder()
            .addProcessor(tagProcessor)
            .setCamera(hardwareMap.get(WebcamName.class, "webcam1")) // TODO: set webcam
            .setCameraResoloution(new Size(640, 480)) // TODO: experiment with sizes
            .build();

        waitForStart();

        while(!stopIsRequested() && opModeIsActive()) {

            if (tagProcessor.getDetections().size() > 0) {

                AprilTagDetection tag = tagProcessor.getDetections().get(0);

                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("z", tag.ftcPose.z);

                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("yaw", tag.ftcPose.yaw);

                telemetry.update();
            }

        }


        
            
    }

}
