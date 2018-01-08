package kryo;

import scala.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;

public class User implements Serializable {
    static String[] array={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    public static void main(String[] args) throws Exception {
        PrintWriter pw = new PrintWriter(new File("F:/test.txt"));
        Random ra = new Random();
        for(int i=0;i<10000000;i++){
            StringBuffer sb = new StringBuffer(String.valueOf(i+1)).append("\t");
            for(int j=0;j<5+ra.nextInt(10);j++){
                sb.append(array[ra.nextInt(36)]);
            }
            sb.append("\t").append(ra.nextInt(2));
            pw.print(sb);
            pw.println();
        }
        pw.flush();
        pw.close();
    }
    long id;
    String name;
    int sex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}