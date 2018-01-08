package kryo;

import org.apache.spark.serializer.KryoRegistrator;

 import com.esotericsoftware.kryo.Kryo;

 public class MyRegistrator implements KryoRegistrator{
     public void registerClasses(Kryo arg0) {
         arg0.register(User.class);
     }
 }
