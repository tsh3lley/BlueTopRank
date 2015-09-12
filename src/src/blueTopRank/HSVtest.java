package blueTopRank;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class HSVtest {
	static int width; 
	static int height; 

	public static void main(String[] args) {
		try { 
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
			File input = new File("2ndAK.jpg"); 
			BufferedImage image = ImageIO.read(input);	
		
			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
			double[] pls;
			mat.put(0, 0, data);
			int i = 0;
			int j = 0;
//			for(int i=0; i<height; i++){ 
//				for(int j=0; j<width; j++){ 
					pls = mat.get(j, i);
					System.out.println("H: " + pls[0] + " S: " + pls[1] + " V: " + pls[2]);
//				}
//			}
		
		}catch (Exception e) { 
				System.out.println("Error: " + e.getMessage()); 
		}
	}

}
