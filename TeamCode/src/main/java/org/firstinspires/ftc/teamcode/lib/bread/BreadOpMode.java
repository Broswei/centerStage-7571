package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.util.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.List;

public abstract class BreadOpMode extends LinearOpMode {

    //constants
    public static double STACK_HEIGHT_INCREASE = 0.5;
    public static double BASE_STACK_HEIGHT = 2.5;


    // global timer
    private ElapsedTime globalTimer;

    // delta
    private double delta;
    private double lastTime; // time between updateDelta calls

    // gamepads
    public GamepadEx gamepadEx1;
    public GamepadEx gamepadEx2;

    // bot config
    public BreadBot bread;

    /**
     * @return the time in seconds since initialize() has last been called
     */
    public double getGlobalTimeSeconds(){
        return globalTimer.seconds();
    }

    /**
     * @return the amount of time passed since the last resetDelta call
     */
    public double getDelta(){
        double time = this.getGlobalTimeSeconds();

        return time-lastTime;
    }

    /**
     * @brief Reset the delta timer to 0 seconds
     */
    public void resetDelta(){
        double time = this.getGlobalTimeSeconds();

        lastTime = time;
    }

    public void initialize(HardwareMap hardwareMap){ //initialize everything
        // construct abe
        this.bread = new BreadBot(hardwareMap);

        // construct global timer
        this.globalTimer = new ElapsedTime();

        this.gamepadEx1 = new GamepadEx(gamepad1);
        this.gamepadEx2 = new GamepadEx(gamepad2);
    }
}
