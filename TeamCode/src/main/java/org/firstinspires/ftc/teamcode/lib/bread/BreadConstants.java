package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Vector2d;

/**
 * @brief Contains all constant values required for Bread functionality.  All bot characteristics and settings should be here.
 *
 * Generally speaking, configurable constants should only be in here, and should only be used in classes in the abe package.
 */
// make configurable from dashboard
@Config
public class BreadConstants {
    // general... //

    // drive related... //

    public static double IMU_P = 1;
    public static double IMU_I = 0.2;
    public static double IMU_D = 0.1;

    public static double IMU_P_REDUCTION = 0.15;

    public static double IMU_D_REDUCTION = 0.2;
    public static double IMU_TARGET = 90;

    public static double DESIRED_DISTANCE = 13.5;
    public static double SPEED_GAIN = 0.04;
    public static double STRAFE_GAIN = 0.05;
    public static double TURN_GAIN = 0.045;

    //rotator related //
    public static double ROT_TPR = 1425.1;
    public static double ROT_GEAR_RATIO = 1;
    public static double ROT_DEFAULT_DEGREES = 2;

    public static double ROT_NORM_DEPO_ANG = 190;

    public static double ROT_LOW_DEPO_ANG = 202;

    public static double ROT_P_GAIN  = 0.007;
    public static double ROT_I_GAIN = 0;

    public static double ROT_D_GAIN = 0.0001;
    public static double ROT_F_GAIN = 0.013;
    public static double ROT_GAINS_SCALE = 1;

    // towers related //
    public static double TOWERS_TPR = 384.5; //this for 435 RPM (3.7 cycle time) --> 1620 RPM = 103.8
    public static double TOWERS_GEAR_RATIO = 1;
    public static double TOWERS_MAX_ROTATIONS = 26.736;

    public static double TOWERS_NORM_VELOCITY = 2000/TOWERS_TPR;

    // wrist related... //
    public static double WRIST_DEPO_POS = 0.4;

    public static double WRIST_LOW_POS = 0.34;
    public static double WRIST_REST_POS = 0.34;

    // not used ATM, but will be;
    public static double WRIST_MIN_POS = 0;



    //launcher related...//
    public static double LAUNCH_ANGLE_POS = 0.15;
    public static double LAUNCH_DEFAULT_REST = 0.25;

    public static double LAUNCH_DOWN = 0.35;


}