import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Receiver {
	
	public static void main(String[] args) throws JDOMException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		  SAXBuilder builder = new SAXBuilder();
		  File xmlFile = new File("XML.xml");
		  
		  Document document = (Document) builder.build(xmlFile);
		  Deserializer deserializer = new Deserializer();
		  RefObject a = (RefObject) deserializer.deserialize(document);

		  System.out.println(a.getObj().isBoolVal());
		  
	}
	
}
