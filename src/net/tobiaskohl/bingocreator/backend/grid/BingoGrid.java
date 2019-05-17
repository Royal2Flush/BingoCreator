package net.tobiaskohl.bingocreator.backend.grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import net.tobiaskohl.bingocreator.backend.text.FieldText;

/**
 * A NxN grid containing multiple {@linkplain Square}s.The
 * squares are generated in the constructor. The center
 * square always is an {@linkplain ImageSquare}, all other
 * squares are {@linkplain TextSquare}s.
 * 
 * @author Tobias Kohl
 *
 */
public class BingoGrid implements Moveable, PdfDrawable {
		
	private final List<Square> squares;
	private final float size;
	
	private float currentX = 0;
	private float currentY = 0;

	/**
	 * Generates a grid of directly adjacent squares of a given size.
	 * The center square will be an image square with the provided
	 * image, all other squares will have the texts provided, ordered
	 * from bottom left to top right (!). If the number of texts
	 * provided is not n^2-1, an {@linkplain IllegalArgumentException}
	 * will be thrown, because then no square grid can be built.
	 * 
	 * @param texts the texts to be displayed in the individual squares
	 * @param centerImage the image displayed in the center
	 * @param squareSize the size of the individual squares
	 */
	public BingoGrid(List<FieldText> texts, PDImageXObject centerImage, float squareSize) {
		this(texts, centerImage, squareSize, 0.0f);
	}
	
	
	/**
	 * Generates a grid of squares of a given size with some distance between them.
	 * The center square will be an image square with the provided
	 * image, all other squares will have the texts provided, ordered
	 * from bottom left to top right (!). If the number of texts
	 * provided is not n^2-1, an {@linkplain IllegalArgumentException}
	 * will be thrown, because then no square grid can be built.
	 * 
	 * @param texts the texts to be displayed in the individual squares
	 * @param centerImage the image displayed in the center
	 * @param squareSize the size of the individual squares
	 * @param relativeSquareDistance the distance between the squares
	 * relative to their size
	 */
	public BingoGrid(List<FieldText> texts, PDImageXObject centerImage, float squareSize, float relativeSquareDistance) {
		int numCols = (int) Math.sqrt(texts.size() + 1);
		if (numCols * numCols != texts.size() + 1 || numCols % 2 == 0) {
			throw new IllegalArgumentException("size of texts is " + texts.size() + ", which does not form a square with a center image!");
		}
		
		squares = new ArrayList<Square>(texts.size() + 1);
		size = numCols * squareSize + (numCols - 1) * squareSize * relativeSquareDistance;
		
		float xOffset = 0;
		float yOffset = 0;
		int colCounter = 1;
		for (int i = 0; i < texts.size() / 2; i++) {
			Square square = new TextSquare(squareSize, texts.get(i));
			square.moveTo(xOffset, yOffset);
			squares.add(square);
			
			colCounter++;
			if (colCounter > numCols) {
				colCounter = 1;
				xOffset = 0;
				yOffset += squareSize * (1 + relativeSquareDistance);
			} else {
				xOffset += squareSize * (1 + relativeSquareDistance);
			}
		}
		
		Square center = new ImageSquare(squareSize, centerImage);
		center.moveTo(xOffset, yOffset);
		squares.add(center);
		
		// the center square never is at the end of a row, so no if/else necessary
		colCounter++;
		xOffset += squareSize * (1 + relativeSquareDistance);
		
		for (int i = texts.size() / 2 + 1; i < texts.size() + 1; i++) {
			Square square = new TextSquare(squareSize, texts.get(i - 1));
			square.moveTo(xOffset, yOffset);
			squares.add(square);
			
			colCounter++;
			if (colCounter > numCols) {
				colCounter = 1;
				xOffset = 0;
				yOffset += squareSize * (1 + relativeSquareDistance);
			} else {
				xOffset += squareSize * (1 + relativeSquareDistance);
			}
		}
	}
	
	@Override
	public void moveTo(float x, float y) {
		float xDelta = x - currentX;
		float yDelta = y - currentY;
		for (Square square : squares) {
			square.moveTo(square.getxPos() + xDelta, square.getyPos() + yDelta);
		}
	}
	
	@Override
	public void centerOn(float x, float y) {
		moveTo(x - size / 2, y - size / 2);
	}

	@Override
	public float getxPos() {
		return currentX;
	}

	@Override
	public float getyPos() {
		return currentY;
	}
	
	@Override
	public void drawInto(PDPageContentStream contentStream) throws IOException {
		for (Square square : squares) {
			square.drawInto(contentStream);
		}
	}	
}
