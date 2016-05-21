package flounder.lights;

import flounder.maths.*;
import flounder.maths.vectors.*;

/**
 * Represents a light in the game, contains a colour, position and attenuation.
 */
public class Light {
	public Colour colour;
	public Vector3f position;
	public Attenuation attenuation;

	/**
	 * Creates a new Light with unlimited range.
	 *
	 * @param colour The colour of the light.
	 * @param position The world position of the light.
	 */
	public Light(final Colour colour, final Vector3f position) {
		this(colour, position, new Attenuation(1.0f, 0.0f, 0.0f));
	}

	/**
	 * Creates a new Light.
	 *
	 * @param colour The colour of the light.
	 * @param position The world position of the light.
	 * @param attenuation How much the intensity of the light is lost over a distance.
	 */
	public Light(final Colour colour, final Vector3f position, final Attenuation attenuation) {
		this.colour = colour;
		this.position = position;
		this.attenuation = attenuation;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(final Colour colour) {
		this.colour = colour;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(final Vector3f position) {
		this.position = position;
	}

	public Attenuation getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(final Attenuation attenuation) {
		this.attenuation = attenuation;
	}
}