package org.firstinspires.ftc.teamcode.lib.bread;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class BreadAutonomous extends BreadOpMode{

    OpenCvCamera camera;

    public void setup (){

        initialize(hardwareMap);
        //this.camera = createCamera("webcam");
        //this.openCameraAndStream();

    }

    public void cycle(){
        //to be done in the future!
    }


    public OpenCvCamera createCamera(String deviceName){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        return OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, deviceName), cameraMonitorViewId);
    }

    /**
     * @brief opens the current camera and streams it, asynchronously
     */
    public void openCameraAndStreamAsync(){
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
    }

    public void openCameraAndStream(){
        camera.openCameraDevice();

        camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
    }


}
