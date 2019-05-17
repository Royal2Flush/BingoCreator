package net.tobiaskohl.bingocreator.backend.Randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.tobiaskohl.bingocreator.backend.text.FieldText;

/**
 * Gets a random subsample of a collection of
 * {@linkplain FieldText}s.
 * 
 * @author Tobias Kohl
 *
 */
public class Sampler {
	
	private static final long DEFAULT_SEED = 654231;

	private final Random rng;
	
	private List<FieldText> allFields;
	
	public Sampler(List<FieldText> fields) {
		this(fields, DEFAULT_SEED);
	}
	
	public Sampler(List<FieldText> fields, long seed) {
		allFields = new ArrayList<FieldText>(fields);
		rng = new Random(seed);
	}
	
	/**
	 * Draws a random sample of a certain size.
	 * 
	 * @param sampleSize
	 * @return
	 */
	public List<FieldText> draw(int sampleSize) {
		Collections.shuffle(allFields, rng);
		return allFields.stream().limit(sampleSize).collect(Collectors.toList());
	}
}
