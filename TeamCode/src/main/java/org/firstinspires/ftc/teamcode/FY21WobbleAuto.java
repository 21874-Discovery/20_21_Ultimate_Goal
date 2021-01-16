package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoWobble", group = "team")

public class FY21WobbleAuto extends LinearOpMode {

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

        TopLeft.setTargetPosition(2250);
        TopRight.setTargetPosition(2200);
        BackLeft.setTargetPosition(2250);
        BackRight.setTargetPosition(2200);
        //1 Tetrix DC motor revolution = 1440 encoder ticks

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

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                {
                    telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
                    telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
                    telemetry.update();
                    idle();
                }
                //stop
                TopLeft.setPower(0);
                TopRight.setPower(0);
                BackLeft.setPower(0);
                BackRight.setPower(0);

                telemetry.addData("end of currentstep 1", "");
                telemetry.update();
                currentstep++;
            }

            if (currentstep == 2) {
                telemetry.addData("inside currentstep 2", "");
                telemetry.update();

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(50);
                TopRight.setTargetPosition(0);
                BackLeft.setTargetPosition(50);
                BackRight.setTargetPosition(0);

                while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                {
                    telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
                    telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
                    telemetry.update();
                    idle();
                }

                //stop
                TopLeft.setPower(0);
                TopRight.setPower(0);
                BackLeft.setPower(0);
                BackRight.setPower(0);
                currentstep++;
            }

            if (currentstep == 3) {
                telemetry.addData("inside currentstep 3", "");
                telemetry.update();
                //Drive forward up to launch line without crossing
                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(-400);
                TopRight.setTargetPosition(-400);
                BackLeft.setTargetPosition(400);
                BackRight.setTargetPosition(400);

                while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                {
                    telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
                    telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
                    telemetry.update();
                    idle();
                }
                //stop
                TopLeft.setPower(0);
                TopRight.setPower(0);
                BackLeft.setPower(0);
                BackRight.setPower(0);

                currentstep++;
            }

            if (currentstep == 4) {
                telemetry.addData("inside currentstep 4", "");
                telemetry.update();
                //Drive forward up to launch line without crossing
                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setTargetPosition(2740);
                TopRight.setTargetPosition(2700);
                BackLeft.setTargetPosition(2740);
                BackRight.setTargetPosition(2700);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                {
                    telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
                    telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
                    telemetry.update();
                    idle();
                }
                //stop
                TopLeft.setPower(0);
                TopRight.setPower(0);
                BackLeft.setPower(0);
                BackRight.setPower(0);

                currentstep++;
            }

        }

    }
}

