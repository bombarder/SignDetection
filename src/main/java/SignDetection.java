import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.util.List;

public class SignDetection {

    private List<String> classifiersList;
    private int cameraDevice = 0;
    private VideoCapture capture = new VideoCapture(cameraDevice);
    private Mat frame = new Mat();
    private boolean alreadyPlayed;

    SignDetection(List<String> classifiersList) {
        this.classifiersList = classifiersList;
    }

    public void run() {
        while (capture.read(frame)) {
            for (String classifier : classifiersList) {
                CascadeClassifier cascadeClassifier = new CascadeClassifier();
                cascadeClassifier.load(classifier);
                detect(frame, cascadeClassifier,"sound");
            }
            HighGui.imshow("Road Sign detection", frame);
            if (HighGui.waitKey(10) == 27) {
                break;// escape
            }
        }
    }

    private void detect(Mat frame, CascadeClassifier classifier, String mesasge) {
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
                new SoundClip(mesasge);
                alreadyPlayed = true;
            }
        }
    }
}
