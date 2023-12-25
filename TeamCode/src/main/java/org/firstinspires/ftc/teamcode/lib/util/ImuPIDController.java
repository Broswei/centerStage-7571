package org.firstinspires.ftc.teamcode.lib.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;

public class ImuPIDController {

    private ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;
    double integralSum = 0;

    double kP;
    double kI;
    double kD;

    public ImuPIDController(double p, double i, double d){
        kP = p;
        kI = i;
        kD = d;
    }

    public double PIDControl(double refrence, double state) {
        double error = angleWrap(refrence - state);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * kP) + (derivative * kD) + (integralSum * kI);
        return output;
    }
    public double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }


}
