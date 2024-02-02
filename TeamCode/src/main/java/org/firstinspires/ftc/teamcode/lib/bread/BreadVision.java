package org.firstinspires.ftc.teamcode.lib.bread;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;
import android.view.Display;

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
        NOT_STREAMING
    }

    public BreadVision (AprilTagProcessor aprilTagProcessor, TSEDetectionProcessor tseDetectionProcessor, WebcamName camera) {
        this.tseDetectionProcessor = tseDetectionProcessor;
        this.aprilTagProcessor = aprilTagProcessor;

        innerVisionPortal = new VisionPortal.Builder()
                .setCamera(camera)
                .addProcessor(aprilTagProcessor)
                .addProcessor(tseDetectionProcessor)
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();



        // TODO: do this later this is a faithful start
    }

    public void stopProcessors () {
        innerVisionPortal.setProcessorEnabled(tseDetectionProcessor, false);
        innerVisionPortal.setProcessorEnabled(aprilTagProcessor, false);

        mode = Modes.NONE;
    }

    public void stopStreaming() {
        stopProcessors();
        innerVisionPortal.stopStreaming();

        mode = Modes.NOT_STREAMING;
    }

    public void startStreaming(Modes startMode) {
        if(mode != Modes.NOT_STREAMING ) {
            throw new RuntimeException("called startStreaming while BreadVision instance was already streaming \n\n pure idiocy, check up on ur codebase");
        }

        if(startMode == Modes.NOT_STREAMING) {
            throw new RuntimeException("Started streaming with invalid mode NOT_STREAMING");
        }

        if(startMode == Modes.APRIL_TAG) {detectAprilTags();}
        else if(startMode == Modes.TSE) {detectTSE();}
        else stopProcessors();

        innerVisionPortal.resumeStreaming();
    }

    public void startStreaming() {
        if(mode != Modes.NOT_STREAMING) {
            throw new RuntimeException("called startStreaming while BreadVision instance was already streaming \n\n pure idiocy, check up on ur codebase");
        }

        stopProcessors();
        innerVisionPortal.resumeStreaming();
    }

    public void setDetectingBlueTSE(boolean detectingBlue) {
        tseDetectionProcessor.setDetectingBlue(detectingBlue);
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
