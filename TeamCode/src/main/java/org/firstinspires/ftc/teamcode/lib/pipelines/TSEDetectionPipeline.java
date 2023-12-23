package org.firstinspires.ftc.teamcode.lib.pipelines;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TSEDetectionPipeline extends OpenCvPipeline{
        Mat YCbCr = new Mat();
        Mat crop1Red;
        Mat crop2Red;
        Mat crop3Red;

        Mat crop1Blue;
        Mat crop2Blue;
        Mat crop3Blue;
        double rect1AvgFin;
        double rect2AvgFin;
        double rect3AvgFin;

        Rect rect1 = new Rect (1, 1, 426, 719); // right
        Rect rect2 = new Rect (427, 1, 426, 719); // middle
        Rect rect3 = new Rect (853, 1, 426, 719); // left

        int spikeMark = 0;

        Boolean detectingBlue = false;
        public TSEDetectionPipeline (Boolean detectingBlue) {
            this.detectingBlue = detectingBlue;
        }

        Mat output = new Mat();
        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        public void setSpikeMark(int x){spikeMark = x;}

        public int getSpikeMark(){
            return spikeMark;
        }

        public Mat processFrame(Mat input) {

            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);


            input.copyTo(output);

            Imgproc.rectangle(output, rect1, rectColor, 2);
            Imgproc.rectangle(output, rect2, rectColor, 2);
            Imgproc.rectangle(output, rect3, rectColor, 2);

            crop1Red = YCbCr.submat(rect1);
            crop2Red = YCbCr.submat(rect2);
            crop3Red = YCbCr.submat(rect3);

            crop1Blue = YCbCr.submat(rect1);
            crop2Blue = YCbCr.submat(rect2);
            crop3Blue = YCbCr.submat(rect3);

            Core.extractChannel(crop1Red, crop1Red, 2);
            Core.extractChannel(crop2Red, crop2Red, 2);
            Core.extractChannel(crop3Red, crop3Red, 2);

            Core.extractChannel(crop1Blue, crop1Blue, 1);
            Core.extractChannel(crop2Blue, crop2Blue, 1);
            Core.extractChannel(crop3Blue, crop3Blue, 1);


            Scalar crop1AvgScalarRed = Core.mean(crop1Red);
            Scalar crop2AvgScalarRed = Core.mean(crop2Red);
            Scalar crop3AvgScalarRed = Core.mean(crop3Red);

            Scalar crop1AvgScalarBlue = Core.mean(crop1Blue);
            Scalar crop2AvgScalarBlue = Core.mean(crop2Blue);
            Scalar crop3AvgScalarBlue = Core.mean(crop3Blue);


            rect1AvgFin = crop1AvgScalarBlue.val[0] - crop1AvgScalarRed.val[0];
            rect2AvgFin = crop2AvgScalarBlue.val[0] - crop2AvgScalarRed.val[0];
            rect3AvgFin = crop3AvgScalarBlue.val[0] - crop3AvgScalarRed.val[0];

            if (detectingBlue) {
                rect1AvgFin *= -1;
                rect2AvgFin *= -1;
                rect3AvgFin *= -1;
            }

            if (Math.max(rect1AvgFin, Math.max(rect2AvgFin, rect3AvgFin)) == rect1AvgFin) {
                setSpikeMark(1);
            } else if (Math.max(rect1AvgFin, Math.max(rect2AvgFin, rect3AvgFin)) == rect2AvgFin) {
                setSpikeMark(2);
            } else {
                setSpikeMark(3);
            }

            return (output);
        }


    }
