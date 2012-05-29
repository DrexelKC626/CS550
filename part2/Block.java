import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Block {

	public static Block[] memory;
	public static int avail;
	
	// Initialize the array before using it.
	public static void initList(int size) {
		memory = new Block[size];
		avail = 0;

		for(int i = 0; i < memory.length; i++) {
			memory[i] = new Block(i, i + 1);
		}
		
		memory[memory.length - 1].setNext(-1);
	}
	
	public static int getNextAvail(HashMap<String, Element> nametable, LinkedList<String> var) {
		if(avail == -1) {
			// Do garbage collection 
			// You can get all the variable in the LinkedList, which should string
			// Get Element in the nametable, use function is list to check if the element is a list.
			// If so, get the list using function getListValue, which will return a block.
			// Then mark the block and all the block after it. You may need an extra private variable
			// to store the mark.
			// Then release blocks in the array that is not marked using the algorithm
			// mentioned in class.

			garbageCollection(nametable, var);
			
			if(avail == -1) {
				System.out.println("Memory full.");
				System.exit(1);
			}
		}
		int current = avail;
		avail = memory[avail].next;
		return current;
	}
	
	public static void garbageCollection(HashMap<String, Element> nametable, LinkedList<String> var) {
		Iterator<String> keyItr = nametable.keySet().iterator();
		
		// Mark
		while(keyItr.hasNext()) {
			String key = keyItr.next();
			Element element = nametable.get(key);
			if(element.isList()) {
				if(!element.isEmpty()) {
					markList(element.getListValue());
				}
			}
		}
		// Sweep
		for(int i = 0; i < memory.length; i++) {
			if(!memory[i].isMark()) {
				memory[i] = new Block(i, avail);
				avail = i;
			}
			memory[i].resetMark();
		}
	}
	
	// Mark all block in the list.
	public static void markList(Block list) {
		Block current = list;
		while(current.hasNext()) {
			current.mark();
			current = current.getNext();
		}
		current.mark();
	}
	
	private Element value;
	private int next;
	private int index;
	private boolean mark;
	
	public Block(Element value, HashMap<String, Element> nametable, LinkedList<String> var) {
		this.value = value;
		this.index = getNextAvail(nametable, var);
		next = -1;
		memory[index] = this;
	}
	
	public Block(int value, HashMap<String, Element> nametable, LinkedList<String> var) {
		this.value = new Element(value);
		this.next = -1;
		this.index = getNextAvail(nametable, var);
		memory[index] = this;
	}
	
	private Block(int index, int next) {
		this.next = next;
		this.index = index;
	}
	
	public Block(Element value, int next, HashMap<String, Element> nametable, LinkedList<String> var) {
		this.value = value;
		this.next = next;
		this.index = getNextAvail(nametable, var);
		memory[index] = this;
	}
	
	public Block(Block block, HashMap<String, Element> nametable, LinkedList<String> var) {
		this.value = block.getVaule();
		this.index = getNextAvail(nametable, var);
		next = -1;
		memory[index] = this;
		Block currentInput = block;
		Block current = this;
		String blockName = "tmpBlock";
		while(var.contains(blockName)) {
			blockName = blockName + "*";
		}
		var.add(blockName);
		nametable.put(blockName, new Element(current));
		while(currentInput.hasNext()) {
			currentInput = currentInput.getNext();
			current.setNext(new Block(currentInput.value, currentInput.next, nametable, var));
			current = current.getNext();
		}
		var.remove(blockName);
		nametable.remove(blockName);
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setNext(int next) {
		this.next = next;
	}
	
	public void setNext(Block next) {
		this.next = next.getIndex();
	}
	
	public Block getNext() {
		if(hasNext()) {
			return memory[next];
		}
		else {
			return null;
		}
	}
	
	public boolean hasNext() {
		if(next == -1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setValue(Element value) {
		this.value = value;
	}
	
	public Element getVaule() {
		return value;
	}
	
	// Append a list to current list.
	public void append(Block block) {
		Block current = this;
		while(current.hasNext()) {
			current = current.getNext();
		}
		current.setNext(block);
	}

	public String toString() {
		StringBuffer out = new StringBuffer("[");
		out.append(value.toString());
		Block current = this;
		while(current.hasNext()) {
			current = current.getNext();
			out.append(",");
			out.append(current.getVaule().toString());		
		}
		out.append("]");
		return out.toString();
	}
	
	public void resetMark() {
		mark = false;
	}
	
	public void mark() {
		mark = true;
	}
	
	public boolean isMark() {
		return mark;
	}
}
