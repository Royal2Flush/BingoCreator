package net.tobiaskohl.bingocreator.backend.text;

/**
 * A class containing currently just one but
 * possibly more in the future methods to break
 * text in a certain way.
 * 
 * @author Tobias Kohl
 *
 */
public class TextBreaker {

	/**
	 * Breaks the text at spaces.
	 * 
	 * @param text the text to break
	 * @return the positions at which the text
	 * can be cut
	 */
	public static int[] breakTextAtSpaces(String text) {
		String[] split = text.split(" ");
	    int[] ret = new int[split.length];
	    ret[0] = split[0].length();
	    for ( int i = 1 ; i < split.length ; i++ )
	        ret[i] = ret[i-1] + split[i].length() + 1; // the +1 is the space we cut
	    return ret;
	}
}
