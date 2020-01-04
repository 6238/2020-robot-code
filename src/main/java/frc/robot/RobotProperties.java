package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotProperties {    
    public static int instanceCount = 0;
    
    private JoystickController l_Stick;
    private JoystickController r_Stick;

    private PowerDistributionPanel pdp;

    private WPI_TalonSRX leftTalon1;
    private WPI_TalonSRX leftTalon2;

    private WPI_TalonSRX rightTalon1;
    private WPI_TalonSRX rightTalon2;

    private SpeedControllerGroup leftTalons;
    private SpeedControllerGroup rightTalons;

    private DifferentialDrive robotDrive;

    public RobotProperties() {
        if (instanceCount > 0) {
            throw new RuntimeException("Only one RobotProperties instance is allowed per robot!");
        }
        
        instanceCount++;

        l_Stick = new JoystickController(0);
        r_Stick = new JoystickController(1);
        pdp = new PowerDistributionPanel();

        leftTalon1 = new WPI_TalonSRX(1);
        leftTalon2 = new WPI_TalonSRX(2);

        rightTalon1 = new WPI_TalonSRX(1);
        rightTalon2 = new WPI_TalonSRX(2);

        leftTalons = new SpeedControllerGroup(leftTalon1, leftTalon2);
        rightTalons = new SpeedControllerGroup(rightTalon1, rightTalon2);

        robotDrive = new DifferentialDrive(leftTalons, rightTalons);
    }

    public JoystickController getL_Stick() {
        return l_Stick;
    }

    public JoystickController getR_Stick() {
        return r_Stick;
    }

    public DifferentialDrive getRobotDrive() {
        return robotDrive;
    }

    public void pushData() {
        // sends input, data values to SmartDashboard
        SmartDashboard.putNumber("l_JoyX", l_Stick.getX());
        SmartDashboard.putNumber("l_JoyY", l_Stick.getY());
        SmartDashboard.putNumber("l_JoyZ", l_Stick.getZ());

        SmartDashboard.putNumber("l_Slider", l_Stick.getSlider());

        SmartDashboard.putNumber("r_JoyX", r_Stick.getX());
        SmartDashboard.putNumber("r_JoyY", r_Stick.getY());
        SmartDashboard.putNumber("r_JoyZ", r_Stick.getZ());

        SmartDashboard.putNumber("r_Slider", r_Stick.getSlider());

        SmartDashboard.putData("robotDrive", getRobotDrive());

        SmartDashboard.putData("pdp", pdp);
    }
}