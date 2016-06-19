package flounder.devices;

import flounder.engine.*;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Manages the creation, updating and destruction of the keyboard keys.
 */
public class DeviceKeyboard implements IModule {
	private int keyboardKeys[];

	private GLFWKeyCallback callbackKey;

	/**
	 * Creates a new GLFW keyboard.
	 */
	protected DeviceKeyboard() {
		keyboardKeys = new int[GLFW_KEY_LAST + 1];
	}

	@Override
	public void init() {
		// Sets the keyboards callbacks.
		glfwSetKeyCallback(FlounderEngine.getDevices().getDisplay().getWindow(), callbackKey = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key < 0 || key > GLFW_KEY_LAST) {
					FlounderEngine.getLogger().error("Invalid action attempted with key " + key);
				} else {
					keyboardKeys[key] = action;
				}
			}
		});
	}

	@Override
	public void update() {
	}

	@Override
	public void profile() {
	}

	/**
	 * Gets whether or not a particular key is currently pressed.
	 * <p>GLFW Actions: GLFW_PRESS, GLFW_RELEASE, GLFW_REPEAT</p>
	 *
	 * @param key The key to test.
	 *
	 * @return If the key is currently pressed.
	 */
	public boolean getKey(int key) {
		return keyboardKeys[key] != GLFW_RELEASE;
	}

	@Override
	public void dispose() {
		callbackKey.free();
	}
}
