package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
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
    public static double f = 0;

    public static int target_Degrees = 0;

    public final double ticks_in_degree = BreadConstants.ROT_TPR / 360.0;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        initialize(hardwareMap);
        waitForStart();

        while(opModeIsActive()){
            controller.setPID(p,i,d);
            double currAngle = bread.arm.getRotatorDegrees();
            double pid = controller.calculate(PositionableMotor.degreesToRotations(currAngle)*BreadConstants.ROT_TPR, PositionableMotor.degreesToRotations(target_Degrees)*BreadConstants.ROT_TPR);
            double ff = Math.cos(Math.toRadians(target_Degrees/ticks_in_degree)) * f;

            double power = pid + ff;

            bread.arm.setPowers(power);

            telemetry.addData("arm pos: ", bread.arm.getRotatorDegrees());
            telemetry.addData("target: ", target_Degrees);
            telemetry.update();

        }

    }

}
