package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetUtil {
	
	public static String getHtmlText(String url){
		String temp;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(url).openStream(), "gbk"));// utf-8 读取网页全部内容
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(getHtmlText("http://jwc.jnu.edu.cn/index-new.asp"));
	}

}
