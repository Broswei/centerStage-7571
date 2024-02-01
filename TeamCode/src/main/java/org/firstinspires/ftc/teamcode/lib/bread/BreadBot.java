package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;
import org.firstinspires.ftc.teamcode.lib.pipelines.TSEDetectionProcessor;
import org.firstinspires.ftc.teamcode.lib.util.Imu;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class BreadBot {

    public final BreadDrive drive;
    public Imu imu;
    public BreadArm arm;

    public BreadRails rails;
    public BreadLauncher launcher;

//    public WebcamName webcamName;

    public BreadVision vision;

    public BreadBot(HardwareMap hardwareMap){

        //load hardware
        BreadConfig.Hardware hardware = BreadConfig.loadHardware(hardwareMap);

        AprilTagProcessor tagProcessor = AprilTagProcessor.easyCreateWithDefaults();

        TSEDetectionProcessor tseProcessor = new TSEDetectionProcessor(false);

        vision = new BreadVision(tagProcessor, tseProcessor,hardware.webcamName);
        //load drive
        drive = new BreadDrive(hardwareMap);

        //load imu
        imu = new Imu(hardwareMap.get(BNO055IMU.class, "imu"));

        //load motor
        PositionableMotor leftRotator = new PositionableMotor(hardware.leftRotator, BreadConstants.ROT_GEAR_RATIO, BreadConstants.ROT_TPR);
        PositionableMotor rightRotator = new PositionableMotor(hardware.rightRotator, BreadConstants.ROT_GEAR_RATIO, BreadConstants.ROT_TPR);

        //load servos (deciding in mid-December, stick with regular servos for simplicity)
        //PositionableServo angleAdjuster = new PositionableServo(hardware.angleAdjuster, BreadConstants.ANG_MAX_RANGE, BreadConstants.ANG_ZERO_ANGLE);
        //PositionableServo wristServo = new PositionableServo(hardware.wristServo, BreadConstants.WRIST_MAX_RANGE, BreadConstants.WRIST_ZERO_ANGLE);

        //load rails
        rails = new BreadRails(hardware.leftRail, hardware.rightRail, BreadConstants.TOWERS_GEAR_RATIO, BreadConstants.TOWERS_TPR);

        //load hand
        BreadHand hand = new BreadHand(hardware.wristServo, hardware.leftClaw, hardware.rightClaw);

        //load arm
        this.arm = new BreadArm(leftRotator, rightRotator, hand);

        //load launcher
        this.launcher = new BreadLauncher(hardware.angleAdjuster, hardware.launcher);
    }


}
