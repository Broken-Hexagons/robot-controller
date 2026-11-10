package org.firstinspires.ftc.team20150;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class BaseTeleOpMode extends OpMode {

    private static final double STEER_COMPENSATION = 0.025;
    private static final double NORMAL_POWER_SCALE = 0.5;
    private static final double FAST_POWER_SCALE = 0.75; //when left bumper is held.
    private static final double SLOW_POWER_SCALE = 0.25; //when right bumper is held.

    // Declare OpMode members
    protected DriveTrain driveTrain;
    protected int sectorCount = 4;

    /*
     * Code to run ONCE when the driver hits INIT
     */

    @Override
    public void init() {
        //Initialize the hardware variables.
        driveTrain = new DriveTrain(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY.
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY.
     */
    //@Override

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    private double stickToDegrees(double x, double y) {
        return Math.toDegrees(Math.atan2(x, -y));
    }

    private double snapAngle(double angleDegrees, double sectorCount) {
        return Math.round(angleDegrees / (360.0 / sectorCount)) * (360.0 / sectorCount);
    }

    private double degreesToStickX(double angleDegrees, double magnitude) {
        return Math.sin(Math.toRadians(angleDegrees)) * magnitude;
    }

    private double degreesToStickY(double angleDegrees, double magnitude) {
        return -Math.cos(Math.toRadians(angleDegrees)) * magnitude;
    }

    private double stickToMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public double computeDriveSpeed() {
        return gamepad1.right_bumper ? SLOW_POWER_SCALE : gamepad1.left_bumper ? FAST_POWER_SCALE : NORMAL_POWER_SCALE;
    }

    @Override
    public void loop() {

        final double magnitude = stickToMagnitude(gamepad1.left_stick_x, gamepad1.left_stick_y);
        final double angleDegrees = stickToDegrees(gamepad1.left_stick_x, gamepad1.left_stick_y);
        final double snapDegrees = snapAngle(angleDegrees, sectorCount);
        final double snappedX = degreesToStickX(snapDegrees, magnitude);
        final double snappedY = degreesToStickY(snapDegrees, magnitude);

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate
        double axial = -snappedY * (1 + STEER_COMPENSATION * Math.abs(gamepad1.right_stick_x));
        double lateral = snappedX;
        double yaw = gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;

        // Scale down together if any wheel is over 1 so the direction stays the same.
        double max = Math.max(1.0, Math.max(Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower))));
        double cPowerScale = computeDriveSpeed() / max;

        // Send calculated power to wheels
        driveTrain.setPower(leftFrontPower * cPowerScale, rightFrontPower * cPowerScale,
                leftBackPower * cPowerScale, rightBackPower * cPowerScale);
    }

    /*
     * Code to run ONCE after the driver hits STOP.
     */
    @Override
    public void stop() {
    }
}
