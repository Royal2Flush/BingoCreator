package net.tobiaskohl.bingocreator.backend.grid;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import net.tobiaskohl.bingocreator.backend.text.FieldText;

/**
 * The drawable and correctly sized representation of a
 * {@linkplain FieldText} object.
 * 
 * @author Tobias Kohl
 *
 */
/*package*/ class RenderedGridText implements Moveable, PdfDrawable {
	
	private final FieldText text;
	private final float w;
	private final float h;
	
	private float x;
	private float y;
	

	/*package*/ RenderedGridText(FieldText text, float w, float h) {
		this.text = text;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		final float lineHeight = text.getUsedFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * text.getRecommendedSize();
		final float fullHeight = lineHeight * text.getLineNumber() + lineHeight * FieldText.relativeLineSpace * (text.getLineNumber() - 1);
		final float yStart = y + h - (h - fullHeight - lineHeight * FieldText.relativeLineSpace) / 2 - lineHeight;
		
		contentStream.setNonStrokingColor(Color.black);
		contentStream.setStrokingColor(Color.black);
		contentStream.beginText();
		contentStream.setFont(text.getUsedFont(), text.getRecommendedSize());
		
		float yOffset = 0;
		float xOffset = 0;
		float previousWidth = 0;
		boolean firstLine = true;
		for (String line : text.getLines()) {
			float lineWidth = text.getUsedFont().getStringWidth(line) / 1000 * text.getRecommendedSize();
			
			if(firstLine) {
				xOffset = x + (w - lineWidth) / 2;
				yOffset = yStart;
				firstLine = false;
			} else {
				xOffset = (previousWidth - lineWidth) / 2;
				yOffset = -lineHeight * (1 + FieldText.relativeLineSpace);
			}
			
			contentStream.newLineAtOffset(xOffset, yOffset);
			contentStream.showText(line);
			
			previousWidth = lineWidth;
		}
		contentStream.endText();
	}

	@Override
	public void moveTo(float x, float y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public void centerOn(float x, float y) {
		this.x = x - w/2;
		this.y = y - h/2;
	}


	@Override
	public float getxPos() {
		return x;
	}


	@Override
	public float getyPos() {
		return y;
	}
}
