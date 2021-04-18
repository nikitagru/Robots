package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JWindow
{
    static JWindow frame = new JWindow();
    public void MakeWindow()
    {
        JPanel panel = new JPanel();
        JButton NewGame = new JButton("Новая игра");
        NewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JWindow w = new JWindow();
                JPanel p = new JPanel();
                JLabel l = new JLabel("Никита лох)");
                p.add(l);
                w.add(p);
                p.setBackground(Color.black);
                w.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                w.setVisible(true);

            }
        });
        JButton LoadingGame = new JButton("Загрузить игру");
        LoadingGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JButton Settings = new JButton("Настройки");
        Settings.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton Exit = new JButton("Выход");
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
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
