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


    //FOR AIDS ENCODER AUTONOMOUS ONLY
    double ticks;
    double ticksPerRotation = 384.5;
    boolean encodersUsed;
    SampleMecanumDrive dt = super.bread.drive.getRoadrunnerDrive();


    public void setup (boolean encodersUsed){

        initialize(hardwareMap);
        this.camera = createCamera("webcam");
        this.openCameraAndStream();

        if (encodersUsed){
            dt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else{
            dt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

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

    public void strafeDistance(double distanceIn, int velocity, boolean isRunning){
        ticks = (-distanceIn/(Math.PI*4)*ticksPerRotation*1.1);
        dt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt.setMotorPositions((int)ticks, (int)-ticks,(int)-ticks, (int)ticks);
        dt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dt.setMotorVelocities(velocity);

        ElapsedTime runtime = new ElapsedTime();
        boolean run = true;
        runtime.reset();
        while(dt.isBusy() && isRunning){

        }
    }

    public void driveDistance(double distanceIn, int velocity, boolean isRunning){
        ticks = (-distanceIn/(Math.PI*4)*ticksPerRotation);
        dt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt.setMotorPositions((int)ticks);
        dt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dt.setMotorVelocities(velocity);

        ElapsedTime runtime = new ElapsedTime();
        boolean run = true;
        runtime.reset();
        while(dt.isBusy() && isRunning){

        }
    }


}
