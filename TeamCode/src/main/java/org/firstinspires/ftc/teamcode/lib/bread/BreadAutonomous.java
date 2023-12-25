package org.firstinspires.ftc.teamcode.lib.bread;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.lib.pipelines.OpenCVPipelineTest;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class BreadAutonomous extends BreadOpMode {

    private OpenCvCamera camera;

    public TSEDetectionPipeline pipeline;

    //FOR AIDS ENCODER AUTONOMOUS ONLY
    double ticks;

    double ticksPerRotation = 384.5;

    int spikeMark = 0;

    public ImuPIDController pid = new ImuPIDController(BreadConstants.IMU_P, BreadConstants.IMU_I, BreadConstants.IMU_D);


    public void setup(boolean detectingBlue) {

        initialize(hardwareMap);
        camera = createCamera("webcam");
        pipeline = new TSEDetectionPipeline(detectingBlue);
        camera.setPipeline(pipeline);
        openCameraAndStreamAsync();


        this.bread.launcher.putDown();
        this.bread.arm.setRotatorAngleDegrees(0);
        this.bread.arm.setRestPos();

        this.bread.arm.updateArm();
    }

    public void cycle() {
        //to be done in the future!
    }


    public OpenCvCamera createCamera(String deviceName) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        return OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, deviceName), cameraMonitorViewId);
    }

    /**
     * @brief opens the current camera and streams it, asynchronously
     */
    public void openCameraAndStreamAsync() {
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public void closeCameraAsync(){
        camera.closeCameraDeviceAsync(new OpenCvCamera.AsyncCameraCloseListener() {
            @Override
            public void onClose() {
            }
        });
    }

    public void openCameraAndStream() {
        camera.openCameraDevice();

        camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
    }

    public void strafeDistance(double distanceIn, int velocity, boolean isRunning) {
        ticks = (-distanceIn / (Math.PI * 4) * ticksPerRotation * 1.1);
        bread.drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bread.drive.setMotorPositions((int) ticks, (int) -ticks, (int) -ticks, (int) ticks);
        bread.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bread.drive.setMotorVelocities(velocity);

        while (opModeIsActive() && bread.drive.isRegBusy()){
        }

    }

    public void driveDistance(double distanceIn, int velocity, boolean isRunning) {
        ticks = (-distanceIn / (Math.PI * 4) * ticksPerRotation);
        bread.drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bread.drive.setMotorPositions((int) ticks);
        bread.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bread.drive.setMotorVelocities(velocity);

        while (opModeIsActive() && bread.drive.isRegBusy()){
        }

    }

    public void turnToPID(double degrees){

        while (Math.abs(degrees - bread.imu.getAngleDegrees()) > 1.5){
            double power = pid.PIDControl(Math.toRadians(degrees), Math.toRadians(bread.imu.getAngleDegrees()));
            bread.drive.setPowers(-0.83*power, -power, power, 0.83*power);
            telemetry.addData("Busy?: ", bread.drive.isRegBusy());
            telemetry.addData("degrees: ", degrees);
            telemetry.addData("current: ", bread.imu.getAngleDegrees());
            telemetry.update();

        }
    }

}
