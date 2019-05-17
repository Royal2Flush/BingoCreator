package net.tobiaskohl.bingocreator.backend.grid;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class BingoField implements Moveable, PdfDrawable {
	
	private final float width, height; 
	private final PDImageXObject background;
	private final BingoGrid grid;
	
	private float xPos = 0;
	private float yPos = 0;
	
	public BingoField(float w, float h, PDImageXObject background, BingoGrid grid) {
		this.width = w;
		this.height = h;
		this.background = background;
		this.grid = grid;
		grid.centerOn(w / 2, h / 2);
	}

	@Override
	public void moveTo(float x, float y) {
		this.xPos = x;
		this.yPos = y;
		this.grid.moveTo(x, y);
	}
	
	@Override
	public void centerOn(float x, float y) {
		this.xPos = x - width / 2;
		this.yPos = y - height / 2;
		this.grid.centerOn(x, y);
	}

	@Override
	public float getxPos() {
		return this.xPos;
	}

	@Override
	public float getyPos() {
		return this.yPos;
	}

	@Override
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		contentStream.drawImage(background, xPos, yPos, width, height);
		grid.drawInto(contentStream);
	}
}
