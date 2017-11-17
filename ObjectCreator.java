import java.io.IOException;
import java.util.Scanner;

public class ObjectCreator {

	public static Scanner scanner;

	public ObjectCreator() {
		this.scanner = new Scanner(System.in);
	}

	/**
	 * Creates a simple object with values set by user input
	 * @return	Simple object
	 */
	public Object makeSimpObj() {

		String input;
		SimpleObject obj = new SimpleObject();

		// Sets value of integer and double
		while(true) {
			try {
				System.out.println("Enter integer value to be stored");
				input = scanner.nextLine();
				obj.setIntegerNum(Integer.parseInt(input));

				System.out.print("Enter double value to be stored");
				input = scanner.nextLine();
				obj.setDoubleNum(Double.parseDouble(input));

				break;
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid value, not a number!");
			}
		}


		// Sets value of boolean
		while(true) {
			System.out.print("Enter boolean value (true/false) to be stored");
			input = scanner.nextLine();

			if(input.equalsIgnoreCase("true")) {
				obj.setBoolVal(true);
				break;
			}
			else if(input.equalsIgnoreCase("false")) {
				obj.setBoolVal(false);
				break;
			}
			else
				System.out.println("Invalid balue, must be 'true' or 'false'");
		}

		return obj;
	}

	/**
	 * Creates a reference object with simple objects stored and set by user input
	 * @return Reference Object
	 */
	public Object makeRefObj() {

		String input;
		RefObject refObj = new RefObject();
		SimpleObject obj = new SimpleObject();
		SimpleObject obj2 = new SimpleObject();

		// Sets value of integer and double for obj
		while(true) {
			try {
				System.out.println("Enter integer value to be stored in object 1");
				input = scanner.nextLine();
				obj.setIntegerNum(Integer.parseInt(input));

				System.out.print("Enter double value to be stored in object 1");
				input = scanner.nextLine();
				obj.setDoubleNum(Double.parseDouble(input));

				break;
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid value, not a number!");
			}
		}

		// Sets value of boolean of object 1
		while(true) {
			System.out.print("Enter boolean value (true/false) to be stored in object 1");
			input = scanner.nextLine();

			if(input.equalsIgnoreCase("true")) {
				obj.setBoolVal(true);
				break;
			}
			else if(input.equalsIgnoreCase("false")) {
				obj.setBoolVal(false);
				break;
			}
			else
				System.out.println("Invalid value, must be 'true' or 'false'");
		}

		// Sets value of integer and double for obj2
		while(true) {
			try {
				System.out.println("Enter integer value to be stored in object 2");
				input = scanner.nextLine();
				obj2.setIntegerNum(Integer.parseInt(input));

				System.out.print("Enter double value to be stored in object 2");
				input = scanner.nextLine();
				obj2.setDoubleNum(Double.parseDouble(input));

				break;
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid value. not a number!");
			}
		}

		// Sets value of boolean of object 2
		while(true) {
			System.out.print("Enter boolean value (true/false) to be stored in object 2");
			input = scanner.nextLine();

			if(input.equalsIgnoreCase("true")) {
				obj2.setBoolVal(true);
				break;
			}
			else if(input.equalsIgnoreCase("false")) {
				obj2.setBoolVal(false);
				break;
			}
			else
				System.out.println("Invalid option, must be 'true' or 'false'");
		}

		// Sets the objects in refObj
		refObj.setObj(obj);
		refObj.setObj2(obj2);

		return refObj;
	}

	public Object makeArrObj() {

		ArrayObject arrObj = new ArrayObject();
		String input;
		int[] integerArr;
		double[] doubleArr;
		boolean[] boolArr = null;

		// Sets value of integer and double arrays
		while(true) {
			try {

				// Sets the values of integer array
				System.out.println("Enter the length of integer array");
				input = scanner.nextLine();
				int intLength = Integer.parseInt(input);
				integerArr = new int[intLength];

				// Sets of value of integer array
				for(int count = 0; count < intLength; count++) {
					System.out.println("Enter the value of integer " + count);
					input = scanner.nextLine();
					integerArr[count] = Integer.parseInt(input);
				}

				// Sets the length of double array
				System.out.println("Enter the length of double array");
				input = scanner.nextLine();
				int doubLength = Integer.parseInt(input);
				doubleArr = new double[doubLength];

				// Sets the values of double array
				for(int count2 = 0; count2 < intLength; count2++) {
					System.out.println("Enter the value of integer " + count2);
					input = scanner.nextLine();
					doubleArr[count2] = Double.parseDouble(input);
				}
				arrObj.setIntegerArr(integerArr);
				arrObj.setDoubleArr(doubleArr);

				break;
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid value, not a number!");
			}
		}

		int boolLength = 0;
		// Sets the length of boolean array
		try {
			System.out.println("Enter the length of boolean array");
			input = scanner.nextLine();
			boolLength = Integer.parseInt(input);
			boolArr = new boolean[boolLength];
		}
		catch(NumberFormatException nfe) {
			System.out.println("Invalid value, not a number");
		}

		// Sets the values of boolean array
		for(int count = 0; count < boolLength; count++) {
			while(true) {
				System.out.print("Enter boolean value (true/false) to be stored in boolean " + count);
				input = scanner.nextLine();

				if(input.equalsIgnoreCase("true")) {
					boolArr[count] = true;
					break;
				}
				else if(input.equalsIgnoreCase("false")) {
					boolArr[count] = false;
					break;
				}
				else
					System.out.println("Invalid option, must be 'true' or 'false'");
			}
		}
		arrObj.setBoolArr(boolArr);

		return arrObj;
	}

	public Object makeArrRefObj() {

		ArrayRefObject obj = new ArrayRefObject();
		String input;
		SimpleObject[] simpObj = null;
		int length = 0;

		while(true) {

			// Gets number of SimpleObjects
			try {
				System.out.println("Enter the number of SimpleObjects");
				input = scanner.nextLine();
				length = Integer.parseInt(input);
				simpObj = new SimpleObject[length];
				
				// Initialize all SimpleObjects
				for(int count = 0; count < length; count++)
					simpObj[count] = new SimpleObject();
				
				break;
			}
			catch(NumberFormatException nfe) {
				System.out.println("Invalid value, not a number");
			}
		}

		int count = 0;
		while(true) {
			if(count >= length)
				break;
			else {
				try {

					// Sets value of integer and double for SimpleObject count
					System.out.println("Enter value of integer for SimpleObject " + count);
					input = scanner.nextLine();
					simpObj[count].setIntegerNum(Integer.parseInt(input));

					System.out.print("Enter value of double for SimpleObject " + count);
					input = scanner.nextLine();
					simpObj[count].setDoubleNum(Double.parseDouble(input));

					// Sets value of boolean
					while(true) {
						System.out.print("Enter boolean value (true/false) for SimpleObject " + count);
						input = scanner.nextLine();

						if(input.equalsIgnoreCase("true")) {
							simpObj[count].setBoolVal(true);
							break;
						}
						else if(input.equalsIgnoreCase("false")) {
							simpObj[count].setBoolVal(false);
							break;
						}
						else
							System.out.println("Invalid balue, must be 'true' or 'false'");
					}

					count++;
				}
				catch(NumberFormatException nfe) {
					System.out.println("Invalid value, not a number");
				}
			}
		}
		obj.setObj(simpObj);

		return obj;
	}
}
