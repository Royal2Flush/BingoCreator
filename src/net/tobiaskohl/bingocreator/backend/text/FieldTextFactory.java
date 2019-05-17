package net.tobiaskohl.bingocreator.backend.text;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;

/**
 * Factory class to create {@linkplain FieldText} objects.
 * 
 * @author Tobias Kohl
 *
 */
public class FieldTextFactory {
	
	private PDFont font;
	private int fontSize = 10;
	private int minFontSize = 4;
	private float w = 1000;
	private float h = 1000;;
	private boolean forceFontSize = false;
	
	/**
	 * Creates a new factory with default values
	 * already set.
	 */
	public FieldTextFactory() {
		try {
			font = PDFontFactory.createDefaultFont();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Sets the font in which the text should be
	 * rendered later as well as the maximum font
	 * size, i.e. the size that is the starting
	 * point of the fitting process.
	 * 
	 * @param font Default: Default system font
	 * @param size Default: 10
	 * @return
	 */
	public FieldTextFactory setFont(PDFont font, int size) {
		this.font = font;
		this.fontSize = size;
		return this;
	}
	
	/**
	 * Sets the minimal font size, i.e. the font
	 * size at which the fitting algorithm will
	 * accept an overflowing text instead of trying
	 * to downsize it further.
	 * 
	 * @param minFontSize Default: 4
	 * @return
	 */
	public FieldTextFactory setMinFontSize(int minFontSize) {
		this.minFontSize = minFontSize;
		return this;
	}
	
	/**
	 * Sets whether or not the font size should be
	 * decreased at all when trying to fit the text
	 * into the boundary.
	 * 
	 * @param force Default: false
	 * @return
	 */
	public FieldTextFactory forceFontSize(boolean force) {
		this.forceFontSize = force;;
		return this;
	}
	
	/**
	 * Sets the boundary of the box to which the text
	 * should be fitted. Note that this is in some
	 * unit native to the pdf library and 250 units
	 * are approximately the height of a 10pt text.
	 * 
	 * @param w Default: 1000
	 * @param h Default: 1000
	 * @return
	 */
	public FieldTextFactory setBoundaries(float w, float h) {
		this.w = w;
		this.h = h;
		return this;
	}
	
	/**
	 * Transform a String into a {@linkplain FieldText}
	 * with the same textual content fitted with the
	 * parameters specified by the various setters of
	 * this class.
	 * 
	 * @param text
	 * @return
	 */
	public FieldText createText(String text) {
		FieldText fieldText = new FieldText(text, font);
		boolean success = false;
		int fontSizeToTry = fontSize;
		while (!success && fontSizeToTry >= minFontSize) {
			try {
				success = fieldText.fit(w, h, fontSizeToTry);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			if (forceFontSize) {
				break;
			} else {
				fontSizeToTry -= 1;
			}
		}
		return fieldText;
	}
}
