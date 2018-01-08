package cn.com.linnax.file.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/13
 */



public class MP3FileFilter implements FileFilter {
//    private static List<String> list;
    @Override
    public boolean accept(File dir) {
        String name = dir.getName();
        if(name.endsWith("360")){
            return false;

        }

        if(name.endsWith("$")){
            return false;

        }

        if(name.endsWith(".")){
            return false;

        }

        if ("$Recycle.Bin".equals(name)) {
            return false;
        }

        if ("$RECYCLE.BIN".equals(name)) {
            return false;
        }

        if ("Program Files".equals(name)) {
            return false;
        }

        if ("Program Files (x86)".equals(name)) {
            return false;
        }

        if ("ProgramData".equals(name)) {
            return false;
        }

        return true;

    }

}
