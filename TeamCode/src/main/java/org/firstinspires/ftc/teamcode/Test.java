package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Test", group = "team")

public class Test extends LinearOpMode {

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor Launcher;
    DcMotor Pickup;
    //DcMotor Angler;


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

        Launcher = hardwareMap.dcMotor.get("L");
        Launcher.setDirection(DcMotorSimple.Direction.FORWARD);

        Pickup =hardwareMap.dcMotor.get("P");
        Pickup.setDirection(DcMotorSimple.Direction.FORWARD);

        //Angler = hardwareMap.dcMotor.get("A");
        //Angler.setDirection(DcMotorSimple.Direction.FORWARD);

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Pickup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Angler.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TopLeft.setTargetPosition(2250);
        TopRight.setTargetPosition(1800);
        BackLeft.setTargetPosition(1800);
        BackRight.setTargetPosition(2250);
        Launcher.setTargetPosition(5000);
        Pickup.setTargetPosition(5000);
        //Angler.setTargetPosition(5000);
        //1 Tetrix DC motor 60:1 revolution = 1440 encoder ticks

        waitForStart();
        while (opModeIsActive()) {

            if (currentstep == 0) {
                currentstep++;
            }

            if (currentstep == 1) {
                telemetry.addData("inside currentstep 1", "");
                telemetry.update();
                //Deliver first wobble to box A
                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
                //Drive backwards to wall
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
                BackLeft.setTargetPosition(0);
                BackRight.setTargetPosition(50);

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
                //Drive left, line up with second wobble
                telemetry.addData("inside currentstep 3", "");
                telemetry.update();

                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(900);
                TopRight.setTargetPosition(900);
                BackLeft.setTargetPosition(900);
                BackRight.setTargetPosition(900);

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
                //Drive forward and deliver second wobble to box B
                telemetry.addData("inside currentstep 4", "");
                telemetry.update();

                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setTargetPosition(2900);
                TopRight.setTargetPosition(2300);
                BackLeft.setTargetPosition(2300);
                BackRight.setTargetPosition(2900);

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

            if (currentstep == 5) {
                //Drive back behind launch line
                telemetry.addData("inside currentstep 5", "");
                telemetry.update();

                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(300);
                TopRight.setTargetPosition(300);
                BackLeft.setTargetPosition(300);
                BackRight.setTargetPosition(300);

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

            if (currentstep == 6) {
                //Drive left, line up with first power shot peg
                telemetry.addData("inside currentstep 6", "");
                telemetry.update();

                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(1000);
                TopRight.setTargetPosition(1000);
                BackLeft.setTargetPosition(1000);
                BackRight.setTargetPosition(1000);

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

            if (currentstep == 7) {
                //Launch rings at power shots
                telemetry.addData("inside currentstep 7", "");
                telemetry.update();
                for (int i = 0; i < 4 && opModeIsActive(); i++) {
                    telemetry.addData("Loop", i);
                    telemetry.update();
                    //reset encoder every loop
                    Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    //Launch 1 ring
                    Launcher.setTargetPosition(300);
                    Launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    Launcher.setPower(1);
                    while (opModeIsActive() && Launcher.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                    {
                        telemetry.addData("encoder-fwd-left", Launcher.getCurrentPosition() + "  busy=" + Launcher.isBusy());
                       telemetry.addData("encoder-fwd-right", Launcher.getCurrentPosition() + "  busy=" + Launcher.isBusy());
                        telemetry.update();
                       idle();
                    }
                    //stop
                   Launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    Launcher.setPower(0);
                    //Move left
                    TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                    TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                    BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                    BackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                    TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    TopLeft.setTargetPosition(500);
                    TopRight.setTargetPosition(500);
                    BackLeft.setTargetPosition(500);
                    BackRight.setTargetPosition(500);

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

                    telemetry.addData("end of loop", "");
                    telemetry.update();

                }
                currentstep++;
            }

            if (currentstep == 8) {
                //Drive forward and park on launch line
                telemetry.addData("inside currentstep 8", "");
                telemetry.update();

                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

                TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TopLeft.setPower(0.125);
                TopRight.setPower(0.125);
                BackLeft.setPower(0.125);
                BackRight.setPower(0.125);

                TopLeft.setTargetPosition(200);
                TopRight.setTargetPosition(200);
                BackLeft.setTargetPosition(200);
                BackRight.setTargetPosition(200);

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

//test JRS 10/13/20