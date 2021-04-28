package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RobotImage extends JPanel {
    private BufferedImage image;

    private int x;
    private int width = 16;
    private int height = 15;
    private int positionX;
    private int positionY;
    private int rotation;

    public RobotImage(int x, int positionX, int positionY, int rotation) {
        this.x = x;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;

        ClassLoader cl = getClass().getClassLoader();

        try {
            image = ImageIO.read(new File(cl.getResource("robot.png").getPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics2D = (Graphics2D)g;
        BufferedImage cropedImage = image.getSubimage(x, 15 * rotation, width, height);
        graphics2D.drawImage(cropedImage, positionX, positionY, this);
    }
}
