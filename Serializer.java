import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {

	public org.jdom2.Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException, IOException {
		
		// Sets root element to 'serialized'
		Element serialized = new Element("serialized");
		Document document = new Document(serialized);
		//document.setRootElement(serialized);
		
		// Produces unique identifier number for object
		int id = System.identityHashCode(obj);
		// Creates new element 'object'
		Element object = new Element("object");
		object.setAttribute(new Attribute("class", obj.getClass().getSimpleName()));
		object.setAttribute(new Attribute("id", Integer.toString(id)));
		document.getRootElement().addContent(object);

		
		// Gets all declared fields of obj
		Field[] allFields = obj.getClass().getDeclaredFields();
		Field[] filtFields = new Field[allFields.length];
		// Filters out static fields of obj
		for(int count = 0; count < allFields.length; count++) {
			String modifier = Modifier.toString(allFields[count].getModifiers());
			if(modifier.indexOf("static") == -1) 
				filtFields[count] = allFields[count];
		}

		// Creates new element 'field'
		for(int count2 = 0; count2 < filtFields.length; count2++) {
			
			Element field = new Element("field");
			filtFields[count2].setAccessible(true);
			// Gets field name and declaring class name to be stored as attributes
			String fieldName = filtFields[count2].getName();
			String declClass = filtFields[count2].getDeclaringClass().getName();
			field.setAttribute(new Attribute("name", fieldName));
			field.setAttribute(new Attribute("declaringclass", declClass));
			
			// Checks if field is primitive
			if(filtFields[count2].getType().isPrimitive()) {
				String value = filtFields[count2].get(obj).toString();
				field.addContent(new Element("value").setText(value));
			}
			else {
				// Produces unique identifier for object reference
				Object tempObj = filtFields[count2].get(obj);
				int fieldID = System.identityHashCode(tempObj);
				
				field.addContent(new Element("reference").setText(Integer.toString(fieldID)));
				
			}
			
			document.getRootElement().addContent(field);
		}
		
		// new XMLOutputter().output(doc, System.out);
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(document, new FileWriter("/home/nathan/XML.xml"));
		
		
		return document;
	}
	
}
