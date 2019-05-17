package net.tobiaskohl.bingocreator.backend.grid;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * An object that can be drawn into a {@linkplain PDPageContentStream}.
 * 
 * @author Tobias Kohl
 *
 */
public interface PdfDrawable {

	/**
	 * draws the object into the content stream. The content
	 * stream is therefore modified!
	 * 
	 * @param contentStream
	 * @throws IOException
	 */
	public void drawInto(PDPageContentStream contentStream) throws IOException;
}
