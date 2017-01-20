import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Constructor, getter and set methods for Room class
 * @author Linh Pham, Eli Salm
 *
 */
public class Room {
	private String name;
	private LinkedList<Item> itemList;
	private int index;
	private boolean visited;
	private boolean locked;
	private Map<String, People> people;

	/**
	 * Constructor for the room class
	 * @param name of the room
	 * @param index of the room
	 * @param visited whether the room is visited
	 * @param locked whether the room is locked
	 * @throws FileNotFoundException
	 */
	public Room(String name, int index, boolean visited, boolean locked) throws FileNotFoundException {
		this.index = index;
		this.setVisited(visited);
		this.setLocked(locked);
		this.setName(name);
		this.setItemList(new LinkedList<Item>());
		this.setPeople(new HashMap<String, People>());
	}

	/**
	 * Get the name of the room
	 * @return the name of the room
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the room
	 * @param name of the room
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the list of item in the room
	 * @return the list of item
	 */
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * Set the item list of the room
	 * @param itemList in the room
	 */
	public void setItemList(LinkedList<Item> itemList){
		this.itemList = itemList;
	}

	/**
	 * Add item to the room
	 * @param item an item
	 */
	public void addItem(Item item) {
		itemList.add(item);
	}

	/**
	 * Set if the room is locked or not
	 * @param bool
	 */
	public void lockRoom(boolean bool){
		this.setLocked(bool);
	}

	/**
	 * Get the index of the room
	 * @return the index of the room
	 */
	public int getIndex(){
		return this.index;
	}

	/**
	 * Get description of the item in the room
	 * @param itemName
	 * @return
	 */
	public String lookAt(String itemName) {
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getName().toLowerCase().equals(itemName)) {
				return itemList.get(i).description;
			}
		}
		return itemName + " is not in the room!";
	}

	/**
	 * Set if the room is visited
	 * @param visited a boolean
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Checks if the room is locked
	 * @return a boolean
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Set if the rooms if locked or not
	 * @param locked. a boolean
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * Get the map of people in the room
	 * @return a map of people in each room
	 */
	public Map<String, People> getPeople() {
		return people;
	}

	/**
	 * Set the map of people
	 * @param the map of people
	 */
	public void setPeople(Map<String, People> people) {
		this.people = people;
	}


}

