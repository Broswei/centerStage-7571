package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.AngleHelper;
import org.firstinspires.ftc.teamcode.lib.util.MathUtils;

public abstract class BreadTeleOp extends BreadOpMode{

    public static double MIN_POWER = 0.2;
    public static double MAX_POWER = 0.6;
    public static double ROTATION_POWER = 0.5;

    public double gyroOffset = 0.0;

    public enum ControlMode {
        GRABBING,
        DEPOSITING
    }

    public enum GrabMode {
        GROUND,
        STACK
    }

    private ControlMode currentControlMode = ControlMode.GRABBING;
    private GrabMode currentGrabMode = GrabMode.GROUND;
    private int alignStackHeight = 5;

    public void setup(){

        initialize(hardwareMap);

    }

    public void update(){
        double delta = getDelta();

        resetDelta();

        updateControllerStates();

        //potential stuff could use in future after RR

        //double speedTrigger = Math.max(gamepad1.left_trigger, gamepad1.right_trigger);
       // double rate = MathUtils.map(speedTrigger, 0.0, 1.0, MAX_POWER, MIN_POWER);
        //double forward = -gamepad1.left_stick_y;
        //double strafe = -gamepad1.left_stick_x;

        //Vector2d rotationVector = new Vector2d(-gamepad1.right_stick_y, -gamepad1.right_stick_x);
        //double rotate = this.getDesiredDriveRotation(rotationVector, rate);

        //drive states
        if(gamepad1.y){
            gyroOffset = super.bread.imu.getAngleRadians();
        }

        double forward = Range.clip(-gamepad1.left_stick_y, -0.8, 0.8);
        double strafe = Range.clip(gamepad1.left_stick_x, -1, 1);
        double rotate = Range.clip(gamepad1.right_stick_x, -0.5, 0.5);

        double temp = strafe*Math.cos(super.bread.imu.getAngleRadians()-gyroOffset)+forward*Math.sin(super.bread.imu.getAngleRadians()-gyroOffset);
        forward = -strafe*Math.sin(super.bread.imu.getAngleRadians()-gyroOffset)+forward*Math.cos(super.bread.imu.getAngleRadians()-gyroOffset);
        strafe = temp;

        //drive
        this.bread.drive.noRoadRunnerDriveFieldOriented(forward, strafe, rotate);
        //this.bread.drive.driveFieldOriented(forward, strafe, rotate);

        switch (this.currentControlMode){
            case GRABBING:
                //controller states
                boolean stackUp = gamepadEx2.dpad_up_pressed;
                boolean stackDown = gamepadEx1.dpad_down_pressed;
                boolean switchMode = gamepadEx2.a_pressed;
                boolean slurping = gamepad2.right_trigger > 0.01;
                boolean vomiting = gamepad2.left_trigger > 0.01;




                if (switchMode){
                    this.currentControlMode = ControlMode.DEPOSITING;
                }

            case DEPOSITING:
                //controller states

                boolean spit = gamepadEx2.y_pressed;

                if (spit){

                }
        }


    }











    public double getDesiredDriveRotation(Vector2d aimVector, double speed){
        if(aimVector.norm() < 0.25) return 0.0;

        // get rotational power
        double rotationSpeed = ROTATION_POWER * speed;

        double aimVectorRotation = aimVector.angle();
        double forwardVectorRotation = this.bread.drive.getPoseEstimate().getHeading();

        // get direction
        double distance = AngleHelper.angularDistanceRadians(forwardVectorRotation, aimVectorRotation);

        return distance * rotationSpeed;
    }

}
