import java.util.*;

/**
 * Constructor and getter methods for the inventory class
 * @author Linh Pham, Eli Salm
 *
 */
public class Inventory {
	private Map<String, Item> itemList;
	private int weight;
	
	/**
	 * Constructor for the Inventory
	 */
	public Inventory() {
		Map<String, Item> temp = new HashMap<String, Item>();
		this.setItemList(temp);
		this.setWeight(0);
	}

	/**
	 * Getter function for Item List
	 * @return the Item List
	 */
	public Map<String, Item> getItemList() {
		return this.itemList;
	}

	/**
	 * Setter for ItemList
	 * @param itemList
	 */
	public void setItemList(Map<String, Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * Getter for weight
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Setter for weight
	 * @param weight, the weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Adding Item to the Item List
	 * @param item, an item
	 */
	public void addItem(Item item){
		itemList.put(item.getName().toLowerCase(), item);
		
	}
	
	/**
	 * Removing given item from the item list
	 * @param item, an item
	 */
	public void removeItem(Item item) {
		itemList.remove(item);
	}
}