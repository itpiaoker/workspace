package cn.com.linnax;

import org.tanukisoftware.wrapper.WrapperListener;

public class Main implements WrapperListener{
    @Override
    public Integer start(String[] strings) {
        return null;
    }

    @Override
    public int stop(int i) {
        return 0;
    }

    @Override
    public void controlEvent(int i) {

    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
