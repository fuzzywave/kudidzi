package com.fuzzywave.commons.graphics;

public interface Shape {
    /**
     * Returns the x coordinate of this object
     *
     * @return 0 by default
     */
    public float getX();

    /**
     * Returns the y coordinate of this object
     *
     * @return 0 by default
     */
    public float getY();

    public int getNumberOfSides();

    public void draw(Graphics g);

    public void fill(Graphics g);
}
