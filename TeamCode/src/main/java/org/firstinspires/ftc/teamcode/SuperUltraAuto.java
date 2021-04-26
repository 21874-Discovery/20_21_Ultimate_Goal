package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "UltraAuto", group = "team")

public class SuperUltraAuto extends LinearOpMode {

    OpenCvInternalCamera phoneCam;
    SkystoneDeterminationPipeline pipeline;

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor Launcher;
    DcMotor Pickup; //intakes rings from playing field
    Servo Flick; //flicks rings from the intake to the launcher

    double Flick_Power;
    public final static double ARM_FLICKED = 0.8;
    public final static double ARM_HOME = 0.2; //sets the starting position for the servo. it will go to this position when robot starts
    public final static double ARM_MIN_RANGE = 0;
    public final static double ARM_MAX_RANGE = 1;
    double FlickPosition = ARM_HOME;

    double RobotDiameter = 20; //Max robot size is 18x18 with max diagonal width of 25.46 in)
    //Robot spins in a circle, rough diameter of robot's circle can be no more than 25.42 (diagonal)
    double RobotCircumference = RobotDiameter * 3.14;//Max circumference of Robot (d * pi) = 80 in
    double WheelSize = 4;  //diameter in inches of wheels (the engineers like 4in)
    double WheelCircumference = WheelSize*3.14; //Circumference (d * pi) of wheel (distance wheel travels for 1 rotation)
    double RotationsPerCircle = RobotCircumference/WheelCircumference;// wheel rotations to turns in complete circle

    int DriveTicks = 480;  //1 wheel rotation = DriveTicks - based on motor and gear ratio  => 1 Tetrix DC motor 60:1 revolution = 1440 encoder ticks (20:1 = 480 ticks (divide by 60/20) or 400 ticks = 1 foot)
    //DriveTicks * RotationsPerCircle = 360 degrees
    //Rotations per degree
    int TicksPerDegree = (int) Math.round((DriveTicks * RotationsPerCircle)/360);

    int currentstep = 0;

    String rings;

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

