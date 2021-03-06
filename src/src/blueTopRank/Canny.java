package blueTopRank;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Canny {

	public static void main(String[] args) {
		try { 
			
			
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
			File input = new File("2ndAK.jpg"); 
			BufferedImage image = ImageIO.read(input);	
		
			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
			mat.put(0, 0, data);
			
			Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
			Mat mat2 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
			Imgproc.cvtColor(mat, mat2, Imgproc.COLOR_RGB2GRAY);
			
			//TODO Canny is producing a decent outline, now find a single outline, perhaps with findcontours, and find a way 
			//     to tell comp to only search that part of mat for blue
			Imgproc.Canny(mat2, mat1, 5000, 5000, 7, true);
		
			byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
			mat1.get(0, 0, data1);
			BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
			image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
			
			File output = new File("grayscale.jpg");
			ImageIO.write(image1, "jpg", output); 
			
			System.out.println("Done");
	
		} catch (Exception e) { 
			System.out.println("Error: " + e.getMessage()); 
		}
	}

}
