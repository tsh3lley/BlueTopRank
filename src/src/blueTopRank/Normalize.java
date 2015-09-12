package blueTopRank;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Normalize {

	BufferedImage image; 
	int width; 
	int height; 
	String path;
	
	public void setPath(String path){
		this.path = path;
	}
	
	public void count() { 
		try { 
			File input = new File(path); 
			image = ImageIO.read(input); 
			width = image.getWidth();
			height = image.getHeight();
			int totalPix = height * width;
			int totalRGB;
			for(int i=0; i<height; i++){ 
				for(int j=0; j<width; j++){  
					Color c = new Color(image.getRGB(j, i));
					totalRGB = c.getBlue() + c.getGreen() + c.getRed();
					
				}
			}
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage()); 
			}
	}
}
