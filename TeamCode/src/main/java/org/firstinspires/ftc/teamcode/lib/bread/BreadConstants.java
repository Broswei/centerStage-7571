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

    //rotator related //
    public static double ROT_TPR = 537.7;
    public static double ROT_GEAR_RATIO = 1;

    // towers related //
    public static double TOWERS_TPR = 384.5;
    public static double TOWERS_GEAR_RATIO = 1;
    public static double TOWERS_BASE_LENGTH_INS = 13.75;
    public static double TOWERS_MAX_EXTENSION_TICKS = 10280;

    // claw related... //


}