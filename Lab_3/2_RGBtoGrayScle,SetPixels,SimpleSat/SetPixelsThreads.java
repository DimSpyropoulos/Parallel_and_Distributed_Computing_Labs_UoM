import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SetPixelsThreads {
    public static void main(String args[]) {
	
		String fileNameR = null;
		String fileNameW = null;
		int numThreads = Runtime.getRuntime().availableProcessors();

		/*if (args.length != 2) {
			System.out.println("Usage: java SetPixel <file to read > <file to write");
			System.exit(1);
		}
		fileNameR = args[0];
		fileNameW = args[1]; */
        fileNameR = "original.jpg";
		fileNameW = "new.jpg";
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNameR));
		} catch (IOException e) {}

		long start = System.currentTimeMillis(); 

		MyThread threads[] = new MyThread[numThreads];
          
        for(int i = 0; i < numThreads; i++) {
                threads[i] = new MyThread(i, img, numThreads);
                threads[i].start();
        }
    
        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }
	
	    long elapsedTimeMillis = System.currentTimeMillis()-start;
       
		try {
		  File file = new File(fileNameW);
		  ImageIO.write(img, "jpg", file);
		} catch (IOException e) {}
      
		System.out.println("Done...");
		System.out.println("time in ms = "+ elapsedTimeMillis);
   }
    
}

class MyThread extends Thread{
    int id, start, stop;
    BufferedImage img;
    
    int redShift = 100;
	int greenShift = 100;
	int blueShift = 100;
      
 

    public MyThread(int id,BufferedImage img, int numThreads){
        this.id = id;
        this.img = img;
        start = id * (img.getHeight() / numThreads);
	    stop = start + (img.getHeight() / numThreads);
		if (id == (numThreads - 1)) stop = img.getHeight();
    }

    public void run()
	{
        for (int y = start; y < stop; y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				//Retrieving contents of a pixel
				int pixel = img.getRGB(x,y);
				//Creating a Color object from pixel value
				Color color = new Color(pixel, true);
				//Retrieving the R G B values, 8 bits per r,g,b
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				//Modifying the RGB values
				red = (red + redShift)%256;
				green = (green + greenShift)%256;
				blue = (blue + blueShift)%256;
				//Creating new Color object
				color = new Color(red, green, blue);
				//Setting new Color object to the image
				img.setRGB(x, y, color.getRGB());
			}
		}
		
           
	}
}
