package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ScrimmageAuto", group = "team")

public class FY21ScrimmageAuto extends LinearOpMode{

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;


    int currentstep = 0;

    public void runOpMode() {


        TopLeft = hardwareMap.dcMotor.get("TL");
        TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        //   TopLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        TopRight = hardwareMap.dcMotor.get("TR");
        TopRight.setDirection(DcMotorSimple.Direction.REVERSE);

        BackRight = hardwareMap.dcMotor.get("BR");
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeft = hardwareMap.dcMotor.get("BL");
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TopLeft.setTargetPosition(50000);
        TopRight.setTargetPosition(50000);
        BackLeft.setTargetPosition(50000);
        BackRight.setTargetPosition(50000);

        waitForStart();
        while (opModeIsActive()) {

        if (currentstep == 0) {
            currentstep++;
        }

        if (currentstep == 1) {
            telemetry.addData("inside currentstep 1", "");
            telemetry.update();
            //Drive forward up to launch line without crossing
            TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            TopLeft.setPower(0.5);
            TopRight.setPower(-0.5);
            BackLeft.setPower(0.5);
            BackRight.setPower(-0.5);
            //stop
            currentstep ++;
        }

        if (currentstep == 2) {
            telemetry.addData("inside currentstep 2", "");
            telemetry.update();
            //Drive onto launch line
            TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            TopLeft.setTargetPosition(70000);
            TopRight.setTargetPosition(70000);
            BackLeft.setTargetPosition(70000);
            BackRight.setTargetPosition(70000);

            TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            TopLeft.setPower(0.5);
            TopRight.setPower(-0.5);
            BackLeft.setPower(0.5);
            BackRight.setPower(-0.5);
            //stop
            TopLeft.setPower(0);
            TopRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
            currentstep++;
        }

        if (currentstep==3){
            telemetry.addData("inside currentstep 1", "");
            telemetry.update();
            TopLeft.setMode(RunMode.RUN_WITHOUT_ENCODER);

            TopRight.setMode(RunMode.RUN_WITHOUT_ENCODER);

            BackLeft.setMode(RunMode.RUN_WITHOUT_ENCODER);

            BackRight.setMode(RunMode.RUN_WITHOUT_ENCODER);

            TopLeft.setPower(0.5);
            TopRight.setPower(-0.5);
            BackLeft.setPower(0.5);
            BackRight.setPower(-0.5);

            sleep(100);
//stop
            TopLeft.setPower(0);
            TopRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
            currentstep++;
        }

    }

    }}

