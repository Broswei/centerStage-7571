package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.motion.LinearRails;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;

public class BreadArm {

    private PositionableMotor rotator;
    private LinearRails towers;
    private BreadHand hand;

    //delta timer
    private ElapsedTime deltaTimer;

    //depositing details
    private double rotatorAngleRadians;
    private double towersLengthInches;

    private double aimWristAngleRadians;
    private double desiredWristAngleRadians;
    private double wristAngleCorrectionRadians = 0.0;


    public BreadArm (PositionableMotor rotator, LinearRails towers, BreadHand hand){

        this.rotator = rotator;
        this.towers = towers;
        this.hand = hand;

        this.deltaTimer = new ElapsedTime();
    }

    public boolean isElbowBusy(){
        return this.rotator.isBusy();
    }

    /**
     * @return true if the slides are currently running to position (doesn't use steady state settings)
     */
    public boolean areSlidesBusy(){
        return this.towers.isBusy();
    }

    /**
     * @brief get the currently desired wrist rotation in radians
     */
    public double getDesiredWristAngleRadians(){
        return this.desiredWristAngleRadians;
    }

    /**
     * @brief get the currently desired wrist rotation in degrees
     *
     * this is uncontrolled by the aiming, must be managed outside
     */
    public double getDesiredWristAngleDegrees(){
        return Math.toDegrees(this.getDesiredWristAngleRadians());
    }

    /**
     * @brief set the currently desired wrist rotation in radians
     */
    public void setDesiredWristAngleRadians(double angle){
        this.desiredWristAngleRadians = angle;
    }

    /**
     * @brief set the currently desired wrist rotation in degrees
     *
     * this is uncontrolled by the aiming, must be managed outside
     */
    public void setDesiredWristAngleDegrees(double angle){
        this.setDesiredWristAngleRadians(Math.toRadians(angle));
    }

    /**
     * @brief set the hand to clamped
     *
     * this is uncontrolled by the aiming, must be managed outside
     */
    public void setHandClamped(){
        this.hand.clamp();
    }

    /**
     * @brief set the hand to unclamped
     *
     * this is uncontrolled by the aiming, must be managed outside
     */
    public void setHandUnclamped(){
        this.hand.unclamp();
    }

    /**
     * @return the current (not intended) angle of the elbow in radians
     */
    public double getRotatorRadians(){
        return this.rotator.getAngleRadians();
    }

    /**
     * @return the current (not intended) angle of the elbow in degrees
     */
    public double getRotatorDegrees(){
        return this.rotator.getAngleDegrees();
    }

    /**
     * @return the intended (not current) angle of the elbow in radians
     */
    public double getDesiredRotatorRadians(){
        return this.rotatorAngleRadians;
    }

    /**
     * @return the intended (not current) angle of the elbow in degrees
     */
    public double getDesiredRotatorDegrees(){
        return Math.toDegrees(this.rotatorAngleRadians);
    }

    /**
     * @brief manually set the elbow angle to some value in radians
     */
    public void setElbowAngleRadians(double elbowAngle){
        this.rotatorAngleRadians = elbowAngle;
    }

    /**
     * @brief manually set the elbow angle to some value in degrees
     */
    public void setElbowAngleDegrees(double elbowAngle){
        this.setElbowAngleRadians(Math.toRadians(elbowAngle));
    }

    /**
     * @brief makes the elbow go the speed specified in radians/s.  do not use in conjunction with update(), it'll either not work at all or mess things up
     *
     * @param velocityRadians
     */
    public void setElbowVelocityRadians(double velocityRadians){
        this.rotator.rotateSpeedRadians(velocityRadians);
    }

    /**
     * @brief see setElbowVelocityRadians
     *
     * @param velocityDegrees
     */
    public void setElbowVelocityDegrees(double velocityDegrees){
        this.setElbowVelocityRadians(Math.toRadians(velocityDegrees));
    }

    /**
     * @return the current (not intended) extension of the slides, in inches
     */
    public double getSlidesLengthInches(){
        return 0.0;
    }

    /**
     * @return the intended (not current) extension of the slides, in inches
     */
    public double getDesiredSlidesLengthInches(){
        return this.towersLengthInches;
    }

}
