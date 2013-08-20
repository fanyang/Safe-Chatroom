package chatroom.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ChatClient extends JFrame {
	
	private JButton jButton1;
	private JButton jButton2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTextArea jTextArea1;
	private JTextArea jTextArea2;
	private JTextField jTextField;
	
	private ClientConnection clientConnection;
	
	
	public ChatClient(String name, ClientConnection clientConnection){
		
		super(name);
		this.clientConnection = clientConnection;
		initComponents();
		
	}


	private void initComponents() {
		
		jPanel1 = new JPanel();
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();
		jTextField = new JTextField(20);
		jButton1 = new JButton();
		jButton2 = new JButton();
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jTextArea2 = new JTextArea();
		jPanel3 = new JPanel();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		jPanel1.setBorder(BorderFactory.createTitledBorder("Messages"));
		jPanel2.setBorder(BorderFactory.createTitledBorder("Users"));
		
		jTextArea1.setColumns(30);
		jTextArea1.setRows(25);
		jTextArea2.setColumns(20);
		jTextArea2.setRows(27);
		jTextArea1.setEditable(false);
		jTextArea2.setEditable(false);
		
		jPanel3.add(jTextField);
		jPanel3.add(jButton1);
		jPanel3.add(jButton2);

		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(jScrollPane1, BorderLayout.NORTH);
		jPanel1.add(jPanel3, BorderLayout.SOUTH);

		jPanel2.add(jScrollPane2);

		jScrollPane1.setViewportView(jTextArea1);
		jScrollPane2.setViewportView(jTextArea2);

		jButton1.setText("Send");
		jButton2.setText("Clear");
		jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientConnection.sendMessage(jTextField.getText());
				jTextField.setText("");
			}
		});

		this.setLayout(new FlowLayout());
		this.getContentPane().add(jPanel1);
		this.getContentPane().add(jPanel2);

		this.pack();
		this.setVisible(true);
	}

	
	
	
}
