import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Receiver {

	public static void main(String[] args) throws Exception {

		if(args.length != 1) {
			System.out.println("Incorrect number of arguments: Receiver <port>");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		// Connects to client
		ServerSocket serverSocket = new ServerSocket(port);
		Socket socket = serverSocket.accept();
	
		
		InputStream inStream = socket.getInputStream();
		// Creates a new file to receive XML file from client
		File xmlFile = new File("XMLReceived.xml");
		OutputStream outStream = new FileOutputStream(xmlFile);
		
        
        int count;
        byte[] buffer = new byte[8192];
        // Reads data from client and writes to file
        while ((count = inStream.read(buffer)) > 0) {
        	outStream.write(buffer, 0, count);
        }
        
        // Closes streams and socket
        inStream.close();
        outStream.close();
        socket.close();
        serverSocket.close();
        
		
		// Builds file
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document) builder.build(xmlFile);
		
		Deserializer deserializer = new Deserializer();
		Object obj = deserializer.deserialize(document);

		Inspector inspector = new Inspector(obj);
		inspector.inspect(obj, true);

	}

}
