package net.tobiaskohl.bingocreator.backend.grid;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * A {@linkplain Square} with an image in it. The image is
 * scaled in such way that it has maximal size while still
 * fitting in the square.
 * 
 * @author Tobias Kohl
 *
 */
public class ImageSquare extends Square {
	
	public static final float relativeOuterMargin = 0.1f;
	
	private final PDImageXObject image;

	public ImageSquare(float size, PDImageXObject image) {
		super(size);
		this.image = image;
	}
	
	public ImageSquare(float size, Color fillColor, float fillOpacity, PDImageXObject image) {
		super(size, fillColor, fillOpacity);
		this.image = image;
	}
	
	@Override
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		super.drawInto(contentStream);
		float imageWidth = (float) image.getWidth();
		float imageHeight = (float) image.getHeight();
		float sideRatio = imageWidth / imageHeight;
		
		float newWidth, newHeight;
		if (imageWidth > imageHeight) {
			newWidth = super.getSize() * (1 - 2 * relativeOuterMargin);
			newHeight = newWidth / sideRatio;	
		} else {
			newHeight = super.getSize() * (1 - 2 * relativeOuterMargin);
			newWidth = newHeight * sideRatio;
		}
		
		contentStream.drawImage(image, super.getxPos() + (super.getSize() - newWidth) / 2, super.getyPos() + (super.getSize() - newHeight) / 2, newWidth, newHeight);
	}

}
