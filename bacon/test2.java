package bacon;
import java.util.*;


public class test2 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		for (int i = 0; i < list.size()-1; i++) {
			String actor1 = list.get(i);
			String actor2 = list.get(i + 1);
			System.out.println(actor1 + " appeared in x movie with " + actor2);
		}
		
		Set<String> set = new HashSet<String>();
		set.add("a");
		set.add("b");
		set.add("c");
		set.add("d");
		set.add("e");
		System.out.println(set);
		
		

	}
	
	


}
