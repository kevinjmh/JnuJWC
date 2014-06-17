import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import util.NetUtil;


public class JWC_core {
	static String newsListUrl="http://jwc.jnu.edu.cn/index-new.asp";
	static String last_id;
	
	public static void getNewsList(){
		String html = NetUtil.getHtmlText(newsListUrl);
		//<A  HREF="readnews.asp?id=2132&bigclassname=教务处
		Pattern pa = Pattern.compile("id=[0-9]+");				
		Matcher ma = pa.matcher(html);
		while (ma.find()) {
			System.out.println(ma.group().substring(3));
		}
		
		pa = Pattern.compile("<FONT COLOR=\"#000000\" CLASS=\"unnamed3\">(.*?)</FONT></A></div>");
        ma = pa.matcher(html);
        while (ma.find()) {
			System.out.println(ma.group().replaceAll("<[^>]*>", "").trim());
        }
		return;
	}
	
	/*
		public static void getNewsList() {
		String linkid = null;
		String linkText;
		Document doc = null;
		Pattern pa = Pattern.compile("id=[0-9]+");
		try {
			doc = Jsoup.connect(newsListUrl).get();
			Elements els = doc.getElementsByTag("A");
			for (Element link : els) {
				linkText = link.text();
				Matcher ma = pa.matcher(link.attr("href"));
				if (ma.find()) {
					linkid = ma.group().substring(3);
				}
				System.out.println(linkid + "\n" + linkText);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/
	
	public static String getLatestNewsID(){
		String html = NetUtil.getHtmlText(newsListUrl);
		//<A  HREF="readnews.asp?id=2132&bigclassname=教务处
		Pattern pa = Pattern.compile("id=[0-9]+");				
		Matcher ma = pa.matcher(html);
		if (ma.find()) {
			last_id=ma.group().substring(3);
			return ma.group().substring(3);
		}
		return null;
	}
	
	public static String buildUrlByID(String id){
		String head ="http://jwc.jnu.edu.cn/ReadNews.asp?ID=";
		String tail ="&BigClassName=0&SmallClassName=0&SpecialID=0";
		return head+id+tail;
	}
	
	public static HashMap<String, String> readNews(String id){
		HashMap<String, String> hm = new HashMap<String, String>();
		String html = NetUtil.getHtmlText(buildUrlByID(id));
		// get title
		String title = null;
		Pattern pa = Pattern
				.compile("<font color=\"#000000\">(.*?)</font>");
		Matcher ma = pa.matcher(html);
		while (ma.find()) {			
			title = ma.group();
			title = title.replaceAll("<[^>]*>", "");
//			System.out.println(title);			
		}
		
		// getContent
		String newsContent = "";
		pa = Pattern.compile("<font class=news>(.*?)<td align=\"left\"  >&nbsp;</td>");
		ma = pa.matcher(html);
		if (ma.find()) {
			newsContent = ma.group();			
			newsContent = newsContent.replaceAll("<P", "\n    <");
			newsContent = newsContent.replaceAll("<[^>]*>", "");
			newsContent = newsContent.replaceAll("&nbsp;", " ");
			newsContent = newsContent.replaceAll("&amp;", "&");
			newsContent = newsContent.replaceAll("<br>", "\n");
		}
//		System.out.println(newsContent);
		hm.put("title", title);
		hm.put("newsContent", newsContent);
		return hm;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.print(getLatestNewsID());
//		readNews(String.valueOf(Integer.parseInt(id)-1));
//		String html = NetUtil.getHtmlText(buildUrlByID(getLatestNewsID()));
//		System.out.print(html);
//		getNewsList();
		readNews("2578");
	}

}
