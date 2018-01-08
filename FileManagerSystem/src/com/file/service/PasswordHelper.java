//package com.file.service;
//
//import org.apache.shiro.crypto.RandomNumberGenerator;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.util.ByteSource;
//
//import com.file.model.AuthUserinfo;
//
//
///**
// * 
// * @Description: 
// * @Author: w-lianxy
// * @Revision: 122149
// * @Date: 2015-9-16 上午11:41:55
// * <pre>
// * @Modification History:
// * [Date]         [Author]        [Description]
// * 
// * </pre>
// */
//public class PasswordHelper {
//
//    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
//    private String algorithmName = "md5";
//    private int hashIterations = 2;
//
//    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
//        this.randomNumberGenerator = randomNumberGenerator;
//    }
//
//    public void setAlgorithmName(String algorithmName) {
//        this.algorithmName = algorithmName;
//    }
//
//    public void setHashIterations(int hashIterations) {
//        this.hashIterations = hashIterations;
//    }
//
//    public void encryptPassword(AuthUserinfo user) {
//
//        user.setSalt(randomNumberGenerator.nextBytes().toHex());
//
//        String newPassword = new SimpleHash(
//                algorithmName,
//                user.getLoginpsw(),
//                ByteSource.Util.bytes(user.getLoginname()+user.getSalt()),
//                hashIterations).toHex();
//
//        user.setLoginpsw(newPassword);
//    }
//}
