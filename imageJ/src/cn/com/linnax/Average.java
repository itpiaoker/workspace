package cn.com.linnax;

import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.process.ImageProcessor;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/2
 */
public class Average {
    
    public static void main(String[] args) {
    	OpenDialog wo=new OpenDialog("");
    	String we=wo.getPath();
    	ImagePlus ming=new ImagePlus(we);
    	ImageProcessor imageProcessor=ming.getProcessor();
    	
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        ImageProcessor copy = imageProcessor.duplicate();
        
        for(int v=1; v <= height - 2; v++){
        	for(int u=1; u <= width - 2; u++){
        		int sum = 0;
        		for (int j = -1; j <= 1; j++) {
        			for (int i = -1; i <= 1; i++) {
        				int pixel = (int)(copy.getPixel(u + i, v + j));
        				sum += sum + pixel;
					}
				}
        		
        		int q = (int)Math.round(sum / 9.0);
        		copy.putPixel(u, v, q);
        	}
        }
        
        ming.setProcessor(copy);
    	ming.show(); 
    	
	}
}
