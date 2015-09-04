import java.awt.*; 
import java.awt.image.BufferedImage; 
import java.io.*; 

import javax.imageio.ImageIO; 
import javax.swing.JFrame; 
	public class PixelCounter { 
		
		BufferedImage image; 
		int width; 
		int height; 
		
		public PixelCounter() { 
			try { 
				File input = new File("bluerange.png"); 
				image = ImageIO.read(input); 
				width = image.getWidth();
				height = image.getHeight();
				
				int count = 0;
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
				
				for(int i=0; i<height; i++){ 
					for(int j=0; j<width; j++){ 
						count++; 
						Color c = new Color(image.getRGB(j, i));
						lowTotal = (c.getRed() + c.getGreen() + c.getBlue() < lowTotal) ? (c.getRed() + c.getGreen() + c.getBlue()) : lowTotal;
						lowRed = (c.getRed() < lowRed) ? c.getRed() : lowRed;
						lowGreen = (c.getGreen() < lowGreen) ? c.getGreen() : lowGreen;
						lowBlue = (c.getBlue() < lowBlue) ? c.getBlue() : lowBlue;

						highTotal = (c.getRed() + c.getGreen() + c.getBlue() > highTotal) ? (c.getRed() + c.getGreen() + c.getBlue()) : highTotal;
						highRed = (c.getRed() > highRed) ? c.getRed() : highRed;
						highGreen = (c.getGreen() > highGreen) ? c.getGreen() : highGreen;
						highBlue = (c.getBlue() > highBlue) ? c.getBlue() : highBlue;
						
						totalRed += c.getRed();
						totalGreen += c.getGreen();
						totalBlue += c.getBlue();
						
						
						
						//System.out.println("S.No: " + count + " Red: " + c.getRed() +" Green: " + c.getGreen() + " Blue: " + c.getBlue());
					}
				}
				System.out.println("Total Range: " + lowTotal + " - " + highTotal);
				System.out.println("Average Red: " + totalRed/count + " Range: " + lowRed + " - " + highRed);
				System.out.println("Average Green: " + totalGreen/count + " Range: " + lowGreen + " - " + highGreen);
				System.out.println("Average Blue: " + totalBlue/count + " Range: " + lowBlue + " - " + highBlue);
				
			}
			
			catch (Exception e){
			System.out.println("Error: " + e.getMessage()); 
			}
	}
		
	public static void main(String args[]) throws Exception{
		
		PixelCounter pc = new PixelCounter(); 
	}
}
