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
    private WPI_TalonSRX leftTalon3;

    private WPI_TalonSRX rightTalon1;
    private WPI_TalonSRX rightTalon2;
    private WPI_TalonSRX rightTalon3;

    private SpeedControllerGroup leftTalons;
    private SpeedControllerGroup rightTalons;

    private DifferentialDrive robotDrive;

    private WPI_TalonSRX shooter1;
    private WPI_TalonSRX shooter2;

    private SpeedControllerGroup shooter;

    private WPI_TalonSRX diskMotor;

    private WPI_TalonSRX intake1;
    private WPI_TalonSRX intake2;

    private SpeedControllerGroup intake;

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
        leftTalon3 = new WPI_TalonSRX(3);

        rightTalon1 = new WPI_TalonSRX(4);
        rightTalon2 = new WPI_TalonSRX(5);
        rightTalon3 = new WPI_TalonSRX(6);

        // combine multiple talons on each side into a single controller (necessary for the robotDrive)
        leftTalons = new SpeedControllerGroup(leftTalon1, leftTalon2, leftTalon3);
        rightTalons = new SpeedControllerGroup(rightTalon1, rightTalon2, rightTalon3);

        robotDrive = new DifferentialDrive(leftTalons, rightTalons);

        shooter1 = new WPI_TalonSRX(7);
        shooter2 = new WPI_TalonSRX(8);

        shooter = new SpeedControllerGroup(shooter1, shooter2);
        
        diskMotor = new WPI_TalonSRX(9);
        
        intake1 = new WPI_TalonSRX(10);
        intake2 = new WPI_TalonSRX(11);

        intake = new SpeedControllerGroup(intake1, intake2);
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

    public WPI_TalonSRX getShooter1() {
        return shooter1;
    }

    public WPI_TalonSRX getShooter2() {
        return shooter2;
    }

    public SpeedControllerGroup getShooter() {
        return shooter;
    }

    public WPI_TalonSRX getDiskMotor() {
        return diskMotor;
    }

    public WPI_TalonSRX getIntake1() {
        return intake1;
    }

    public WPI_TalonSRX getIntake2() {
        return intake2;
    }

    public SpeedControllerGroup getIntake() {
        return intake;
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

        SmartDashboard.putData("shooter", getShooter());

        SmartDashboard.putData("diskMotor", getDiskMotor());

        SmartDashboard.putData("intake", getIntake());

        SmartDashboard.putData("pdp", pdp);
    }
}