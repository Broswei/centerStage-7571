package org.firstinspires.ftc.teamcode.lib.pipelines;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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



    Rect rectLeft = new Rect (1, 200, 426, 279); // right
    Rect rectMiddle = new Rect (457, 200, 366, 279); // middle
    Rect rectRight = new Rect (853, 200, 426, 279); // left

    public double scoreLeft;
    public double scoreMiddle;
    public double scoreRight;

    private int spikeMark = 0;

    public int getSpikeMark() {return spikeMark;}
    private boolean detectingBlue;

    public boolean isReady;

    public TSEDetectionProcessor(boolean detectingBlue) {
        this.detectingBlue = detectingBlue;
        isReady = true;
    }

    public TSEDetectionProcessor() {
        this.detectingBlue = false;
    }

    public void setDetectingBlue (boolean detectingBlue) {
        this.detectingBlue = detectingBlue;
        isReady = true;
    }
    @Override
    public void init(int width, int height, CameraCalibration cameraCalibration){}

    @Override
    public Object processFrame (Mat frame, long captureTimeNS) {
        if(!isReady) {return null;}

        Imgproc.cvtColor(frame, YCbCr, Imgproc.COLOR_RGB2YCrCb);

        Core.extractChannel(YCbCr,redMat, 2);
        Core.extractChannel(YCbCr,blueMat, 1);

        scoreLeft   = getTSEValue(blueMat,redMat,rectLeft);
        scoreMiddle = getTSEValue(blueMat,redMat,rectMiddle);
        scoreRight  = getTSEValue(blueMat,redMat,rectRight);

        if(scoreLeft > scoreMiddle && scoreLeft > scoreRight) {
            spikeMark = 1;
        }

        else if(scoreMiddle > scoreRight && scoreMiddle > scoreLeft) {
            spikeMark = 2;
        }

        else if (scoreRight > scoreLeft && scoreRight > scoreMiddle){ spikeMark = 3; }

        return null;
    }

    private double getTSEValue (Mat blueInput, Mat redInput, Rect area) {
        blueSubmat = blueInput.submat(area);
        redSubmat = redInput.submat(area);

        double value =  Core.mean(blueSubmat).val[0] - Core.mean(redSubmat).val[0];

        if(detectingBlue) value *= -1;

        return value;
    }



    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(scaleCanvasDensity * 4);

        canvas.drawRect(makeGraphicsRect(rectLeft, scaleBmpPxToCanvasPx), rectPaint);
        canvas.drawRect(makeGraphicsRect(rectMiddle, scaleBmpPxToCanvasPx), rectPaint);
        canvas.drawRect(makeGraphicsRect(rectRight, scaleBmpPxToCanvasPx), rectPaint);
    }

    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);

        return new android.graphics.Rect(left, top, right, bottom);
    }

}