        Flick = hardwareMap.servo.get("F");
        Flick.setDirection(Servo.Direction.FORWARD);
        Flick_Power = 0.2;
        Flick.setPosition(ARM_HOME);

        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Pickup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Pickup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //Launcher.setTargetPosition(5000);
        //Pickup.setTargetPosition(5000);



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new SkystoneDeterminationPipeline();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
        });
        telemetry.addData("Analysis", pipeline.getAnalysis());
        telemetry.addData("Position", pipeline.position);
        telemetry.update();

        // Don't burn CPU cycles busy-looping in this sample
        //sleep(50);
        if(rings==null) {
            rings = String.valueOf(pipeline.position);
        }
        waitForStart();

        while (opModeIsActive()) {


            rings="NONE";
            telemetry.addData("rings:",rings);
            telemetry.update();
            switch (rings) {
                case "NONE":
                    telemetry.addData("case:","NONE");
                    telemetry.update();

                    if (currentstep == 0) {
                        //Deliver wobble to box A
                        telemetry.addData("inside case zero", "");
                        telemetry.update();

                        OmniTurn("Left", 0.25,60);
                        OmniDrive("Forward", 0.125, 2000);

                        currentstep++;
                    }

                    if (currentstep == 1) {
                        //align for power shots

                        OmniTurn("Left", 0.25, 30);
                        OmniDrive("Backward", 0.25, 500);
                        Launcher.setPower(1);
                        OmniDrive("Left", 0.25, 800);

                        currentstep++;
                    }

                    if (currentstep == 2){
                        //Launch at power shots

                        for (int i = 0; i < 3 && opModeIsActive(); i++) {
                            telemetry.addData("Loop", i);
                            telemetry.update();

                            FlickPosition = (ARM_FLICKED);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            //Flick.setPower(1);
                            sleep (500);
                            //CR2.setPower(0);
                            FlickPosition = (ARM_HOME);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            sleep (500);


                            telemetry.addData("end of loop", "");
                            telemetry.update();

                        }

                        currentstep++;
                    }

                    if (currentstep == 3) {
                        //deliver second wobble

                        Launcher.setPower(0);
                        OmniDrive("Backward", 0.25, 1800);
                        OmniDrive("Left", 0.25, 500);
                        OmniTurn("Right", 0.25, 35);
                        OmniDrive("Forward", 0.25, 2000);

                        currentstep++;
                    }

                    if (currentstep == 4) {
                        //park

                        OmniTurn("Left", 0.25, 35);
                        OmniDrive("Backward", 0.25, 100);
                        OmniDrive("Left", 0.25, 400);
                        OmniDrive("Forward", 0.25, 100);

                        currentstep++;
                    }

                case "ONE":
                    telemetry.addData("inside case one", "");
                    telemetry.update();
                    if (currentstep == 0) {
                        //deliver wobble 1 to B

                        OmniTurn("Left", 0.25, 90);
                        OmniDrive("Forward", 0.25, 3000);

                        currentstep++;
                    }

                    if (currentstep == 1) {
                        //align for power shots

                        Launcher.setPower(1);
                        OmniDrive("Backward", 0.25, 1400);

                        currentstep++;
                    }

                    if (currentstep == 2) {
                        //for loop launch rings

                        for (int i = 0; i < 3 && opModeIsActive(); i++) {
                            telemetry.addData("Loop", i);
                            telemetry.update();

                            FlickPosition = (ARM_FLICKED);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            //Flick.setPower(1);
                            sleep (500);
                            //CR2.setPower(0);
                            FlickPosition = (ARM_HOME);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            sleep (500);


                            telemetry.addData("end of loop", "");
                            telemetry.update();

                        }

                        currentstep++;
                    }

                    if (currentstep == 3) {
                        //pickup rings

                        OmniTurn("Left", 0.25, 5);
                        Pickup.setPower(1);
                        OmniDrive("Backward", 0.25, 700);
                        Launcher.setPower(1);
                        sleep(500);

                        currentstep++;
                    }

                    if (currentstep == 4) {
                        //launch rings at goal

                        for (int i = 0; i < 3 && opModeIsActive(); i++) {
                            telemetry.addData("Loop", i);
                            telemetry.update();

                            FlickPosition = (ARM_FLICKED);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            //Flick.setPower(1);
                            sleep (500);
                            //CR2.setPower(0);
                            FlickPosition = (ARM_HOME);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            OmniTurn("Left",0.25,3);
                            sleep (500);


                            telemetry.addData("end of loop", "");
                            telemetry.update();

                        }

                        currentstep++;
                    }

                    if (currentstep == 5) {
                        //deliver second wobble

                        Launcher.setPower(0);
                        Pickup.setPower(0);
                        OmniDrive("Backward", 0.25, 1600);
                        OmniDrive("Left", 0.25, 500);
                        OmniTurn("Right", 0.25, 5);
                        OmniDrive("Forward", 0.25, 3000);

                        currentstep++;
                    }

                    if (currentstep == 6) {
                        //park

                        OmniDrive("Forward", 0.25, 200);

                        currentstep++;
                    }

                case "FOUR":

                    telemetry.addData("inside case four", "");
                    telemetry.update();
                    if (currentstep == 0) {
                        //deliver wobble 1 to C

                        OmniTurn("Left", 0.1, 70);
                        OmniDrive("Forward", 0.25, 3200);

                        currentstep++;
                    }

                    if (currentstep == 1) {
                        //align for power shots

                        OmniTurn("Left", 0.25, 5);
                        OmniDrive("Backward", 0.25, 100);
                        Launcher.setPower(1);
                        OmniDrive("Left", 0.25, 500);
                        OmniDrive("Backward", 0.25, 2000);

                        currentstep++;
                    }

                    if (currentstep == 2) {
                        //for loop

                        for (int i = 0; i < 3 && opModeIsActive(); i++) {
                            telemetry.addData("Loop", i);
                            telemetry.update();

                            FlickPosition = (ARM_FLICKED);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            //Flick.setPower(1);
                            sleep (300);
                            //CR2.setPower(0);
                            FlickPosition = (ARM_HOME);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                           // OmniTurn("Left",0.25,3);
                            sleep (300);


                            telemetry.addData("end of loop", "");
                            telemetry.update();

                        }

                        currentstep++;
                    }

                    if (currentstep == 3) {
                        //pickup

                        Pickup.setPower(1);
                        OmniDrive("Backward", 0.25, 200);

                        currentstep++;
                    }

                    if (currentstep == 4) {
                        //launch

                        for (int i = 0; i < 3 && opModeIsActive(); i++) {
                            telemetry.addData("Loop", i);
                            telemetry.update();

                            FlickPosition = (ARM_FLICKED);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            //Flick.setPower(1);
                            sleep (500);
                            //CR2.setPower(0);
                            FlickPosition = (ARM_HOME);
                            FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                            Flick.setPosition(FlickPosition);
                            sleep (500);


                            telemetry.addData("end of loop", "");
                            telemetry.update();

                        }

                        currentstep++;
                    }

                    if (currentstep == 5) {
                        //deliver woble 2

                        Launcher.setPower(0);
                        Pickup.setPower(0);
                        OmniDrive("Left", 0.25, 1600);
                        OmniDrive("Backward", 0.25, 1000);
                        OmniDrive("Right", 0.25, 800);
                        OmniTurn("Right", 0.25, 20);
                        OmniDrive("Forward", 0.25, 3200);

                        currentstep++;
                    }

                    if (currentstep == 6) {
                        //park

                        OmniDrive("Backward", 0.25, 1200);

                        currentstep ++;
                    }

            }

        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////
    public void OmniDrive(String Dir, double Spd, int Dist) {
        telemetry.addData("Direction", Dir);
        telemetry.update();

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        switch (Dir) {
            case "Forward":
                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case "Backward":
                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case "Left":
                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case "Right":
                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            default:
                telemetry.addData("Invalid Direction", Dir);
                telemetry.update();
                return;
        }
        Dist=Math.abs(Dist);
        TopLeft.setTargetPosition(Dist);
        TopRight.setTargetPosition(Dist);
        BackLeft.setTargetPosition(Dist);
        BackRight.setTargetPosition(Dist);

        TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Spd=Range.clip(Spd, 0, 1);
        TopLeft.setPower(Spd);
        TopRight.setPower(Spd);
        BackLeft.setPower(Spd);
        BackRight.setPower(Spd);



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

    }


    public void OmniTurn(String DirT, double SpdT, int Deg) {
        //Ugh even more Math!!
        //number of ticks for a turn of 1 degree TicksPerDegree
        //if I want to turn more than 1 degree; multiple by number of degrees I want to turn
        //had to convert Rotate to int to use in setTargetPosition below
        int Rotate = (int) Math.round(Deg * TicksPerDegree);
        telemetry.addData("Rotating", Rotate + " ticks or " +Deg + " degrees");
        telemetry.update();

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if(DirT.equals("Left")) {
            TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
            BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
            BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        if(DirT.equals("Right")) {
            TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
            BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
            BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        Rotate=Math.abs(Rotate);
        TopLeft.setTargetPosition(Rotate);
        TopRight.setTargetPosition(Rotate);
        BackLeft.setTargetPosition(Rotate);
        BackRight.setTargetPosition(Rotate);

        TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        SpdT=Range.clip(SpdT, 0, 1);
        TopLeft.setPower(SpdT);
        TopRight.setPower(SpdT);
        BackLeft.setPower(SpdT);
        BackRight.setPower(SpdT);



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

    }
//**********************************************************************************************
    public static class SkystoneDeterminationPipeline extends OpenCvPipeline
    {
        /*
         * An enum to define the skystone position
         */
        public enum RingPosition
        {
            FOUR,
            ONE,
            NONE
        }

        /*
         * Some color constants
         */
        static final Scalar BLUE = new Scalar(0, 0, 255);
        static final Scalar GREEN = new Scalar(0, 255, 0);

        /*
         * The core values which define the location and size of the sample regions
         */
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(220,110);

        static final int REGION_WIDTH = 35;
        static final int REGION_HEIGHT = 25;

        final int FOUR_RING_THRESHOLD = 150;
        final int ONE_RING_THRESHOLD = 135;

        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        /*
         * Working variables
         */
        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1;

        // Volatile since accessed by OpMode thread w/o synchronization
        private volatile SkystoneDeterminationPipeline.RingPosition position = SkystoneDeterminationPipeline.RingPosition.FOUR;

        /*
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Cb channel to the 'Cb' variable
         */
        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 1);
        }

        @Override
        public void init(Mat firstFrame)
        {
            inputToCb(firstFrame);

            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input)
        {
            inputToCb(input);

            avg1 = (int) Core.mean(region1_Cb).val[0];

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            position = SkystoneDeterminationPipeline.RingPosition.FOUR; // Record our analysis
            if(avg1 > FOUR_RING_THRESHOLD){
                position = SkystoneDeterminationPipeline.RingPosition.FOUR;
            }else if (avg1 > ONE_RING_THRESHOLD){
                position = SkystoneDeterminationPipeline.RingPosition.ONE;
            }else{
                position = SkystoneDeterminationPipeline.RingPosition.NONE;
            }

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill

            return input;
        }

        public int getAnalysis()
        {
            return avg1;
        }
    }
}