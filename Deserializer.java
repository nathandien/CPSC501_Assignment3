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

	private List<Element> objElements;

	public Object deserialize(org.jdom2.Document document) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		SAXBuilder saxBuilder = new SAXBuilder();
		Element root = document.getRootElement();
		Object arrObj = null;
		Object obj = null;

		if(objElements == null) {
			objElements = root.getChildren();
		}
		HashMap<Integer, Object> hmap = new HashMap<Integer, Object>();

		for(int count = 0; count < objElements.size(); count++) {
			// Sets arrObj and obj to null initially for later checking if object is an array
			Element currElement = objElements.get(count);
			// Gets the current class
			System.out.println(currElement.getAttributeValue("class"));
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

			// Assigns values to instance variables
			if(obj.getClass().isArray()) {
				
			}
			else {
				List<Element>fieldElements = currElement.getChildren();
				System.out.println(currElement.getAttributeValue("class") + objElements.get(count).getChildren());

				// Gets all instance variables
				for(int fieldCount = 0; fieldCount < fieldElements.size(); fieldCount++) {
					Element currFieldEle = fieldElements.get(fieldCount);
					String fieldName = currFieldEle.getAttributeValue("name");
					try {
						// Gets the current field given by fieldName
						Field field = obj.getClass().getDeclaredField(fieldName);
						// Sets accessible in case the field is private
						field.setAccessible(true);
						// Assigns the value to the field
						if(isPrimitive(field.getType())) {
							// Gets value from current field element
							String value = currFieldEle.getChildText("value");
							field.set(obj, parse(field.getType(), value));
						}
						else {
							// Gets object reference element and name
							Element objRef = currFieldEle.getChild("object");
							String objRefName = objRef.getAttribute("class").getValue();

							// Creates instance of object reference
							Class currEleClass = Class.forName(objRefName);
							Constructor con = currEleClass.getConstructor(null);
							con.setAccessible(true);
							Object eleObj = con.newInstance();

							// Gets all elements of object reference
							List<Element>objRefEle = objRef.getChildren();
							
							// Sets all values of fields in eleObj
							for(int refCount = 0; refCount < objRefEle.size(); refCount++) {
								Field refField = currEleClass.getDeclaredFields()[refCount];
								refField.setAccessible(true);
								String value = objRefEle.get(refCount).getChildText("value");
								refField.set(eleObj, parse(refField.getType(), value));
							}
							
							// Sets the object reference field
							field.set(obj, (eleObj));


						}
					} catch (NoSuchFieldException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		return obj;
	}
	
	private Object setObj(Object obj, String value) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] fields = obj.getClass().getFields();
		
		for(int count = 0; count < fields.length; count++) {
			
			fields[count].set(obj, parse(fields[count].getType(),value));
			
		}
		
		return obj;
	}

	/**
	 * Source: https://stackoverflow.com/questions/209366/how-can-i-generically-tell-if-a-java-class-is-a-primitive-type
	 * @param c
	 * @return boolean value
	 */
	private boolean isPrimitive(Class c) {
		if (c.isPrimitive()) {
			return true;
		} else if (c == Byte.class
				|| c == Short.class
				|| c == Integer.class
				|| c == Long.class
				|| c == Float.class
				|| c == Double.class
				|| c == Boolean.class
				|| c == Character.class) {
			return true;
		} else {
			return false;
		}

	}


	private Object parse(Class c, String val) {
		if(c == Byte.class || c == Byte.TYPE) 
			return Boolean.parseBoolean(val);
		else if(c == Short.class || c == Short.TYPE)
			return Short.parseShort(val);
		else if(c == Integer.class || c == Integer.TYPE)
			return Integer.parseInt(val);
		else if(c == Long.class || c == Long.TYPE)
			return Long.parseLong(val);
		else if(c == Float.class || c == Float.TYPE)
			return Float.parseFloat(val);
		else if(c == Double.class || c == Double.TYPE)
			return Double.parseDouble(val);
		else if(c == Boolean.class || c == Boolean.TYPE)
			return Boolean.parseBoolean(val);
		else
			return null;
	}

}
