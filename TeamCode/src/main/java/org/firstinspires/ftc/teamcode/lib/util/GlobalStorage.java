package org.firstinspires.ftc.teamcode.lib.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;

/**
 * @brief Class for managing "global" storage (data that persists between opmodes)
 */
public class GlobalStorage {
    // global telemetry instance
    public static Telemetry globalTelemetry;

    // current pose
    public static Pose2d currentPose;


    // did we do auto?
    // generally going to be yes
    public static boolean didAuto;

    public static void clearGlobalStorage(){
        GlobalStorage.globalTelemetry = null;
        GlobalStorage.currentPose = new Pose2d();
        GlobalStorage.didAuto = false;
    }

    public static void logGlobalStorage(Telemetry telemetry){
        telemetry.addData("globalTelemetry?", GlobalStorage.globalTelemetry != null);
        telemetry.addData("current pose", GlobalStorage.currentPose != null ? GlobalStorage.currentPose.toString() : "null");
        telemetry.addData("did auto", GlobalStorage.didAuto);
    }
}
