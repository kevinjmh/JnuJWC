package dump;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class urlToText {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://jwcweb.jnu.edu.cn/ReadNews.asp?ID=2130&BigClassName=0&SmallClassName=0&SpecialID=0";
		/*
		 * final BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in)); try { url = br.readLine(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		urlToText jwc = new urlToText();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm = jwc.getFromJWC(url);
		System.out.println("标题:" + hm.get("title"));
		System.out.println("内容:" + hm.get("newsContent"));
	}

	private HashMap<String, String> getFromJWC(String url) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm = new HashMap<String, String>();
		String html = "";
		String title = "";
		String newsContent = "";

		System.out.println("\n--开始读取网页--");
		try {
			html = getOneHtml(url);
		} catch (final Exception e) {
			e.getMessage();
		}
		System.out.println("--读取网页结束--\n");

		// get title
		Pattern pa = Pattern
				.compile("color=\"#FF6600\"><p>&nbsp;</p>(.*?)</div>");
		Matcher ma = pa.matcher(html);
		if (ma.find()) {
			pa = Pattern.compile("</p>(.*?)</div>");
			ma = pa.matcher(ma.group());
			if (ma.find()) {
				title = ma.group();
			}
		}

		// getContent
		pa = Pattern.compile("<font class=news>(.*?)<hr size=1 color=#66CC99>");
		ma = pa.matcher(html);

		if (ma.find()) {
			newsContent = ma.group();
//==================================================================================
			String temp="";
			StringBuffer  space = new StringBuffer();
			int spacenum;
			Pattern pa2 = Pattern.compile("TEXT-INDENT: [0-9]*"); 
					//.compile("TEXT-INDENT: (.*?)pt;");
			Matcher ma2 = pa2.matcher(newsContent);
			while(ma2.find()){
				temp=ma2.group();

				for(spacenum=(int)getSpaceNum(temp);spacenum>0;spacenum--){
					  space.append("&nbsp;");
				  }
				newsContent = newsContent.replaceAll(temp, ">"+space.toString()+"<");
			}

			//newsContent = newsContent.replaceAll("<(/?)((a)|(o)|(FONT)|(font)|(SPAN)|(span)|(table)|(class)|(td)|(tr)|(lang)|(style)|(height)|(width))(.*?)>","");
//======================================================================
			newsContent = newsContent.replaceAll(" ", "");
			newsContent = newsContent.replaceAll("<br>", "\n");
			newsContent = newsContent.replaceAll("<P", "\n<");
			newsContent = newsContent.replaceAll("&nbsp;", " ");
			newsContent = newsContent.replaceAll("&amp;", "&");
			newsContent = newsContent.replaceAll("&quot;", "\"");
		}

		hm.put("title", title.replaceAll("<.*?>", ""));
		hm.put("newsContent", newsContent.replaceAll("<.*?>", ""));
		return hm;
	}

	private double getSpaceNum(String newsContent) {
		newsContent = newsContent.replace("TEXT-INDENT: ","");
		newsContent = newsContent.replace("pt;", "");
		return (Double.parseDouble(newsContent)) / 7;
	}

	public String getOneHtml(final String htmlurl) throws IOException {
		URL url;
		String temp;
		final StringBuffer sb = new StringBuffer();
		try {
			url = new URL(htmlurl);
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream(), "gbk"));// utf-8 读取网页全部内容
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			in.close();
		} catch (final MalformedURLException me) {
			System.out.println("你输入的URL格式有问题！请仔细输入");
			me.getMessage();
			throw me;
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		}
		return sb.toString();
	}

}
