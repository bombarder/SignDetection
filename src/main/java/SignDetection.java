import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SignDetection {

    private Map<String, String> classifiersMap;
    private int cameraDevice = 0;
    private VideoCapture capture = new VideoCapture(cameraDevice);
    private Mat frame = new Mat();
    private boolean alreadyPlayed;

    SignDetection(Map<String, String> classifiersMap) {
        this.classifiersMap = classifiersMap;
    }

    public void run() {
        while (capture.read(frame)) {
            for (Object o : classifiersMap.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                CascadeClassifier cascadeClassifier = new CascadeClassifier();
                cascadeClassifier.load(String.valueOf(pair.getValue()));
                detect(frame, cascadeClassifier, String.valueOf(pair.getKey()));
            }

            HighGui.imshow("Road Sign detection", frame);
            if (HighGui.waitKey(10) == 27) {
                break;// escape
            }
        }
    }

    private void detect(Mat frame, CascadeClassifier classifier, String message) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);

        MatOfRect matOfRect = new MatOfRect();
        classifier.detectMultiScale(frameGray, matOfRect);
        List<Rect> listOfSigns = matOfRect.toList();

        for (Rect sign : listOfSigns) {
            Point rightDownAngle = new Point(sign.x + sign.width, sign.y + sign.height);
            Point leftUpAngle = new Point(sign.x, sign.y);
            Imgproc.rectangle(frame, rightDownAngle, leftUpAngle, new Scalar(255, 0, 0), 2);
            if (!alreadyPlayed){
                new SoundClip(message);
                alreadyPlayed = true;
            }
        }
    }
}
