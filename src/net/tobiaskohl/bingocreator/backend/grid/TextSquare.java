package net.tobiaskohl.bingocreator.backend.grid;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import net.tobiaskohl.bingocreator.backend.text.FieldText;

/**
 * A {@linkplain Square} with a black text in it. The text
 * is broken into lines and scaled automatically in such
 * a way to have it at maximal size while still fitting in
 * the square.
 * 
 * @author Tobias Kohl
 *
 */
public class TextSquare extends Square {

	private final FieldText text;

	public TextSquare (float size, FieldText text) {
		super(size);
		this.text = text;
	}
	
	public TextSquare(float size, Color fillColor, float fillOpacity, FieldText text) {
		super(size, fillColor, fillOpacity);
		this.text = text;
	}

	@Override
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		super.drawInto(contentStream);
		RenderedGridText renderedText = new RenderedGridText(this.text, super.getSize(), super.getSize());
		renderedText.moveTo(super.getxPos(), super.getyPos());
		renderedText.drawInto(contentStream);
	}
}
