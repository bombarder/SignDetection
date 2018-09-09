import java.util.List;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

class ObjectDetection {


    private void detectAndDisplay(Mat frame,  CascadeClassifier pedestrianCascade, CascadeClassifier stopCascade) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);

        // -- Detect pedestrian sign
        MatOfRect pedestrianSign = new MatOfRect();
        pedestrianCascade.detectMultiScale(frameGray, pedestrianSign);

        MatOfRect stopSign = new MatOfRect();
        stopCascade.detectMultiScale(frameGray, stopSign);

        List<Rect> listOfPedestrianSigns = pedestrianSign.toList();
        for (Rect sign : listOfPedestrianSigns) {
            Point rightDownAngle = new Point(sign.x + sign.width, sign.y + sign.height);
            Point leftUpAngle = new Point(sign.x, sign.y);
            Point leftDownAngle = new Point(sign.x, sign.y + sign.height + 20);
            Imgproc.rectangle(frame, rightDownAngle, leftUpAngle, new Scalar(255, 0, 0), 4);
            Imgproc.putText(frame, "pedestrian", leftDownAngle, Core.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 0, 255),
                    2, 2, false);
        }

        List<Rect> listOfTopSigns = stopSign.toList();
        for (Rect sign : listOfTopSigns) {
            Point rightDownAngle = new Point(sign.x + sign.width, sign.y + sign.height);
            Point leftUpAngle = new Point(sign.x, sign.y);
            Point leftDownAngle = new Point(sign.x, sign.y + sign.height + 20);
            Imgproc.rectangle(frame, rightDownAngle, leftUpAngle, new Scalar(255, 0, 0), 4);
            Imgproc.putText(frame, "stop", leftDownAngle, Core.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 0, 255),
                    2, 2, false);
        }

        HighGui.imshow("Capture - Road Sign detection", frame);
    }

    public void run() {
        String pedestrianCascade = "C:/Users/user/IdeaProjects/SignDetection/src/main/resources/pedestrianSign.xml";
        String stopCascade = "C:/Users/user/IdeaProjects/SignDetection/src/main/resources/stopSign.xml";
        int cameraDevice = 0;

        CascadeClassifier pedestrianCascadeClassifier = new CascadeClassifier();
        pedestrianCascadeClassifier.load(pedestrianCascade);

        CascadeClassifier stopCascadeClassifier = new CascadeClassifier();
        stopCascadeClassifier.load(stopCascade);

        VideoCapture capture = new VideoCapture(cameraDevice);
        Mat frame = new Mat();

        while (capture.read(frame)) {
            detectAndDisplay(frame, pedestrianCascadeClassifier, stopCascadeClassifier);

            if (HighGui.waitKey(10) == 27) {
                break;// escape
            }
        }
        System.exit(0);
    }
}




