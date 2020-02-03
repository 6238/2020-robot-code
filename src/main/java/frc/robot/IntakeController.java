package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeController implements RobotController {
    private SpeedControllerGroup intake;

    private JoystickController l_Stick;

    private double intakeSpeed = 0.5;

    public IntakeController(RobotProperties properties) {
        intake = properties.getIntake();

        l_Stick = properties.getL_Stick();

        SmartDashboard.putNumber("intakeSpeed", intakeSpeed);
    }

    public boolean performAction() {

        intakeSpeed = SmartDashboard.getNumber("intakeSpeed", intakeSpeed);

        if (l_Stick.getTrigger()) {
            intake.set(intakeSpeed);
        }

        return true;
    }
}