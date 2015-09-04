package blueTopRank;

public class Demo {

	public static void main(String args[]) throws Exception{
		
		PixelCounter pc1 = new PixelCounter(); 
		pc1.setPath("1stAK.png");
		pc1.count();
		PixelCounter pc2 = new PixelCounter();
		pc2.setPath("2ndAK.png");
		pc2.count();
	}

}
