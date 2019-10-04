import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGame<V,E> {
	static String actors = "PS4/bacon/actors.txt"; //files to be read
	static String movies = "PS4/bacon/movies.txt";
	static String movieactors = "PS4/bacon/movie-actors.txt";
	
	public static Map<String, String> actorsMap() throws IOException{
		Map<String, String> actorsMap = new HashMap<String, String>();
		FileReader fileReader = new FileReader(actors);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		try {
			String read = bufferedReader.readLine();
			while (read != null) {
				String[] results = read.split("\\|"); //splits line, makes array
				String actorID = results[0]; //sets the first element (number) to be the actorID
				String actorName = results[1]; // sets the second element (name) to be the actorName
				actorsMap.put(actorID, actorName); //insert actorID key with corresponding actorName into actorMap
				read = bufferedReader.readLine(); //read next line
			}
		}
		
		catch(FileNotFoundException e){
			System.out.println("Not Found");
		}
			
		finally {
			bufferedReader.close();
		}
		
		return actorsMap;
	}
	
	public static Map<String, String> moviesMap() throws IOException{
		Map <String, String> moviesMap = new HashMap<String, String>();
		FileReader fileReader = new FileReader(movies);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		try {
			String read = bufferedReader.readLine();
			while (read != null) {
				String[] results = read.split("\\|");
				String movieID = results[0];
				String movieName = results[1];
				moviesMap.put(movieID, movieName);
				read = bufferedReader.readLine();
			}
		}
		
		catch(FileNotFoundException e) {
			System.out.println("Not Found");
		}
		
		finally {
			bufferedReader.close();
		}
		
		return moviesMap;
		
	}

	public static Map<String, HashSet<String>> movieactorsMap() throws IOException{
		Map <String, HashSet<String>> movieactorsMap = new HashMap<String, HashSet<String>>(); 
		Map <String, String> movies = moviesMap();
		Map <String, String> actors = actorsMap();

		FileReader fileReader = new FileReader(movieactors);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		try {
			String read = bufferedReader.readLine();
			while (read != null) {
				String [] results = read.split("\\|");
				String movieID = results[0];
				String actorID = results[1];
				
				if (movieactorsMap.containsKey(movies.get(movieID))) { // if movieactorsMap already contains movie
					movieactorsMap.get(movies.get(movieID)).add(actors.get(actorID)); //add corresponding actors
				}
				
				else {
					movieactorsMap.put(movies.get(movieID), new HashSet<String>()); //add movie (that corresponds to its ID) to movieactorsMap, with new set as corresponding value 
					movieactorsMap.get(movies.get(movieID)).add(actors.get(actorID)); //at that movie, add corresponding actors
				}
				
				read = bufferedReader.readLine();
			}
				
		}
		
		catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	
		
		finally {
			bufferedReader.close();
		}
	
		return movieactorsMap;
	}
	
	public static Graph<String, Set<String>> baconGraph() throws IOException{
		Graph<String,Set<String>> baconGraph = new AdjacencyMapGraph<String,Set<String>>();
		Map<String, HashSet<String>> movieActors = movieactorsMap();
		
		for (String movie : movieActors.keySet()) {
			for (String actor: movieActors.get(movie)) {
				if(!baconGraph.hasVertex(actor)) {
					baconGraph.insertVertex(actor); //make vertices of graph
				}
				
			for (String actor2: movieActors.get(movie)) {
				if (!baconGraph.hasVertex(actor2)) { //make vertices of graph
					baconGraph.insertVertex(actor2);
				}
				
				if (!actor2.equals(actor)){ //if actor2 isn't equal to actor1
					if(!baconGraph.hasEdge(actor, actor2)) { //and an edge doesn't exist between them
						baconGraph.insertUndirected(actor, actor2, new HashSet<String>()); //make a new edge with label being a set of movies 
						baconGraph.getLabel(actor, actor2).add(movie); //add movies
					}
					else {
						baconGraph.getLabel(actor, actor2).add(movie); //if an edge exists, add movies
					}
				}
			}
			}
		}
		
		return baconGraph;
	
	}
	
	public static void game(String center) throws IOException {
		center = "Kevin Bacon";
		Graph<String, Set<String>> wholeGraph = baconGraph();
		Graph<String, Set<String>> bfs = BaconGraphLib.bfs(wholeGraph, center);
		bfs = BaconGraphLib.bfs(wholeGraph, center);
		
		Scanner in = new Scanner(System.in);
		String comm = in.nextLine();
		char command = comm.charAt(0);
		
		while (command != 'q' ){
			if (command == 'u') {
				if (comm.length() > 2) {
					center = comm.substring(2, comm.length());
					if(wholeGraph.hasVertex(center)) {
						System.out.println(center + " is now the center of the universe");
						bfs = BaconGraphLib.bfs(wholeGraph, center);
					}
					else {
						System.out.println("Actor Not Found");
					}
				}
				else {
					System.out.println("Please enter a name");
				}
			}
			
			if (command == 'p') {
				if (comm.length() > 2) {
					String actor = comm.substring(2, comm.length());
					if (bfs.hasVertex(actor)) {
						List<String> shortestPath = BaconGraphLib.getPath(bfs, actor);
						
						for (int i = 0; i < shortestPath.size()-1; i++) {
							String actor1 = shortestPath.get(i);
							String actor2 = shortestPath.get(i + 1);
							Set<String> commonMovies = bfs.getLabel(actor1, actor2);
							System.out.println(actor1 + " appeared in " + commonMovies + " with " + actor2);
						}
					}
					else {
						System.out.println("Actor Not Found");
					}
					
				}
				else {
					System.out.println("Please enter a name");
				}
			}
			
			if (command == 's') {
				if (comm.length() > 2) {
					String actor = comm.substring(2, comm.length());
					if (bfs.hasVertex(actor)) {
						double avgSep = BaconGraphLib.averageSeparation(bfs, actor);
						System.out.println("Average Separation for " + actor + ": " + avgSep);
					}
					else {
						System.out.println("Actor Not Found");
					}
				}
				else {
					System.out.println("Please enter a name");
				}
			}
			
			if (command == 'c') {
				if (comm.length() > 2) {
					String numString = comm.substring(2, comm.length());
					int num = Integer.parseInt(numString);
					
					if (Math.abs(num) > wholeGraph.numVertices()) {
						System.out.println("Invalid Number");
					}
					else {
						Map<String, Double> actorsSep = new HashMap<String, Double>();
						for (String actor : wholeGraph.vertices()) {
							Graph<String, Set<String>> BFS = BaconGraphLib.bfs(wholeGraph, actor);
							if (BFS.hasVertex("Kevin Bacon")){
								Double avgSep = BaconGraphLib.averageSeparation(BFS, actor); //calculate avg sep for each actor
								actorsSep.put(actor, avgSep); //place actor with his/her avg sep into actorSep map
							}
						}
						
						if (num > 0) {
							List<String> highestSeps = new ArrayList<String>();
							
							for (String actor : actorsSep.keySet()) { //get the keyset of actors
								highestSeps.add(actor); //add actors to a list
							}
							
							highestSeps.sort((actor1, actor2) -> (int) (100 * (actorsSep.get(actor2) - actorsSep.get(actor1)))); //sort from high to low
							
							for (int i = 0; i < num; i++) { //print out n actors with highest seps
								String highActor = highestSeps.get(i);
								System.out.println(highActor + ": " + actorsSep.get(highActor));
							}
						}
						
						else if (num < 0) {
							List<String> lowestSeps = new ArrayList<String>();
							
							for (String actor : actorsSep.keySet()) {
								lowestSeps.add(actor);
							}

							lowestSeps.sort((actor1, actor2) -> (int)(100 * (actorsSep.get(actor1) - actorsSep.get(actor2)))); //sort from low to high

							for (int i = 0; i < Math.abs(num); i++) {
								String lowActor = lowestSeps.get(i);
								System.out.println(lowActor + ": " + actorsSep.get(lowActor));
							}
						}
						
						else {
							System.out.println("Nothing");
						}
					}
				}
				else {
					System.out.println("Please enter a number");
				}
			}
			
			comm = in.nextLine();
			command = comm.charAt(0);	
		}

		in.close();
	}
	
	public static void main(String[] args) throws IOException {
		game("Kevin Bacon");
	}
	
	
	
	
	
}
