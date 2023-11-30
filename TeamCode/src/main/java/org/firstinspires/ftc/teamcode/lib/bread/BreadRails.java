package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class BreadRails {
    // motor
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    public double tickOffset = 0; // current tick offset from the number returned by getCurrentPosition, added to whenever resetEncoders is called

    // details
    private double gearRatio; // NOTE: ratio of driver gear rotations : driven gear rotations
    private double tickRatio;

    // FIXME: make work for more than just methods that utilize target position
    private double upperLimit; // an upper limit on how far the motor can rotate, in rotations
    private double lowerLimit; // a lower limit on how far the motor can rotate, in rotations

    private boolean limitsEnabled;

    // disabled
    private boolean disabled;

    public static double rotationsToDegrees(double rotations){
        return rotations * 360;
    }

    public static double rotationsToRadians(double rotations){
        return rotations * Math.PI*2;
    }

    public static double radiansToRotations(double radians){
        return radians / (Math.PI*2);
    }

    public static double degreesToRotations(double degrees){
        return degrees / 360.0;
    }

    public BreadRails(DcMotorEx leftMotor, DcMotorEx rightMotor, double gearRatio, double tickRatio){
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.gearRatio = gearRatio;
        this.tickRatio = tickRatio;

        // brake at zero
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // hard reset encoders
        this.hardResetEncoders();

        this.tickOffset = 0;

        // enable
        this.enable();

        this.limitsEnabled = false;
    }

    public BreadRails(DcMotorEx leftMotor, DcMotorEx rightMotor, double gearRatio, double tickRatio, double lowerLimit, double upperLimit){
        this(leftMotor, rightMotor, gearRatio, tickRatio);

        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.limitsEnabled = true;
    }

    /**
     * @return true if the slides are currently running to position (doesn't use steady state settings)
     */
    public boolean areTowersBusy(){
        return this.leftMotor.isBusy() || this.rightMotor.isBusy();
    }

    public void setTargetPos (int ticks, double velocity){
        this.leftMotor.setTargetPosition(ticks);
        this.rightMotor.setTargetPosition(ticks);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setVelocity(velocity);
        this.rightMotor.setVelocity(velocity);
    }

    public void presetRaiseTowersUp(int level){
        if (level == 1){
            rotateTo(0,BreadConstants.TOWERS_NORM_VELOCITY);
        }
        if (level == 2){
            rotateTo(inchesToRotations(5.74),BreadConstants.TOWERS_NORM_VELOCITY);
        }
        else{
            rotateTo(BreadConstants.TOWERS_MAX_ROTATIONS, BreadConstants.TOWERS_NORM_VELOCITY);
        }
    }

    public double rotationsToTicks(double rotations){
        return rotations * this.tickRatio * this.gearRatio;
    }

    public double ticksToRotations(double ticks){
        return ticks / this.tickRatio / this.gearRatio;
    }

    public double degreesToTicks(double degrees){
        return this.rotationsToTicks(BreadRails.degreesToRotations(degrees));
    }

    public double ticksToDegrees(double ticks){
        return this.ticksToRotations(ticks) * 360;
    }

    public double radiansToTicks(double radians){
        return this.rotationsToTicks(BreadRails.radiansToRotations(radians));
    }

    public double inchesToRotations(double inches){
        return inches *25.4/8;
    }

    public static double rotationsToInches(double rotations){
        return rotations*8/25.4;
    }

    /**
     * @brief set mode mirror
     *
     * @param mode
     */
    public void setMode(DcMotor.RunMode mode){
        this.leftMotor.setMode(mode);
        this.rightMotor.setMode(mode);
    }

    /**
     * @brief abstraction for is busy
     *
     * @return true if motor is busy
     */
    public boolean isBusy(){
        return this.leftMotor.isBusy() || this.rightMotor.isBusy();
    }

    /**
     * @brief Reset the motor encoders
     */
    public void resetEncoders(){
        this.tickOffset += (double)this.getRawPosition();
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * @brief Reset the motor encoders without saving the tick offset
     */
    public void hardResetEncoders(){
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setAngleOffsetRotations(double offset){
        this.tickOffset = this.rotationsToTicks(offset);
    }

    /**
     * @brief Set the internal tickOffset to some value based on a radian offset.
     *
     * @param offset offset in radians
     */
    public void setAngleOffsetRadians(double offset){
        this.tickOffset = this.radiansToTicks(offset);
    }

    /**
     * @brief Set the internal tickOffset to some value based on a degree offset.
     *
     * @param offset offset in degrees
     */
    public void setAngleOffsetDegrees(double offset){
        this.tickOffset = this.degreesToTicks(offset);
    }

    public void addToAngleOffsetRadians(double offset){
        this.tickOffset += this.radiansToTicks(offset);
    }

    public void addToAngleOffsetDegrees(double offset){
        double offsetTicks = this.degreesToTicks(offset);

        this.tickOffset += offsetTicks;
    }

    public void disable(){
        // stops the motor from running to position
        this.resetEncoders();

        // might stop it quicker?
        this.leftMotor.setPower(0);
        this.rightMotor.setPower(0);

        // prevents any other calls from moving the motor, provided that the user doesn't use it directly
        this.disabled = true;
    }

    public void enable(){
        this.disabled = false;
    }

    /**
     * @brief Wrapper for setPower of DcMotorEx
     */
    public void setPower(double power){
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.leftMotor.setPower(power);
        this.rightMotor.setPower(power);
    }

    /**
     * @brief returns the current position in ticks of the drive shaft
     *
     * @return current position in ticks as an int
     */
    public int getRawPosition(){
        return this.leftMotor.getCurrentPosition();
    } //two motors should be roughly equal

    /**
     * @brief gets the cumulative number of rotations of the driven gear (even if encoders are reset) from start
     *
     * @return number of rotations
     */
    public double getRotations() {
        // get current rotations from ticks + gear ratio
        int position = this.getRawPosition();
        double rotations = ((double)(position) + this.tickOffset) / this.tickRatio;

        double drivenRotations = rotations / this.gearRatio;

        return drivenRotations;
    }

    /**
     * @brief gets the cumulative angle (even if encoders are reset) from start
     *
     * @return angle in radians
     */
    public double getAngleRadians() {
        // get current rotations * radians per rotation
        return this.getRotations() * Math.PI*2;
    }

    /**
     * @brief gets the cumulative angle (even if encoders are reset) from start
     *
     * @return angle in degrees
     */
    public double getAngleDegrees(){
        // get current rotations * degrees per rotation
        return this.getRotations() * 360.0;
    }

    /**
     * @brief Get the current velocity in ticks of the drive shaft
     *
     * @return the velocity in raw ticks
     */
    public double getRawVelocity(){
        return this.leftMotor.getVelocity();
    } //two velocities should be roughly equal

    /**
     * @brief Get driven velocity in terms of rotations / second
     *
     * @return driven velocity in terms of rotations / second
     */
    public double getVelocityRotations(){
        double velocity = this.getRawVelocity();
        double rotations = velocity / this.tickRatio;

        double drivenRotations = rotations / this.gearRatio;

        return drivenRotations;
    }

    public double getVelocityRadians(){
        return this.getVelocityRotations() * 2 * Math.PI;
    }

    public double getVelocityDegrees(){
        return this.getVelocityRotations() * 360.0;
    }

    /**
     * @brief rotate the driven gear a certain amount of rotations
     *
     * @param rotations desired amount of rotations
     * @param velocity in rotations
     */
    public void rotate(double rotations, double velocity){
        if(this.disabled) return;

        if(this.limitsEnabled && (this.getRotations()+rotations < lowerLimit || this.getRotations()+rotations > upperLimit)) return;

        // position calculation
        double drivingRotations = rotations * this.gearRatio;
        double ticks = drivingRotations * this.tickRatio;

        // velocity calculation
        double drivingVelocity = velocity * this.gearRatio;
        double velocityTicks = drivingVelocity * this.tickRatio;

        // offset
        double offset = this.rotationsToTicks(this.getRotations());

        this.leftMotor.setTargetPosition((int)(ticks + offset - this.tickOffset));
        this.rightMotor.setTargetPosition((int)(ticks + offset - this.tickOffset));

        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.leftMotor.setVelocity(velocityTicks);
        this.rightMotor.setVelocity(velocityTicks);
    }

    /**
     * @brief rotate the motor a certain amount of degrees
     *
     * @param angle angle in degrees
     * @param velocity velocity in degrees
     */
    public void rotateAngleDegrees(double angle, double velocity){
        double rotations = angle / 360.0;
        double velocityRotations = velocity / 360.0;

        this.rotate(rotations, velocityRotations);
    }

    /**
     * @brief rotate the motor a certain amount of radians
     *
     * @param angle angle in radians
     * @param velocity velocity in radians
     */
    public void rotateAngleRadians(double angle, double velocity){
        double rotations = angle / (2*Math.PI);
        double velocityRotations = velocity / (2*Math.PI);

        this.rotate(rotations, velocityRotations);
    }

    public void rotateTo(double rotations, double velocity){
        // just adjust rotations
        rotations -= this.getRotations();

        this.rotate(rotations, velocity);
    }

    public void rotateToDegrees(double degrees, double velocity){
        // just adjust degrees
        degrees -= this.getAngleDegrees();

        this.rotateAngleDegrees(degrees, velocity);
    }

    public void rotateToRadians(double radians, double velocity){
        // just adjust radians
        radians -= this.getAngleRadians();

        this.rotateAngleRadians(radians, velocity);
    }

    /**
     * @brief rotate the motor a certain number of rotations per second (not going to any particular position)
     *
     * This preserves the position of the motor despite resetting encoders
     *
     * @param rotationsPerSecond
     */
    public void rotateSpeed(double rotationsPerSecond){
        // velocity calculation
        double drivingVelocity = rotationsPerSecond * this.gearRatio;
        double drivingTicks = drivingVelocity * this.tickRatio;

        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.leftMotor.setVelocity(drivingTicks);
        this.rightMotor.setVelocity(drivingTicks);
    }

    /**
     * @brief rotate the motor at a certain speed defined by degrees
     *
     * @param angularVelocity
     */
    public void rotateSpeedDegrees(double angularVelocity){
        double rotationsPerSecond = angularVelocity / 360.0;

        this.rotateSpeed(rotationsPerSecond);
    }

    public void rotateSpeedRadians(double angularVelocity){
        double rotationsPerSecond = angularVelocity / (2*Math.PI);

        this.rotateSpeed(rotationsPerSecond);
    }
}
