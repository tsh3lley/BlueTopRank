package blueTopRank;

import java.awt.*; 
import java.awt.image.BufferedImage; 
import java.io.*; 
import javax.imageio.ImageIO; 
import javax.swing.JFrame; 

	public class PixelCounter { 
		
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
				
				int count = 0;
				int bluePix = 0;
				
				int totalRed = 0;
				int totalGreen = 0;
				int totalBlue = 0;
				int lowTotal = 255*3;
				int lowRed = 255;
				int lowGreen = 255;
				int lowBlue = 255;
				int highTotal = 0;
				int highRed = 0;
				int highGreen = 0;
				int highBlue = 0;
				int rgbTotal;
				
				for(int i=0; i<height; i++){ 
					for(int j=0; j<width; j++){ 
						count++; 
						Color c = new Color(image.getRGB(j, i));
						
						int H=hsv.val[0]; //hue
						int S=hsv.val[1]; //saturation
						int V=hsv.val[2]; //value
						
						rgbTotal = c.getRed() + c.getGreen() + c.getBlue();
						lowTotal = (rgbTotal < lowTotal) ? (rgbTotal) : lowTotal;
						lowRed = (c.getRed() < lowRed) ? c.getRed() : lowRed;
						lowGreen = (c.getGreen() < lowGreen) ? c.getGreen() : lowGreen;
						lowBlue = (c.getBlue() < lowBlue) ? c.getBlue() : lowBlue;

						highTotal = (rgbTotal > highTotal) ? (rgbTotal) : highTotal;
						highRed = (c.getRed() > highRed) ? c.getRed() : highRed;
						highGreen = (c.getGreen() > highGreen) ? c.getGreen() : highGreen;
						highBlue = (c.getBlue() > highBlue) ? c.getBlue() : highBlue;
						
						totalRed += c.getRed();
						totalGreen += c.getGreen();
						totalBlue += c.getBlue();
						
						//checking if the colors are within the acceptable ranges
						if (c.getRed() < 31 && c.getRed() > 9 && c.getGreen() < 250 && c.getGreen() > 222 && c.getBlue() < 214 && c.getBlue() > 157 && rgbTotal < 469 && rgbTotal > 400){
							bluePix ++;
						}
					}
				}
				
				double percent = ((double)bluePix / (double)totalPix) * 100;
				System.out.println("Percent Blue: " + percent);
				System.out.println("Blue pixels: " + bluePix + " Total Pixels: " + totalPix);
				System.out.println("Total Range: " + lowTotal + " - " + highTotal);
				System.out.println("Average Red: " + totalRed/count + " Range: " + lowRed + " - " + highRed);
				System.out.println("Average Green: " + totalGreen/count + " Range: " + lowGreen + " - " + highGreen);
				System.out.println("Average Blue: " + totalBlue/count + " Range: " + lowBlue + " - " + highBlue);
				
			}
			
			catch (Exception e){
			System.out.println("Error: " + e.getMessage()); 
			}
	}
		
}
