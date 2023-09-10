package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.lib.util.AngleHelper;
import org.firstinspires.ftc.teamcode.lib.util.MathUtils;

public abstract class BreadTeleOp extends BreadOpMode{

    public static double MIN_POWER = 0.2;
    public static double MAX_POWER = 0.6;
    public static double ROTATION_POWER = 0.5;

    public void setup(){
        initialize(hardwareMap);
    }

    public void update(){
        double delta = getDelta();

        resetDelta();

        double speedTrigger = Math.max(gamepad1.left_trigger, gamepad1.right_trigger);
        double rate = MathUtils.map(speedTrigger, 0.0, 1.0, MAX_POWER, MIN_POWER);
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;

        Vector2d rotationVector = new Vector2d(-gamepad1.right_stick_y, -gamepad1.right_stick_x);
        double rotate = this.getDesiredDriveRotation(rotationVector, rate);

        this.bread.drive.driveFieldOriented(forward, strafe, rotate);

    }

    public double getDesiredDriveRotation(Vector2d aimVector, double speed){
        if(aimVector.norm() < 0.25) return 0.0;

        // get rotational power
        double rotationSpeed = ROTATION_POWER * speed;

        double aimVectorRotation = aimVector.angle();
        double forwardVectorRotation = this.bread.drive.getPoseEstimate().getHeading();

        // get direction
        double distance = AngleHelper.angularDistanceRadians(forwardVectorRotation, aimVectorRotation);

        return distance * rotationSpeed;
    }

}
