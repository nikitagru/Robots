package gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram
{
    public static void main(String[] args) {
      try {
       UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      } catch (Exception e) {
        e.printStackTrace();
      }
      SwingUtilities.invokeLater(() -> {

          MainWindow windows = new MainWindow();
          windows.pack();
          windows.setVisible (true);
          windows.MakeWindow();
       // MainApplicationFrame frame = new MainApplicationFrame();
       // frame.pack();//вызывает окно
      //  frame.setVisible(true);
     //   frame.setExtendedState(Frame.MAXIMIZED_BOTH);//расрывает на полный экран
      });
    }}
