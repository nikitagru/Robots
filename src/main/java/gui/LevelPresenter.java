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
    private RobotController robotController;

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
        mainWindow = frame;
        this.levelController = levelController;
        this.robotController = new RobotController(levelController.getGameLevels().getCurrentLevel(levelController.getLevelNum()),
                levelController.getLevelGapX(), levelController.getLevelGapY());

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
        drawTarget(graphics2D, robotController.getTargetPositionX(), robotController.getTargetPositionY());
    }

    public void drawLevel(Graphics g) {
        Level level = levelController.getGameLevels().getCurrentLevel(levelController.getLevelNum());
        int[][] levelArray = level.getLevel();
        for (int i = 0; i < levelArray.length; i++) {
            for (int j = 0; j < levelArray[0].length; j++) {
                int levelPoint = level.getLevel()[i][j];
                drawComponent(i, j, levelPoint, g);
            }
        }
    }

    public void drawComponent(int i, int j, int levelPoint, Graphics g) {
        Level level = levelController.getGameLevels().getCurrentLevel(levelController.getLevelNum());
        if (levelPoint == 1) {
            Wall wall = new Wall(j * 32 + levelController.getLevelGapX(),
                    i * 32 + levelController.getLevelGapY());
            wall.paintComponent(this.getGraphics());
        } else if (levelPoint == 3) {
            drawFinish(g, level.getFinishX() + levelController.getLevelGapX(),
                    level.getFinishY() + levelController.getLevelGapY());       // отрисовка точки финиша
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
        Level level = levelController.getGameLevels().getCurrentLevel(levelController.getLevelNum());
        robotController.updateRobot(level.getLevel(), level.getLinkSize(),
                levelController.getLevelGapX(), levelController.getLevelGapY());
        robotController.changeRobotDirection();
        if (robotController.isFinished(level.getFinishX(), level.getFinishY(), level.getLinkSize())) {
            if (!levelController.changeLevel()) {
                this.closeGameWindow();
            }
        }
    }

    public void closeGameWindow() {
        this.setVisible(false);
        this.getMainWindow().setVisible(true);
        this.getMainWindow().revalidate();
    }

    public void drawRobot(Graphics g) {
        double distance = robotController.distanceToTarget();

        if (distance >= 10.0) {
            if (currentFrame == 14) {
                currentFrame = 0;
            }
            RobotImage robotImage = createRobotImage(currentFrame * 16);
            robotImage.paintComponents(g);
            currentFrame++;
        } else {
            RobotImage robotImage = createRobotImage(0);
            robotImage.paintComponents(g);
        }

    }

    private RobotImage createRobotImage(int imagePoxX) {
        return new RobotImage(imagePoxX,
                (int)robotController.getRobot().getRobotPositionX(),
                (int)robotController.getRobot().getRobotPositionY(),
                (int)robotController.getRobot().getRobotDirection());
    }

}
