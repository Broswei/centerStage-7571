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
        Mat leftCrop;
        Mat rightCrop;
        double leftavgfin;
        double rightavgfin;

        // NOTE: so yeah I think it s an issue with this constructor, it is somehwo getting like a 0x0 ma
        Mat output = new Mat();
        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        public Mat processFrame(Mat input){

            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);

            telemetry.addLine("let the detection games begin");

            Rect leftRect = new Rect (1, 1, 639, 719);
            Rect rightRect = new Rect (640, 1, 639, 719);

            input.copyTo(output);

            Imgproc.rectangle(output,leftRect,rectColor,2);
            Imgproc.rectangle(output,rightRect,rectColor,2);

            leftCrop = YCbCr.submat(leftRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);

            Scalar leftAvgScalar = Core.mean(leftCrop);
            Scalar rightAvgScalar = Core.mean(rightCrop);

            leftavgfin = leftAvgScalar.val[0];
            rightavgfin = rightAvgScalar.val[0];

            if(leftavgfin > rightavgfin) {
                telemetry.addLine("Obj on left");
            } else {
                telemetry.addLine("Obj on right");
            }


            return(output);
        }
    }
}