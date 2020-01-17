package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanelController implements RobotController {

    private WPI_TalonSRX diskMotor;

    private boolean positionControl = false;
    private boolean rotationControl = false;

    private boolean blue = false;
    private boolean green = false;
    private boolean red = false;
    private boolean yellow = false;

    private double rotationSpeed = 0.6;
    private double positionSpeed = 0.25;

    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();

    /* private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); */
    
    private final Color kBlueTarget = ColorMatch.makeColor(0.224, 0.476, 0.300);
    private final Color kGreenTarget = ColorMatch.makeColor(0.209, 0.565, 0.226);
    private final Color kRedTarget = ColorMatch.makeColor(0.526, 0.341, 0.129);
    private final Color kYellowTarget = ColorMatch.makeColor(0.337, 0.528, 0.113);

    private Color setColor;

    private int tempCount = 0;
    private int colorCount = 0;

    private int countThreshold = 2;
    private double confidenceThreshold = 0.7;
    private int maxCount = 4;

    private boolean pcSetup = false;
    private boolean rcSetup = false;

    private int colorInt;
    private int positionInt;

    public ControlPanelController(RobotProperties properties) {
        diskMotor = properties.getDiskMotor();

        // bring over teleopInit stuff, make position control and rotation control dashboard buttons & joystick activated

        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);

        SmartDashboard.putNumber("countThreshold", countThreshold);
        SmartDashboard.putNumber("confidenceThreshold", confidenceThreshold);
        SmartDashboard.putNumber("maxCount", maxCount);

        SmartDashboard.putBoolean("positionControl", positionControl);
        SmartDashboard.putBoolean("rotationControl", rotationControl);
    }

    public boolean performAction() {
        positionControl = SmartDashboard.getBoolean("positionControl", positionControl);
        rotationControl = SmartDashboard.getBoolean("rotationControl", rotationControl);

        countThreshold = (int) SmartDashboard.getNumber("countThreshold", countThreshold);
        confidenceThreshold = SmartDashboard.getNumber("confidenceThreshold", confidenceThreshold);
        maxCount = (int) SmartDashboard.getNumber("maxCount", maxCount);

        if (positionControl && rotationControl) {
            rotationControl = false;
            SmartDashboard.putBoolean("rotationControl", rotationControl);
        }

        if (rotationControl) {
            if (!rcSetup) {
                Color detectedColor = colorSensor.getColor();
                SmartDashboard.putString("color", "" + detectedColor);
    
                ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
    
                setColor = match.color;
    
                colorCount = 0;
                tempCount = 0;
    
                rcSetup = true;
            } else if (rcSetup) {
                Color newColor = colorSensor.getColor();

                ColorMatchResult match = colorMatcher.matchClosestColor(newColor);
                
                boolean colorMatched = match.color == setColor;
    
                SmartDashboard.putBoolean("color matched", colorMatched);
                SmartDashboard.putNumber("colorCount", colorCount);
                SmartDashboard.putNumber("tempCount", tempCount);
    
                if (colorCount < maxCount * 2) {
                    diskMotor.set(SmartDashboard.getNumber("insanityFactor", rotationSpeed));
                } else {
                    diskMotor.set(0);
                    positionControl = true;
                }
    
                if (colorMatched && match.confidence > confidenceThreshold) {
                    tempCount++;
                } else {
                    if (tempCount > countThreshold) {
                    colorCount++;
                    }
                    tempCount = 0;
                }
            }
        } else if (positionControl) {
            if (!pcSetup) {
                String gameData;
                gameData = DriverStation.getInstance().getGameSpecificMessage();

                blue = SmartDashboard.getBoolean("blue", blue);
                green = SmartDashboard.getBoolean("green", green);
                red = SmartDashboard.getBoolean("red", red);
                yellow = SmartDashboard.getBoolean("yellow", yellow);

                if (gameData.length() > 0) {
                    switch (gameData.charAt(0)) {
                        case 'B':
                            blue = true;
                            green = false;
                            red = false;
                            yellow = false;
                            positionInt = 0;
                        break;
                        case 'G':
                            blue = false;
                            green = true;
                            red = false;
                            yellow = false;
                            positionInt = 1;
                        break;
                        case 'R':
                            blue = false;
                            green = false;
                            red = true;
                            yellow = false;
                            positionInt = 2;
                        break;
                        case 'Y':
                            blue = false;
                            green = false;
                            red = false;
                            yellow = true;
                            positionInt = 3;
                        break;
                        default:
                            System.out.println("Ishan can't code, so colors are weird");
                        break;
                    }

                    positionInt += 2;
                    if (positionInt > 3) {
                        positionInt -= 4;
                    }
        
                    SmartDashboard.putBoolean("blue", blue);
                    SmartDashboard.putBoolean("green", green);
                    SmartDashboard.putBoolean("red", red);
                    SmartDashboard.putBoolean("yellow", yellow);

                    pcSetup = true;
        
                } else if (blue || green || red || yellow) {
                    if (blue) {
                        green = false;
                        red = false;
                        yellow = false;
                    } else if (green) {
                        blue = false;
                        red = false;
                        yellow = false;
                    } else if (red) {
                        blue = false;
                        green = false;
                        yellow = false;
                    } else if (yellow) {
                        blue = false;
                        green = false;
                        red = false;
                    }
        
                    SmartDashboard.putBoolean("blue", blue);
                    SmartDashboard.putBoolean("green", green);
                    SmartDashboard.putBoolean("red", red);
                    SmartDashboard.putBoolean("yellow", yellow);
                    
                    pcSetup = true;
                }
            } else if (pcSetup) {
                Color detectedColor = colorSensor.getColor();
                SmartDashboard.putString("color", "" + detectedColor);

                ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

                if (match.color == kBlueTarget) {
                    colorInt = 0;
                } else if (match.color == kGreenTarget) {
                    colorInt = 1;
                } else if (match.color == kRedTarget) {
                    colorInt = 2;
                } else if (match.color == kYellowTarget) {
                    colorInt = 3;
                } else {
                    colorInt = positionInt;
                }

                if (colorInt < positionInt) {
                    diskMotor.set(-1 * SmartDashboard.getNumber("positionSpeed", positionSpeed));
                } else if (colorInt > positionInt) {
                    diskMotor.set(SmartDashboard.getNumber("positionSpeed", positionSpeed));
                } else {
                    diskMotor.set(0);
                    positionControl = false;
                }
            }
        }

        return true;
    }
}