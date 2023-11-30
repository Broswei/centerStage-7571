package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.util.AngleHelper;
import org.firstinspires.ftc.teamcode.lib.util.MathUtils;

public abstract class BreadTeleOp extends BreadOpMode {

    //public static double MIN_POWER = 0.2;
   // public static double MAX_POWER = 0.6;
    //public static double ROTATION_POWER = 0.5;

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
    private DepoMode currentDepoMode = DepoMode.BACKDROP;
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
        double strafe = Range.clip(gamepad1.left_stick_x, -0.9, 0.9);
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

        //arm details

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

                this.bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_DEFAULT_DEGREES);
                this.bread.rails.setTargetPos(0, BreadConstants.TOWERS_NORM_VELOCITY);

                if (openWideMF) {
                    this.bread.arm.setHandUnclamped();
                    this.bread.arm.setPickUpPos();

                } else {
                    this.bread.arm.setHandClamped();
                    this.bread.arm.setRestPos();
                }

                if (climb) {
                    this.bread.rails.rotateTo(BreadConstants.TOWERS_MAX_ROTATIONS,BreadConstants.TOWERS_NORM_VELOCITY);
                    if (!this.bread.rails.areTowersBusy()) {
                        climbed = true;
                        needToGoDown  = true;
                    }
                }
                if (pullUp) {
                    this.bread.rails.rotateTo(0, BreadConstants.TOWERS_NORM_VELOCITY);
                    while(!this.bread.rails.isBusy()){
                        needToGoDown = false;
                        climbed = false;
                    }
                }

                if (switchTowers) {
                    if (needToGoDown) {
                        this.bread.rails.rotateTo(BreadConstants.TOWERS_MAX_ROTATIONS,BreadConstants.TOWERS_NORM_VELOCITY);
                        needToGoDown = false;
                    } else {
                        this.bread.rails.rotateTo(0,BreadConstants.TOWERS_NORM_VELOCITY);
                        needToGoDown = true;
                    }
                }

                if (launching) {
                    this.bread.launcher.launch();
                } else {
                    this.bread.launcher.release();
                }
                if (aimPlane) {
                    if (!aiming){
                        this.bread.launcher.readyAim();
                        aiming = true;
                    }
                    else{
                        this.bread.launcher.putDown();
                        aiming = false;
                    }
                }

                if (switchMode) {
                    this.currentControlMode = ControlMode.DEPOSITING;
                }


                this.bread.arm.updateArm();

                break;

            case DEPOSITING:
                //controller states
                boolean spit = gamepad2.y;
                boolean switchModeDepo = gamepadEx2.a_pressed;
                boolean switchDropLocation = gamepadEx2.b_pressed;
                boolean upPixelSet = gamepadEx2.dpad_up_pressed;
                boolean downPixelSet = gamepadEx2.dpad_down_pressed;


                switch (this.currentDepoMode) {

                    case GROUND:
                        this.bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_DEFAULT_DEGREES);

                        if (spit) {
                            this.bread.arm.setHandUnclamped();
                            this.bread.arm.setPickUpPos();
                        } else {
                            this.bread.arm.setHandClamped();
                            this.bread.arm.setRestPos();
                        }

                        if (switchDropLocation) {
                            this.currentDepoMode = DepoMode.BACKDROP;
                        }

                        break;


                    case BACKDROP:

                        this.bread.arm.setRotatorAngleDegrees(BreadConstants.ROT_NORM_DEPO_ANG);
                        this.bread.arm.setNormalDepoPos();
                        this.bread.rails.presetRaiseTowersUp(pixelRow);

                        if (spit) {
                            this.bread.arm.setHandUnclamped();
                        } else {
                            this.bread.arm.setHandClamped();
                        }

                        if (upPixelSet) {
                            pixelRow++;

                            pixelRow = Math.min(3, pixelRow);
                        }
                        if (downPixelSet) {
                            pixelRow--;

                            pixelRow = Math.max(1, pixelRow);
                        }

                        if (switchDropLocation) {
                            this.currentDepoMode = DepoMode.GROUND;
                        }

                        if (switchModeDepo) {
                            this.currentControlMode = ControlMode.GRABBING;
                        }


                        break;
                }

                this.bread.arm.updateArm();
            }

        //update....//

        //telemetry...//
        telemetry.addData("Rotator Angle: ", this.bread.arm.getRotatorDegrees());
        telemetry.addData("Launcher Ready?: ", aiming);
        telemetry.addData("Rails Height: ", BreadRails.rotationsToInches(this.bread.rails.getRotations()));
        telemetry.addData("Gyro Offset: ", gyroOffset);
        telemetry.addData("Control Mode: ", currentControlMode);
        telemetry.addData("Depositing Mode: ", currentDepoMode);
        telemetry.addData("Pixel Row: ", pixelRow);
        telemetry.addData("Climbed?: ", climbed);
        telemetry.addData("Need to Come Down?: ", needToGoDown);

        telemetry.update();

        }





    /*public double getDesiredDriveRotation(Vector2d aimVector, double speed){
        if(aimVector.norm() < 0.25) return 0.0;

        // get rotational power
        //double rotationSpeed = ROTATION_POWER * speed;

        double aimVectorRotation = aimVector.angle();
        double forwardVectorRotation = this.bread.drive.getPoseEstimate().getHeading();

        // get direction
        double distance = AngleHelper.angularDistanceRadians(forwardVectorRotation, aimVectorRotation);

       // return distance * rotationSpeed;
    }
*/
}
