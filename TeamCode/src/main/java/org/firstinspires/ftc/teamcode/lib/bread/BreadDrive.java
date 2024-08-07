package org.firstinspires.ftc.teamcode.lib.bread;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.util.Imu;

public class BreadDrive {

    private SampleMecanumDrive roadrunnerDrive;
    private Pose2d desiredMovement;
    private Vector2d poseOffset;
    public BNO055IMU imu;

    public double movement_x = 0;
    public double movement_y = 0;
    public double movement_turn = 0;

    private ElapsedTime deltaTimer;

    public BreadDrive(HardwareMap hardwareMap){

        this.roadrunnerDrive = new SampleMecanumDrive(hardwareMap);
        this.desiredMovement = new Pose2d(0, 0, 0);

        this.deltaTimer = new ElapsedTime();
    }

    public void initGyro(BNO055IMU IMU){
        imu = IMU;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

    }
    public void initGyro(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
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

    public SampleMecanumDrive getRoadrunnerDrive(){
        return roadrunnerDrive;
    }

    public void noRoadRunnerDriveFieldOriented(double forward, double strafe, double rotate){
        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;


        double largestPower = Math.max(fl, fr);
        largestPower = Math.max(br, largestPower);
        largestPower = Math.max(br, largestPower);

        if(largestPower > 1) {
            fl /= largestPower;
            fr /= largestPower;
            bl /= largestPower;
            br /= largestPower;
        }

        roadrunnerDrive.setMotorPowers(0.83*fl, bl, br, 0.83*fr);
    }

    public void setMotorVelocities(int v){
        this.roadrunnerDrive.setMotorVelocities(v);
    }

    public void setMotorPositions(int v, int v1, int v2, int v3){
        this.roadrunnerDrive.setMotorPositions(v, v1, v2, v3);
    }

    public void setMotorPositions(int v){
        this.roadrunnerDrive.setMotorPositions(v);
    }

    public void setMode(DcMotor.RunMode runMode) {
        this.roadrunnerDrive.setMode(runMode);
    }

    public void setPowers(double v, double v1, double v2, double v3){
        this.roadrunnerDrive.setMotorPowers(v, v1, v2, v3);
    }

    public boolean isRegBusy(){
        return this.roadrunnerDrive.isRegBusy();
    }

    public String getPowers(){
        return this.roadrunnerDrive.getPowers();
    }
}
