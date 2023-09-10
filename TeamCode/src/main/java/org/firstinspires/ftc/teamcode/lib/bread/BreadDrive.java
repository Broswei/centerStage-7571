package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class BreadDrive {

    private SampleMecanumDrive roadrunnerDrive;
    private Pose2d desiredMovement;
    private Vector2d poseOffset;

    private ElapsedTime deltaTimer;

    public BreadDrive(HardwareMap hardwareMap){

        this.roadrunnerDrive = new SampleMecanumDrive(hardwareMap);
        this.desiredMovement = new Pose2d(0, 0, 0);

        this.deltaTimer = new ElapsedTime();
    }

    /**
     * @param startPose
     * @return passthrough to rr drive trajectory builder
     */
    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose){
        return this.roadrunnerDrive.trajectoryBuilder(startPose);
    }

    /**
     * @brief follow a trajectory synchronously, blocking until complete
     *
     * @param trajectory
     */
    public void followTrajectory(Trajectory trajectory){
        this.roadrunnerDrive.followTrajectory(trajectory);
    }


    public void setPoseEstimate(Pose2d pose){
        this.roadrunnerDrive.setPoseEstimate(pose);

        this.setPoseOffset(new Vector2d(0, 0));
    }

    public void setPoseOffset(Vector2d offset){
        this.poseOffset = new Vector2d(offset.getX(), offset.getY());
    }

    public void addToPoseOffset(Vector2d offset){
        this.poseOffset = this.poseOffset.plus(offset);
    }

    public Pose2d getPoseEstimate(){
        Pose2d corrected = new Pose2d(this.roadrunnerDrive.getPoseEstimate().vec().plus(this.poseOffset), this.roadrunnerDrive.getPoseEstimate().getHeading());

        return corrected;
    }

    public void driveFieldOriented(double x, double y, double r){
        this.desiredMovement = new Pose2d(x, y, r);
    }

}
