package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;

public class BreadArm {

    private PositionableMotor leftRotator;
    private PositionableMotor rightRotator;
    private BreadHand hand;

    //delta timer
    private ElapsedTime deltaTimer;

    //arm details
    private double rotatorAngleRadians;

    private double desiredWristAngleRadians;

    private double rotatorError;
    private double rotatorLastError;
    private double rotatorTotalError;
    private double rotatorDervError;

    private double lastTime = 0;


    public BreadArm (PositionableMotor leftRotator, PositionableMotor rightRotator, BreadHand hand){

        this.leftRotator = leftRotator;
        this.rightRotator = rightRotator;
        this.hand = hand;

        this.deltaTimer = new ElapsedTime();

        leftRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void shutDown(){
        this.leftRotator.setPower(0);
        this.rightRotator.setPower(0);
    }

    public void setPowers(double power){
        this.leftRotator.setPower(power);
        this.rightRotator.setPower(power);
    }

    public boolean areRotatorsBusy(){
        return this.leftRotator.isBusy();
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

    public void setRightUnclamped(){this.hand.rightUnclamp();}

    public void setLeftUnclamped(){this.hand.leftUnclamp();}

    public void setRightClamped(){this.hand.rightClamp();}

    public void setLeftClamped(){this.hand.leftClamp();}
    /**
     * @return the current (not intended) angle of the elbow in radians
     */
    public double getRotatorRadians(){
        return this.leftRotator.getAngleRadians();
    }

    /**
     * @return the current (not intended) angle of the elbow in degrees
     */
    public double getRotatorDegrees(){
        return this.leftRotator.getAngleDegrees();
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
    public void setRotatorAngleRadians(double elbowAngle){
        this.rotatorAngleRadians = elbowAngle;
    }

    /**
     * @brief manually set the elbow angle to some value in degrees
     */
    public void setRotatorAngleDegrees(double elbowAngle){
        this.setRotatorAngleRadians(Math.toRadians(elbowAngle));
    }

    public void updateArm(){

        leftRotator.rotateToRadians(this.rotatorAngleRadians, 2*Math.PI/3);
        rightRotator.rotateToRadians(this.rotatorAngleRadians, 2*Math.PI/3);

    }

    public void updatePIDArm () {

        double dt = deltaTimer.milliseconds() - lastTime;

        // proportional
        rotatorError = getRotatorRadians();

        // derivative
        rotatorDervError = (rotatorError-rotatorLastError) / dt;

        // integral
        rotatorTotalError += rotatorError * dt;

        // integral windup preventer
        if(Math.abs(rotatorTotalError) > 1/BreadConstants.ROT_I_GAIN) {
            rotatorTotalError = Math.signum(rotatorTotalError) * 1/BreadConstants.ROT_I_GAIN;
        }


        double correction =
                BreadConstants.ROT_P_GAIN * rotatorError +
                BreadConstants.ROT_I_GAIN * rotatorTotalError +
                BreadConstants.ROT_D_GAIN * rotatorDervError;

        leftRotator.rotateSpeedRadians(correction);
        rightRotator.rotateSpeedRadians(correction);

        rotatorLastError = rotatorError;
    }

    public void setNormalDepoPos(){
        this.hand.setDeposit();
    }

    public void setLowDepoPos(){this.hand.setLow();}

    public void setPickUpPos(){this.hand.setPickUp();}

    public void setRestPos(){this.hand.setRest();}
}
