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
				File input = new File("opencv.jpg"); 
				image = ImageIO.read(input); 
				width = image.getWidth();
				height = image.getHeight();
				int count = 0;
				
				for(int i=0; i<height; i++){ 
					for(int j=0; j<width; j++){ 
						count++; 
						Color c = new Color(image.getRGB(j, i)); 
						System.out.println("S.No: " + count + " Red: " + c.getRed() +" Green: " + c.getGreen() + " Blue: " + c.getBlue());
					}
				}
			}
			
			catch (Exception e){
			System.out.println("Error: " + e.getMessage()); 
			}
	}
		
	public static void main(String args[]) throws Exception{
		
		PixelCounter pc = new PixelCounter(); 
	}
}
