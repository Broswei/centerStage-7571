package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;
import java.util.List;

public abstract class BreadOpMode extends LinearOpMode {


    // global timer
    private ElapsedTime globalTimer;

    // delta
    private double delta;
    private double lastTime; // time between updateDelta calls

    // gamepads
    public GamepadEx gamepadEx1;
    public GamepadEx gamepadEx2;

    // bot config
    public BreadBot bread;


    /**
     * @return the amount of time passed since the last resetDelta call
     */
    /**
     * @return the time in seconds since initialize() has last been called
     */
    public double getGlobalTimeSeconds(){
        return globalTimer.seconds();
    }

    /**
     * @return the amount of time passed since the last resetDelta call
     */
    public double getDelta(){
        double time = this.getGlobalTimeSeconds();

        return time-lastTime;
    }

    /**
     * @brief Reset the delta timer to 0 seconds
     */
    public void resetDelta(){
        double time = this.getGlobalTimeSeconds();

        lastTime = time;
    }

    public void initialize(HardwareMap hardwareMap){ //initialize everything
        // construct abe
        this.bread = new BreadBot(hardwareMap);

        // construct global timer
        this.globalTimer = new ElapsedTime();

        this.gamepadEx1 = new GamepadEx(gamepad1);
        this.gamepadEx2 = new GamepadEx(gamepad2);
    }

    public void updateControllerStates(){
        gamepadEx1.updateControllerStates();
        gamepadEx2.updateControllerStates();
    }

    public double moveTowardAprilTag(int tagId, double distance) {
        ArrayList<AprilTagDetection> detections = bread.vision.getDetections();

        if(detections.size() == 0) {
            bread.drive.noRoadRunnerDriveFieldOriented(0,0,0);
            return 0;
        }

        AprilTagDetection desiredTag = null;

        for (AprilTagDetection detection:detections) {
            if (detection.id != tagId) { continue; }

            desiredTag = detection;

            break;
        }

        if (desiredTag != null) {
            bread.drive.noRoadRunnerDriveFieldOriented(0,0,0);

            return 0;
        }

        double  rangeError      = (desiredTag.ftcPose.range - distance);
        double  headingError    = desiredTag.ftcPose.bearing;
        double  yawError        = desiredTag.ftcPose.yaw;

        // Use the speed and turn "gains" to calculate how we want the robot to move.
        double drive  = Range.clip(rangeError * BreadConstants.SPEED_GAIN, -1, 1);
        double turn   = Range.clip(headingError * BreadConstants.TURN_GAIN, -1, 1);
        double strafe = Range.clip(yawError * BreadConstants.STRAFE_GAIN, -1, 1);

        // when in doubt, slap a sqrt ccurve on it
        drive = Math.signum(drive) * Math.sqrt(Math.abs(drive));
        turn = Math.signum(turn) * Math.sqrt(Math.abs(turn));
        strafe = Math.signum(strafe) * Math.sqrt(Math.abs(strafe));

        bread.drive.noRoadRunnerDriveFieldOriented(drive,strafe,0);

        telemetry.addData("fwd:", drive);
        telemetry.addData("rot:", turn);
        telemetry.addData("strafe:", strafe);

        return (rangeError + yawError + headingError) * 0.333333;

    }

    public double moveTowardAprilTag (double distance) {
        ArrayList<AprilTagDetection> detections = bread.vision.getDetections();

        if(detections.size() == 0) {
            bread.drive.noRoadRunnerDriveFieldOriented(0,0,0);
            return 0;
        }

        AprilTagDetection desiredTag = detections.get(0);

        double  rangeError      = (desiredTag.ftcPose.range - distance);
        double  headingError    = desiredTag.ftcPose.bearing;
        double  yawError        = desiredTag.ftcPose.yaw;

        // Use the speed and turn "gains" to calculate how we want the robot to move.
        double drive  = -Range.clip(rangeError * BreadConstants.SPEED_GAIN, -1, 1);
        double turn   = Range.clip(headingError * BreadConstants.TURN_GAIN, -1, 1);
        double strafe = -Range.clip(-yawError * BreadConstants.STRAFE_GAIN, -1, 1);

        // when in doubt, slap a sqrt curve on it
        drive = Math.signum(drive) * Math.sqrt(Math.abs(drive));
        turn = Math.signum(turn) * Math.sqrt(Math.abs(turn));
        strafe = Math.signum(strafe) * Math.sqrt(Math.abs(strafe));


        bread.drive.noRoadRunnerDriveFieldOriented(drive,strafe,0);

        telemetry.addData("fwd:", drive);
        telemetry.addData("rot:", turn);
        telemetry.addData("strafe:", strafe);

        return (rangeError + yawError + headingError) * 0.333333;
    }
}
