package org.firstinspires.ftc.teamcode.lib.bread;

import org.firstinspires.ftc.teamcode.lib.bread.BreadConstants;
import org.firstinspires.ftc.teamcode.lib.motion.PositionableServo;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * @brief Dedicated class for managing the flipper + wrist + claw servo combo
 */
public class BreadHand {
    private Servo wristServo;

    /**
     * @brief Clamps cones
     */
    private Servo leftClaw;
    private Servo rightClaw;


    public BreadHand(Servo wristServo, Servo leftClaw, Servo rightClaw){
        this.wristServo = wristServo;
        this.leftClaw = leftClaw;
        this.rightClaw = rightClaw;
    }

    /**
     * @brief Set the wrist to pickup mode (ideally this position is 0)
     */
    public void setPickUp(){
        this.wristServo.setPosition(0);
    }

    /**
     * @brief Set the wrist to deposit mode (backdrop is fixed position!)
     */
    public void setDeposit(){
        this.wristServo.setPosition(BreadConstants.WRIST_DEPO_POS);
    }

    public void setRest(){
        this.wristServo.setPosition(BreadConstants.WRIST_REST_POS);
    }

    public void clamp(){
        this.leftClaw.setPosition(0.5);
        this.rightClaw.setPosition(0.5);
    }

    public void unclamp(){
        this.leftClaw.setPosition(0.25);
        this.rightClaw.setPosition(0.25);
    }

    public void rightUnclamp(){
        this.rightClaw.setPosition(0.5);
    }

    public void leftUnclamp(){
        this.leftClaw.setPosition(0.5);
    }

    public void rightClamp(){
        this.rightClaw.setPosition(0.25);
    }

    public void leftClamp(){
        this.leftClaw.setPosition(0.25);
    }

}