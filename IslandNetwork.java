import java.util.*;

import big.data.DataSource;

/**
 * A class that represents the island that contains the cities, and performs search operations on it.
 * @author Dennis Heerlein
 * 		ID: 111689989
 * 		email: dennis.heerlein@stonybrook.edu
 *
 */
public class IslandNetwork {

	private HashMap<String, City> graph = new HashMap<>();
	private CapacityHolder result = new CapacityHolder();
	
	/**
	 * Loads an island from a URL.
	 * @param url To load the island from
	 * @return an IslandNetwork to perform the menu operations on.
	 */
	public static IslandNetwork loadFromFile(String url) {	
		IslandNetwork nIsland = new IslandNetwork();
		DataSource ds = DataSource.connectXML(url);
        ds.load();
        String cityNamesStr=ds.fetchString("cities");
        String[] cityNames=cityNamesStr.substring(1,cityNamesStr.length()-1).replace("\"","").split(",");
        String roadNamesStr=ds.fetchString("roads");
        String[] roadNames=roadNamesStr.substring(1,roadNamesStr.length()-1).split("\",\"");
        
        for(int i = 0; i < cityNames.length-1; i++) {
        	for(int j = 0; j < cityNames.length-i-1; j++)
        		if(cityNames[j].compareTo(cityNames[j+1]) > 0) {
        			String temp = cityNames[j];
        			cityNames[j] = cityNames[j+1];
        			cityNames[j+1] = temp;
        		}
        }
        System.out.println("\nCities: ");
        System.out.println("---------------------");
        for(int i = 0; i < cityNames.length; i++) {
        	City nCity = new City(cityNames[i]);
        	nIsland.graph.put(cityNames[i], nCity);
        	System.out.println(nCity.getName());
        } 
        System.out.println("\nRoad\t\t\tCapacity");
        System.out.println("----------------------------------------------");
        for(int i = 0; i < roadNames.length; i++) {
        	String road = roadNames[i].replaceAll("\"", "");
        	String start = road.substring(0, road.indexOf(","));
        	String dest = road.substring(road.indexOf(",")+1, road.indexOf(",", road.indexOf(",")+1));
        	int num = Integer.parseInt(road.substring(road.indexOf(",", road.indexOf(",")+1)+1));
        	nIsland.getGraph().get(start).addNeighbor(nIsland.getGraph().get(dest), num);
        	nIsland.getGraph().get(start).addTempNeighbor(nIsland.getGraph().get(dest), num); // newly added
        	System.out.println(start + " to " + dest + "    " + num);
        }
        
        return nIsland;
	}
	
	/**
	 * Calls a helper method to find the max flow from a city to another destination.
	 * @param from The start city
	 * @param to The end city
	 * @throws InvalidCityNameException If one of the city names is invalid
	 */
	public void maxFlow(String from, String to) throws InvalidCityNameException {
		if(!(graph.containsKey(from) && graph.containsKey(to))) {
			throw new InvalidCityNameException("Invalid city entered.");
		}
		helper(from, to);
	}
	
	/**
	 * The helper method that similates more than one flow from the "from" to "to" city. Calls findCap to accomplish this.
	 * @param from The start city
	 * @param to The end city
	 */
	public void helper(String from, String to) {
		int capSum = 0;
		CapacityHolder currentCH = findCap(from, to);
		while(currentCH.getCap() > 0) {
			
			for(int i = currentCH.list.size()-1 ; i >= 0; i--) {
				if(i == 0)
					System.out.println(currentCH.list.get(i) + ": " + currentCH.getCap());
				else
					System.out.print(currentCH.list.get(i) + "->");
			}
			
			capSum += currentCH.getCap();
			for (int i = currentCH.list.size()-1 ; i > 0; i--) {
				City city = graph.get(currentCH.list.get(i));
				int oldCap = city.getTempNeighbors().get(currentCH.list.get(i-1));
				int newCap = oldCap - currentCH.getCap();
				//city.getTempNeighbors().remove(currentCH.list.get(i-1));
				city.getTempNeighbors().put(currentCH.list.get(i-1), newCap);
			}
			currentCH = findCap(from, to);
		}
		if (capSum == 0)
			System.out.println("No route available!");
		else
			System.out.println("Maximum flow: " + capSum);
	}
	
