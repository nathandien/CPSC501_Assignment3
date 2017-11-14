import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
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
		String id = Integer.toString(System.identityHashCode(obj));
		// Creates new element 'object'
		Element object = new Element("object");
		object.setAttribute(new Attribute("class", obj.getClass().getSimpleName()));
		object.setAttribute(new Attribute("id", id));
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
				Element tempEle = serializeRefObj(tempObj);
				field.addContent(tempEle);
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

	public org.jdom2.Element serializeRefObj(Object obj) throws IllegalArgumentException, IllegalAccessException {

		int length = 1;
		Element object = new Element("object");
		// Produces unique identifier number for object
		String id = Integer.toString(System.identityHashCode(obj));

		String className = obj.getClass().getName();
		object.setAttribute(new Attribute("class", className));
		object.setAttribute(new Attribute("id", id));

		// Adds all array elements to object element
		if(obj.getClass().isArray()) {
			length = Array.getLength(obj);
			object.setAttribute(new Attribute("length", Integer.toString(length)));

			// Adds all values from the array
			for(int countArr = 0; countArr < length; countArr++) {
				String arrVal = Array.get(obj, countArr).toString();
				object.addContent(new Element("value").setText(arrVal));
			}
		}
		else {
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
					Element tempEle = serializeRefObj(tempObj);
					field.addContent(tempEle);

				}
			object.addContent(field);
			}
		}

		return object;
	}

}
