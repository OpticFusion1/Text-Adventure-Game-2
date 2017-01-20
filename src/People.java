import java.util.ArrayList;
import java.util.List;

/**
 * Constructor, getter and set mothods for People class
 * @author Linh Pham, Eli Salm
 *
 */
public class People {
	private String name;
	private String response;
	public List<Item> item;
	
	/**
	 * Constructor for the people class
	 * @param name, the name of the person
	 * @param response, the response associated with the person
	 */
	public People(String name, String response) {
		this.setName(name);
		this.setResponse(response);
		this.item = new ArrayList<Item>();
	}

	/**
	 * Set the item list of the person holds
	 * @param item
	 */
	public void setItem(List<Item> item) {
		this.item = item;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public List<Item> getItem() {
		return this.item;
	}


	public void delete(){
		this.getItem().remove(0);
	}
	
}
