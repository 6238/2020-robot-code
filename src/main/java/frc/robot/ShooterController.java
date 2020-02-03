package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterController implements RobotController {
    private SpeedControllerGroup shooter;

    private JoystickController r_Stick;

    private double shooterSpeed = 0.5;

    public ShooterController(RobotProperties properties) {
        shooter = properties.getShooter();

        r_Stick = properties.getR_Stick();

        SmartDashboard.putNumber("shooterSpeed", shooterSpeed);
    }

    public boolean performAction() {
        shooterSpeed = SmartDashboard.getNumber("shooterSpeed", shooterSpeed);

        if (r_Stick.getTrigger()) {
            shooter.set(shooterSpeed);
        }

        return true;
    }
}