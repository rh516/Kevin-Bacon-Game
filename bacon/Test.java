package bacon;
import java.io.*;

public class Test {
	public static void main(String[] args) throws IOException {
		String actors = "PS4/bacon/actorsTest.txt";
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(actors);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
	        }
			
		}
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                actors + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + actors+ "'");
        }
	}
}
