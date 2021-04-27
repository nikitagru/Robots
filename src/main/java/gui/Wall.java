package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends JPanel {
    private BufferedImage image;

    private int x;
    private int y;

    public Wall(int x, int y) {
        super();
        this.x = x;
        this.y = y;

        ClassLoader cl = getClass().getClassLoader();

        try {
            image = ImageIO.read(new File(cl.getResource("wall.png").getPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.drawImage(image, x, y, this);
    }
}
