package org.firstinspires.ftc.teamcode.lib.bread;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.lib.pipelines.OpenCVPipelineTest;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionPipeline;
import org.firstinspires.ftc.teamcode.lib.util.ImuPIDController;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BreadAutonomous extends BreadOpMode {

    private OpenCvCamera camera;

    public ElapsedTime timer = new ElapsedTime();
    public TSEDetectionPipeline pipeline;

    //FOR AIDS ENCODER AUTONOMOUS ONLY
    double ticks;

    double ticksPerRotation = 384.5;

    boolean arrived = false;

    int spikeMark = 0;

    public ImuPIDController pid = new ImuPIDController(BreadConstants.IMU_P, BreadConstants.IMU_I, BreadConstants.IMU_D);

    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private AprilTagDetection desiredTag = null;


    public void setup(boolean detectingBlue) {

        initialize(hardwareMap);
        camera = createCamera("webcam");
        pipeline = new TSEDetectionPipeline(detectingBlue);
        camera.setPipeline(pipeline);
        //initAprilTag();

        openCameraAndStreamAsync();
        //setManualExposure(6,250);

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

    public void closeCameraAsync() {
        camera.closeCameraDeviceAsync(new OpenCvCamera.AsyncCameraCloseListener() {
            @Override
            public void onClose() {
            }
        });
    }

    public void strafeDistance(double distanceIn, int velocity, boolean isRunning) {
        timer.reset();
        ticks = (-distanceIn / (Math.PI * (2* DriveConstants.WHEEL_RADIUS)) * ticksPerRotation*1.1);
        bread.drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bread.drive.setMotorPositions((int) ticks, (int) -ticks, (int) -ticks, (int) ticks);
        bread.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bread.drive.setMotorVelocities(velocity);

        if (Math.abs(distanceIn) <= 8){
            timer.reset();
            while (opModeIsActive() && bread.drive.isRegBusy() && timer.seconds() < 4) {
            }
        }
        else{
            while (opModeIsActive() && bread.drive.isRegBusy()) {
            }
        }
        bread.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void driveDistance(double distanceIn, int velocity, boolean isRunning) {
        ticks = (-distanceIn / (Math.PI * (2* DriveConstants.WHEEL_RADIUS)) * ticksPerRotation);
        bread.drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bread.drive.setMotorPositions((int) ticks);
        bread.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bread.drive.setMotorVelocities(velocity);

        if (Math.abs(distanceIn) <= 4){
            timer.reset();
            while (opModeIsActive() && bread.drive.isRegBusy() && timer.seconds() < 2) {
            }
        }
        else{
            while (opModeIsActive() && bread.drive.isRegBusy()) {
            }
        }

        bread.drive.setMotorVelocities(0);
        bread.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void turnNoPID(double degrees){
        bread.imu.resetAngle();
        double error = degrees;
        while (opModeIsActive() && Math.abs(error) > 2){
            double motorPower = (error < 0 ? -0.6 : 0.6);
            bread.drive.setPowers(-0.83*motorPower, -motorPower, motorPower, 0.83*motorPower);
            error = degrees - bread.imu.getAngle();
            telemetry.addData("error", error);
            telemetry.update();
        }
    }


    public void turnToPID(double degrees, double time) {

        timer.reset();
        telemetry.addLine("Starting pleuh");
        pid.setGains(BreadConstants.IMU_P, BreadConstants.IMU_I, BreadConstants.IMU_D);
        pid.resetIntegral();

        // double time =

        while (Math.abs(degrees - bread.imu.getAngleDegrees()) > 2 && opModeIsActive() && timer.seconds() < time) {

            if(Math.abs(degrees - bread.imu.getAngleDegrees()) < 8) {
                pid.setGains(BreadConstants.IMU_P * BreadConstants.IMU_P_REDUCTION, BreadConstants.IMU_I, BreadConstants.IMU_D * BreadConstants.IMU_D_REDUCTION);
            } else {
                pid.setGains(BreadConstants.IMU_P, 0, BreadConstants.IMU_D);
            }

            double power = pid.PIDControl(Math.toRadians(degrees), Math.toRadians(bread.imu.getAngleDegrees()));

            power = Range.clip(power,-1,1);

            bread.drive.setPowers(-0.83 * power, -power, power, 0.83 * power);




            telemetry.addData("Busy?: ", bread.drive.isRegBusy());
            telemetry.addData("degrees: ", degrees);
            telemetry.addData("current: ", bread.imu.getAngleDegrees());
            telemetry.addData("powers: ", power);
            telemetry.addData("timer", timer.seconds());
            telemetry.addData("I", pid.integralSum);

            telemetry.addLine("currently pleuhhing");
            telemetry.update();

        }

        bread.drive.setPowers(0,0,0,0);
    }

    public void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(2);
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .build();
    }

    public void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested()) {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }

    public void moveRobot(double x, double y, double yaw) {
        // Calculate wheel powers.
        double leftFrontPower = x - y - yaw;
        double rightFrontPower = x + y + yaw;
        double leftBackPower = x + y - yaw;
        double rightBackPower = x - y + yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the wheels.
        bread.drive.setPowers(0.83 * leftFrontPower, leftBackPower, rightBackPower, 0.83 * rightFrontPower);
    }

    public void checkAndMoveAprilTags(int desiredNumber) {
        while (!arrived) {
            boolean targetFound = false;
            while (!targetFound) {
                List<AprilTagDetection> currentDetections = aprilTag.getDetections();
                for (AprilTagDetection detection : currentDetections) {
                    // Look to see if we have size info on this tag.
                    if (detection.metadata != null) {
                        //  Check to see if we want to track towards this tag.
                        if (detection.id == desiredNumber) {
                            // Yes, we want to use this tag.
                            targetFound = true;
                            desiredTag = detection;
                            break;  // don't look any further.
                        }
                    }
                }
            }
            telemetry.addLine("going");

            arrived = (Math.abs(desiredTag.ftcPose.range - BreadConstants.DESIRED_DISTANCE) <= 0.5 && Math.abs(desiredTag.ftcPose.bearing) > 1 && Math.abs(desiredTag.ftcPose.yaw) <= 1);

            if (!arrived) {
                double rangeError = (desiredTag.ftcPose.range - BreadConstants.DESIRED_DISTANCE);
                double headingError = desiredTag.ftcPose.bearing;
                double yawError = desiredTag.ftcPose.yaw;

                // Use the speed and turn "gains" to calculate how we want the robot to move.
                double drive = -Range.clip(rangeError * BreadConstants.SPEED_GAIN, -1, 1);
                double turn = Range.clip(headingError * BreadConstants.TURN_GAIN, -1, 1);
                double strafe = -Range.clip(-yawError * BreadConstants.STRAFE_GAIN, -1, 1);

                moveRobot(drive, turn, strafe);
                sleep(10);
            }
            bread.drive.setPowers(0, 0, 0, 0);
        }
    }



    public void aimForYellow(){
        bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_LOW_DEPO_ANG);
        bread.arm.updateArm();
        bread.arm.setLowDepoPos();
    }
}
