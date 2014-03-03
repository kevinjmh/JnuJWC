package dump;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse {
	
	/**
	 * ��ȡ��Ϣ
	 * @param url
	 * @return
	 */
	public static Content getHtmlInfo(String url){
		Content content = new Content();
		
		if(url.indexOf("xxx")!=-1){
			try {
				content = getxxInfo(url);
			} catch (Exception e) {
				content = null;
			}
		
		}else if(url.indexOf("http://jwcweb.jnu.edu.cn/index-new.asp")!=-1){
			try {
				content = getJWCNews(url);
			} catch (Exception e) {
				content = null;
			}
		}
		
		return content;
	}
	
	
	
	
	/**
	 * 
	 * @param url  
	 */
	public static Content getxxInfo(String url) throws Exception{
		return null;
		
	}
	
	/**
	 * ��ȡ��������֪ͨ
	 * @param url  http://jwcweb.jnu.edu.cn/index-new.asp
	 */
    
	public static Content getJWCNews(String url) throws Exception{
		 
  	  String jwc=java.net.URLEncoder.encode("����");
  	  String tongzhi=java.net.URLEncoder.encode("֪ͨ");
		 try { 
             //��ȡĿ�����ӵ�Document 
             Document doc = Jsoup.connect(url).get(); 
             //��ȡ����input��ǩ 
             Elements els = doc.getElementsByTag("A"); 
             System.out.println("\n\n\n"+els+"\n"); 
             //��������õı�ǩ 
             for (Element e : els) { 
                  System.out.println(e.nodeName()+":\t"+e.val()); 
             } 
       	  System.out.println("------------------------------�����������--------------------------");
       	  System.out.println("\t");
             for (Element link : els) {
            	  String linkHref = link.attr("href");
            	  String linkText = link.text();
            	/*  System.out.println("------------------------------��ȡ��������--------------------------"+"\t");
            	  System.out.println(link.nodeName()+":\t"+ "http://jwcweb.jnu.edu.cn/"+linkHref+"--"+linkText ); */
            	  
            	  /**����ֱ�ӻ�ȡ������url����ֱ��ʹ�ã���Ҫ������ת����ܷ��ʸ�����
            	   * ������Ҫ��url�𿪣����������ֱ�������ع�
            	   */
            	 
            	  String []split=linkHref.split("����");
            	    
            	  String href2=split[0]+jwc+split[1];
          
            	  String []last=href2.split("֪ͨ");

            	  System.out.println(link.nodeName()+":\t"+ "http://jwcweb.jnu.edu.cn/"+last[0]+tongzhi+last[1]);
            	  System.out.println(linkText); 

             }
        } catch (IOException e) {          
            e.printStackTrace(); 
        } 
		return null;
		
	}

	/**
	 * ��ȡ6�䷿��Ƶʱ��    
	 *//*
	private static String getVideoTime(Document doc, String url, String id) {
		String time = null;
		
		Element timeEt = doc.getElementById(id); 
		Elements links = timeEt.select("dt > a");
		
		
		for (Element link : links) {
		  String linkHref = link.attr("href");
		  if(linkHref.equalsIgnoreCase(url)){
			  time = link.parent().getElementsByTag("em").first().text();
			  break;
		  }
		}
		return time;
	}*/
	
			
	/**
	 * ��ȡscriptĳ��������ֵ
	 * @param name  ��������
	 * @return   ���ػ�ȡ��ֵ 
	 *//*
	private static String getScriptVarByName(String name, String content){
		String script = content;
		
		int begin = script.indexOf(name);
		
		script = script.substring(begin+name.length()+2);
		
		int end = script.indexOf(",");
		
		script = script.substring(0,end);
		
		String result=script.replaceAll("'", "");
		result = result.trim();
		
		return result;
	}
	*/
	
	/**
	 * ����HTML��ID��������������ȡ����ֵ
	 * @param id  HTML��ID��
	 * @param attrName  ������
	 * @return  ��������ֵ
	 *//*
	private static String getElementAttrById(Document doc, String id, String attrName)throws Exception{
		Element et = doc.getElementById(id);
		String attrValue = et.attr(attrName);
		
		return attrValue;
	}*/
	
	
	
	/**
	 * ��ȡ��ҳ������
	 *//*
	private static Document getURLContent(String url) throws Exception{
		Document doc = Jsoup.connect(url)
		  .data("query", "Java")
		  .userAgent("Mozilla")
		  .cookie("auth", "token")
		  .timeout(6000)
		  .post();
		return doc;
	}
	*/
	
	public static void main(String[] args) {
	
		try {
			getHtmlInfo("http://jwcweb.jnu.edu.cn/index-new.asp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
