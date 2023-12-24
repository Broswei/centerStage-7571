package org.firstinspires.ftc.teamcode.lib.util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ImuPIDController {

    private double targetAngle;
    public static double kP = 1;
    public static double kI = 0;
    public static double kD= 0;
    private double accumulatedError;
    private ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;
    private double lastTime = 0;
    public ImuPIDController(double target, double p, double i, double d){
        targetAngle = target;
        kP = p;
        kI = i;
        kD = d;

    }

    public void setTarget (double target) {
        targetAngle = target;
    }

    public double update(double currentAngle){

        //P
        double error = targetAngle - currentAngle;
        error %= 360;
        error += 360;
        error %= 360;
        if (error > 180){
            error -= 180;
        }
        //I
        accumulatedError += error*lastTime;
        if (Math.abs(error) < 1){
            accumulatedError = 0;
        }
        accumulatedError = Math.abs(accumulatedError) * Math.signum(error);
        //D
        double slope = 0;
        if (lastTime > 0){
            slope = (error - lastError)/(timer.milliseconds() - lastTime);
        }
        lastTime = timer.milliseconds();
        lastError = error;

        //motor power calculation
        double motorPower = 0.1 * Math.signum(error) + 0.9 * Math.tanh(kP*error + kI*accumulatedError + kD*slope); //ensures between -1,1

        return motorPower;
    }


}
