import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;



public class JWC extends JFrame{

	/**
	 * @param args
	 */
	JTextArea contentArea;
	String title;
	String content;
	String url;
	String head ="http://jwcweb.jnu.edu.cn/ReadNews.asp?ID=";
	String tail ="&BigClassName=0&SmallClassName=0&SpecialID=0";
	int IDnum=getLatesetNum();//2132;
	HashMap<String, String> hm;
	private Container container;
	private FlowLayout layout;
	private JButton nextButton,lastButton,explorerButton;
	
	JWC(){
		super( "Get JWC Info" );//set title
		layout = new FlowLayout();
		container = getContentPane();
		container.setLayout(layout);
		
		//init do sth
		url = head + IDnum + tail;
		hm = getFromJWC(url);
		title = hm.get("title");
		
		//last
		lastButton = new JButton("Last");
		lastButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IDnum-=1;
				url = head + IDnum + tail;
				hm = getFromJWC(url);
				title = hm.get("title");
				content = hm.get("newsContent");
				if(content.isEmpty()){
					contentArea.setText("该新闻已被删除");
				}else{
					contentArea.setText(IDnum+"\n\n"+content);
				}
				contentArea.setCaretPosition(0);
				
			}
		});
		
		container.add(lastButton);
		
		//content
		content = hm.get("newsContent");
		contentArea = new JTextArea(content);
		contentArea.setSize(550, 600);
		contentArea.setEditable(false);
		contentArea.setLineWrap(true);
		contentArea.setCaretPosition(0);
		container.add(new JScrollPane(contentArea));
		
		setSize(600, 600);
		setVisible(true);
	}
	
	
	private HashMap<String, String> getFromJWC(String url2) {
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
		try {
			return (Double.parseDouble(newsContent)) / 7 /2;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
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

	private int getLatesetNum(){
		int num=2122;
		String html="";
		try {
			html = getOneHtml("http://jwcweb.jnu.edu.cn/index-new.asp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//<A  HREF="readnews.asp?id=2132&bigclassname=教务处
		Pattern pa = Pattern
				.compile("id=(.*?)&bigclassname=教务处");
		Matcher ma = pa.matcher(html);
		if (ma.find()) {
			num = Integer.parseInt(ma.group().substring(3, 7));
		}
		return num;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JWC test = new JWC();
		test.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	}

}
