package org.zp.vimg.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.math.RandomUtils;

public class VimgUtil {

	private static final int width = 360;
	private static final int height = 200;
	private static final int [] index = {0, 1, 2, 3, 4, 5, 6, 7};
	private static String [] imageName = {"huangbo", "lyf", "baoshijie", "nezha", "zhanlang"};
	private static BufferedImage image = null;
	private static Map<String, BufferedImage> imageMap = null;		// 把读到的图片缓存起来
	
	static{
		image = new BufferedImage(width, height, 1) ;
		imageMap = new HashMap<>(30);
	}
	
	// 绘制图片验证码
	private static BufferedImage bulidImage(List<Integer> answer) throws Exception {
		
		Graphics2D graphics2d = image.createGraphics();
		
		List<String> imageNames = new ArrayList<>(imageName.length);
		
		for(String name : imageName){
			imageNames.add(name);
		}
		
		String answerName = imageNames.remove(getRandom(imageName.length));
		
		BufferedImage bgImage = imageMap.get(answerName);
		
		if(bgImage == null){
			bgImage = ImageIO.read(new File("C:\\vimg\\" + answerName + ".jpg"));
			imageMap.put(answerName, bgImage);
		}
		
		// 绘制背景
		graphics2d.drawImage(bgImage, 0, 0, 360, 200, null);
		
		// 绘制内容
		String cImageName;
		BufferedImage cImage = null;
		
		int x = 0, y = 0;
		for (int i = 0; i < 8; i++) {
			x = 24 + (i > 3 ? i - 4 : i) * 84;
			y = i > 3 ? 110 : 30;
			
			cImageName = answer.contains(i) ? answerName : imageNames.get(getRandom(imageNames.size()));
			
			cImageName = cImageName + "_c_" + getRandom(3);
			
			cImage = imageMap.get(cImageName);
			
			if(cImage == null){
				cImage = ImageIO.read(new File("C:\\vimg\\"+cImageName+".jpg"));
				imageMap.put(cImageName, cImage);
			}
			
			graphics2d.drawImage(cImage, x, y, 60, 60, null);
		}
		graphics2d = null;
		imageNames = null;
		answerName = null;
		cImageName = null;
		cImage = null;
		return image;
	}
	
	public static BufferedImage getImage(List<Integer> answer) throws Exception {
		check();
		return bulidImage(answer);
	}
	
	public static void writeImage(List<Integer> answer, OutputStream outputStream) throws Exception {
		check();
		ImageIO.write(bulidImage(answer), "JPEG", outputStream);
	}
	
	public static List<Integer> getAnswer(){
		
		int answerNum = getRandom(4);
		
		List<Integer> answer = new ArrayList<Integer>(8);
		
		for (int i : index)
			answer.add(i);
		
		for (int i = 0; i < 7-answerNum; i++)
			 answer.remove(getRandom(8-i));
		
		return answer;
	}
	
	private static int getRandom(int max){
		return RandomUtils.nextInt(max);
	}
	
	private static void check() {
		if(image == null  || imageMap == null)
			throw new RuntimeException(" validateImage initialization fail");
	}
	
}