package chatroom.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * login : 1
 * client message : 2
 * server message : 3
 * user list: 4
 *
 */


public class XMLUtil {
	
	private static Document constructDocument() {
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("message");
		
		document.setRootElement(root);
		
		return document;
	}
	
	
	/**
	 * XML that client send to server when client login
	 * @return
	 */
	public static String constructLoginXML(String username) {
		Document document = constructDocument();
		
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText(MessageType.LOGIN.name());
		
		Element body = root.addElement("body");
		
		Element user = body.addElement("user");
		user.setText(username);
		
		return document.asXML();
	}
	
	
	/**
	 * extract username from login message
	 */
	public static String extractUsername(String xml) {
		
		try {
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element user = document.getRootElement().element("body").element("user");
			
			return user.getText();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * construct user list
	 */
	
	public static String constructUserList(List<String> users) {
		
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText(MessageType.USER_LIST.name());
		
		Element body = root.addElement("body");
		
		for(String user : users)
		{
			Element e = body.addElement("user");
			e.setText(user);
		}
		
		return document.asXML();
	}
	
	/**
	 * extract user list
	 */
	public static List<String> extractUserList(String xml) {
		
		List<String> list = new ArrayList<String>();
		
		try {
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			for(Iterator<Element> iter = document.getRootElement().element("body")
					.elementIterator("user"); iter.hasNext();) {
				
				Element e = iter.next();
				list.add(e.getText());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 *  extract message type
	 */
	public static MessageType extractType(String xml) {
		try {
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element typeElement = document.getRootElement().element("type");
			
			return MessageType.valueOf(typeElement.getText());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * ����ͻ�����������˷��͵��������xml
	 * 
	 * @param username
	 * @param message
	 * @return
	 */
	public static String constructMessageXML(String username, String message)
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("2");
		
		Element user = root.addElement("user");
		user.setText(username);
		
		Element content = root.addElement("content");
		content.setText(message);
		
		return document.asXML();
	}
	
	/**
	 * ����������������пͻ��˷��͵�XML�������
	 * 
	 */
	public static String constructServerMessageXML(String message)
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("3");
		
		Element content = root.addElement("content");
		content.setText(message);
		
		return document.asXML();
	}
	
	
	
	
	/**
	 * �ӿͻ�����������˷��͵�XML��������н�������������
	 */
	public static String extractContent(String xml)
	{
		try
		{
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element contentElement = document.getRootElement().element("content");
			
			return contentElement.getText();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	

}


