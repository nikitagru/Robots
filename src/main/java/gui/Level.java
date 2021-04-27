package gui;

import model.RobotController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Level extends JDialog {

    private final Timer timer = initTimer();
    private RobotController robotController = new RobotController();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public Level() throws IOException {
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 10);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                drawLevel();
            }
        }, 0, 1);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                robotController.setTargetPosition(e.getPoint());
                repaint();
            }
        });
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
        //drawLevel();
    }
    int[][] level1 = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 2, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawRobot();
        Graphics2D graphics2D = (Graphics2D)this.getGraphics();
        drawTarget(graphics2D, robotController.getTargetPositionX(), robotController.getTargetPositionY());
    }

    public void drawLevel() {
        for (int i = 0 ; i < level1.length; i++) {
            for (int j = 0; j < level1[0].length; j++) {
                if (level1[i][j] == 1) {
                    Wall wall = new Wall(j * 32 + 650, i * 32 + 300);
                    wall.paintComponents(this.getGraphics());
                } else if (level1[i][j] == 2) {
                    robotController.getRobot().setRobotPositionX(j * 32 + 650);
                    robotController.getRobot().setRobotPositionY(i * 32 + 300);
                    level1[i][j] = 0;
                }
            }
        }

    }

    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void onModelUpdateEvent()
    {
        robotController.updateRobot();
    }

    public void drawRobot() {
        if (robotController.getRobot().getRobotPositionX() > robotController.getTargetPositionX()) {
            robotController.getRobot().setRobotDirection(1);
        } else {
            robotController.getRobot().setRobotDirection(0);
        }

        Graphics g = this.getGraphics();

        double distance = robotController.distance(robotController.getTargetPositionX(),
                robotController.getTargetPositionY(),
                robotController.getRobot().getRobotPositionX(),
                robotController.getRobot().getRobotPositionY());

        if (distance >= 50.0) {
            for (int i = 0; i < 14; i++) {
                RobotImage robotImage = new RobotImage(i * 16,
                        (int)robotController.getRobot().getRobotPositionX(),
                        (int)robotController.getRobot().getRobotPositionY(),
                        (int)robotController.getRobot().getRobotDirection());
                robotImage.paintComponents(g);
            }
        }

    }
}
