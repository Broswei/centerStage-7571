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


    public void setup(/*boolean encodersUsed, */boolean detectingBlue) {

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

        ElapsedTime runtime = new ElapsedTime();
        boolean run = true;
        runtime.reset();
        while (opModeIsActive() && bread.drive.isRegBusy()){
        }

    }

    public void driveDistance(double distanceIn, int velocity, boolean isRunning) {
        ticks = (-distanceIn / (Math.PI * 4) * ticksPerRotation);
        bread.drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bread.drive.setMotorPositions((int) ticks);
        bread.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bread.drive.setMotorVelocities(velocity);

        ElapsedTime runtime = new ElapsedTime();
        boolean run = true;
        runtime.reset();
        while (opModeIsActive() && bread.drive.isRegBusy()){
        }

    }

    public void turn(double degrees){

        bread.imu.resetAngle();

        double error = degrees;

        while(opModeIsActive() && Math.abs(error) > 2){
            double motorPower = (error < 0 ? -0.6 : 0.6);
            bread.drive.setPowers(-0.83*motorPower, -motorPower, motorPower, 0.83*motorPower);
            error = degrees - bread.imu.getAngleDegrees();
            telemetry.addData("error: ", error);
            telemetry.update();
        }

        bread.drive.setPowers(0,0,0,0);

    }

    public void turnTo(double degrees){

        Orientation orientation = bread.imu.getOrientation();

        double error = degrees - orientation.firstAngle;

        if (error > 180){
            error -= 360;
        }
        else if (error < -180){
            error += 360;
        }

        turn(error);
    }

    public void turnToPID (double targetAngle){
        ImuPIDController pid = new ImuPIDController(targetAngle, 0, 0, 0);
        while (opModeIsActive() && Math.abs(targetAngle - bread.imu.getAbsoluteAngleDegrees()) > 1){
            double motorPower = pid.update(bread.imu.getAbsoluteAngleDegrees());
            bread.drive.setPowers(-0.83*motorPower, -motorPower, motorPower, 0.83*motorPower);
        }
        bread.drive.setPowers(0,0,0,0);
    }

    public void turnPID(double degrees){
        turnToPID(degrees + bread.imu.getAbsoluteAngleDegrees());
    }

}
