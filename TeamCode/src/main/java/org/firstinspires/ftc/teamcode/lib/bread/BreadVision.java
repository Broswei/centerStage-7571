package org.firstinspires.ftc.teamcode.lib.bread;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;


public class BreadVision {

    AprilTagProcessor aprilTagProcessor;
    TSEDetectionProcessor tseDetectionProcessor;
    VisionPortal innerVisionPortal;

    public Modes mode = Modes.NONE;

    public enum Modes {
        APRIL_TAG,
        TSE,
        NONE,
    }

    public BreadVision (AprilTagProcessor aprilTagProcessor, TSEDetectionProcessor tseDetectionProcessor, Camera camera) {
        this.tseDetectionProcessor = tseDetectionProcessor;
        this.aprilTagProcessor = aprilTagProcessor;

        innerVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTagProcessor)
                .addProcessor(tseDetectionProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();



        // TODO: do this later this is a faithful start
    }

    public void detectAprilTags() {
        innerVisionPortal.setProcessorEnabled(tseDetectionProcessor,false);
        innerVisionPortal.setProcessorEnabled(aprilTagProcessor,true);

        mode = Modes.APRIL_TAG;
    }

    public void detectTSE() {
        innerVisionPortal.setProcessorEnabled(aprilTagProcessor,false);
        innerVisionPortal.setProcessorEnabled(tseDetectionProcessor,true);

        mode = Modes.TSE;
    }

    public ArrayList<AprilTagDetection> getDetections () {
        return aprilTagProcessor.getDetections();
    }

    public int getSpikeMark () {
        return tseDetectionProcessor.getSpikeMark();
    }

}
