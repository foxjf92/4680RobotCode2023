package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.Num;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends CommandBase {
    private static final double Climb_Speed = 1.0;
    private ElevatorSubsystem Elevator;
    private int Num = 1;
    private Timer timer = new Timer();
    
    
    

    public ElevatorCommand(ElevatorSubsystem elevator) {
        this.Elevator = elevator;
        addRequirements(Elevator);
        }
        

    @Override
    public void initialize(){
        timer.reset();
        Elevator.errorSum = 0;
        Elevator.lastTimestamp = Timer.getFPGATimestamp();
        Elevator.lastError = 0;
       
    }
    

    @Override
    public void execute() {

         if(RobotContainer.shootController.getAButtonPressed()){
            Elevator.setpoint = 2;
        } else if(RobotContainer.shootController.getBButtonPressed()){
            Elevator.setpoint = 0;
        }

        double sensorPosition = Elevator.encoder.getPosition() * Elevator.kDriveTick2Feet;

        // calculations
        double error = Elevator.setpoint - sensorPosition;
        double dt = Timer.getFPGATimestamp() - Elevator.lastTimestamp;

        if(Math.abs(error) < Elevator.iLimit){
            Elevator.errorSum += error * dt;
        }

        double errorRate = (error - Elevator.lastError) / dt;
    
        double outputSpeed = Elevator.kP * error + Elevator.kI * Elevator.errorSum + Elevator.kD * errorRate;
        Elevator.move(outputSpeed);
        Elevator.lastTimestamp = Timer.getFPGATimestamp();
        Elevator.lastError = error;
        SmartDashboard.putNumber("Encoder value", Elevator.encoder.getPosition() * Elevator.kDriveTick2Feet);
 
     /*   if(RobotContainer.shootController.getBButtonPressed()){
            if(Num == 3){
                Elevator.moveDown(Climb_Speed);
                timer.start();
            
                if (timer.hasElapsed(2.0)) {
                    Elevator.stop();
                    timer.stop();
                    timer.reset();
                }
                Num = 1;
            } if(Num == 2){
            Elevator.moveDown(Climb_Speed);
          
            timer.start();
            
            if (timer.hasElapsed(1.0)) {
                Elevator.stop();
                timer.stop();
                timer.reset();
            }
                
                Num = 1;
            }

            
        }  if(RobotContainer.shootController.getAButtonPressed()){
            if(Num == 3){
                Elevator.moveDown(Climb_Speed);
            timer.start();
            
            if (timer.hasElapsed(1.0)) {
                Elevator.stop();
                timer.stop();
                timer.reset();
            }
                Num = 2;
            } if(Num == 1){
            Elevator.moveUp(Climb_Speed);
            timer.start();
            
            if (timer.hasElapsed(1.0)) {
                Elevator.stop();
                timer.stop();
                timer.reset();
            }
            //FIXME: the timer doesnt really work, after 1 second has passed, all the other commands with a scheduler wont work
            //need to find a way to implement a new timer so it only spins for a certain amount of time
            Num = 2;
        }
        }
        if(RobotContainer.shootController.getXButtonPressed()){
            if(Num == 1){
                Elevator.moveUp(Climb_Speed);
                timer.start();
            
                if (timer.hasElapsed(2.0)) {
                    Elevator.stop();
                    timer.stop();
                    timer.reset();
                }
                Num = 3;
            } if(Num == 2){
            Elevator.moveUp(Climb_Speed);
            timer.start();
            
            if (timer.hasElapsed(1.0)) {
                Elevator.stop();
                timer.stop();
                timer.reset();
            }
            Num = 3;
        }
        }
    

    if (timer.hasElapsed(1)) {
        timer.reset();
    }
    */
    
}

@Override
public boolean isFinished(){
return false;
}
}