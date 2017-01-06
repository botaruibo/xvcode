package com.xinguang.xvcode.generator;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import com.xinguang.xvcode.gifencoder.AnimatedGifEncoder;

import static com.xinguang.xvcode.generator.XRandoms.*;

public class GifVCGenerator extends Generator {

	/** oval stroke size**/
	private static float ovalSize = 4.0f;
	
	/** git delay. unit ms**/
	private static int gifDelayTime = 500;
	
	public GifVCGenerator() {
	}

	public GifVCGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public GifVCGenerator(int width, int height, int len) {
		this(width, height);
		this.len = len;
	}

	public GifVCGenerator(int width, int height, int len, Font font) {
		this(width, height, len);
		this.font = font;
	}

	/**this constructor is different from others.
	 *  parameters with prefix 'p' are static field.
	 *  be care for the side effect to other beans
	 * @param width image width
	 * @param height image height
	 * @param len validation code length
	 * @param font font features
	 * @param pOvalSize the interference oval strike size. default 4
	 * @param pGifDelayTime gif frame delay time.default 500ms 
	 */
	public GifVCGenerator(int width, int height, int len, Font font, int pOvalSize, int pGifDelayTime) {
		this(width, height, len, font);
		ovalSize = pOvalSize;
		gifDelayTime = pGifDelayTime;	
	}
	
	@Override
	public OutputStream write2out(OutputStream out) {
		if (out == null) {
			return null;
		}
		AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
		gifEncoder.start(out);
		gifEncoder.setQuality(180);
		gifEncoder.setDelay(gifDelayTime);
		gifEncoder.setRepeat(0);
		BufferedImage frame;
		char[] rands = alphas();
		Color fontcolor[] = new Color[len];
		for (int i = 0; i < len; i++) {
			fontcolor[i] = new Color(20 + num(110), 20 + num(110), 20 + num(110));
		}
		for (int i = 0; i < len; i++) {
			frame = getValidCodeImage(fontcolor, rands, i);
			gifEncoder.addFrame(frame);
			frame.flush();
		}
		gifEncoder.finish();
		return out;
	}

	/** draw one frame for the GIF image
	 * @param fontcolor font color
	 * @param strs validation string
	 * @param flag
	 * @return
	 */
	private BufferedImage getValidCodeImage(Color[] fontcolor, char[] strs, int flag) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		//set background color to white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		g2d.setStroke(new BasicStroke(ovalSize));
		AlphaComposite ac3;
		int h = height - ((height - font.getSize()) >> 1);
		int w = width / len;
		g2d.setFont(font);
		for (int i = 0; i < len; i++) {
			ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i) / 2);
			g2d.setComposite(ac3);
			g2d.setColor(fontcolor[i]);			
			// one frame with tow ovals
			g2d.drawOval(num(width), num(height), 10 + num(10), 10 + num(10));
			g2d.drawOval(num(width), num(height), 10 + num(10), 10 + num(10));
			ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i));
			g2d.setComposite(ac3);
			g2d.drawString(strs[i] + "", (width - (len - i) * w) + (w - font.getSize()) + 1, h - 4);
		}
		g2d.dispose();
		return image;
	}

	/** Calculate alpha
	 * @param i
	 * @param j
	 * @return
	 */
	private float getAlpha(int i, int j) {
		int num = i + j;
		float r =  1f / len, s = (len + 1) * r;
		return num > len ? (num * r - s) : num * r;
	}

	/* return null for all invoke. 
	 * @see com.xinguang.xvcode.generator.Captcha#getValidCodeImage(char[])
	 */
	@Override
	public BufferedImage getValidCodeImage() {
		return null;
	}
}
