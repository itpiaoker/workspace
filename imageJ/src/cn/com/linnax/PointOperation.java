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
public class PointOperation {
    
    public static void main(String[] args) {
    	
    	OpenDialog wo=new OpenDialog("");
    	String we=wo.getPath();
    	ImagePlus ming=new ImagePlus(we);
    	
    	ImageProcessor imageProcessor=ming.getProcessor();
    	
    	int[] h = new int[256];
    	
//        int width = imageProcessor.getWidth();
//        int height = imageProcessor.getHeight();
//        
//        for(int u=0; u < height; u++){
//        	for(int v=0; v < width; v++){
//        		int pixel = (int)(imageProcessor.get(u, v) * 1.5 + 0.5);
//        		if( pixel > 255){
//        			pixel = 255;
//        		}
//        		imageProcessor.set(u, v, pixel);
//        	}
//        }
        imageProcessor.invert();
    	ImagePlus ming2=new ImagePlus("平滑后的图像",imageProcessor);//图像有标题
    	ming2.show(); 
        
    	
	}
    
    
}
