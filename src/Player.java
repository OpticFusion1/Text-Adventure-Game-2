import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.Iterator;

/**
 * Constructor and methods for the player of the Text Adventure game
 * @author Linh Pham, Eli Salm
 *
 */
public class Player {
	public String name;
	public int time;
	public Room currentRoom;
	public int score;
	public Inventory inventory;
	private List<Room> roomList;

	/**
	 * Constructor for player
	 * @param name, the name of the player
	 * @param room, the current room of the player
	 * @param roomList, the list of room of the bank
	 * @throws FileNotFoundException
	 */
	public Player(String name, Room room, List<Room> roomList) throws FileNotFoundException {
		this.inventory = new Inventory();
		this.name = name;
		this.time = 0;
		this.currentRoom = room;
		this.score = 0;
		this.roomList = roomList;
	}

	/**
	 * Checks if the inventory of the player has the key to the next room
	 * @return boolean
	 */
	private boolean hasKey(){
		Iterator<Item> iter = this.inventory.getItemList().values().iterator();
		for(int i = 0; i < this.inventory.getItemList().size(); i++){
			String temp = iter.next().getKeyTo();
			if(temp.equals(this.roomList.get(this.currentRoom.getIndex() + 1).getName())){
				return true;
			}	
		}return false;
	}

	/**
	 * Decide if the player can enter room;
	 */
	public void enterRoom() {
		if (hasKey()) {
			this.roomList.get(this.currentRoom.getIndex() + 1).lockRoom(false);
		} else {
			return;
		}
	}

	/**
	 * Set method for the current room of the player
	 * @param room, a room
	 */
	public void setRoom(Room room){
		this.currentRoom = room;
	}

	/**
	 * Check if the next door is locked or not
	 * @return boolean
	 */
	public boolean hasOpenDoorNorth(){
		return (!this.roomList.get(this.currentRoom.getIndex() + 1).isLocked());
	}

	/**
	 * Moving between room by changing index of the room based on room list and whether
	 * the player has the key to enter the room
	 * @param command, north or south
	 */
	public void moveRoom(String command){
		switch (command) {
		case "north":
			if (this.currentRoom.getIndex() < this.roomList.size() - 1) {
				this.enterRoom();
				if (this.hasOpenDoorNorth() && this.currentRoom.getIndex() < 4) {
					this.currentRoom = this.roomList.get(this.currentRoom.getIndex() + 1);
				} else {
					System.out.println("The door is locked.");
				}
			} else {
				System.out.println("Can't go north.");
			}
			break;
		case "south":
			if (this.currentRoom.getIndex() != 0) {
				this.currentRoom = this.roomList.get(this.currentRoom.getIndex() - 1);
			} else {
				System.out.println("You can't go south");
			}
			break;
		}
	}

	/**
	 * Stocking each room in the room list with items found in StockRoom.txt
	 * @param itemMap, a map of item created from items.txt
	 * @throws FileNotFoundException
	 */
	public void stockRoom(Map<String, Item> itemMap) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("StockRoom.txt"));
		while(scan.hasNextLine()){
			String temp = scan.nextLine();
			String[] split = temp.split(",");
			for(int i = 0; i < roomList.size(); i++){
				if(split[0].equals(roomList.get(i).getName())){
					for (int j = 1; j < split.length; j++) {
						roomList.get(i).addItem(itemMap.get(split[j]));
					}
					continue;
				}
			}
		}
		scan.close();
	}

	/**
	 * Adding the given item at the current room into the inventory of the player. If
	 * the weight is more than 10, the player need to drop some item from their
	 * inventory 
	 * @param itemName, the name of the item the player wants to pick up
	 */
	public void pickUp(String itemName) {
		if (this.inventory.getWeight() > 12) {
			System.out.println("Your inventory is too heavy. Drop some items!");
			return;
		}
		for (int i = 0; i < this.currentRoom.getItemList().size(); i++) {
			if (this.currentRoom.getItemList().get(i).getName().toLowerCase().equals(itemName)) {
				this.inventory.addItem(this.currentRoom.getItemList().get(i));
				System.out.println("You have picked up " + itemName + ".");
				System.out.println(this.currentRoom.getItemList().get(i).getWeight() + 
						" weight have been added to your inventory!");
				this.inventory.setWeight(this.inventory.getWeight() 
						+ this.currentRoom.getItemList().get(i).getWeight());

				this.currentRoom.getItemList().remove(i);
				this.score += this.inventory.getItemList().get(itemName).getValue();
				return;
			} 
		}
		System.out.println(itemName + " is not in the room!");
	}

	/**
	 * Drops the given item from the inventory
	 * @param itemName
	 */
	public void drop(String itemName) {
		if (this.inventory.getItemList().containsKey(itemName)) {
			this.currentRoom.addItem(this.inventory.getItemList().get(itemName));
			this.inventory.setWeight(this.inventory.getWeight()
					- this.inventory.getItemList().get(itemName).getWeight());
			this.inventory.getItemList().remove(itemName);
			System.out.println("You have dropped " + itemName);
			return;
		}
		System.out.println((itemName + " is not in your inventory"));
	}

	/**
	 * Locate the People around the room based on LocatePeople.txt
	 * @param peopleList, the map of name of people and people 
	 * @throws FileNotFoundException
	 */
	public void locatePeople(Map<String, People> peopleList) throws FileNotFoundException {
		Scanner in = new Scanner (new File("LocatePeople.txt"));
		while(in.hasNextLine()){
			String[] temp = in.nextLine().split(",");
			for (int i = 0; i < roomList.size(); i++) {
				if (temp[0].equals(roomList.get(i).getName())) {
					People cur = peopleList.get(temp[1]);
					roomList.get(i).getPeople().put(temp[1].toLowerCase(), cur);
					continue;
				}
			}
		}
		in.close();
	}

	/**
	 * Getting the response from the people in the room
	 * @param name, the name of people
	 * @return the response of the person 
	 */
	public String talkTo (String name){
		if (!currentRoom.getPeople().containsKey(name)) {
			return "You can't talk to " + name;
		} else {
			return	currentRoom.getPeople().get(name).getResponse();
		}
	}

	/**
	 * Threatens the person given his/her name and gets to know what items that person holds
	 * @param name
	 */
	public void threaten(String name){
		if (this.inventory.getItemList().containsKey("handgun")) {
			if (!currentRoom.getPeople().containsKey(name)) {
				System.out.println("You can't threaten " + name + "! " + 
			name + " is not in the room");
			} else {
				if (currentRoom.getPeople().get(name).getItem().size() == 0) {
					System.out.println("I dont have anything!"); 
				} else {
					System.out.println("I have " + 
				currentRoom.getPeople().get(name).item.get(0).getName()
							+ ". You can take it if you want!");
				}
			}
		} else {
			System.out.println("You can't threaten " + name + " without a gun!");
		}
	}

	/**
	 * Taking the item from the person
	 * @param name, the name of the person
	 * @param itemName, the item that the player wants to take from the given person
	 */
	public void takePersonItem(String name, String itemName){
		if (this.inventory.getItemList().containsKey("handgun")) {
			inventory.addItem(currentRoom.getPeople().get(name).item.get(0));
			currentRoom.getPeople().get(name).delete();
			this.score += this.inventory.getItemList().get(itemName).getValue();
			System.out.println("You have taken " + itemName);		
		} else {
			System.out.println("You can't take this item without a gun!");
		}
	}
}


