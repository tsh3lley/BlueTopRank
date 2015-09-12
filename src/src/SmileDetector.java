

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.*;
// import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvSVM;
import org.opencv.ml.CvSVMParams;
import org.opencv.objdetect.CascadeClassifier;


/**
 * Class that detects smile(s) on given picture using OpenCV library.
 * 
 * @author Pawel Nowak
 * @version 1.0
 */
public class SmileDetector {
	private CascadeClassifier faceDetector;
	private CascadeClassifier mouthDetector;
	private Mat image;
	private Mat trainingImages;
	private Mat trainingLabels;
	private CvSVM clasificador;
	private static final String trainPath = "src/main/resources/train/";
	private static final Size trainSize = new Size(50, 30);

	/**
	 * Detects smile on given image
	 * 
	 * @param filename path to file on which smile(s) will be detected
	 * @throws URISyntaxException 
	 */
	public void detectSmile(String filename) {
		init();
		detectSmile(detectMouth(filename));
	}

	/**
	 * Initializes class. Loads OpenCV library and initializes variables
	 * @throws URISyntaxException 
	 * 
	 */
	private void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		faceDetector = new CascadeClassifier(
				new File("src/main/resources/haarcascade_frontalface_alt.xml").getAbsolutePath());
		mouthDetector = new CascadeClassifier(
				new File("src/main/resources/haarcascade_mcs_mouth.xml").getAbsolutePath());
		trainingImages = new Mat();
		trainingLabels = new Mat();
	}

	/**
	 * Detects face(s) and then for each detects and crops mouth
	 * 
	 * @param filename path to file on which smile(s) will be detected
	 * @return List of Mat objects with cropped mouth pictures.
	 */
	private ArrayList<Mat> detectMouth(String filename) {
		int i = 0;
		ArrayList<Mat> mouths = new ArrayList<Mat>();
		// reading image in grayscale from the given path
		image = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		MatOfRect faceDetections = new MatOfRect();
		// detecting face(s) on given image and saving them to MatofRect object
		faceDetector.detectMultiScale(image, faceDetections);
		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		MatOfRect mouthDetections = new MatOfRect();
		// detecting mouth(s) on given image and saving them to MatOfRect object
		mouthDetector.detectMultiScale(image, mouthDetections);
		System.out.println(String.format("Detected %s mouths", mouthDetections.toArray().length));
		for (Rect face : faceDetections.toArray()) {
			Mat outFace = image.submat(face);
			// saving cropped face to picture
			Highgui.imwrite("face" + i + ".png", outFace);
			for (Rect mouth : mouthDetections.toArray()) {
				// trying to find right mouth
				// if the mouth is in the lower 2/5 of the face
				// and the lower edge of mouth is above of the face
				// and the horizontal center of the mouth is the enter of the face
				if (mouth.y > face.y + face.height * 3 / 5 && mouth.y + mouth.height < face.y + face.height
						&& Math.abs((mouth.x + mouth.width / 2)) - (face.x + face.width / 2) < face.width / 10) {
					Mat outMouth = image.submat(mouth);
					// resizing mouth to the unified size of trainSize
					Imgproc.resize(outMouth, outMouth, trainSize);
					mouths.add(outMouth);
					// saving mouth to picture 
					Highgui.imwrite("mouth" + i + ".png", outMouth);
					i++;
				}
			}
		}
		return mouths;
	}
	
	/**
	 * Trains SVM
	 * @throws URISyntaxException 
	 * 
	 */
	private void trainSVM() {
		train("positive");
		train("negative");
	}

	/**
	 * Detects smile on given mouths list
	 * 
	 * @param mouths list of Mat objects with cropped mouth picture
	 * @throws URISyntaxException 
	 */
	private void detectSmile(ArrayList<Mat> mouths) {
		trainSVM();
		CvSVMParams params = new CvSVMParams();
		// set linear kernel (no mapping, regression is done in the original feature space)
		params.set_kernel_type(CvSVM.LINEAR);
	// train SVM with images in trainingImages, labels in trainingLabels, given params with empty samples
		clasificador = new CvSVM(trainingImages, trainingLabels, new Mat(), new Mat(), params);
		// save generated SVM to file, so we can see what it generated
		clasificador.save("svm.xml");
		// loading previously saved file
		clasificador.load("svm.xml");
		// returnin, if there aren't any samples
		if (mouths.isEmpty()) {
			System.out.println("No mouth detected");
			return;
		}
		for (Mat mouth : mouths) {
			Mat out = new Mat();
			// converting to 32 bit floating point in gray scale
			mouth.convertTo(out, CvType.CV_32FC1);
			if (clasificador.predict(out.reshape(1, 1)) == 1.0) {
				System.out.println("Detected happy face");
			} else {
				System.out.println("Detected not a happy face");
			}
		}
	}

	/**
	 * Trains SVM with samples located in /resources directory
	 * 
	 * @param flag control training for positive or neutral samples
	 * @throws URISyntaxException 
	 */
	private void train(String flag) {
		String path;
		if (flag.equalsIgnoreCase("positive")) {
			path = trainPath + "smile/";
		} else {
			path = trainPath + "neutral/";
		}
		for (File file : new File(path).listFiles()) {
			Mat img = new Mat();
			// loading image to Mat object in grayscale
			Mat con = Highgui.imread(file.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			// converting image to 32 bit floating point signed depth in one channel 
			con.convertTo(img, CvType.CV_32FC1, 1.0 / 255.0);
			// resizing to the unified size
			Imgproc.resize(img, img, trainSize);
			// adding reshaped sample element to the end of the matrix
			trainingImages.push_back(img.reshape(1, 1));
			// adding labels elements to the end of the matrix, depends on the flag
			if (flag.equalsIgnoreCase("positive")) {
				trainingLabels.push_back(Mat.ones(new Size(1, 1), CvType.CV_32FC1));
			} else {
				trainingLabels.push_back(Mat.zeros(new Size(1, 1), CvType.CV_32FC1));
			}
		}
	}
}