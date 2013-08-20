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
 * close client window: 5
 * close server window: 6
 * close client window confirmation: 7
 * login result: 8
 *
 */


public class XMLUtil
{
	private static Document constructDocument()
	{
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("message");
		
		document.setRootElement(root);
		
		return document;
	}
	
	
	/**
	 * XML that client send to server when client login
	 * @return
	 */
	public static String constructLoginXML(String username)
	{
		Document document = constructDocument();
		
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("1");
		
		Element user = root.addElement("user");
		user.setText(username);
		
		return document.asXML();
	}
	
	/**
	 * �ӿͻ��˵�¼���͵�XML����н������û���username��
	 */
	public static String extractUsername(String xml)
	{
		try
		{
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element user = document.getRootElement().element("user");
			
			return user.getText();
		}
		catch(Exception ex)
		{
			
		}
		
		return null;
	}
	
	/**
	 * ������ͻ��˷��͵������û��б�xml���
	 */
	
	public static String constructUserList(Set<String> users)
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("4");
		
		for(String user : users)
		{
			Element e = root.addElement("user");
			e.setText(user);
		}
		
		return document.asXML();
	}
	
	/**
	 * ��XML��Ϣ����ȡ�����е������û��б���Ϣ
	 */
	public static List<String> extractUserList(String xml)
	{
		List<String> list = new ArrayList<String>();
		
		try
		{
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			for(Iterator iter = document.getRootElement().elementIterator("user"); iter.hasNext();)
			{
				Element e = (Element)iter.next();
				
				list.add(e.getText());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 *  ��xml��Ϣ�н���������ֵ
	 */
	public static String extractType(String xml)
	{
		try
		{
			SAXReader saxReader = new SAXReader();
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element typeElement = document.getRootElement().element("type");
			
			return typeElement.getText();
		}
		catch(Exception ex)
		{
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
	
	/**
	 * ����������˴��ڹرյ�XML���
	 * 
	 */
	public static String constructCloseServerWindowXML()
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("6");
		
		return document.asXML();
	}
	
	/**
	 * ����ͻ��˴��ڹرյ�XML���
	 */
	public static String constructCloseClientWindowXML(String username)
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("5");
		
		Element user = root.addElement("user");
		user.setText(username);
		
		return document.asXML();
	}
	
	/**
	 * �����������ȷ�Ͽͻ��˹رյ�XML��Ϣ
	 */
	
	public static String constructCloseClientWindowConfirmationXML()
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element type = root.addElement("type");
		type.setText("7");
		
		return document.asXML();
	}
	
	/**
	 * ������ͻ��˷��صĵ�¼���XML
	 */
	public static String constructLoginResultXML(String result)
	{
		Document document = constructDocument();
		Element root = document.getRootElement();
		
		Element typeElement = root.addElement("type");
		typeElement.setText("8");
		
		Element resultElement = root.addElement("result");
		resultElement.setText(result);
		
		return document.asXML();
	}
	
	/**
	 * ��xml����н�������¼���
	 */
	
	public static String extractLoginResult(String xml)
	{
		String result = null;
		
		try
		{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new StringReader(xml));
			
			Element root = document.getRootElement();
			
			result = root.element("result").getText();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return result;
	}
	
}



















