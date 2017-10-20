package dip;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Img {
	BufferedImage img;
	int width;
	int height;
	double[] arr;

	public double[] grayScale() throws IOException {
		File input = new File("photo.jpg");
		img = ImageIO.read(input);
		width = img.getWidth();
		height = img.getHeight();
		BufferedImage img2=new BufferedImage(width/20, height/20, img.getType());
		arr=new double[(width*height)/400];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(img.getRGB(j, i));
				int red = (int) (c.getRed() * 0.299);
				int green = (int) (c.getGreen() * 0.587);
				int blue = (int) (c.getBlue() * 0.114);
				Color nc = new Color(red + green + blue, red + green + blue, red + green + blue);
				img.setRGB(j, i, nc.getRGB());
				if(i%20==0 && j%20==0){
				arr[(i/20)*(width/20)+(j/20)]=((double)(red+green+blue)-127)/128;
				img2.setRGB(j/20, i/20, nc.getRGB());
				}
			}
			
		}
		
		File out = new File("photo.jpg");
		ImageIO.write(img, "jpg", out);
		File out2 = new File("photo2.jpg");
		ImageIO.write(img2, "jpg", out2);
		return arr;
	}

	public static double[] takePic() throws IOException{
		String link = "http://192.168.0.101:8080/photo.jpg";
		URL u = new URL(link);
		InputStream is = u.openStream();
		OutputStream os = new FileOutputStream("photo.jpg");
		byte[] buff = new byte[2048];
		int l = 0;
		while ((l = is.read(buff)) != -1) {
			os.write(buff, 0, l);
		}
		is.close();
		os.close();
		Img i = new Img();
		return i.grayScale();
		
	}

}
