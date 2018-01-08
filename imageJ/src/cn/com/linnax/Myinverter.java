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
public class Myinverter implements PlugInFilter{


    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        
        for(int u=0; u < width; u++){
        	for(int v=0; v < height; v++){
        		int pixel = imageProcessor.getPixel(u, v);
        		imageProcessor.putPixel(u, v, pixel);
        	
        	}
        }
        
        
        
        
        
        
        
    }
    
    public static void main(String[] args) {
//    	OpenDialog wo=new OpenDialog("");
//    	String we=wo.getPath();
//    	ImagePlus ming=new ImagePlus(we);
//    	ming.setTitle("原图像");
//    	ming.show();
//
//    	ImageProcessor ip1=ming.getProcessor();
//    	ImageProcessor ip2=ip1.duplicate();
//    	ip2.invert();
//    	ImagePlus ming2=new ImagePlus("平滑后的图像",ip2);//图像有标题
//    	ming2.show(); 
    	
    	OpenDialog wo=new OpenDialog("");
    	String we=wo.getPath();
    	ImagePlus ming=new ImagePlus(we);
    	
    	ImageProcessor imageProcessor=ming.getProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        
        for(int u=0; u < width; u++){
        	for(int v=0; v < height; v++){
        		int pixel = imageProcessor.getPixel(u, v);
        		imageProcessor.putPixel(u, v, 255-pixel);
        	
        	}
        }
        
    	ImagePlus ming2=new ImagePlus("平滑后的图像",imageProcessor);//图像有标题
    	ming2.show(); 
    	
    	
	}
    
    
}
