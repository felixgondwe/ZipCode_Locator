import java.util.Scanner;
/*
 * This class represents the user. it creates a map object that the user can use
 * It also has operations that the user can use to manipulate information of zip codes contained in the map
 */

public class User {
	//instance data
	Scanner scanner;
	String zipcode;
	AMap map;

	//constructor 
	//initialize instance data
	public User(){
		map = new AMap("http://csweb.stlawu.local/~ehar/data/zips1990.txt");
		zipcode = "";
		scanner = new Scanner(System.in);
		commands();
	}

	/*
	 * print out user commands
	 */
	private void commands() {
		System.out.println(">>>>>>>>>>>> Welcome <<<<<<<<<<<<");
		System.out.println("1) Zip code data\n" +
				"2) Mainland zip codes that are furthest NSEW.\n" + 
				"3) Distance between two zip codes\n" +
				"4) Quit");
		//begin commands
		getCommands();
	}
	
	/*
	 * get commands from the user
	 */
	private void getCommands(){
		//flag
		boolean run = true;
		while(run){
			System.out.println("Enter option: ");
			int userInput = scanner.nextInt();
			if(userInput == 1){
				map.option1();
				System.out.println("................................................................................\n");
			}
			else if(userInput == 2){
				map.option2();
				System.out.println("................................................................................\n");
			}
			else if(userInput == 3){
				map.option3();
				System.out.println("................................................................................\n");
				}
			else if(userInput == 4){
				run = false;
				System.out.println(">>>>>>>>>>>>>>>Thank you for using our program<<<<<<<<<<<<<<<");
			}
		}

	}
	

} // end class User
