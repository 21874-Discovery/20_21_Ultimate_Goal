package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "PowerShotAuto", group = "team")

public class FY21Autonomous extends LinearOpMode{

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor Launcher;
    DcMotor Pickup;

    int currentstep = 0;

    public void runOpMode() {

        TopLeft = hardwareMap.dcMotor.get("TL");
        TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        //   TopLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        TopRight = hardwareMap.dcMotor.get("TR");
        TopRight.setDirection(DcMotorSimple.Direction.FORWARD);

        BackRight = hardwareMap.dcMotor.get("BR");
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        BackLeft = hardwareMap.dcMotor.get("BL");
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        Launcher = hardwareMap.dcMotor.get("L");
        Launcher.setDirection(DcMotorSimple.Direction.FORWARD);

        Pickup =hardwareMap.dcMotor.get("P");
        Pickup.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        while (opModeIsActive()) {

            if (currentstep == 0) {
                //Drive forward to launch line
                TopLeft.setPower(0.5); TopRight.setPower(-0.5); BackLeft.setPower(0.5); BackRight.setPower(-0.5);
                sleep(2300);//2200
                //stop
                TopLeft.setPower(0); TopRight.setPower(0); BackLeft.setPower(0); BackRight.setPower(0);
                sleep(500);
                currentstep ++;
            }

            if (currentstep == 1) {
                //Launch rings
                for (int i = 0; i < 3; i++) {
                    //Launch 1 ring
                    Launcher.setPower(1);
                    sleep(500);
                    //stop
                    Launcher.setPower(0);
                    sleep(500);
                    //Move left
                    TopLeft.setPower(-0.5); TopRight.setPower(-0.5); BackLeft.setPower(0.5); BackRight.setPower(0.5);
                    sleep(1000);
                    //stop
                    TopLeft.setPower(0); TopRight.setPower(0); BackLeft.setPower(0); BackRight.setPower(0);
                    sleep(500);
                }
            }

            if (currentstep == 2) {
                //Stop/Park
            }
    }
}}

