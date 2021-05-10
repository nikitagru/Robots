package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Arrays;

public class Level extends JPanel {

    private RobotController robotController = new RobotController();
    private int currentFrame = 0;
    private int startPositionX = 650;
    private int startPositionY = 300;
    private int linkSize = 32;

    private double finishX;
    private double finishY;

    private int levelNum;

    private UsersProfile currentPlayer;

    private GameLevels gameLevels = new GameLevels();
    private LevelModel levelModel = new LevelModel();

    private int[][] level;

    private JFrame mainWindow;

    
    Timer timerMap = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            onRedrawEvent();
        }
    });

    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                onModelUpdateEvent();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    });


    public Level(int levelNum, JFrame frame, UsersProfile currentPlayer) throws IOException {
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
        if (levelModel.isFinished(robotController, finishX, finishY, linkSize)) {
            levelNum++;
            if (levelNum == gameLevels.getLevelsCount() + 1) {
                this.setVisible(false);
                mainWindow.setVisible(true);
                mainWindow.revalidate();
            }
            level = gameLevels.getLevel(levelNum);
        }
    }

    public void drawLevel(Graphics g) {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                if (level[i][j] == 1) {
                    Wall wall = new Wall(j * 32 + startPositionX, i * 32 + startPositionY);
                    wall.paintComponent(g);
                } else if (level[i][j] == 2) {
                    robotController.getRobot().setRobotPositionX(j * 32 + startPositionX);
                    robotController.getRobot().setRobotPositionY(i * 32 + startPositionY);
                    level[i][j] = 0;
                } else if (level[i][j] == 3) {
                    drawFinish(g, j * 32 + startPositionX, i * 32 + startPositionY);
                    finishX = j * 32 + startPositionX;
                    finishY = i * 32 + startPositionY;
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
        robotController.updateRobot(level, linkSize, startPositionX, startPositionY);
        saveData();
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

    private void saveData() throws IOException {
        currentPlayer.setLevel(levelNum);
        UserSer.userSer(currentPlayer);
    }
}
