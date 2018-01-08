//package cn.com.linnax.file.listener;
//
//import net.contentobjects.jnotify.JNotify;
//import net.contentobjects.jnotify.JNotifyException;
//import net.contentobjects.jnotify.JNotifyListener;
//
//public class MyJNotifyListener implements JNotifyListener{
//
//
//    @Override
//    public void fileCreated(int wd, String rootPath, String name) {
//        System.err.println("create: --->" + wd + "--->" + rootPath + "--->" + name);
//    }
//
//    @Override
//    public void fileDeleted(int wd, String rootPath, String name) {
//        System.err.println("delete: --->" + wd + "--->" + rootPath + "--->" + name);
//    }
//
//    @Override
//    public void fileModified(int wd, String rootPath, String name) {
//        System.err.println("modified: --->" + wd + "--->" + rootPath + "--->" + name);
//    }
//
//    @Override
//    public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
//        System.err.println("rename: --->" + wd + "--->" + rootPath + "--->" + oldName + "--->" + newName);
//    }
//
//    /**
//     * JNotify监控方法
//     * @throws JNotifyException
//     * @throws InterruptedException
//     */
//    public static void sample() throws JNotifyException, InterruptedException {
//
//        //要监控哪个目录
//        String path = "C://";
//
//        //监控用户的操作，增，删，改，重命名
//        int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED ;
//
//
//
//        //是否监控子目录
//        boolean subTree = true;
//
//        //开始监控
//        int watchID = JNotify.addWatch(path, mask, subTree, new MyJNotifyListener());
//
//        //睡一会，看看效果
//        Thread.sleep(1000 * 60 * 3);
//
//        //停止监控
//        boolean res = JNotify.removeWatch(watchID);
//
//        if (res) {
//            System.err.println("已停止监听");
//        }
//        System.err.println(path);
//    }
//
//}
