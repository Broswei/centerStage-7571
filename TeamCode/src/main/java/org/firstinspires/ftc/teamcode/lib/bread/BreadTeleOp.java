package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.AngleHelper;
import org.firstinspires.ftc.teamcode.lib.util.MathUtils;

public abstract class BreadTeleOp extends BreadOpMode {

    public static double MIN_POWER = 0.2;
    public static double MAX_POWER = 0.6;
    public static double ROTATION_POWER = 0.5;

    public double gyroOffset = 0.0;

    public enum ControlMode {
        GRABBING,
        DEPOSITING
    }

    public enum DepoMode {
        GROUND,
        BACKDROP
    }

    private ControlMode currentControlMode = ControlMode.GRABBING;
    private DepoMode currentDepoMode = DepoMode.GROUND;
    private int alignStackHeight = 1;
    private int scoreLevel = 1;
    private boolean aiming = false;
    private int pixelRow = 1;
    boolean climbed = false;
    boolean needToGoDown = false;

    public void setup() {

        initialize(hardwareMap);

    }

    public void update() {
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
        if (gamepad1.y) {
            gyroOffset = super.bread.imu.getAngleRadians();
        }

        double forward = Range.clip(-gamepad1.left_stick_y, -0.8, 0.8);
        double strafe = Range.clip(gamepad1.left_stick_x, -0.8, 0.8);
        double rotate = Range.clip(gamepad1.right_stick_x, -0.5, 0.5);

        double temp = strafe * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset);
        forward = -strafe * Math.sin(super.bread.imu.getAngleRadians() - gyroOffset) + forward * Math.cos(super.bread.imu.getAngleRadians() - gyroOffset);
        strafe = temp;

        //drive
        if (gamepad1.a) {
            this.bread.drive.noRoadRunnerDriveFieldOriented(1.25 * forward, 1.25 * strafe, 1.25 * rotate);
        } else if (gamepad1.right_trigger > 0.01) {
            this.bread.drive.noRoadRunnerDriveFieldOriented(0.8 * forward, 0.8 * strafe, 0.8 * rotate);
        } else {
            this.bread.drive.noRoadRunnerDriveFieldOriented(forward, strafe, rotate);
        }
        //this.bread.drive.driveFieldOriented(forward, strafe, rotate);


        switch (this.currentControlMode) {
            case GRABBING:
                //controller states

                boolean climb = gamepadEx2.y_pressed;
                boolean pullUp = gamepadEx2.y_pressed & climbed;
                boolean switchTowers = gamepadEx2.b_pressed;
                boolean launching = gamepad2.left_trigger > 0.05;
                boolean aimPlane = gamepadEx2.x_pressed;
                boolean openWideMF = gamepad2.right_trigger > 0.05;
                boolean switchMode = gamepadEx2.a_pressed;


                if (openWideMF) {
                    this.bread.hand.unclamp();
                    //this.bread.wristServo.setPosition(0);
                } else {
                    this.bread.hand.clamp();
                   // this.bread.wristServo.setPosition(0.05);
                }

                if (climb) {
                    this.bread.rotator.rotateToDegrees(180, 750);
                    this.bread.towers.rotate(this.bread.towers.ticksToRotations(BreadConstants.TOWERS_MAX_EXTENSION_TICKS), 2000 / BreadConstants.TOWERS_TPR);
                    if (!this.bread.towers.isBusy()) {
                        climbed = true;
                        needToGoDown  = true;
                    }
                }
                if (pullUp) {
                    this.bread.towers.rotate(-this.bread.towers.ticksToRotations(BreadConstants.TOWERS_MAX_EXTENSION_TICKS), 2000 / BreadConstants.TOWERS_TPR);
                    while(!this.bread.towers.isBusy()){
                        needToGoDown = false;
                        climbed = false;
                    }
                }

                if (switchTowers) {
                    if (needToGoDown) {
                        bread.towers.setTargetPos(10280,2000);
                        needToGoDown = false;
                    } else {
                        bread.towers.setTargetPos(0,2000);
                        needToGoDown = true;
                    }
                }

                if (launching) {
                    this.bread.launcher.setPosition(0.5);
                } else {
                    this.bread.launcher.setPosition(0);
                }
                if (aimPlane) {
                    if (!aiming){
                        this.bread.angleAdjuster.setPosition(0.15);
                        aiming = true;
                    }
                    else{
                        this.bread.angleAdjuster.setPosition(0.08);
                    }
                }

                if (switchMode) {
                    this.currentControlMode = ControlMode.DEPOSITING;
                }

                break;

            case DEPOSITING:
                //controller states
                boolean spit = gamepad2.y;
                boolean switchModeDepo = gamepadEx2.a_pressed;
                //boolean switchDropLocation = gamepadEx2.b_pressed;
                //boolean upPixelRow = gamepadEx2.dpad_up_pressed;


                switch (this.currentDepoMode) {

                    case GROUND:
                        this.bread.rotator.rotateToDegrees(0, 750);
                        //this.bread.wristServo.setPosition(0);

                        //if (switchDropLocation){
                            //this.currentDepoMode = DepoMode.BACKDROP;
                        //}

                        break;


                    case BACKDROP:
                        //this.bread.wristServo.setPosition(0.6);
                        if (pixelRow == 1) {
                            this.bread.rotator.rotateToDegrees(240, 750);
                        }
                        else {
                            this.bread.rotator.rotateToDegrees(225, 750);
                        }

                        /* if (upPixelRow) {
                            pixelRow++;

                            pixelRow = Math.min(2, pixelRow);
                        }
                        if (downPixelRow) {
                            pixelRow--;

                            pixelRow = Math.max(1, pixelRow);
                        }

                        if (switchDropLocation) {
                            this.currentDepoMode = DepoMode.GROUND;
                        }*/

                    break;
                    }

                    if(switchModeDepo) {
                        this.currentControlMode = ControlMode.GRABBING;
                    }

                    break;

            }

        }



    public void goStackBrr(int alignStackHeight){
        if (alignStackHeight == 2){
            this.bread.rotator.rotateToDegrees(8,750);
        }
        else if (alignStackHeight == 3){
            this.bread.rotator.rotateToDegrees(16,750);
        }
        else if (alignStackHeight == 4){
            this.bread.rotator.rotateToDegrees(24,750);
        }
        else if (alignStackHeight == 5){
            this.bread.rotator.rotateToDegrees(32,750);
        }
        else{
            this.bread.rotator.rotateToDegrees(0,750);
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
