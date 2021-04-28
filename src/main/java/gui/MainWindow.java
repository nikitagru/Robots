package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow extends JFrame
{
    static JFrame frame = new JFrame();

    private JButton NewGame = new JButton("Новая игра");
    private JButton LoadingGame = new JButton("Загрузить игру");
    private JButton Settings = new JButton("Настройки");
    private JButton Exit = new JButton("Выход");

    public void MakeWindow() throws IOException {
        Level level = new Level();
        frame.add(level);
        frame.setVisible(true);
        JPanel panel = new JPanel();
        NewGame.addActionListener(e -> {
            level.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            level.setVisible(true);
            this.setVisible(false);
        });

        LoadingGame.addActionListener(e -> {
        });

        Settings.addActionListener(e -> {

        });

        Exit.addActionListener(e -> System.exit(1));
        panel.add(NewGame);
        panel.add(LoadingGame);
        panel.add(Settings);
        panel.add(Exit);
        panel.setBackground(Color.BLUE);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }


}
