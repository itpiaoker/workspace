//package sparkPro;
//
//import java.io.File;
//import java.io.IOException;
//
//import com.fasterxml.jackson.core.JsonEncoding;
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//
//public class Testa {
//
//	public static void main(String[] args) throws IOException {
//		// TODO Auto-generated method stub
//		JsonFactory jfactory = new JsonFactory();
//
//		/*** write to file ***/
//		JsonGenerator jGenerator = jfactory.createGenerator(new File("c:\\user.json"), JsonEncoding.UTF8);
//
////		 jfactory.
//		// jGenerator.close();
//
//		/*** read from file ***/
//		JsonParser jParser = jfactory.createParser(new File("c:\\user.json"));
//		// loop until token equal to "}"
//		while (jParser.nextToken() != JsonToken.END_OBJECT) {
//
//			String fieldname = jParser.getCurrentName();
//			if ("name".equals(fieldname)) {
//
//				// current token is "name",
//				// move to next, which is "name"'s value
//				jParser.nextToken();
//				System.out.println(jParser.getText()); // display mkyong
//
//			}
//
//			if ("age".equals(fieldname)) {
//
//				// current token is "age",
//				// move to next, which is "name"'s value
//				jParser.nextToken();
//				System.out.println(jParser.getIntValue()); // display 29
//
//			}
//
//			if ("messages".equals(fieldname)) {
//
//				jParser.nextToken(); // current token is "[", move next
//
//				// messages is array, loop until token equal to "]"
//				while (jParser.nextToken() != JsonToken.END_ARRAY) {
//
//					// display msg1, msg2, msg3
//					System.out.println(jParser.getText());
//
//				}
//
//			}
//
//			jParser.close();
//
//		}
//	}
//
//}
