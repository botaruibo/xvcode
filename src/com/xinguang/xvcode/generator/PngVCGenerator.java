package com.xinguang.xvcode.generator;

import java.awt.AlphaComposite;
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import static com.xinguang.xvcode.generator.XRandoms.num;

/**
 *  PNG validation code image generator
 * @author brui
 *
 */
public class PngVCGenerator extends Generator {
			
	/** background graphic alpha value **/
	private static float bkAlpha = 0.7f; 
	
	/** validation code font alpha value **/
	private static float fontAlpha = 0.7f; 
	
	/** oval stroke size**/
	private static float ovalSize = 4.0f;
	
	/** oval count. decide to draw how many ovals as background**/
	private static int ovalCount = 20;
	
	public PngVCGenerator() {
	}
	
	public PngVCGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public PngVCGenerator(int width, int height, int len) {
		this(width, height);
		this.len = len;
	}

	public PngVCGenerator(int width, int height, int len, Font font) {
		this(width, height, len);
		this.font = font;
	}

	/** this constructor is different from others.
	 *  parameters with prefix 'p' are static field.
	 *  be care for the side effect to other beans
	 * @param width image width
	 * @param height image height
	 * @param len validation code length
	 * @param font  font features
	 * @param pbkAlpha  alpha channel for image background. default 7f
	 * @param pFontAlpha alpha channel for validation code font. default 7f
	 * @param pOvalSize the interference oval strike size. default 4
	 * @param pOvalCount the interference oval count. default 20
	 */
	public PngVCGenerator(int width, int height, int len, Font font,
			float pbkAlpha, float pFontAlpha, float pOvalSize, int pOvalCount) {
		this(width, height, len, font);
		bkAlpha = pbkAlpha;
		fontAlpha = pFontAlpha;
		ovalSize = pOvalSize;
		ovalCount = pOvalCount;
	}
	
	@Override
	public OutputStream write2out(OutputStream os) {
		if (os == null) {
			return null;
		}
		BufferedImage bi = getValidCodeImage(alphas());
		
		try {
			ImageIO.write(bi, "png", os);
			os.flush();
		} catch (IOException e) {
			//ignore
			e.printStackTrace();
		} 
		return os;
	}

	/**draw random validation code image
	 * @param strs the random validation code
	 * @return BufferedImage with validation code 
	 */
	private BufferedImage getValidCodeImage(char[] strs) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		AlphaComposite ac3;
		Color color;
		int len = strs.length;
		//set background color to white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		g2d.setStroke(new BasicStroke(ovalSize)); //set the stroke to 4 pixel
		ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bkAlpha);// specify the background graphic's alpha channel
		g2d.setComposite(ac3);
		// draw random ovals
		for (int i = 0; i < ovalCount; i++) {
			color = color(150, 250);
			g2d.setColor(color);
			g2d.drawOval(num(width), num(height), 10 + num(10), 10 + num(10));
		}
		g2d.setFont(font);
		ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fontAlpha);// specify the validation code's alpha channel
		g2d.setComposite(ac3);
		int h = height - ((height - font.getSize()) >> 1);
		int w = width / len, size = w - font.getSize() + 2;
		/* draw font */
		for (int i = 0; i < len; i++) {
			color = new Color(20 + num(110), 20 + num(110), 20 + num(110));// 对每个字符都用随机颜色
			g2d.setColor(color);
			int degree = num(90); // random rotate degree. -90 < degree < 90
			degree = num(2) == 0 ? -degree : degree;
			g2d.rotate(Math.toRadians(degree), (width - (len - i) * w) + w / 2, height / 2 + 2);
			g2d.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
			g2d.rotate(-Math.toRadians(degree), (width - (len - i) * w) + w / 2, height / 2 + 2);
		}
		g2d.dispose();
		return bi;
	}

	@Override
	public BufferedImage getValidCodeImage() {
		return getValidCodeImage(alphas());
	}

}
