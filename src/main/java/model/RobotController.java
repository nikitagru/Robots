package model;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.io.IOException;

public class RobotController {

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;
    private Robot robot = new Robot();

    public Robot getRobot() {
        return robot;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }

    public double distanceToTarget()
    {
        double diffX = targetPositionX - robot.getRobotPositionX();
        double diffY = targetPositionY - robot.getRobotPositionY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private void moveRobot(int[][] level, int linkSize, int startPositionX, int startPositionY)
    {
        Vector2d way = new Vector2d(targetPositionX - robot.getRobotPositionX(),
                targetPositionY - robot.getRobotPositionY());
        way.normalize();

        double dirX = robot.getRobotPositionX() + way.x * robot.getVelocity();
        double dirY = robot.getRobotPositionY() + way.y * robot.getVelocity();

        if (level[(int)((dirY - startPositionY) / linkSize)][(int)((dirX - startPositionX + 7) / linkSize)] != 1) {
            robot.setRobotPositionX(dirX);
            robot.setRobotPositionY(dirY);
        }

    }

    public void updateRobot(int[][] level, int linkSize, int startPositionX, int startPositionY) {
        double distance = distanceToTarget();
        if (distance < 10.0)
        {
            return;
        }

        moveRobot(level, linkSize, startPositionX, startPositionY);
    }

    public void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    public boolean isFinished(int finishX, int finishY, int linkSize) {
        if (robot.getRobotPositionX() <= finishX + linkSize
                && robot.getRobotPositionX() >= finishX - linkSize
                && robot.getRobotPositionY() <= finishY + linkSize
                && robot.getRobotPositionY() >= finishY - linkSize) {
            return true;
        }
        return false;
    }

    public void changeRobotDirection() {
        if (robot.getRobotPositionX()
                > targetPositionX) {
            robot.setRobotDirection(1);
        } else {
            robot.setRobotDirection(0);
        }
    }
}
