package flounder.shaders;

import flounder.maths.matrices.*;
import flounder.platform.*;

import java.nio.*;

/**
 * Represents a Matrix3F uniform type that can be loaded to the shader.
 */
public class UniformMat3 extends Uniform {
	private Matrix3f current;
	private FloatBuffer floatBuffer;

	public UniformMat3(String name, ShaderObject shader) {
		super(name, shader);
		this.current = new Matrix3f();
		this.floatBuffer = FlounderPlatform.createFloatBuffer(9);
	}

	/**
	 * Loads a Matrix3F to the uniform if the value already on the GPU is not the same as the new value.
	 *
	 * @param value The new value.
	 */
	public void loadMat3(Matrix3f value) {
		if (value != null && !current.equals(value)) {
			current.set(value);
			FlounderShaders.storeMatrixData(super.getLocation(), floatBuffer, value);
		}
	}
}
