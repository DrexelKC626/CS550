
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class Element {
	
	private boolean isList;
	private Integer intValue;
	private LinkedList<Element> listValue;
	
	// Create an element from a string.
	public static Element parseElement(String in) {	
		if(in.startsWith("[")) {
			String listString = in.substring(1, in.length() - 1);
			
			String[] parse = listString.split(",");
			Stack<LinkedList<Element>> listStack = new Stack<LinkedList<Element>>();
			
			LinkedList<Element> root = new LinkedList<Element>();
			
			int i = 0;
			
			while(i < parse.length) {
				String value = parse[i];
				
				if(value.length() == 0) {
					i++;
					continue;
				}
				
				// A new sub list.
				if(value.startsWith("[")) {
					listStack.push(root);
					root = new LinkedList<Element>();
					parse[i] = parse[i].substring(1);
					continue;
				}
				
				// The sub list ends.
				if(value.endsWith("]")) {
					// Get the value before a series of ]
					value = value.substring(0, value.indexOf("]"));
							
					if(value.length() > 0) {
						root.add(new Element(Integer.parseInt(value)));
					}
					
					// Append to last element of the supper-list
					if(!listStack.empty()) {
						LinkedList<Element> upperList = listStack.pop();
						upperList.add(new Element(root));
						root = upperList;
						parse[i] = parse[i].substring(parse[i].indexOf("]") + 1);
					}
					else {
						System.err.println("Illegal list structure.");
						System.exit(1);
					}
					continue;
				}
				
				root.add(new Element(Integer.parseInt(value)));
				i++;
			}
			
			return new Element(root);
		}
		else {
			return new Element(Integer.parseInt(in));
		}
	}
	
	public Element() {
		isList = false;
		intValue = 0;
		listValue = null;
	}
	
	// Way to create a new element with int.
	public Element(Integer value) {
		isList = false;
		intValue = value;
		listValue = null;
	}
	
	// Way to create a new element with list.
	public Element(LinkedList<Element> value) {
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
	
	public LinkedList<Element> getListValue() {
		if(!isList) {
			System.err.println("The element is not a List.");
			System.exit(1);
		}
		return listValue;
	}
	
	public boolean isList() {
		return isList;
	}
	
	public String toString() {
		if(isList) {
			if(listValue.size() == 0) {
				return "[]";
			}
			StringBuffer out = new StringBuffer("[");
			out.append(listValue.get(0).toString());
			Iterator<Element> itr = listValue.subList(1, listValue.size()).iterator();
			while(itr.hasNext()) {
				out.append(",");
				out.append(itr.next().toString());
			}
			out.append("]");
			return out.toString();
		}
		else {
			return intValue.toString();
		}
	}
}