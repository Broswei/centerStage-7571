package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lib.bread.BreadArm;
import org.firstinspires.ftc.teamcode.lib.bread.BreadAutonomous;
import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableMotor;

@TeleOp
public class PIDF_Arm extends BreadAutonomous {

    private PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double scale = 1;
    public static double f = 0;

    public static int target = 700;

    public final double ticks_in_degree = BreadConstants.ROT_TPR / 360.0;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(BreadConstants.ROT_P_GAIN, BreadConstants.ROT_I_GAIN, BreadConstants.ROT_D_GAIN);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());



        initialize(hardwareMap);
        waitForStart();

        while(opModeIsActive()){
            controller.setPID(BreadConstants.ROT_P_GAIN*scale,BreadConstants.ROT_I_GAIN*scale,BreadConstants.ROT_D_GAIN*scale);
            scale = BreadConstants.ROT_GAINS_SCALE;

            double current = bread.arm.getPosition();
            double pid = controller.calculate(current, target);
            double ff = Math.sin(Math.toRadians(current/ticks_in_degree + 49.68)) * BreadConstants.ROT_F_GAIN;

            double power = pid + ff;

            bread.arm.setPowers(power);

            telemetry.addData("arm pos: ", current);
            telemetry.addData("target: ", target);
            telemetry.update();

        }

    }

}
