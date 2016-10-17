import java.io.*;
import java.util.*;

import com.opencsv.CSVWriter;

public class Export {
	// Data fields
	DataStorage storage;
	
	public Export() {
		try {
			storage = DataStorage.getMainDataStorage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportToPalladio(Collection<Connection> list) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("DataFiles/palladioData.csv"));
		writer.writeNext(new String[] {"Source", "Target"});
		
	}
	
	public void exportToGephiNodes() {
		
	}
	
	public void exportToGephiEdges() {
		
	}
}
