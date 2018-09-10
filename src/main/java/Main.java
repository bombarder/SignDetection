import org.opencv.core.Core;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String pedestrianCascade = "C:/Work/projects/signRecognition/src/main/resources/pedestrianSign.xml";
        String stopCascade = "C:/Work/projects/signRecognition/src/main/resources/stopSign.xml";

        List<String> availableClassifiers = new ArrayList<String>();
        availableClassifiers.add(pedestrianCascade);
        availableClassifiers.add(stopCascade);

        new SignDetection(availableClassifiers).run();
    }
}