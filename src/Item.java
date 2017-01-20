
/**
 * Constructor and getter and set methods for the Item class
 * @author Linh Pham, Eli Salm
 *
 */
public class Item {
	private String name;
	private int weight;
	private int value;
	public String description;
	public String keyTo;

	/**
	 * The constructor for the item class
	 * @param name, the name of the item
	 * @param description, the description of the item
	 * @param weight, the weight of the item
	 * @param keyTo, indicates which room this item unlocks
	 * @param value, the score player get for picking up the item
	 */
	public Item(String name, String description, int weight, String keyTo, int value) {
		this.setName(name);
		this.setWeight(weight);
		this.description  = description;
		this.keyTo = keyTo;
		this.setValue(value);
	}

	/**
	 * Get the name of the item
	 * @return name of item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name for the item
	 * @param name of the item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the weight of the item
	 * @return weight of the item
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Set the weight of the item
	 * @param weight of the item
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Get the room which this item unlocks
	 * @return
	 */
	public String getKeyTo(){
		return this.keyTo;
	}

	/**
	 * Get the value of the item
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set the value of the item
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
