import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;


public class Element {
	
	private boolean isList;
	private Integer intValue;
	private Block listValue;
	
	// Create an element from a string.
	public static Element parseElement(String in, HashMap<String, Element> nametable, LinkedList var) {	
		if(in.startsWith("[")) {
			String listString = in.substring(1, in.length() - 1);
			
			String[] parse = listString.split(",");
			Stack<Block> listStack = new Stack<Block>();
			Stack<Block> rootStack = new Stack<Block>();
			
			Block root = null;
			Block current = null;
			
			int i = 0;
			
			while(i < parse.length) {
				String value = parse[i];
				
				if(value.length() == 0) {
					i++;
					continue;
				}
				
				// A new sub list.
				if(value.startsWith("[")) {
					listStack.push(current);
					rootStack.push(root);
					root = null;
					current = null;
					parse[i] = parse[i].substring(1);
					continue;
				}
				
				// The sub list ends.
				if(value.endsWith("]")) {
					// Get the value before a series of ]
					value = value.substring(0, value.indexOf("]"));
					
					if(value.length() > 0) {
						if(root == null) {
							root = new Block(new Element(Integer.parseInt(value)), nametable, var);
							current = root;
						}
						else {
							current.setNext(new Block(new Element(Integer.parseInt(value)), nametable, var));
						}
					}
					
					// Append to last element of the supper-list
					if(!listStack.empty()) {
						Block supperList = listStack.pop();
						if(supperList == null) {
							root = new Block(new Element(root), nametable, var);
							current = root;
						}
						else {
							supperList.setNext(new Block(new Element(root), nametable, var));
							current = supperList;
							root = rootStack.pop();
						}
						parse[i] = parse[i].substring(parse[i].indexOf("]") + 1);
					}
					else {
						System.err.println("Illegal list structure.");
						System.exit(1);
					}
					continue;
				}
				
				if(root == null) {
					root = new Block(new Element(Integer.parseInt(value)), nametable, var);
					current = root;
				}
				else {
					current.setNext(new Block(new Element(Integer.parseInt(value)), nametable, var));
				}
				i++;
			}
			
			return new Element(root);
		}
		else {
			return new Element(Integer.parseInt(in));
		}
	}
	
	public Element() {
		isList = true;
		intValue = 0;
		listValue = null;
	}
	
	// Way to create a new element with int.
	public Element(int value) {
		isList = false;
		intValue = value;
		listValue = null;
	}
	
	// Way to create a new element with list.
	public Element(Block value) {
		isList = true;
		intValue = 0;
		listValue = value;
	}
	
	public int getIntValue() {
		if(isList) {
			System.err.println("The element is not a Integer.");
			System.exit(1);
		}
		return intValue;
	} 
	
	public Block getListValue() {
		if(!isList) {
			System.err.println("The element is not a List.");
			System.exit(1);
		}
		return listValue;
	}
	
	public boolean isList() {
		return isList;
	}
	
	public boolean isEmpty() {
		if(isList) {
			if(listValue == null) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		if(isList) {
			if(isEmpty()) {
				return "[]";
			}
			return listValue.toString();
		}
		else {
			return intValue.toString();
		}
	}
}
