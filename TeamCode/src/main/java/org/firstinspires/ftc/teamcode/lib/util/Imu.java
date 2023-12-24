package org.firstinspires.ftc.teamcode.lib.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * @brief Basic abstraction for the BNO055 imu
 */
public class Imu {
    private BNO055IMU imu;

    private Orientation lastAngles = new Orientation();
    private double currAngle = 0.0;

    public Imu(BNO055IMU imu) {
        this.imu = imu;

        BNO055IMU.Parameters params = new BNO055IMU.Parameters();

        // params here...

        this.imu.initialize(params);
    }

    /**
     * @brief returns angle of the imu in radians
     *
     * @return radians
     */
    public float getAngleRadians(){
        return this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
    }

    /**
     * @brief returns angle of the imu in degrees
     *
     * @return degrees
     */
    public double getAngleDegrees(){
        Orientation orientation = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = orientation.firstAngle - lastAngles.firstAngle;

        if (deltaAngle > 180){
            deltaAngle -= 360;
        }
        else if (deltaAngle < -180){
            deltaAngle += 360;
        }

        currAngle += deltaAngle;
        lastAngles = orientation;

        return currAngle;
    }

    public void resetAngle(){
        lastAngles = this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currAngle = 0;
    }

    public Orientation getOrientation(){

        return this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    public double getAbsoluteAngleDegrees() {
        return this.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

    }

}