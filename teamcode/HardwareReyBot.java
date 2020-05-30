//This was where all the mapping took place
//Every motor,servo and colour sensor was set up here

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareReyBot {
    /* Public OpMode members. */
    public DcMotor  leftDriveFront   = null;
    public DcMotor  rightDriveFront  = null;
    public DcMotor  leftIntake       = null;
    public DcMotor  rightIntake      = null;
    public DcMotor  leftExit         = null;
    public DcMotor  rightExit        = null;
    public DcMotor  elevator         = null;
    public Servo    leftServo        = null;
    public Servo    rightServo       = null;
    public Servo    blockServo       = null;
    public CRServo  capstone         = null;
    public Servo    fan              = null;
    public ColorSensor CSensorLeft   = null;
    public ColorSensor CSensorRight  = null;
    public ColorSensor CSensorBottom = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareReyBot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDriveFront = hwMap.get(DcMotor.class, "leftDriveFront");
        rightDriveFront = hwMap.get(DcMotor.class, "rightDriveFront");
        leftIntake = hwMap.get(DcMotor.class, "leftIntake");
        rightIntake = hwMap.get(DcMotor.class, "rightIntake");
        leftExit = hwMap.get(DcMotor.class, "leftExit");
        rightExit = hwMap.get(DcMotor.class, "rightExit");
        elevator = hwMap.get(DcMotor.class, "elevator");
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftDriveFront.setPower(0);
        rightDriveFront.setPower(0);
        leftIntake.setPower(0);
        rightIntake.setPower(0);
        leftExit.setPower(0);
        rightExit.setPower(0);
        elevator.setPower(0);

        // SetUp Motors
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightExit.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftExit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Define and initialize ALL installed servos.
        leftServo = hwMap.get(Servo.class, "leftServo");
        rightServo = hwMap.get(Servo.class, "rightServo");
        blockServo = hwMap.get(Servo.class, "blockServo");
        capstone = hwMap.get(CRServo.class, "captstone");
        fan = hwMap.get(Servo.class, "fan");
        leftServo.setPosition(1);
        rightServo.setPosition(0);
        blockServo.setPosition(0);

        //Define and initialize ALL installed colourSensors
        CSensorLeft = hwMap.get(ColorSensor.class, "leftIntake_sensor_colour");
        CSensorBottom = hwMap.get(ColorSensor.class, "sensor_colour_bottom");
        CSensorRight = hwMap.get(ColorSensor.class, "rightIntake_sensor_colour");
    }
}
