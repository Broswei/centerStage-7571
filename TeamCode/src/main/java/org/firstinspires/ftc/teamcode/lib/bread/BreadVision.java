package org.firstinspires.ftc.teamcode.lib.bread;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


public class BreadVision {

    AprilTagProcessor aprilTagProcessor;
    TSEDetectionProcessor tseDetectionProcessor;
    VisionPortal innerVisionPortal;
    BreadVision (AprilTagProcessor aprilTagProcessor, TSEDetectionProcessor tseDetectionProcessor) {
        this.tseDetectionProcessor = tseDetectionProcessor;
        this.aprilTagProcessor = aprilTagProcessor;

        innerVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .addProcessor(tseDetectionProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();

        // TODO: do this later this is a faithful start
    }

    public void runAprilTag() {
        innerVisionPortal.setProcessorEnabled(tseDetectionProcessor,false);
        innerVisionPortal.setProcessorEnabled(aprilTagProcessor,true);
    }

    public void runTSEDetection() {
        innerVisionPortal.setProcessorEnabled(aprilTagProcessor,false);
        innerVisionPortal.setProcessorEnabled(tseDetectionProcessor,true);
    }

}
