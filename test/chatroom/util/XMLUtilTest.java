package chatroom.util;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class XMLUtilTest
{
	private SAXReader saxReader;
	
	@Before
	public void setUp()
	{
		saxReader = new SAXReader();
	}
	
	@Test
	public void testConstructLoginXML()
	{
		try
		{
			String xml = XMLUtil.constructLoginXML("Alice");
			
			Document document = saxReader.read(new StringReader(xml));
			
			Element root = document.getRootElement();
			
			String rootName = root.getName();
			
			Element typeElement = root.element("type");
			Element userElement = root.element("user");
			
			assertThat(rootName, is("message"));
			assertThat(typeElement.getText(), is("1"));
			assertThat(userElement.getText(), is("Alice"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
}
