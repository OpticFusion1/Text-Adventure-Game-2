
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main driver of the Text Adventure game
 * @author Linh Pham, Eli Salm
 *
 */
public class TextAdventure {

	/**
	 * Printing the greetings of the game 
	 */
	public static void printWelcome() {

		System.out.println("Welcome to the \"Amateur Bank Robber\" Adventure Game!");
		System.out.printf("\n");
		System.out.println("You burst into a small bank lobby. There are a few people "
				+ "around the room—a quiet day at the bank, as planned.");
		System.out.println(" “Uh... Attention! This is a bank robbery! Everybody just, uh, remain calm! "
				+ "I just want my money and then I'm out of here.” ");
		System.out.println("You read mixed emotions around the room of fear, confusion, and … bemusement?");
		System.out.println("Well, go on! Go and rob that bank so you can tell all of your friends about it!");
		System.out.printf("\n");


		System.out.println("Your goal is to successfully rob Main Street Bank and "
				+ "take away as much money as you can without getting caught.");
		System.out.println("You have 100 moves to get the bag of money from the Vault and bring it to the Trap Door!");
		System.out.printf("\n");
		System.out.println("Type 'help' to see the list of commands.");
		System.out.printf("\n");	
	}

	/**
	 * Create a list of room for our bank by reading the Room.txt file
	 * @return a list of room
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Room> createRoomList() throws FileNotFoundException {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Scanner fileScan = new Scanner(new File("Room.txt"));
		while(fileScan.hasNextLine()){
			String[] temp = fileScan.nextLine().split(",");
			roomList.add(new Room(temp[1], Integer.parseInt(temp[0]), 
					Boolean.parseBoolean(temp[2]), Boolean.parseBoolean(temp[3]))); 
		}
		fileScan.close();
		return roomList;

	}

	/**.
	 * Create a item list through reading the Items.txt file
	 * @return an item list
	 * @throws FileNotFoundException
	 */
	public static Map<String, Item> createItemList() throws FileNotFoundException {
		Map<String, Item> itemList = new HashMap<String, Item>();
		Scanner fileScan = new Scanner(new File("Items.txt"));
		while (fileScan.hasNextLine()) {
			String[] split = fileScan.nextLine().split(",");
			itemList.put(split[0], new Item(split[0], split[1], 
					Integer.parseInt(split[2]), split[3], Integer.parseInt(split[4])));
		}
		fileScan.close();
		return itemList;
	}

	/**
	 * Create a list of people by reading the Person.txt file 
	 * @param itemList
	 * @return a list of people
	 * @throws FileNotFoundException
	 */
	public static Map<String, People> createPeopleList(Map<String, Item> itemList) throws FileNotFoundException {
		Map<String, People> peopleList = new HashMap<String, People>();
		Scanner fileScan = new Scanner(new File("Person.txt"));
		while(fileScan.hasNextLine()){
			String[] split = fileScan.nextLine().split(",");
			List<Item> tempList = new ArrayList<Item>();
			if (split.length > 2){ tempList.add(itemList.get(split[2])); }
			People addPeople = new People(split[0], split[1]);
			addPeople.setItem(tempList);
			peopleList.put(split[0], addPeople);
		}
		fileScan.close();
		return peopleList;
	}


	/**
	 * Main driver of our game
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		printWelcome();

		ArrayList<Room> roomList = createRoomList();
		Map<String, Item> itemList = createItemList();
		Map<String, People> peopleList = createPeopleList(itemList);

		Scanner in = new Scanner(System.in);
		//Creates a new player and put him/her in the first room
		Player player = new Player("Paps", roomList.get(0), roomList);
		player.stockRoom(itemList);
		player.locatePeople(peopleList);
		System.out.println("You are at the " +  player.currentRoom.getName() + " of the Main Street Bank");
		String command = in.nextLine();
		while (!command.equals("quit")) {
			Parser.parse(player, command);
			player.time++;
			
			if (player.time == 100) {
				System.out.println("You have used up " + player.time + " times. I'm sorry. You suck!");
				break;
			}
			
			if (player.inventory.getItemList().containsKey("watch")) {
				int move = 100 - player.time;
				System.out.println("You have " + move + " move(s) left!" );
			}
			//wins the game if gets the bag of money and get to the trap door
			if (player.inventory.getItemList().containsKey("bag of money") && 
					player.currentRoom.getName().equals("TrapDoor")) {
				System.out.println("Yay! You have survived from the cops!!!!!! ");
				System.out.println("You have taken " + player.score + " money within " + player.time + " moves!" );
				break;
			}			
			command = in.nextLine();
		}
		in.close();
	}
}
