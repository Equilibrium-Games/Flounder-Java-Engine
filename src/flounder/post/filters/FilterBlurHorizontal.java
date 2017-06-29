package flounder.post.filters;

import flounder.devices.*;
import flounder.fbos.*;
import flounder.post.*;
import flounder.resources.*;
import flounder.shaders.*;

import static flounder.platform.Constants.*;

public class FilterBlurHorizontal extends PostFilter {
	private int widthValue;
	private float scaleValue;
	private boolean fitToDisplay;
	private float sizeScalar;

	public FilterBlurHorizontal(float sizeScalar) {
		super(ShaderFactory.newBuilder().setName("filterBlurHorizontal").addType(
				new ShaderType(GL_VERTEX_SHADER, VERTEX_LOCATION)).addType(
				new ShaderType(GL_FRAGMENT_SHADER, new MyFile(PostFilter.POST_LOC, "blurHorizontalFragment.glsl"))
		).create(), FBO.newFBO(sizeScalar).create());
		this.fitToDisplay = true;
		this.sizeScalar = sizeScalar;
		init((int) (FlounderDisplay.get().getWidth() * sizeScalar));
	}

	private void init(int widthValue) {
		this.widthValue = widthValue;
		this.scaleValue = 2.0f;
	}

	public FilterBlurHorizontal(int widthValue, int heightValue) {
		super(ShaderFactory.newBuilder().setName("filterBlurHorizontal").addType(
				new ShaderType(GL_VERTEX_SHADER, VERTEX_LOCATION)).addType(
				new ShaderType(GL_FRAGMENT_SHADER, new MyFile(PostFilter.POST_LOC, "blurHorizontalFragment.glsl"))
		).create(), FBO.newFBO(widthValue, heightValue).create());
		this.fitToDisplay = false;
		this.sizeScalar = 1.0f;
		init(widthValue);
	}

	public void setScale(float scale) {
		this.scaleValue = scale;
	}

	@Override
	public void storeValues() {
		if (fitToDisplay) {
			widthValue = (int) (FlounderDisplay.get().getWidth() * sizeScalar);
		}

		shader.getUniformFloat("width").loadFloat(widthValue);
		shader.getUniformFloat("scale").loadFloat(scaleValue);
	}
}
