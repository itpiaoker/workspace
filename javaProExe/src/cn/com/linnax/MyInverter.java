package cn.com.linnax;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/2
 */
public class MyInverter implements PlugInFilter{


    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return 0;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        imageProcessor.getWidth();
    }
}
