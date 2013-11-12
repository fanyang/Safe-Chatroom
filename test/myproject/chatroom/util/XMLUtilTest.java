package myproject.chatroom.util;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import myproject.chatroom.util.MessageType;
import myproject.chatroom.util.XMLUtil;

import org.junit.*;

public class XMLUtilTest
{
	
	@Before
	public void setUp() {
	}
	
	
	@Test
	public void testConstructLoginXML() {
		
		String username = "Alice";
		String xml = XMLUtil.constructLoginXML(username);
		
		String extractedUsername = XMLUtil.extractUsername(xml);
		
		assertThat(extractedUsername, is(username));
		
	}
	
	@Test
	public void testExtractType() {
		
		List<String> users = new ArrayList<>();
		users.add("Alice");
		users.add("Bob");
		
		String xml = XMLUtil.constructUserList(users);
		MessageType messageType = XMLUtil.extractType(xml);
		
		assertThat(messageType, is(MessageType.USER_LIST));
		
	}
	
	@Test
	public void testExtractUserMessage() {
		
		String user = "Alice";
		String content = "Hello, I'm Alice.";
		
		String xml = XMLUtil.constructMessageXML(user, content);
		String message = XMLUtil.extractContent(xml);
		
		assertThat(message, is("Alice: Hello, I'm Alice."));
		
	}
	
}
