package org.firstinspires.ftc.teamcode.lib.bread;

import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableServo;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * @brief Dedicated class for managing the flipper + wrist + claw servo combo
 */
public class BreadHand {
    /**
     * @brief pitches the claw up and down, allowing the angle to stay consistent at all angles of the elbow (and for the cone to be manipulated to make better deposits)
     */
    private PositionableServo wristServo;

    /**
     * @brief Clamps cones
     */
    private Servo clawServo;


    public BreadHand(PositionableServo wristServo, Servo clawServo){
        this.wristServo = wristServo;
        this.clawServo = clawServo;
    }

    /**
     * @brief Set the pitch of the wrist (actuates wrist servo) in radians
     *
     * @param pitch
     */
    public void setPitchRadians(double pitch){
        this.wristServo.rotateToRadians(pitch);
    }

    /**
     * @brief Set the pitch of the wrist (actuates wrist servo) in degrees
     *
     * @param pitch
     */
    public void setPitchDegrees(double pitch){
        this.wristServo.rotateToDegrees(pitch);
    }

    public void clamp(){
        this.clawServo.setPosition(0.5);
    }

    public void unclamp(){
        this.clawServo.setPosition(0.25);
    }
}