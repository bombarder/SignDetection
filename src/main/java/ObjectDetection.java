import java.util.List;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

class ObjectDetection {
    private void detectAndDisplay(Mat frame, CascadeClassifier signCascade) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);

        // -- Detect pedestrian sign
        MatOfRect pedestrianSign = new MatOfRect();
        signCascade.detectMultiScale(frameGray, pedestrianSign);

        List<Rect> listOfSigns = pedestrianSign.toList();
        for (Rect sign : listOfSigns) {
            Point rightDownAngle = new Point(sign.x + sign.width, sign.y + sign.height);
            Point leftUpAngle = new Point(sign.x, sign.y);
            Point leftDownAngle = new Point(sign.x, sign.y + sign.height + 20);
            Imgproc.rectangle(frame, rightDownAngle,  leftUpAngle, new Scalar(255, 0, 0), 4);
            Imgproc.putText(frame,"pedestrian", leftDownAngle, Core.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 0, 255),
                    2, 2,false);
        }

        HighGui.imshow("Capture - Sign detection", frame);
    }

    public void run(String[] args) {
        String filenameFaceCascade = args.length > 2 ? args[0] : "C:/Work/projects/signRecognition/src/main/resources/cascade.xml";
        int cameraDevice = args.length > 2 ? Integer.parseInt(args[2]) : 0;

        CascadeClassifier signCascade = new CascadeClassifier();

        if (!signCascade.load(filenameFaceCascade)) {
            System.err.println("--(!)Error loading sign cascade: " + filenameFaceCascade);
            System.exit(0);
        }

        VideoCapture capture = new VideoCapture(cameraDevice);
        if (!capture.isOpened()) {
            System.err.println("--(!)Error opening video capture");
            System.exit(0);
        }

        Mat frame = new Mat();
        while (capture.read(frame)) {
            if (frame.empty()) {
                System.err.println("--(!) No captured frame -- Break!");
                break;
            }

            //-- 3. Apply the classifier to the frame
            detectAndDisplay(frame, signCascade);

            if (HighGui.waitKey(10) == 27) {
                break;// escape
            }
        }
        System.exit(0);
    }
}