	/**
	 * Simulates one flow from "from" to "to" cities
	 * @param from The start city
	 * @param to The end city
	 * @return a CapacityHolder with a list of cities and the capacity that represents the max flow between them.
	 */
	public CapacityHolder findCap(String from, String to) {
		int maxCap = 0;
		CapacityHolder bestNeighbor = new CapacityHolder();
		for (String key : graph.get(from).getTempNeighbors().keySet()) {
			CapacityHolder result = new CapacityHolder();
			if(graph.get(key) == graph.get(to)) { 
				result.setCap(graph.get(from).getTempNeighbors().get(key));
				result.list.add(key);
			}
			else {
				result = (findCap(key, to));
				result.setCap(Math.min(result.getCap(), graph.get(from).getTempNeighbors().get(key).intValue()));
			}
			if(result.getCap() > maxCap) {
				maxCap = result.getCap();
				bestNeighbor = result;
			}
			
		}
		bestNeighbor.list.add(from);
		return bestNeighbor;
	}
	
	/**
	 * Calls the real depth-first search method, but checks the city names first.
	 * @param from The start city
	 * @return a list of reachable destinations in city names
	 * @throws InvalidCityNameException if the "from" city name is invalid
	 */
	public List<String> dfs(String from) throws InvalidCityNameException {
		if(!graph.containsKey(from)) {
			throw new InvalidCityNameException("Invalid city entered.");
		}
		ArrayList<String> result = new ArrayList<>();
		return dfs2(from, result);
	}
	
	/**
	 * Simulates depth-first search on the "from" city. 
	 * @param from The start city
	 * @param result Stores the reachable cities
	 * @return A list of reachable city names
	 */
	public List<String> dfs2(String from, ArrayList<String> result) {
		graph.get(from).visit();
		for (String key : graph.get(from).getNeighbors().keySet()) {
			if(!graph.get(key).visited()) {
				result.add(graph.get(key).getName());
				dfs2(graph.get(key).getName(), result);
			} 
		}
		return result;
	}
	
	/**
	 * Adds a city to the Island's graph
	 * @param n The new city
	 */
	public void addCity(City n) {
		graph.put(n.getName(), n);
	}
	
	/**
	 * Changes the reference of the graph reference variable to a new graph
	 * @param n The new graph the reference variable points to
	 */
	public void setGraph(HashMap<String, City> n) {
		graph = n;
	}
	
	/**
	 * Resets the discovered and visited variables of each city in the graph.
	 */
	public void resetDiscoveredVisited() {
		for(City i : graph.values()) {
			i.unvisit();
			i.undiscover();
		}
	}
	
	/**
	 * Returns the Hash Map representing the cities on the island
	 * @return the Hash Map representing the cities on the island
	 */
	public HashMap<String, City> getGraph() {
		return graph;
	}
	
	/**
	 * Performs dijkstra's algorithm on the "from" to the "to" city
	 * @param from The start city
	 * @param to The end city
	 * @throws InvalidCityNameException If either city name refers to a city that does not exist in the graph
	 */
	public void dijkstra(String from, String to) throws InvalidCityNameException {
		if(!(graph.containsKey(from) && graph.containsKey(to))) {
			throw new InvalidCityNameException("Invalid city entered.");
		}
		ArrayList<City> q = new ArrayList<City>();
		for(String key : graph.keySet()) {
			graph.get(key).setDist(Integer.MAX_VALUE);
			graph.get(key).setPrev(null);
			q.add(graph.get(key));
		}
		graph.get(from).setDist(0);
		
		City minC = null;
		while(!q.isEmpty()) {
			int min = 1000;
			for(City c : q) {
				if(min > c.getDist()) {
					min = c.getDist();
					minC = c;
				}
			}
			q.remove(minC);
			if (minC.getName().equals(graph.get(to).getName())) {
				break;
			}
			
			for(String s : minC.getNeighbors().keySet()) {
				int alt = min + minC.getNeighbors().get(s);
				if (alt < graph.get(s).getDist()) {
					graph.get(s).setDist(alt);
					graph.get(s).setPrev(minC);
				}
			}
		}
		LinkedList<City> s = new LinkedList<>();
		City target = minC;
		while(target.getPrev() != null) {
			s.addFirst(target);
			target = target.getPrev();
		}
		s.addFirst(target);

		System.out.print("Path: ");
		for(City c : s) {

			if(s.indexOf(c) == s.size()-1) {
				System.out.print(c.getName());
				System.out.println("\nCost: " + c.getDist());
			}
			else
				System.out.print(c.getName() + "->");
		}
		
	}
}
