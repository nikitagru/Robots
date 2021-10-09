package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class LevelPresenter extends JPanel {
    private int currentFrame = 0;

    private LevelController levelController;

    private int[][] level;

    private JFrame mainWindow;

    
    Timer timerMap = new Timer(10, e -> onRedrawEvent());

    Timer timer = new Timer(10, e -> {
        try {
            onModelUpdateEvent();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    });


    public LevelPresenter(JFrame frame, LevelController levelController) throws IOException {
        level = levelController.getGameLevels().getLevelArray(levelController.getLevelNum());
        mainWindow = frame;
        this.levelController = levelController;

        timer.start();
        timerMap.start();
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                levelController.getRobotController().setTargetPosition(e.getPoint());
            }
        });

        setDoubleBuffered(true);
    }

    public int[][] getLevel() {
        return level;
    }

    public void setLevelPoint(int x, int y, int value) {
        level[x][y] = value;
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawLevel(g);
        drawRobot(g);
        Graphics2D graphics2D = (Graphics2D)g;
        drawTarget(graphics2D, levelController.getRobotController().getTargetPositionX(), levelController.getRobotController().getTargetPositionY());
        levelController.changeLevel(this);
    }

    public void drawLevel(Graphics g) {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                if (level[i][j] == 3) {
                    drawFinish(g, j * levelController.getLinkSize() + levelController.getLevelGapX(),
                            i * levelController.getLinkSize() + levelController.getLevelGapY());
                } else {
                    levelController.drawComponent(this, i, j);
                }
            }
        }
    }

    private void drawFinish(Graphics g, int x, int y) {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.drawRect(x, y, 32, 32);
        graphics2D.setColor(Color.ORANGE);
        graphics2D.fillRect(x, y, 32, 32);
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

    private void onModelUpdateEvent() throws IOException {
        levelController.getRobotController().updateRobot(level, levelController.getLinkSize(),
                levelController.getLevelGapX(), levelController.getLevelGapY());
    }

    public void drawRobot(Graphics g) {
        levelController.changeRobotDirection();
        double distance = levelController.getRobotController().distanceToTarget();

        if (distance >= 10.0) {
            if (currentFrame == 14) {
                currentFrame = 0;
            }
            RobotImage robotImage = new RobotImage(currentFrame * 16,
                    (int)levelController.getRobotController().getRobot().getRobotPositionX(),
                    (int)levelController.getRobotController().getRobot().getRobotPositionY(),
                    (int)levelController.getRobotController().getRobot().getRobotDirection());
            robotImage.paintComponents(g);
            currentFrame++;
        } else {
            RobotImage robotImage = new RobotImage(0,
                    (int)levelController.getRobotController().getRobot().getRobotPositionX(),
                    (int)levelController.getRobotController().getRobot().getRobotPositionY(),
                    (int)levelController.getRobotController().getRobot().getRobotDirection());
            robotImage.paintComponents(g);
        }

    }

}
