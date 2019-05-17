package net.tobiaskohl.bingocreator.backend.Randomizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.tobiaskohl.bingocreator.backend.text.FieldText;
import net.tobiaskohl.bingocreator.backend.text.FieldTextFactory;

/**
 * Loads the possible content of Bingo Fields from a
 * text file. Each line is interpreted as one possible
 * field content. Currently only supports ASCII and
 * äöüß.
 * 
 * @author Tobias Kohl
 *
 */
public class FieldContentLoader {

	private final FieldTextFactory textFactory;
	
	/**
	 * The constructor sets up the style in which the
	 * the content will be rendered. This is necessary
	 * to get the correct linebreaks.
	 * 
	 * @param textFactory
	 */
	public FieldContentLoader(FieldTextFactory textFactory) {
		this.textFactory = textFactory;
	}
	
	/**
	 * Loads the contents of a text file and returns it
	 * as a list of FieldTexts. The List is in order of
	 * the appearance of the line in the file. Non-ASCII
	 * characters (except äöüß) are omitted.
	 * 
	 * @param filename
	 * @return a list of converted texts
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<FieldText> read(String filename) throws FileNotFoundException, IOException {
		List<FieldText> entries = new LinkedList<FieldText>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
	        String line = "";
	        while ((line = reader.readLine()) != null) {
	        	String cleanedLine = clean(line);
	            entries.add(textFactory.createText(cleanedLine));
	        }
		}
        return entries.stream().distinct().collect(Collectors.toList());
	}
	
	private static String clean(String originalString) {
		return originalString.replaceAll("ü", "__ue__")
				.replaceAll("ä", "__ae__")
				.replaceAll("ö", "__oe__")
				.replaceAll("ß", "__ss__")
				.replaceAll("[^\\x00-\\x7F]", "")
				.replaceAll("__ss__", "ß")
				.replaceAll("__ae__", "ä")
				.replaceAll("__oe__", "ö")
				.replaceAll("__ue__", "ü");
		
		// below is a replacement I got from somewhere in the internet which is supposed to remove all
		// fishy characters, but unfortunately also removes chars like * or - :(
		//String cleanedLine = line.replaceAll("[^\\p{L}\\p{N}\\p{Z}\\p{Sm}\\p{Sc}\\p{Sk}\\p{Pi}\\p{Pf}\\p{Pc}\\p{Mc}]","");
	}
}
