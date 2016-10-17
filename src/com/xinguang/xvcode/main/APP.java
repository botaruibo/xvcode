package com.xinguang.xvcode.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xinguang.xvcode.generator.Generator;
import com.xinguang.xvcode.generator.Gif2VCGenerator;
import com.xinguang.xvcode.generator.Gif3VCGenerator;
import com.xinguang.xvcode.generator.GifVCGenerator;
import com.xinguang.xvcode.generator.PngVCGenerator;

/**just a common dynamic validation code image generator
 * provide 1 png image generator and 3 gif image generators
 * @author brui
 *
 */
public class APP {
	/**run on console to generate the images.use:
	 * <code>java -jar xvcode.jar -p "/test" -h 50 -w 120 -cl 5</code>
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		if (args.length > 0 && (args.length % 2) != 0) {
			System.err.println("parameter error");
			return;
		}
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add("-p");
		cmd.add("-h");
		cmd.add("-w");
		cmd.add("-cl");
		String path = ".";
		Integer height = 40;
		Integer width = 200;
		Integer count = 5;	
		if (args.length > 0 && (args.length % 2) == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < args.length; i += 2) {
				if (cmd.contains(args[i])){
					map.put(args[i], args[i+1]);
				}				
			}
			Matcher m = null;
			if (map.get("-p") != null) {
				Pattern p = Pattern.compile("^[A-z]:\\\\(.+?\\\\)*$");
				m = p.matcher(map.get("-p").toString());
				if (!m.matches()) {
					System.err.println("-p format error");
					return;
				};
			}
			if (map.get("-w") != null) {
				Pattern p = Pattern.compile("^[0-9]*$");
				m = p.matcher(map.get("-w").toString());
				if (!m.matches() || Integer.valueOf(map.get("-w").toString()) < 50 || Integer.valueOf(map.get("-w").toString()) > 500) {
					System.err.println("-w must be number, and big than 10 and litter than 500");
					return;
				};
			}
			if (map.get("-h") != null) {
				Pattern p = Pattern.compile("^[0-9]*$");
				m = p.matcher(map.get("-h").toString());
				if (!m.matches() || Integer.valueOf(map.get("-h").toString()) < 30 || Integer.valueOf(map.get("-h").toString()) > 300) {
					System.err.println("-h must be number, and big than 10 and litter than 500");
					return;
				};
			}
			if (map.get("-cl") != null) {
				Pattern p = Pattern.compile("^[0-9]*$");
				m = p.matcher(map.get("-cl").toString());
				if (!m.matches() || Integer.valueOf(map.get("-cl").toString()) > 10 || Integer.valueOf(map.get("-cl").toString()) < 2) {
					System.err.println("-cl must be number, and bigger than 1 and no more than 10");
					return;
				};
			}
			if (map.get("-p") != null) {
				path = map.get("-p");
			}
			if (map.get("-w") != null) {
				width = Integer.valueOf(map.get("-w"));
			}
			if (map.get("-h") != null) {
				height = Integer.valueOf(map.get("-h"));
			}
			if (map.get("-cl") != null) {
				count = Integer.valueOf(map.get("-cl"));
			}
		};
		
		
		Generator generator = new PngVCGenerator(width, height, count);
        generator.write2out(new FileOutputStream(path + "/1.png")).close();
        System.out.println(generator.text());
        generator = new GifVCGenerator(width, height, count);//   gif
        generator.write2out(new FileOutputStream(path + "/1.gif")).close();
        System.out.println(generator.text());
        generator = new Gif2VCGenerator(width, height, count);//   gif
        generator.write2out(new FileOutputStream(path + "/2.gif")).close();
        System.out.println(generator.text());
        generator = new Gif3VCGenerator(width, height, count);//   gif
        generator.write2out(new FileOutputStream(path + "/3.gif")).close();
        System.out.println(generator.text());
	}

}
