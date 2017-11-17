import java.io.*;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {


	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, IOException, ClassNotFoundException, InstantiationException {

		Scanner scanner = new Scanner(System.in);

		if(args.length != 2) {
			System.out.println("Incorrect number of arguments: Sender <hostname> <port>");
			System.exit(1);
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		String input;
		int option;
		Object obj = null;
		Document document = null;
		ObjectCreator objCreator = new ObjectCreator();

		Serializer serializer = new Serializer();


		while(true) {

			System.out.println("Object Creator\n____________________________");
			System.out.println("Select an object to create:");
			System.out.println("(1) Simple Object");
			System.out.println("(2) Object with references to other objects");
			System.out.println("(3) Object with array of primitives");
			System.out.println("(4) Object with array of references");
			System.out.println("(5) Object using instance collection");
			System.out.println("(6) Stop object creation");

			try {
				input = scanner.nextLine();
				option = Integer.parseInt(input);


				if(option == 1) {
					System.out.println("\nCreating simple object:\n____________________________");
					obj = objCreator.makeSimpObj();
				}
				else if(option == 2) {
					System.out.println("\nCreating object with references:\n____________________________");
					obj = objCreator.makeRefObj(); 
				}
				else if(option == 3) {
					System.out.println("\nCreating object with array of primitves:\n____________________________");
					obj = objCreator.makeArrObj();
				}
				else if(option == 4) {
					System.out.println("\nCreating object with array of references:\n____________________________");
					obj = objCreator.makeArrRefObj();
				}
				else if(option == 5) {
					System.out.println("\nCreating object with collection classes:\n____________________________");
				}
				else if(option == 6) {
					break;
				}
				else {
					System.out.println("Invalid Option, must be a number from 1-5");
					throw new Exception();
				}
				document = serializer.serialize(obj);
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid option, not a number!");
			}
			catch(Exception e) {
				// Empty exception
			}

		}

		// new XMLOutputter().output(doc, System.out);
		XMLOutputter xmlOutput = new XMLOutputter();

		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(document, new FileWriter("XML.xml"));

		// Initializes socket
		Socket socket = new Socket(hostname, port);
		// Creates new file XML.xml to send to server
		File xmlFile = new File("XML.xml");
		
		// Initializes buffer for reading in XML data
		byte[] buffer = new byte[8192];
		
		// Initializes streams for reading and writing
		InputStream inStream = new FileInputStream(xmlFile);
        OutputStream outStream = socket.getOutputStream();
        
        int count;
        // Reads file and sends data to server
        while ((count = inStream.read(buffer)) > 0) {
        	outStream.write(buffer, 0, count);
        }
        
        // Closes streams and socket
        inStream.close();
        outStream.close();
        socket.close();
		
	}
}
