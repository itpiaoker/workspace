package cn.com.linnax;

import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/2
 */
public class ComputeHistogram {
    
    public static void main(String[] args) {
    	
    	OpenDialog wo=new OpenDialog("");
    	String we=wo.getPath();
    	ImagePlus ming=new ImagePlus(we);
    	
    	ImageProcessor imageProcessor=ming.getProcessor();
    	
    	int[] h = new int[256];
    	
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        
        for(int u=0; u < height; u++){
        	for(int v=0; v < width; v++){
        		int pixel = imageProcessor.getPixel(u, v);
        		h[pixel] = h[pixel] + 1;
        	}
        }
        
        int[] histogram = imageProcessor.getHistogram();
        
        
    	
	}
    
    
}
