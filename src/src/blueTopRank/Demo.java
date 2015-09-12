package blueTopRank;

public class Demo {

	public static void main(String args[]) throws Exception{
		
		PixelCounter pc1 = new PixelCounter(); 
		pc1.setPath("bluesample.jpg");
		pc1.count();
		PixelCounter pc2 = new PixelCounter();
		pc2.setPath("hsvsample.jpg");
		pc2.count();
	}

}
