package org.zp.vimg.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.zp.vimg.util.VimgUtil;

public class VimgTest {

	public static void main(String[] args) {
		List<Integer> answer = VimgUtil.getAnswer();
		System.out.println(" --- 正确答案 ： " + answer);
		
		try {
			ImageIO.write(VimgUtil.getImage(answer), "JPEG", new File("C:\\vimg\\vimg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
