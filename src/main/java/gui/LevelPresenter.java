package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Arrays;

public class LevelPresenter extends JPanel {

    private RobotController robotController = new RobotController();
    private int currentFrame = 0;
    private int levelGapX = 650;
    private int levelGapY = 300;

    private int levelNum;

    private UsersProfile currentPlayer;

    private GameLevels gameLevels = new GameLevels();
    private LevelController levelController = new LevelController();

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


    public LevelPresenter(int levelNum, JFrame frame, UsersProfile currentPlayer) throws IOException {
        this.levelNum = levelNum;
        level = Arrays.stream(gameLevels.getLevel(levelNum)).toArray(int[][]::new);
        mainWindow = frame;

        this.currentPlayer = currentPlayer;

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

    public int[][] getLevel() {
        return level;
    }

    public int getLevelGapX() {
        return levelGapX;
    }

    public int getLevelGapY() {
        return levelGapY;
    }

    public RobotController getRobotController() {
        return robotController;
    }

    public void setLevelPoint(int x, int y, int value) {
        level[x][y] = value;
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public GameLevels getGameLevels() {
        return gameLevels;
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public UsersProfile getCurrentPlayer() {
        return currentPlayer;
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
        levelController.changeLevel(this);
    }

    public void drawLevel(Graphics g) {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                if (level[i][j] == 3) {
                    drawFinish(g, j * levelController.getLinkSize() + levelGapX, i * levelController.getLinkSize() + levelGapY);
                    levelController.setFinishX(j, levelGapX);
                    levelController.setFinishY(i, levelGapY);
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
        robotController.updateRobot(level, levelController.getLinkSize(), levelGapX, levelGapY);
    }

    public void drawRobot(Graphics g) {
        if (robotController.getRobot().getRobotPositionX() > robotController.getTargetPositionX()) {
            robotController.getRobot().setRobotDirection(1);
        } else {
            robotController.getRobot().setRobotDirection(0);
        }

        double distance = robotController.distanceToTarget();

        if (distance >= 10.0) {
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
