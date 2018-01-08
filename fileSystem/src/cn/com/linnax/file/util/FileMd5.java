//package cn.com.linnax.file.util;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class FileMd5 {
//
//	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//
//	/**
//	 * @param args
//	 * @throws IOException
//	 */
//	public static void main(String[] args) throws IOException {
//		// TODO Auto-generated method stub
//        File f = FileUtils.getFile("D:\\BaiduYunDownload\\2015ZY\\2015张宇数学冲刺班\\2015张宇概率冲刺", "01.avi");
//        FileInputStream fis = FileUtils.openInputStream(f);
////        FileChannel channel = fis.getChannel();
////        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
////        MessageDigest md5Digest = DigestUtils.getMd5Digest();
////        md5Digest.update(byteBuffer);
////        byte[] b = md5Digest.digest();
////        String hexString = byteToHexString(b);
//
//        String aa = DigestUtils.md5Hex(fis);
//        System.out.println(aa);
//        fis.close();
//
//
//	}
//
//	   /** */
//
//	   /**
//
//	    * 把byte[]数组转换成十六进制字符串表示形式
//
//	    * @param tmp    要转换的byte[]
//
//	    * @return 十六进制字符串表示形式
//
//	    */
//
//	   private static String byteToHexString(byte[] tmp) {
//
//	      String s;
//
//	      // 用字节表示就是 16 个字节
//
//	      char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
//
//	      // 所以表示成 16 进制需要 32 个字符
//
//	      int k = 0; // 表示转换结果中对应的字符位置
//
//	      for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
//
//	        // 转换成 16 进制字符的转换
//
//	        byte byte0 = tmp[i]; // 取第 i 个字节
//
//	        str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
//
//	        // >>> 为逻辑右移，将符号位一起右移
//
//	        str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
//
//	      }
//
//	      s = new String(str); // 换后的结果转换为字符串
//
//	      return s;
//
//	   }
//
//}
