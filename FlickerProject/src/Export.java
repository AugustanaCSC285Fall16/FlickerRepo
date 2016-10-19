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
		for(Connection connection: list) {
			String direction = connection.getDirection();
			List<Person> personList = connection.getPeopleList();
			System.out.println(personList.toString());
			if(direction.equals("One-to-One")) {
				//System.out.println("" + personList.get(0) + personList.get(1));
				writer.writeNext(connection.toPalladioArray(personList.get(0), personList.get(1)));
			} else if(direction.equals("One-to-Many")) {
				for(int i = 1; i < personList.size(); i++) {
					//System.out.println("" + personList.get(0) + personList.get(i));
					writer.writeNext(connection.toPalladioArray(personList.get(0), personList.get(i)));
				}
			} else if(direction.equals("Many-to-Many")) {
				for(int i = 0; i < personList.size() - 1; i++) {
					for(int j = i + 1; j < personList.size(); j++) {
						//System.out.println("" + personList.get(i) + personList.get(j));
						writer.writeNext(connection.toPalladioArray(personList.get(i), personList.get(j)));
					}
				}
			}
		}
		writer.close();
	}
	
	public void exportToGephiNodes() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("DataFiles/gephiNodes.csv"));
		writer.writeNext(new String[] {"Node ID", "Label"});
		Collection<Person> personList = storage.getPeopleList();
		for(Person person: personList) {
			writer.writeNext(person.toGephiNodeArray());
		}
		writer.close();
	}
	
	public void exportToGephiEdges(Collection<Connection> list) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("DataFiles/gephiEdges.csv"));
		writer.writeNext(new String[] {"Source", "Target", "Edge ID"});
		int edgeId = 1;
		for(Connection connection: list) {
			String direction = connection.getDirection();
			List<Person> personList = connection.getPeopleList();
			System.out.println(personList.toString());
			if(direction.equals("One-to-One")) {
				writer.writeNext(connection.toGephiEdgeArray(personList.get(0), personList.get(1), edgeId));
				edgeId++;
			} else if(direction.equals("One-to-Many")) {
				for(int i = 1; i < personList.size(); i++) {
					writer.writeNext(connection.toGephiEdgeArray(personList.get(0), personList.get(i), edgeId));
					edgeId++;
				}
			} else if(direction.equals("Many-to-Many")) {
				for(int i = 0; i < personList.size() - 1; i++) {
					for(int j = i + 1; j < personList.size(); j++) {
						writer.writeNext(connection.toGephiEdgeArray(personList.get(i), personList.get(j), edgeId));
						edgeId++;
					}
				}
			}
		}
		writer.close();
	}
}
