package flounder.devices;

import flounder.engine.*;
import flounder.engine.profiling.*;
import org.lwjgl.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Manages the creation, updating and destruction of joysticks.
 */
public class DeviceJoysticks {
	private FloatBuffer joystickAxes[];
	private ByteBuffer joystickButtons[];
	private String joystickNames[];

	/**
	 * Creates a new GLFW joystick manager.
	 */
	protected DeviceJoysticks() {
		joystickAxes = new FloatBuffer[GLFW_JOYSTICK_LAST];
		joystickButtons = new ByteBuffer[GLFW_JOYSTICK_LAST];
		joystickNames = new String[GLFW_JOYSTICK_LAST];
		update(0.0f);
	}

	/**
	 * Updates all connected joysticks, and finds new ones. Should be called once every frame.
	 *
	 * @param delta The time in seconds since the last frame.
	 */
	protected void update(final float delta) {
		// For each joystick check if connected and update.
		for (int i = 0; i < GLFW_JOYSTICK_LAST; i++) {
			if (glfwJoystickPresent(i) == GL_TRUE) {
				if (joystickAxes[i] == null || joystickButtons[i] == null || joystickNames[i] == null) {
					FlounderLogger.log("Connecting Joystick: " + i);
					joystickAxes[i] = BufferUtils.createFloatBuffer(glfwGetJoystickAxes(i).capacity());
					joystickButtons[i] = BufferUtils.createByteBuffer(glfwGetJoystickButtons(i).capacity());
					joystickNames[i] = glfwGetJoystickName(i);
				}

				joystickAxes[i].clear();
				joystickAxes[i].put(glfwGetJoystickAxes(i));

				joystickButtons[i].clear();
				joystickButtons[i].put(glfwGetJoystickButtons(i));
			} else {
				if (joystickAxes[i] != null || joystickButtons[i] != null || joystickNames[i] != null) {
					FlounderLogger.log("Disconnecting Joystick: " + i);
					joystickAxes[i].clear();
					joystickAxes[i] = null;

					joystickButtons[i].clear();
					joystickButtons[i] = null;
					joystickNames[i] = null;
				}
			}
		}
	}

	/**
	 * Dobermans if the GLFW joystick is connected
	 *
	 * @param joystick The joystick to check connection with.
	 *
	 * @return If the joystick is connected.
	 */
	public boolean isConnected(final int joystick) {
		return joystick >= 0 && joystick < GLFW_JOYSTICK_LAST && joystickNames[joystick] != null;
	}

	/**
	 * Gets the name of the joystick.
	 *
	 * @param joystick The joystick of interest.
	 *
	 * @return The joysticks name.
	 */
	public String getName(final int joystick) {
		return joystickNames[joystick];
	}

	/**
	 * Gets the value of a joystick's axis.
	 *
	 * @param joystick The joystick of interest.
	 * @param axis The axis of interest.
	 *
	 * @return The value of the joystick's axis.
	 */
	public float getAxis(final int joystick, final int axis) {
		return joystickAxes[joystick].get(axis);
	}

	/**
	 * Gets the whether a button on a joystick is pressed.
	 *
	 * @param joystick The joystick of interest.
	 * @param button The button of interest.
	 *
	 * @return Whether a button on a joystick is pressed.
	 */
	public boolean getButton(final int joystick, final int button) {
		return joystickButtons[joystick].get(button) != 0;
	}

	/**
	 * Gets the number of axes the joystick offers.
	 *
	 * @param joystick The joystick of interest.
	 *
	 * @return The number of axes the joystick offers.
	 */
	public int getCountAxes(final int joystick) {
		return joystickAxes[joystick].capacity();
	}

	/**
	 * Gets the number of buttons the joystick offers.
	 *
	 * @param joystick The joystick of interest.
	 *
	 * @return The number of buttons the joystick offers.
	 */
	public int getCountButtons(final int joystick) {
		return joystickButtons[joystick].capacity();
	}

	/**
	 * Closes the GLFW joystick system, do not use joysticks after calling this.
	 */
	protected void dispose() {
	}
}
