import org.opencv.core.Core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String DIALOG_NAME = "Sign recognition";
    private static final String[] NAME_OF_BUTTONS = new String[]{"Start", "Available signs", "Quit"};

    public static void main(String[] args) {


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String pedestrianCascade = "C:/Work/projects/signRecognition/src/main/resources/pedestrianSign.xml";
        String stopCascade = "C:/Work/projects/signRecognition/src/main/resources/stopSign.xml";
//        String stopLight = "C:/Work/projects/signRecognition/src/main/resources/stopLight.xml";

        List<String> availableClassifiers = new ArrayList<String>();
        availableClassifiers.add(pedestrianCascade);
        availableClassifiers.add(stopCascade);
//        availableClassifiers.add(stopLight);

        while (true){
            JPanel panel = new JPanel();
            panel.add(new JLabel("Сделайте Ваш выбор:"));
            int result = JOptionPane.showOptionDialog(null, panel, DIALOG_NAME,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, NAME_OF_BUTTONS, null);

            if (result == JOptionPane.YES_OPTION) {
                new SignDetection(availableClassifiers).run();
                break;
            } else if (result == JOptionPane.YES_NO_CANCEL_OPTION) {

                JPanel innerPanel = new JPanel();
                ImageIcon pedestrian = new ImageIcon("C:/Work/projects/signRecognition/src/main/resources/pedestrian.jpeg");
                ImageIcon stop = new ImageIcon("C:/Work/projects/signRecognition/src/main/resources/stop.jpg");
                ImageIcon redLight = new ImageIcon("C:/Work/projects/signRecognition/src/main/resources/redLight.jpg");

                JLabel label1 = new JLabel(pedestrian);
                JLabel label2 = new JLabel(stop);
                JLabel label3 = new JLabel(redLight);

                innerPanel.add(label1);
                innerPanel.add(label2);
                innerPanel.add(label3);

                JOptionPane.showMessageDialog(null, innerPanel,
                        "Модели знаков доступные для определения", JOptionPane.PLAIN_MESSAGE);
            } else {
                break;
            }
        }
    }
}