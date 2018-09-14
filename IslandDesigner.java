import java.util.*;
import big.data.DataSource;
/**
 * Driver program that simulates meny operations to run on an island that is loaded.
 * @author Dennis Heerlein
 * 		ID: 111689989
 * 		email: dennis.heerlein@stonybrook.edu
 *
 */
public class IslandDesigner {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Island Designer");
		
		System.out.print("Please enter a url: ");
		String url = input.nextLine();
		IslandNetwork island = IslandNetwork.loadFromFile(url);

                
        
        
		System.out.println("\nMenu:");
		System.out.println("\tD) Destinations reachable (Depth First Search)");
		System.out.println("\tF) Maximum Flow");
		System.out.println("\tS) Shortest Path (Extra Credit)");
		//System.out.println("\tS) Shortest Path (Extra Credit)");
		System.out.println("\tQ) Quit");
		System.out.println();
		String option = "";
		do {
			System.out.print("\nPlease select an option: ");
			option = input.nextLine();
			
			if (option.equalsIgnoreCase("D")) {
				System.out.print("Please enter a starting city: ");
				String start = input.nextLine();
				try {
					List<String> toPrint = island.dfs(start);
					System.out.println("DFS results (reachable destinations): ");
					for (int i = 0; i < toPrint.size(); i++) {
						System.out.println(toPrint.get(i));
					}
					island.resetDiscoveredVisited();
				} catch (InvalidCityNameException e) {
					System.out.println(e.getMessage());
				}
			}
			
			else if (option.equalsIgnoreCase("F")) {
				System.out.print("Please enter the starting city: ");
				String start = input.nextLine();
				System.out.print("Please enter the destination city: ");
				String dest = input.nextLine();
				/*CapacityHolder result = island.findCap(start, dest);
				for(String s : result.list) {
					System.out.println(s);
				} */
				System.out.println("Routing:");
				try {
					island.maxFlow(start, dest);
				}
				catch (InvalidCityNameException e) {
					System.out.println(e.getMessage());
				}
			}
			
			else if (option.equalsIgnoreCase("S")) {
				System.out.print("Please enter a starting node: ");
				String start = input.nextLine();
				System.out.print("Please enter a destination node: ");
				String dest = input.nextLine();
				try {
					island.dijkstra(start, dest);
				}
				catch (InvalidCityNameException e) {
					System.out.println(e.getMessage());
				}
			}
			
		} while (!(option.equals("Q") || option.equals("q")));
		
		System.out.println("Goodbye!");
	}
}
