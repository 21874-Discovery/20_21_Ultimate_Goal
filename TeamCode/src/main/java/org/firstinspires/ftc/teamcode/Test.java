package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Test", group = "team")

public class Test extends LinearOpMode {

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor Launcher;
    DcMotor Pickup; //intakes rings from playing field
    Servo Flick; //flicks rings from the intake to the launcher

    double Flick_Power;
    public final static double ARM_HOME = 0.6; //sets the starting position for the servo. it will go to this position when robot starts
    public final static double ARM_MIN_RANGE = 0;
    public final static double ARM_MAX_RANGE = 1;
    double FlickPosition = ARM_HOME;

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
        Launcher.setDirection(DcMotorSimple.Direction.REVERSE);
        Pickup =hardwareMap.dcMotor.get("P");
        Pickup.setDirection(DcMotorSimple.Direction.FORWARD);

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Flick = hardwareMap.servo.get("F");
        Flick.setDirection(Servo.Direction.FORWARD);
        Flick_Power = 0.5;
        Flick.setPosition(ARM_HOME);

        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Pickup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TopLeft.setTargetPosition(2250);
        TopRight.setTargetPosition(1800);
        BackLeft.setTargetPosition(1800);
        BackRight.setTargetPosition(2250);
        Launcher.setTargetPosition(5000);
        Pickup.setTargetPosition(5000);
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

                TopLeft.setPower(0.25);
                TopRight.setPower(0.25);
                BackLeft.setPower(0.25);
                BackRight.setPower(0.25);

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

                TopLeft.setPower(0.25);
                TopRight.setPower(0.25);
                BackLeft.setPower(0.25);
                BackRight.setPower(0.25);

                TopLeft.setTargetPosition(750);
                TopRight.setTargetPosition(750);
                BackLeft.setTargetPosition(750);
                BackRight.setTargetPosition(750);

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

                TopLeft.setTargetPosition(3000);
                TopRight.setTargetPosition(1800);
                BackLeft.setTargetPosition(1800);
                BackRight.setTargetPosition(3000);

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

                TopLeft.setPower(0.25);
                TopRight.setPower(0.25);
                BackLeft.setPower(0.25);
                BackRight.setPower(0.25);

                TopLeft.setTargetPosition(500);
                TopRight.setTargetPosition(500);
                BackLeft.setTargetPosition(500);
                BackRight.setTargetPosition(500);

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

                TopLeft.setTargetPosition(700);
                TopRight.setTargetPosition(700);
                BackLeft.setTargetPosition(700);
                BackRight.setTargetPosition(700);

                while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                {
                    telemetry.addData("6 encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
                    telemetry.addData("6 encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
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
                //turn on launcher and run until step 9
                telemetry.addData("inside currentstep 7", "");
                telemetry.update();
                Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                Launcher.setPower(1);
                sleep(500);

                currentstep ++;
            }

            if (currentstep == 8) {
                //Launch rings at power shots
                //We need to change this code to use the 360 servo instead of the Launcher motor to launch the rings three times.
                //The 3 start rings will be stacked and we will slide each ring forward using the servo to feed the launcher.
                //Need to find 360 servo code!  this could be listed as CR Servo
                telemetry.addData("inside currentstep 8", "");
                telemetry.update();
                for (int i = 0; i < 4 && opModeIsActive(); i++) {
                    telemetry.addData("Loop", i);
                    telemetry.update();
                    //reset encoder every loop
                    //Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    //Launch 1 ring
                    //Launcher.setTargetPosition(400);
                    //Launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //Launcher.setPower(1);
                    /*while (opModeIsActive() && Launcher.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
                    {
                        telemetry.addData("8 encoder-fwd-left", Launcher.getCurrentPosition() + "  busy=" + Launcher.isBusy());
                        telemetry.addData("8 encoder-fwd-right", Launcher.getCurrentPosition() + "  busy=" + Launcher.isBusy());
                        telemetry.update();
                       idle();
                    }
                    //stop*/
                    //the servo flicks a ring from the stack forward to the launcher then resets so it can flick another ring
                    Flick.getController().pwmEnable();
                    //CR2.getController().pwmEnable();
                    FlickPosition = (FlickPosition-.4);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);
                    //Flick.setPower(1);
                    sleep (500);
                    //CR2.setPower(0);
                    FlickPosition = (FlickPosition+.4);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);;
                    Flick.getController().pwmDisable();
                    //CR2.getController().pwmDisable();



                   //Launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //Launcher.setPower(0);
                    //Move left
                    TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                    TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                    BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                    BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

                    TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    TopLeft.setTargetPosition(25);
                    TopRight.setTargetPosition(25);
                    BackLeft.setTargetPosition(25);
                    BackRight.setTargetPosition(25);

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

            if (currentstep == 9) {
                //turn off launcher
                telemetry.addData("inside currentstep 9", "");
                telemetry.update();
                Launcher.setPower(0);

                currentstep ++;
            }

            if (currentstep == 10) {
                //Drive forward and park on launch line
                telemetry.addData("inside currentstep 10", "");
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