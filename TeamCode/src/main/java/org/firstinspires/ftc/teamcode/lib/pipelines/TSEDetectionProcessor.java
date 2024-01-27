package org.firstinspires.ftc.teamcode.lib.pipelines;

import android.graphics.Canvas;
import android.telecom.VideoProfile;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TSEDetectionProcessor implements VisionProcessor {
    Mat YCbCr = new Mat();

    Mat blueMat = new Mat();
    Mat redMat = new Mat();
    Mat blueSubmat = new Mat();
    Mat redSubmat = new Mat();



    Rect rectLeft = new Rect (1, 200, 426, 479); // right
    Rect rectMiddle = new Rect (457, 200, 366, 479); // middle
    Rect rectRight = new Rect (853, 200, 426, 479); // left

    private int spikeMark = 0;

    int getSpikeMark() {return spikeMark;}
    boolean detectingBlue;

    TSEDetectionProcessor(boolean detectingBlue) {
        this.detectingBlue = detectingBlue;
    }

    TSEDetectionProcessor() {
        this.detectingBlue = false;
    }
    @Override
    public void init(int width, int height, CameraCalibration cameraCalibration){}

    @Override
    public Object processFrame (Mat frame, long captureTimeNS) {
        Imgproc.cvtColor(frame, YCbCr, Imgproc.COLOR_RGB2YCrCb);

        Core.extractChannel(YCbCr,redMat, 2);
        Core.extractChannel(YCbCr,blueMat, 1);

        double scoreLeft   = getTSEValue(blueMat,redMat,rectLeft);
        double scoreMiddle = getTSEValue(blueMat,redMat,rectMiddle);
        double scoreRight  = getTSEValue(blueMat,redMat,rectRight);

        if(scoreLeft > scoreMiddle && scoreLeft > scoreRight) {
            spikeMark = 1;
            return null;
        }

        if(scoreMiddle > scoreRight) {
            spikeMark = 2;
            return null;
        }

        spikeMark = 3;

        return null;
    }

    private double getTSEValue (Mat blueInput, Mat redInput, Rect area) {
        blueSubmat = blueInput.submat(area);
        redSubmat = redInput.submat(area);

        double value = Core.mean(redSubmat).val[0] - Core.mean(blueSubmat).val[0];

        if(detectingBlue) value *= -1;

        return value;
    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {}
}
