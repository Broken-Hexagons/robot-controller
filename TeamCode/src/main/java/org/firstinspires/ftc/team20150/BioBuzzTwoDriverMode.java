package org.firstinspires.ftc.team20150;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz 2 Driver TeleOp")
public class BioBuzzTwoDriverMode extends BaseTeleOpMode {

    private final String robotCaption = "Robot";
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
        if(gamepad1.squareWasPressed()){
            this.stop();
        }

        // Shoot
        if(gamepad2.crossWasPressed()){
            shooter.openGatekeeper();
        }
        if(gamepad2.crossWasReleased()){
            shooter.closeGatekeeper();
        }

        // intake start and stop
        if(gamepad2.circleWasPressed()){
            intake.run();
        }
        if(gamepad2.squareWasPressed()){
            intake.stop();
        }

        // intake reverse
        if(gamepad2.triangleWasPressed()){
            intake.reverse();
        }
        if(gamepad2.triangleWasReleased()){
            intake.run();
        }

        if(gamepad2.right_trigger > 0.75){
            intake.shortReverse();
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
            //Control Shooter speed using presets
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
