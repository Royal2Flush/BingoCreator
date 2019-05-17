package net.tobiaskohl.bingocreator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import net.tobiaskohl.bingocreator.backend.Randomizer.FieldContentLoader;
import net.tobiaskohl.bingocreator.backend.Randomizer.Sampler;
import net.tobiaskohl.bingocreator.backend.grid.BingoField;
import net.tobiaskohl.bingocreator.backend.grid.BingoGrid;
import net.tobiaskohl.bingocreator.backend.text.FieldText;
import net.tobiaskohl.bingocreator.backend.text.FieldTextFactory;

/**
 * Main method to start the Bingo Creator.</br>
 * 
 * Usage:</br>
 * java net.tobiaskohl.bingocreator.BingoCreator [n] [l] [b] [c] [o]</br>
 * [n]: The number of Bingo sheets needed</br>
 * [l]: The textfile containing the field entries, one per line. At least 24, but any larger number is possible</br>
 * [b]: The background image file</br>
 * [c]: The file containing the image to be displayed on the joker field in the center</br>
 * [o]: The output filename</br>
 * It might be necessary to put the individual arguments in ""</br>
 * 
 * @author Tobias Kohl
 *
 */
public class BingoCreator {
	public static final int boxSize = 70;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Invalid number of parameters. Aborting!");
			System.out.println("Usage:");
			System.out.println("java net.tobiaskohl.bingocreator.BingoCreator [n] [l] [b] [c] [o]");
			System.out.println("[n]: The number of Bingo sheets needed");
			System.out.println("[l]: The textfile containing the field entries, one per line. At least 24, but any larger number is possible");
			System.out.println("[b]: The background image file");
			System.out.println("[c]: The file containing the image to be displayed on the joker field in the center");
			System.out.println("[o]: The output filename");
			System.out.println("It might be necessary to put the individual arguments in \"\".");
			System.exit(1);
		}
		int numGames = Integer.parseInt(args[0]) + 1;
		String entryFile = args[1];
		String bgFile = args[2];
		String centerFile = args[3];
		String outfile = args[4];
		
		FieldTextFactory factory = new FieldTextFactory().setBoundaries(boxSize, boxSize).setFont(PDType1Font.HELVETICA, 10).setMinFontSize(5);
		try (PDDocument doc = new PDDocument()) {
			List<FieldText> allTexts =  new FieldContentLoader(factory).read(entryFile);
			PDImageXObject pdImage = PDImageXObject.createFromFile(centerFile, doc);
			Sampler sampler = new Sampler(allTexts);
			
			PDPage page = new PDPage();
			
			for (int i = 0; i < numGames; i++) {
				try (PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true))
				{
					if (i % 2 == 0) {
						page = new PDPage();
						doc.addPage(page);
					}

					BingoGrid grid = new BingoGrid(sampler.draw(24), pdImage, boxSize);
					PDImageXObject bgImage = PDImageXObject.createFromFile(bgFile, doc);
					BingoField field = new BingoField(page.getArtBox().getWidth(), page.getArtBox().getHeight() / 2, bgImage, grid);
					if (i % 2 == 1) {
						field.moveTo(0, page.getArtBox().getHeight() / 2);
					} else {
						field.moveTo(0, 0);
					}
					field.drawInto(contents);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			} 
		doc.save(outfile);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
