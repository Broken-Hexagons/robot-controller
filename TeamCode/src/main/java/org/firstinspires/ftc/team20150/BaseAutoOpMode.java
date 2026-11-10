package org.firstinspires.ftc.team20150;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class BaseAutoOpMode extends LinearOpMode {

    //Declare OpMode members.
    protected DriveTrain driveTrain;

    protected ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 751.8; // eg: TETRIX motor encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0 / 1.0; // No external gearing
    static final double WHEEL_DIAMETER_INCHES = 3.75; // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double DRIVE_SPEED = 1;
    static final double TURN_SPEED = 0.5;
    static final double TURN_INCHES_PER_DEGREE = 0.194;
    static final long SETTLE_MS = 250;

    protected abstract void runAuto();

    @Override
    public void runOpMode() {
        initHardware();
        waitForStart();
        runAuto();
    }

    protected void initHardware() {
        // Initialize the drive system variables.
        driveTrain = new DriveTrain(hardwareMap);

        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    private boolean allBusy() {
        for (DcMotor motor : driveTrain.motors) {
            if (!motor.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public void encoderDrive(
            double speed,
            double leftFront,
            double rightFront,
            double leftBack,
            double rightBack,
            double timeoutSec) {
        final double[] inches = {leftFront, rightFront, leftBack, rightBack};
        final int[] targets = new int[4];

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            for (int i = 0; i < 4; i++) {
                final DcMotor motor = driveTrain.motors[i];
                targets[i] = motor.getCurrentPosition() + (int) (inches[i] * COUNTS_PER_INCH);
                motor.setTargetPosition(targets[i]);
            }

            // Turn On RUN_TO_POSITION
            driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            driveTrain.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and all
            // motors are running. When ANY motor hits its target position, the motion
            // will stop.
            while (opModeIsActive() && (runtime.seconds() < timeoutSec) && allBusy()) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d %7d %7d %7d", targets[0], targets[1], targets[2], targets[3]);

                telemetry.addData("power", "%4.2f %4.2f %4.2f %4.2f", driveTrain.leftFront.getPower(),
                        driveTrain.rightFront.getPower(), driveTrain.leftBack.getPower(),
                        driveTrain.rightBack.getPower());
                telemetry.update();
            }

            // Stop all motion;
            driveTrain.setPower(0);

            // Turn off RUN_TO_POSITION
            driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(SETTLE_MS); // Pause after each move.
        }
    }

    public void driveForwardInches(double speed, double distanceInches, double timeoutSec) {
        encoderDrive(speed, distanceInches, distanceInches, distanceInches, distanceInches, timeoutSec);
    }

    public void strafeLeftInches(double speed, double distanceInches, double timeoutSec) {
        encoderDrive(speed, -distanceInches, distanceInches, distanceInches, -distanceInches, timeoutSec);
    }

    public void strafeRightInches(double speed, double distanceInches, double timeoutSec) {
        strafeLeftInches(speed, -distanceInches, timeoutSec);
    }

    public void turnLeftDegrees(double speed, double angleDeg, double timeoutSec) {
        final double inches = angleDeg * TURN_INCHES_PER_DEGREE;

        encoderDrive(speed, -inches, inches, -inches, inches, timeoutSec);
    }

    public void turnRightDegrees(double speed, double angleDeg, double timeoutSec) {
        turnLeftDegrees(speed, -angleDeg, timeoutSec);
    }
}
