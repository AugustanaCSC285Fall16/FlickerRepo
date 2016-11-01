package dataModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class VocabStorage {

	private ArrayList<String> interactionChoices;
	private ArrayList<String> locationChoices;
	private ArrayList<String> cultureChoices;
	private ArrayList<String> occupationChoices;

	private static final String INTERACTION_CHOICES_FILE_NAME = "InteractionChoices.csv";
	private static final String LOCATION_CHOICES_FILE_NAME = "LocationChoices.csv";
	private static final String CULTURE_CHOICES_FILE_NAME = "CultureChoices.csv";
	private static final String OCCUPATION_CHOICES_FILE_NAME = "OccupationChoices.csv";

	private static VocabStorage primaryVocabStorage = null;

	/**
	 * Creates our main VocabStorage Object that will be used all over in the
	 * program to call methods relating to the controlled vocab
	 * 
	 * @return VocabStorage object that will be a singleton
	 * @throws IOException
	 */
	public static VocabStorage getMainVocabStorage() throws IOException {
		if (primaryVocabStorage == null) {
			primaryVocabStorage = new VocabStorage();
		}
		return primaryVocabStorage;
	}

	/**
	 * The Private constructor only Initializes the vocab data fields but
	 * doesn't fill them in This allows for the singleton pattern
	 * 
	 * @throws IOException
	 */
	private VocabStorage() throws IOException {
		interactionChoices = new ArrayList<>();
		locationChoices = new ArrayList<>();
		cultureChoices = new ArrayList<>();
		occupationChoices = new ArrayList<>();
		loadControlledVocab(INTERACTION_CHOICES_FILE_NAME, interactionChoices);
		loadControlledVocab(LOCATION_CHOICES_FILE_NAME, locationChoices);
		loadControlledVocab(CULTURE_CHOICES_FILE_NAME, cultureChoices);
		loadControlledVocab(OCCUPATION_CHOICES_FILE_NAME, occupationChoices);
	}

	/**
	 * Reads in vocabulary data from the csv
	 * 
	 * @param String
	 *            fileName - a csv file that contains ONE column of data, with a
	 *            vocab word on each row,
	 * @param ArrayList<String>
	 *            list - the destination where the vocab words get stored
	 * @throws IOException
	 */
	private void loadControlledVocab(String fileName, ArrayList<String> list) throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DataStorage.DATA_FOLDER + "/" + fileName)), "UTF-8"));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			String item = nextLine[0];
			list.add(item);
		}
	}

	/**
	 * Writes the vocabulary data to a csv
	 * 
	 * @param String
	 *            fileName - a csv file that will be written to having only ONE
	 *            column of data, one vocab word a row.
	 * @param ArrayList<String>
	 *            vocabChoices - the source of the vocab words that will be
	 *            written.
	 * @throws IOException
	 */
	public void saveControlledVocab(String fileName, ArrayList<String> vocabChoices) throws IOException {
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter(new FileOutputStream((DataStorage.DATA_FOLDER + "/" + fileName)), "UTF-8"));
		List<String[]> choicesArray = new ArrayList<>();
		for (int i = 0; i < vocabChoices.size(); i++) {
			choicesArray.add(this.toCSVControlledVocabArray(vocabChoices.get(i).toLowerCase()));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}

	/**
	 * Saves the Occupation choices to the correct file
	 * 
	 * @throws IOException
	 */
	public void saveOccupationControlledVocab() throws IOException {
		saveControlledVocab(OCCUPATION_CHOICES_FILE_NAME, occupationChoices);
	}

	/**
	 * Saves the CulturalID choices to the correct file
	 * 
	 * @throws IOException
	 */
	public void saveCulturalIDControlledVocab() throws IOException {
		saveControlledVocab(CULTURE_CHOICES_FILE_NAME, cultureChoices);
	}

	/**
	 * Saves the Location choices to the correct file
	 * 
	 * @throws IOException
	 */
	public void saveLocationControlledVocab() throws IOException {
		saveControlledVocab(LOCATION_CHOICES_FILE_NAME, locationChoices);
	}

	/**
	 * Saves the Interaction choices to the correct file
	 * 
	 * @throws IOExeption
	 */
	public void saveInteractionControlledVocab() throws IOException {
		saveControlledVocab(INTERACTION_CHOICES_FILE_NAME, interactionChoices);
	}

	/**
	 * Converts the parameter into a string array
	 * 
	 * @return String[]
	 */
	public String[] toCSVControlledVocabArray(String item) {
		return new String[] { item };
	}

	/**
	 * Adds the parameter to the occupationChocies
	 * 
	 * @param item
	 */
	public void addOccupationChoice(String item) {
		occupationChoices.add(item.toLowerCase());
	}

	/**
	 * Adds the parameter to the cultureChoices
	 * 
	 * @param item
	 */
	public void addCulteralIdChoice(String item) {
		cultureChoices.add(item.toLowerCase());
	}

	/**
	 * Adds the parameter to the locationChoices
	 * 
	 * @param item
	 */
	public void addLocationChoice(String item) {
		locationChoices.add(item.toLowerCase());
	}

	/**
	 * Adds the parameter to the interactionChoices
	 * 
	 * @param item
	 */
	public void addInteractionChoice(String item) {
		interactionChoices.add(item.toLowerCase());
	}

	/**
	 * Retrieves and returns the InteractionChoices ArrayList
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getInteractionTypes() {
		return interactionChoices;
	}

	/**
	 * Retrieves and returns the locationChoices ArrayList
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getLocationTypes() {
		return locationChoices;
	}

	/**
	 * Retrieves and returns the cultureChoices ArrayList
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCultureChoices() {
		return cultureChoices;
	}

	/**
	 * Retrieves and returns the occupationChoices ArrayList
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getOccupationChoices() {
		return occupationChoices;
	}

}
