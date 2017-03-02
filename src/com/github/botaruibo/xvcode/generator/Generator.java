package com.github.botaruibo.xvcode.generator;

import static com.github.botaruibo.xvcode.generator.XRandoms.alpha;
import static com.github.botaruibo.xvcode.generator.XRandoms.num;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * abstract class for validation code generator
 * @author brui
 *
 */
public abstract class Generator {

	protected Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28); // Font
	protected int len = 5; // validation code length
	protected int width = 150; // image width
	protected int height = 40; // image height
	private String chars = null; // valid string
	
	/**generate the random validation strings
	 * @return
	 */
	protected char[] alphas() {
		char[] cs = new char[len];
		for (int i = 0; i < len; i++) {
			cs[i] = alpha();
		}
		chars = new String(cs);
		return cs;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * pick color from a random range
	 * 
	 * @return Color random color
	 */
	protected Color color(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + num(bc - fc);
		int g = fc + num(bc - fc);
		int b = fc + num(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @param os source OutputStream
	 * @return OutputStream  return OutputStream wrote the validation code image
	 */
	public abstract OutputStream write2out(OutputStream os);

	/**no result provide for gif validation code. only for PNG validation code image.
	 *  only @see com.github.botaruibo.xvcode.generator.Captcha#write2out(OutputStream os) avaliable for GIF
	 * @return
	 * 			1.BufferedImage for PNG
	 * 			2.<code>null</code> for GIF
	 */
	public abstract BufferedImage getValidCodeImage();

	/** get random validation code string as lower case
	 * @return validation code
	 */
	public String text() {
		return chars.toLowerCase();
	}
}
