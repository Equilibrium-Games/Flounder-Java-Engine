package flounder.platform;

import flounder.framework.*;
import flounder.profiling.*;

import java.nio.*;

/**
 * A module used for handling networking, servers, clients, and packets.
 */
public class FlounderPlatform extends Module {
	private static final FlounderPlatform INSTANCE = new FlounderPlatform();
	public static final String PROFILE_TAB_NAME = "Platform";

	private IPlatform platform;

	/**
	 * Creates a new network manager.
	 */
	public FlounderPlatform() {
		super(ModuleUpdate.UPDATE_ALWAYS, PROFILE_TAB_NAME);
	}

	@Handler.Function(Handler.FLAG_INIT)
	public void init() {
		update();
		if (platform != null) {
			Framework.getUpdater().setTiming(platform.getTiming());
			//	FlounderLogger.get().log(platform.getTiming());
		}
	}

	@Handler.Function(Handler.FLAG_UPDATE_PRE)
	public void update() {
		// Gets a new platform, if available.
		IPlatform newPlatform = (IPlatform) getExtensionMatch(platform, IPlatform.class, true);

		// If there is a new player, disable the old one and start to use the new one.
		if (newPlatform != null) {
			if (platform != null) {
				platform.setInitialized(false);
			}

			if (!newPlatform.isInitialized()) {
				//	newPlatform.init();
				newPlatform.setInitialized(true);
			}

			platform = newPlatform;
		}
	}

	@Handler.Function(Handler.FLAG_PROFILE)
	public void profile() {
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Platform", platform);
	}

	public static Platform getPlatform() {
		return INSTANCE.platform.getPlatform();
	}

	public static ByteBuffer createByteBuffer(int capacity) {
		return INSTANCE.platform.createByteBuffer(capacity);
	}

	public static ShortBuffer createShortBuffer(int capacity) {
		return INSTANCE.platform.createShortBuffer(capacity);
	}

	public static CharBuffer createCharBuffer(int capacity) {
		return INSTANCE.platform.createCharBuffer(capacity);
	}

	public static IntBuffer createIntBuffer(int capacity) {
		return INSTANCE.platform.createIntBuffer(capacity);
	}

	public static LongBuffer createLongBuffer(int capacity) {
		return INSTANCE.platform.createLongBuffer(capacity);
	}

	public static FloatBuffer createFloatBuffer(int capacity) {
		return INSTANCE.platform.createFloatBuffer(capacity);
	}

	public static DoubleBuffer createDoubleBuffer(int capacity) {
		return INSTANCE.platform.createDoubleBuffer(capacity);
	}

	public static float getMaxAnisotropy() {
		return INSTANCE.platform.getMaxAnisotropy();
	}


	@Handler.Function(Handler.FLAG_DISPOSE)
	public void dispose() {
	}
}
