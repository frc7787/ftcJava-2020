//Autonomous Phase for the Blue Alliance
//This code was all done through timings

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Move Base Straight Blue")
public class Move_Base_Straight_Blue extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftDriveFront, rightDriveFront;
        Servo leftServo, rightServo;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDriveFront = hardwareMap.dcMotor.get("leftDriveFront");
        rightDriveFront = hardwareMap.dcMotor.get("rightDriveFront");

        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        //Wait for telemetry
        sleep(500);

        //Go Backwards for 2.1 seconds
        leftDriveFront.setPower(-0.25);
        rightDriveFront.setPower(-0.25);

        sleep(2800);

        //Stop for a second
        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        sleep(500);

        //Grab the base
        leftServo.setPosition(0.4);
        rightServo.setPosition(0.5);

        sleep(1000);

        //Move Forward
        leftDriveFront.setPower(0.50);
        rightDriveFront.setPower(0.50);

        sleep(1100);

        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        sleep(500);

        //Go forward with a curve
        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.75);

        sleep(2000);

        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        sleep(500);

        //Go Backwards for 1 seconds
        leftDriveFront.setPower(-0.75);
        rightDriveFront.setPower(-0.75);

        sleep(1000);

        //Stop and release base for a second
        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        leftServo.setPosition(1);
        rightServo.setPosition(0);

        sleep(1000);

        //Stop for a second
        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        sleep(1000);

        //Move towards the line
        leftDriveFront.setPower(0.25);
        rightDriveFront.setPower(0.25);

        sleep(2800);

        //Stop for a second
        leftDriveFront.setPower(0.0);
        rightDriveFront.setPower(0.0);

        sleep(1000);
    }
}