import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

public class JWC extends JFrame{

	JTextArea contentArea;
	String title;
	String content;
	String id;
	HashMap<String, String> hm;
	private Container container;
	private FlowLayout layout;
	private JButton lastButton;
	
	JWC(){
		super( "Get JWC Info" );//set title
		container = getContentPane();
		container.setLayout(null);
		
		//init do sth	
		id=JWC_core.getLatestNewsID();
		hm =JWC_core.readNews(id);
		title = hm.get("title");
		
		//last
		lastButton = new JButton("Last");
		lastButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				hm = JWC_core.readNews(String.valueOf(Integer.parseInt(id)-1));
				title = hm.get("title");
				content = hm.get("newsContent");
				if(content.isEmpty()){
					contentArea.setText("该新闻已被删除");
				}else{
					contentArea.setText("\n\n"+content);
				}
				contentArea.setCaretPosition(0);				
			}
		});
		
		container.add(lastButton);			
		
		contentArea = new JTextArea(hm.get("newsContent"));
//		contentArea.setSize(500, 600);
		contentArea.setEditable(false);
		contentArea.setLineWrap(true);
		contentArea.setCaretPosition(0);
		
		JScrollPane scroll = new JScrollPane(contentArea);    //添加滚动条
		scroll.setSize(500, 600);		
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 		
		container.add(scroll);
		
		setSize(600, 600);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JWC test = new JWC();
		test.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	}

}
