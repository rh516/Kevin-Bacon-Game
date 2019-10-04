import java.util.*;

public class BaconGraphLib {
	public static <V, E> Graph<V,E> bfs(Graph<V,E> g, V source){
		Graph<V, E> outputTree = new AdjacencyMapGraph<V,E>(); //new output tree to be returned
		Queue<V> toVisit = new LinkedList<V>(); //queue for points that need to be visited
		Set<V> Visited = new HashSet<V>(); //set for visited points
		
		toVisit.add(source); //enqueue the starting vertex (source)

		while (!toVisit.isEmpty()) { //while the queue isn't empty
			V vertex = toVisit.poll(); //dequeue, store in variable called "vertex"
			if (!outputTree.hasVertex(vertex)) { //if "vertex" isn't already in the output tree
				outputTree.insertVertex(vertex); //insert it in the output tree as a vertex
			}
			for (V neighbor : g.outNeighbors(vertex)) { //loop through the out-neighbors of "vertex"
				if (!toVisit.contains(neighbor) && !Visited.contains(neighbor)) { //for each neighbor of "vertex"
					toVisit.add(neighbor); //enqueue the neighbor if it hasn't been enqueued yet AND if it hasn't been visited yet
					if (!outputTree.hasVertex(neighbor)) { //if the output tree doesn't already contain "neighbor"
						outputTree.insertVertex(neighbor); //insert "neighbor" in the output tree as a vertex
					}
					outputTree.insertDirected(neighbor, vertex, g.getLabel(neighbor, vertex)); //insert directed edge from "neighbor" to "vertex" with the label from the original graph 
				}	
			}
			Visited.add(vertex); //add "vertex" to Visited set (mark as visited)
		}
		
		return outputTree;
	}
	
	public static <V,E> List<V> getPath(Graph<V,E> tree, V v){ //used to extract path from BFS (from any vertex "v" to the center of the universe)
		List <V> Path = new ArrayList<V>(); //path is a list of vertices
		if (!tree.hasVertex(v)) { //if a vertex doesn't exist in the tree, then no path exists
			return null;
		}
		else {
			Path.add(v); //add vertex "v" to path as starting point
			Iterator<V> iterator = tree.outNeighbors(v).iterator(); //loop through the out-neighbors of "v"
			while(iterator.hasNext()) { //while a next vertex exists
				V nextVertex = iterator.next(); //move to the next vertex
				Path.add(nextVertex); //add the next vertex to Path
				iterator = tree.outNeighbors(nextVertex).iterator(); //keep going to the next vertex, etc.
				v = nextVertex; //keep going to the next vertex, etc..
			}
		}
		
		return Path;
	}
	
	public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
		Set<V> missing = new HashSet<V>(); //set for all unique vertices in the graph but not the subgraph
		for (V vertex : graph.vertices()) { //for all vertices in the graph
			if (!subgraph.hasVertex(vertex)) { //if the subgraph doesn't contain that vertex,
				missing.add(vertex); //add that vertex to "missing"
			}
		}
		return missing;
	}

	public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
		return (totalSeparation(tree, root, 0)) / (tree.numVertices()); //average separation = total separation divided by the number of vertices
	}

	public static <V,E> int totalSeparation(Graph<V,E> tree, V root, int total) {
		for (V vertex : tree.inNeighbors(root)) { //for all the in-neighbors of given vertex "root"
			total += totalSeparation(tree, vertex, total + 1); //increment the total separation by 1, recursively calling on each vertex
		}
		return total;
	}
}