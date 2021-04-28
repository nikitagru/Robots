package gui;

import model.RobotController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.TimerTask;

public class Level extends JPanel {

    private RobotController robotController = new RobotController();
    private int currentFrame = 0;
    private int startPositionX = 650;
    private int startPositionY = 300;
    private int linkSize = 32;
    
    Timer timerMap = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            onRedrawEvent();
        }
    });

    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            onModelUpdateEvent();
        }
    });



    public Level() throws IOException {
//        timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                onRedrawEvent();
//            }
//        }, 0, 10);
//        timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//
//            }
//        }, 0, 10);
        timer.start();
        timerMap.start();
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                robotController.setTargetPosition(e.getPoint());
            }
        });
        setDoubleBuffered(true);
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
        drawLevel(g);
        drawRobot(g);
        Graphics2D graphics2D = (Graphics2D)g;
        drawTarget(graphics2D, robotController.getTargetPositionX(), robotController.getTargetPositionY());
    }

    public void drawLevel(Graphics g) {
        for (int i = 0 ; i < level1.length; i++) {
            for (int j = 0; j < level1[0].length; j++) {
                if (level1[i][j] == 1) {
                    Wall wall = new Wall(j * 32 + startPositionX, i * 32 + startPositionY);
                    wall.paintComponent(g);
                } else if (level1[i][j] == 2) {
                    robotController.getRobot().setRobotPositionX(j * 32 + startPositionX);
                    robotController.getRobot().setRobotPositionY(i * 32 + startPositionY);
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
        robotController.updateRobot(level1, linkSize, startPositionX, startPositionY);
    }

    public void drawRobot(Graphics g) {
        if (robotController.getRobot().getRobotPositionX() > robotController.getTargetPositionX()) {
            robotController.getRobot().setRobotDirection(1);
        } else {
            robotController.getRobot().setRobotDirection(0);
        }

        double distance = robotController.distance(robotController.getTargetPositionX(),
                robotController.getTargetPositionY(),
                robotController.getRobot().getRobotPositionX(),
                robotController.getRobot().getRobotPositionY());

        if (distance >= 100.0) {
            if (currentFrame == 14) {
                currentFrame = 0;
            }
            RobotImage robotImage = new RobotImage(currentFrame * 16,
                    (int)robotController.getRobot().getRobotPositionX(),
                    (int)robotController.getRobot().getRobotPositionY(),
                    (int)robotController.getRobot().getRobotDirection());
            robotImage.paintComponents(g);
            currentFrame++;
        } else {
            RobotImage robotImage = new RobotImage(0,
                    (int)robotController.getRobot().getRobotPositionX(),
                    (int)robotController.getRobot().getRobotPositionY(),
                    (int)robotController.getRobot().getRobotDirection());
            robotImage.paintComponents(g);
        }

    }
}
