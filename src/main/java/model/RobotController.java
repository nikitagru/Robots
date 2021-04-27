package model;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.io.IOException;

public class RobotController {

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;
    private Robot robot = new Robot();

    public RobotController() throws IOException {
    }


    public Robot getRobot() {
        return robot;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }

    public static double distance(double targetX, double targetY, double robotX, double robotY)
    {
        double diffX = targetX - robotX;
        double diffY = targetY - robotY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    private void moveRobot()
    {
        Vector2d way = new Vector2d(targetPositionX - robot.getRobotPositionX(),
                targetPositionY - robot.getRobotPositionY());
        way.normalize();

        robot.setRobotPositionX(robot.getRobotPositionX() + way.x * robot.getVelocity());
        robot.setRobotPositionY(robot.getRobotPositionY() + way.y * robot.getVelocity());

    }

    public void updateRobot() {
        double distance = distance(targetPositionX, targetPositionY,
                robot.getRobotPositionX(), robot.getRobotPositionY());
        if (distance < 0.5)
        {
            return;
        }
//        double angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(), targetPositionX, targetPositionY);
//        double angularVelocity = 0;
//        if (angleToTarget > robot.getRobotDirection())
//        {
//            angularVelocity = robot.getMaxAngularVelocity();
//        }
//        if (angleToTarget < robot.getRobotDirection())
//        {
//            angularVelocity = -(robot.getMaxAngularVelocity());
//        }
//        double newDirection = asNormalizedRadians(robot.getRobotDirection() + robot.getMaxAngularVelocity() * 100);
//        robot.setRobotDirection(newDirection);

        moveRobot();
    }

    public void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }
}
