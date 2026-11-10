package org.firstinspires.ftc.team20150;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz 2 Driver TeleOp")
public class BioBuzzTwoDriverMode extends BaseTeleOpMode {

    private static final String robotCaption = "Robot";
    private Shooter shooter;
    private Intake intake;

    @Override
    public void init(){
        super.init();
        shooter = new Shooter(hardwareMap,telemetry);
        intake = new Intake(hardwareMap, telemetry);
        telemetry.addData(robotCaption,"Happy");
        telemetry.speak("Initialized");
    }

    @Override
    public void start() {
        super.start();
        shooter.nextFlywheelMode();
        intake.run();
        telemetry.speak("Started");
    }

    @Override
    public void loop(){
        super.loop();

        //Emergency stop
        if(gamepad1.xWasPressed()){
            shooter.stop();
        }

        // Release pollen
        if(gamepad2.aWasPressed()){
            shooter.openGatekeeper();
        }
        if(gamepad2.aWasReleased()){
            shooter.closeGatekeeper();
        }

        //intake control
        if(gamepad2.yWasPressed()){
            intake.reverse();

        }
        if(gamepad2.yWasReleased()){
            intake.run();
        }

        if(gamepad2.right_bumper) {
            // Control Shooter speed by increment
            if (gamepad2.dpadUpWasPressed()) {
                shooter.increaseFlywheelSpeed();
            }
            if (gamepad2.dpadDownWasPressed()) {
                shooter.decreaseFlywheelSpeed();
            }
        }else{
            //Control Shooter speed using presets.
            if (gamepad2.dpadUpWasPressed()) {
                shooter.nextFlywheelMode();
            }
            if (gamepad2.dpadDownWasPressed()) {
                shooter.previousFlywheelMode();
            }
        }
        shooter.updateTelemetry();
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        shooter.stop();
        intake.stop();
        telemetry.addData(robotCaption, "Stopped.");
    }
}
