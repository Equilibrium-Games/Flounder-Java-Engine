package flounder.post;

import flounder.engine.*;
import flounder.fbos.*;
import flounder.helpers.*;
import flounder.resources.*;
import flounder.shaders.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents a post effect shader and on application saves the result into a FBO.
 */
public abstract class PostFilter {
	public static final MyFile POST_LOC = new MyFile("flounder/post/filters");
	public static final MyFile VERTEX_LOCATION = new MyFile(POST_LOC, "defaultVertex.glsl");

	private static float[] POSITIONS = {0, 0, 0, 1, 1, 0, 1, 1};
	private static int VAO = FlounderEngine.getLoader().createInterleavedVAO(POSITIONS, 2);

	public ShaderProgram shader;
	public FBO fbo;

	public PostFilter(String filterName, MyFile fragmentShader) {
		this(new ShaderProgram(filterName, VERTEX_LOCATION, fragmentShader), FBO.newFBO(FlounderEngine.getDevices().getDisplay().getWidth(), FlounderEngine.getDevices().getDisplay().getHeight()).fitToScreen().create());
	}

	public PostFilter(ShaderProgram shader, FBO fbo) {
		this.shader = shader;
		this.fbo = fbo;
	}

	/**
	 * Stores any shader uniforms into the filters shader program.
	 *
	 * @param uniforms The uniforms to store in the shader program.
	 */
	public void storeUniforms(Uniform... uniforms) {
		shader.storeAllUniformLocations(uniforms);
	}

	/**
	 * Renders the filter to its FBO.
	 *
	 * @param textures A list of textures in indexed order to be bound for the shader program.
	 */
	public void applyFilter(int... textures) {
		boolean lastWireframe = OpenGlUtils.isInWireframe();

		fbo.bindFrameBuffer();
		OpenGlUtils.prepareNewRenderParse(1.0f, 1.0f, 1.0f);
		shader.start();
		storeValues();
		OpenGlUtils.antialias(false);
		OpenGlUtils.disableDepthTesting();
		OpenGlUtils.cullBackFaces(true);
		OpenGlUtils.goWireframe(false);
		OpenGlUtils.bindVAO(VAO, 0);

		for (int i = 0; i < textures.length; i++) {
			OpenGlUtils.bindTextureToBank(textures[i], i);
		}

		glDrawArrays(GL_TRIANGLE_STRIP, 0, POSITIONS.length); // Render post filter.

		OpenGlUtils.unbindVAO(0);
		OpenGlUtils.goWireframe(lastWireframe);
		shader.stop();
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting();
		fbo.unbindFrameBuffer();
	}

	/**
	 * Can be used to store values into the shader, this is called when the filter is applied and the shader has been already started.
	 */
	public abstract void storeValues();

	/**
	 * Cleans up all of the filter processes and images.
	 */
	public void dispose() {
		fbo.delete();
		shader.dispose();
	}
}
