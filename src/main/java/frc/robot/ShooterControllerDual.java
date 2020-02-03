package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterControllerDual implements RobotController {

    private JoystickController r_Stick;

    private WPI_TalonSRX shooter1;
    private WPI_TalonSRX shooter2; 
    
    private double shooter1Speed = 0.5;
    private double shooter2Speed = 0.5;

    private boolean invertShooter = false;
    private boolean syncMotors = true;

    public ShooterControllerDual(RobotProperties properties) {

        r_Stick = properties.getR_Stick();
        
        shooter1 = properties.getShooter1();
        shooter2 = properties.getShooter2();

        SmartDashboard.putNumber("shooter1Speed", shooter1Speed);
        SmartDashboard.putNumber("shooter2Speed", shooter2Speed);

        SmartDashboard.putBoolean("invertShooter", invertShooter);
        SmartDashboard.putBoolean("syncMotors", syncMotors);
    }

    public boolean performAction() {
        syncMotors = SmartDashboard.getBoolean("syncMotors", syncMotors);

        if (!syncMotors) {
            shooter1Speed = SmartDashboard.getNumber("shooter1Speed", shooter1Speed);
            shooter2Speed = SmartDashboard.getNumber("shooter2Speed", shooter2Speed);
        } else {
            if (SmartDashboard.getNumber("shooter1Speed", shooter1Speed) != shooter1Speed) {
                shooter1Speed = SmartDashboard.getNumber("shooter1Speed", shooter1Speed);
                shooter2Speed = SmartDashboard.getNumber("shooter1Speed", shooter1Speed);
            } else if (SmartDashboard.getNumber("shooter2Speed", shooter2Speed) != shooter2Speed) {
                shooter1Speed = SmartDashboard.getNumber("shooter2Speed", shooter2Speed);
                shooter2Speed = SmartDashboard.getNumber("shooter2Speed", shooter2Speed);
            }

            SmartDashboard.putNumber("shooter1Speed", shooter1Speed);
            SmartDashboard.putNumber("shooter2Speed", shooter2Speed);
        }

        if (invertShooter) {
            shooter1.setInverted(true);
        } else {
            shooter1.setInverted(false);
        }

        if (r_Stick.getTrigger()) {

            shooter1.set(-shooter1Speed);
            shooter2.set(-shooter2Speed);
        }

        return true;
    }
}