import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PersonList {
	private ArrayList<String> personData;
	
	public PersonList(ArrayList<String> data){
		personData = data;
	}
	
	public void writePersonData() throws IOException{
		FileWriter filewriter = new FileWriter("DataFiles/PersonData.csv", true);
			filewriter.append("\n");
		for (int i = 0; i<personData.size(); i++){
			filewriter.append(personData.get(i));
			filewriter.append(", ");
		
		}
				
		filewriter.flush();
	    filewriter.close();
	}
}
