package kryo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.storage.StorageLevel;

public class KryoTest {
 public static void main(String[] args) {
	 SparkConf conf = new SparkConf();
	 conf.setMaster("local");
	 conf.setAppName("KryoTest");
	 conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
	 conf.set("spark.kryo.registrator", "kryo.MyRegistrator");
//	 conf.registerKryoClasses(new Class[]{User.class});
	 JavaSparkContext sc = new JavaSparkContext(conf);

	 JavaRDD<String> rdd = sc.textFile("F://test.txt");
	 JavaRDD<User> map = rdd.map(new Function<String, User>() {

		 public User call(String v1) throws Exception {
			 String s[] =  v1.split("\t");
			 User q = new User();
			 q.setId(Long.parseLong(s[0]));
			 q.setName(s[1]);
			 q.setSex(Integer.valueOf(s[2]));
			 return q;
		 }
	 });
	 map.persist(StorageLevel.MEMORY_AND_DISK_SER());
	 System.out.println(map.count());
 }
}