package net.tobiaskohl.bingocreator.backend.grid;

/**
 * An interface for a (at creation time) movable object in a pdf.
 * 
 * @author Tobias Kohl
 *
 */
public interface Moveable {

	/**
	 * moves the lower left corner of the object to the
	 * specified position
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(float x, float y);
	
	/**
	 * moves the center of the object to the specified
	 * position
	 * 
	 * @param x
	 * @param y
	 */
	public void centerOn(float x, float y);
	
	/**
	 * returns the x position of the lower left corner of
	 * the object
	 * 
	 * @return
	 */
	public float getxPos();
	
	/**
	 * returns the y position of the lower left corner of
	 * the object
	 * 
	 * @return
	 */
	public float getyPos();
}
