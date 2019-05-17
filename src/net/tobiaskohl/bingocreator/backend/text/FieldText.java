package net.tobiaskohl.bingocreator.backend.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Contains all relevant information about a text
 * for it to be rendered inside a fixed boundary.
 * Can be created with {@linkplain FieldTextFactory}.
 * 
 * @author Tobias Kohl
 *
 */
public class FieldText {
	public static final float relativeLineSpace = 0.3f;
	
	private final String text;
	private final int[] possibleBreakPoints;
	private final PDFont font;
	
	private String[] lines;
	private int lastSuccessfulSize = 10;
	
	/*package*/ FieldText(String text, PDFont font) {
		this.text = text;
		this.possibleBreakPoints = TextBreaker.breakTextAtSpaces(text);
		this.font = font;
		this.lines = new String[] {text};
	}
	
	/**
	 * Returns the number of lines in which the
	 * text has been split.
	 * 
	 * @return
	 */
	public int getLineNumber() {
		return lines.length;
	}
	
	/**
	 * Gets the individual lines in which the
	 * text has been split.
	 * 
	 * @return
	 */
	public String[] getLines() {
		return lines.clone();
	}
	
	/**
	 * Returns the font that was used as a
	 * reference when splitting the text into
	 * lines.
	 * 
	 * @return
	 */
	public PDFont getUsedFont() {
		return this.font;
	}
	
	/**
	 * Returns the largest font size with which
	 * the split text fits into the enclosing
	 * boundary.
	 * 
	 * @return
	 */
	public int getRecommendedSize() {
		return this.lastSuccessfulSize;
	}
	
	/*package*/ boolean fit(float w, float h, int fontSize) throws IOException {
		List<String> result = new ArrayList<String>();
		int start = 0;
        int end = 0;
        for ( int i : possibleBreakPoints ) {
            float width = font.getStringWidth(text.substring(start,i)) / 1000 * fontSize;
            if ( start < end && width > w ) {
                result.add(text.substring(start,end));
                start = end + 1; // +1 because we do not want the space
            }
            end = i;
        }
        // Last piece of text
        result.add(text.substring(start));
        
        for (String line : result) {
        	float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
        	if (lineWidth > w) {
        		return false;
        	}
        }
        
		float lineHeight = font.getHeight(fontSize) / 1000;
		float totalHeight = lineHeight * (result.size() + relativeLineSpace * (result.size() - 1));
		if (totalHeight <= h) {
			this.lines = result.toArray(new String[result.size()]);
			this.lastSuccessfulSize = fontSize;
			return true;
		} else {
			return false;
		}
	}
}
