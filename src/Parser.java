import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * Language parser that interprets user commands
 * @author Linh Pham, Eli Salm
 *
 */
public class Parser {
	/**
	 * Help function to get a list of commands
	 */
	public static void help() {
		System.out.println("List of commands:");
		System.out.printf("\n");
		System.out.println("current room");
		System.out.println("search room");
		System.out.println("search people");
		System.out.println("inventory");
		System.out.println("go north/south");
		System.out.println("take + <item>");
		System.out.println("take + <item> + from + <person>");
		System.out.println("take all");
		System.out.println("drop + <item>");
		System.out.println("combine + <item> + with + <item>");
		System.out.println("threaten + <person>");
		System.out.println("describe + <item>");
		System.out.println("talk to + <person>");
		System.out.println("quit");
	}

	/**
	 * Interprets the command from the user and responds accordingly
	 * @param player, the player
	 * @param code, the command from the player
	 * @throws FileNotFoundException
	 */
	public static void parse(Player player, String code) throws FileNotFoundException {
		//splitting the command based on space
		String[] split = code.split(" ");
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].toLowerCase();
		}
		if (split.length == 1 && !split[0].equalsIgnoreCase("help") && 
				!split[0].equalsIgnoreCase("inventory")) {
			System.out.println("This is not a valid command. Type help to get instruction");
			return;
		}
		//switch cases based on the command
		switch (split[0].toLowerCase()) {
		case "help":
			help();
			break;
		case "go":
			if (split[1].equals("north")) {
				player.moveRoom("north");
				System.out.println("You are at the " + player.currentRoom.getName() + "!");

			} else if (split[1].equals("south")) {
				player.moveRoom("south");
				System.out.println("You are at the " + player.currentRoom.getName() + "!");
			} else {
				System.out.println("This is not a valid command. Type help to get instruction");
			}
			break;
			
		case "search":
			if (split[1].equals("room")) {
				System.out.printf("Found the following item(s)\n\n");
				for(int i = 0; i < player.currentRoom.getItemList().size(); i++) {
					System.out.println(player.currentRoom.getItemList().get(i).getName());
				}
			} else if (split[1].equals("people")){
				System.out.printf("Found the following people\n");
				Iterator<People> iter = player.currentRoom.getPeople().values().iterator();
				while (iter.hasNext()) {
					System.out.println(iter.next().getName());
				}
			} else {
				System.out.println("This is not a valid command. Type help to get instruction");
			}
			break;

		case "inventory":
			Iterator<Item> iter = player.inventory.getItemList().values().iterator();
			if (!iter.hasNext()) {
				System.out.println("You have nothing in your inventory!");
			}
			while (iter.hasNext()) {
				Item item = iter.next();
				if (!item.getName().equals("money")) {
					System.out.printf("%s, weight %d \n", item.getName(), item.getWeight());
				}
			}
			break;

		case "take":
			if (player.currentRoom.getPeople().containsKey(split[split.length-1])) {
				String temp = "";
				for(int i = 1; i < split.length - 2; i++){
					temp += split[i];
				}
				player.takePersonItem(split[split.length-1], temp);
			} else if (split[1].equals("all")){
				for (int i = player.currentRoom.getItemList().size(); i > 0; i--) {
					player.pickUp(player.currentRoom.getItemList().get(0).getName().toLowerCase());
				}
			} else {
				String item = "";
				for (int i = 1; i < split.length - 1; i++) {
					item += split[i];
					item += " ";
				}
				item += split[split.length - 1];
				player.pickUp(item);
			}
			break;

		case "current":
			if (split[1].equals("room")) {
				System.out.printf("You are at the %s\n\n", player.currentRoom.getName());
			} else {
				System.out.println("This is not a valid command. Type help to get instruction");
			}
			break;

		case "describe":
			String itemName = "";
			for (int i = 1; i < split.length - 1; i++) {
				itemName += split[i];
				itemName += " ";
			}
			itemName += split[split.length - 1];
			System.out.println(player.currentRoom.lookAt(itemName));
			break;

		case "drop":
			String itemDropName = "";
			for (int i = 1; i < split.length - 1; i++) {
				itemDropName += split[i];
				itemDropName += " ";
			}
			itemDropName += split[split.length - 1];
			player.drop(itemDropName);
			break;

		case "talk":
			if (split[1].equals("to")) {
				System.out.println(player.talkTo(split[2]));
			} else {
				System.out.println("This is not a valid command. Type help to get instruction");
			}
			break;
			
		case "threaten":
			player.threaten(split[1]);
			break;
			
		case "combine":
			int cur = 2;
			while(cur < split.length){
				if(split[cur].equals("with")){ cur++; break; }
				else split[1] += " " + split[cur];
				cur++;
			}
			for(int i = cur + 1; i < split.length; i++){
				split[cur] += " " + split[i];
			}
			//create new item Bomb if the player combines string and explosives
			if((split[1].equals("explosives") && split[3].equals("string")) ||
					(split[1].equals("string") && split[3].equals("explosives")) &&
					player.inventory.getItemList().containsKey("explosives") &&
					player.inventory.getItemList().containsKey("string")){
				player.inventory.getItemList().remove("explosives");
				player.inventory.getItemList().remove("string");
				player.inventory.getItemList().put("time bomb", 
						new Item("Time Bomb","A bomb.",10,"Closet",0));
				System.out.println("Yeahhhhh! Check your inventory!");
			} else if (!split[cur - 1].equals("with")){
				System.out.println("Incorrect command. Try format: combine <item 1> with <item 2>");
			} else {
				System.out.println("You cannot combine these items!");
			}
			break;

		default:
			System.out.println("This is not a valid command. Type help to get instruction");
			break;
		}
	}
}
