package net.tobiaskohl.bingocreator.backend.grid;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

/**
 * A colored square with a black border.
 * 
 * @author Tobias Kohl
 *
 */
public class Square implements Moveable, PdfDrawable {

	private final float size;
	private final Color boxStrokingColor;
	private final float opacity;
	
	private float xPos = 0;
	private float yPos = 0;
	
	public Square (float size) {
		this(size, Color.white, 0.5f);
	}
	
	public Square (float size, Color fillColor, float fillOpacity) {
		this.size = size;
		this.boxStrokingColor = fillColor;
		this.opacity = fillOpacity;
	}
	
	public float getSize() {
		return this.size;
	}
	
	@Override
	public void moveTo(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	@Override
	public void centerOn(float x, float y) {
		moveTo(x - size / 2, y - size / 2);
	}
	
	@Override
	public float getxPos() {
		return this.xPos;
	}
	
	@Override
	public float getyPos() {
		return this.yPos;
	}
	
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
		extendedGraphicsState.setNonStrokingAlphaConstant(opacity);
	 
		contentStream.setGraphicsStateParameters(extendedGraphicsState);
		contentStream.setNonStrokingColor(boxStrokingColor);
	 
		contentStream.addRect(xPos, yPos, this.size, this.size);
		contentStream.fill();
		
		contentStream.setStrokingColor(Color.black);
        contentStream.addRect(xPos, yPos, this.size, this.size);
        contentStream.closeAndStroke();
        
        PDExtendedGraphicsState defaultGraphicsState = new PDExtendedGraphicsState();
        defaultGraphicsState.setNonStrokingAlphaConstant(1.0f);
        contentStream.setGraphicsStateParameters(defaultGraphicsState);
	}
}
