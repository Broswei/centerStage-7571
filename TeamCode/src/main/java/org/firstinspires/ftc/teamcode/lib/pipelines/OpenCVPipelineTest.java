package org.firstinspires.ftc.teamcode.lib.pipelines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;


import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class OpenCVPipelineTest extends OpMode {

    OpenCvWebcam webcam = null;

    @Override
    public void init() {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam"); //setup in like config

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName,cameraMonitorViewId);

        webcam.setPipeline(new examplePipeline()); // write later

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT); // do resolution
            }

            public void onError(int errorCode) {

            }


        });
    }


    @Override
    public void loop () {

    }

    class examplePipeline extends OpenCvPipeline{
        Mat YCbCr = new Mat();
        Mat crop1;
        Mat crop2;
        Mat crop3;
        double rect1AvgFin;
        double rect2AvgFin;
        double rect3AvgFin;

        // NOTE: so yeah I think it s an issue with this constructor, it is somehwo getting like a 0x0 ma
        Mat output = new Mat();
        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        public Mat processFrame(Mat input){

            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);

            telemetry.addLine("let the detection games begin");

            Rect rect1 = new Rect (1, 1, 426, 719);
            Rect rect2 = new Rect (427, 1, 426, 719);
            Rect rect3 = new Rect (855, 1, 426, 719);


            input.copyTo(output);

            Imgproc.rectangle(output,rect1,rectColor,2);
            Imgproc.rectangle(output,rect2,rectColor,2);
            Imgproc.rectangle(output,rect3,rectColor,2);

            crop1 = YCbCr.submat(rect1);
            crop2 = YCbCr.submat(rect2);
            crop3 = YCbCr.submat(rect3);

            Core.extractChannel(crop1, crop1, 2);
            Core.extractChannel(crop2, crop2, 2);
            Core.extractChannel(crop3, crop3, 2);


            Scalar crop1AvgScalar = Core.mean(crop1);
            Scalar crop2AvgScalar = Core.mean(crop2);
            Scalar crop3AvgScalar = Core.mean(crop3);


            rect1AvgFin = crop1AvgScalar.val[0];
            rect2AvgFin = crop2AvgScalar.val[0];
            rect3AvgFin = crop2AvgScalar.val[0];


            if(rect1AvgFin > rect2AvgFin && rect1AvgFin > rect3AvgFin) {
                telemetry.addLine("Obj on right");
            } else if(rect2AvgFin > rect1AvgFin && rect2AvgFin > rect3AvgFin) {
                telemetry.addLine("Obj on middle");
            } else {
                telemetry.addLine("Obj on left");
            }


            return(output);
        }
    }
}