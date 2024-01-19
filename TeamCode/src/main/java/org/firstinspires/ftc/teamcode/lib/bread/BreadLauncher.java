package org.firstinspires.ftc.teamcode.lib.bread;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableServo;

public class BreadLauncher {

    //angleAdjuster allows the entire launcher to change angle (may be used co-existing with localization)
    private Servo angleAdjuster;

    //the servo hub that pushes the standoff of our plane design
    private Servo launcher;

    public BreadLauncher(Servo angleAdjuster, Servo launcher){
        this.angleAdjuster = angleAdjuster;
        this.launcher = launcher;
    }
    /**
     * @brief Set the plane to ready position!
     */
    public void readyAim (){angleAdjuster.setPosition(BreadConstants.LAUNCH_ANGLE_POS);}
    /**
     * @brief neutral position, not 0 because extending past bot width and also I'm lazy
     */
    public void putDown(){
        angleAdjuster.setPosition(BreadConstants.LAUNCH_DEFAULT_REST);
    }

    /**
     * @brief FIRE IN THE HOLE!
     */
    public void launch(){
        launcher.setPosition(0.53);
    }
    public void release(){launcher.setPosition(0);}

    public void climb(){angleAdjuster.setPosition(BreadConstants.LAUNCH_DOWN);}
}
