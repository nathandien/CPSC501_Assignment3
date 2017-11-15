import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class Deserializer {
	
	public Object deserialie(org.jdom2.Document document) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		SAXBuilder saxBuilder = new SAXBuilder();
		Element root = document.getRootElement();
		List<Element> elements = root.getChildren();
		HashMap<Integer, Object> hmap = new HashMap<Integer, Object>();
		
		for(int count = 0; count < elements.size(); count++) {
			// Sets arrObj and obj to null initially for later checking if object is an array
			Object arrObj = null;
			Object obj = null;
			Element currElement = elements.get(count);
			// Gets the current class
			Class currClass = Class.forName(currElement.getAttributeValue("class"));
			// Creates instance of currClass
			if(currClass.isArray()) {
				// Gets length from currElement
				int length = Integer.parseInt(currElement.getAttributeValue("length"));
				Class compType = currClass.getComponentType();
				obj = Array.newInstance(compType, length);
			}
			else {
				Constructor c;
				c = currClass.getConstructor(null);
				c.setAccessible(true);
				obj = c.newInstance();
			}
			// Gets the object ID
			int id = Integer.parseInt(currElement.getAttributeValue("id"));
			hmap.put(id, obj);
		}
		
		return document;
	}
	
	
}
