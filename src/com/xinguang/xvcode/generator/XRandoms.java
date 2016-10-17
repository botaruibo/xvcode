package com.xinguang.xvcode.generator;

import java.util.Random;

public class XRandoms {
	private static final Random RANDOM = new Random();
	// alhpa table without 'O' and 'I' eg.
	public static final char ALPHA[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**generator random number between <code>min</code> and  <code>max </code>.
	 * including <code>min</code> but not <code>max </code> 
	 * @param min 
	 * @param max
	 * @return random number
	 */
	public static int num(int min, int max) {
		return min + RANDOM.nextInt(max - min);
	}

	/**generator random number between <code>0</code> and  <code>num </code>.
	 * including <code>0</code> but not <code>num</code> 
	 * @param num
	 * @return
	 */
	public static int num(int num) {
		return RANDOM.nextInt(num);
	}

	public static char alpha() {
		return ALPHA[num(0, ALPHA.length)];
	}
}
