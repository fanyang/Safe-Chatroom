package myproject.chatroom.server;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class Server extends JFrame {
	
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton jButton;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane;
	private JTextArea jTextArea;
	private JTextField jTextField;
	
	/**
	 * 
	 * @param name server title
	 */
	public Server(String title)	{
		super(title);
		this.initComponents(); //initialize UI
	}
	

	private void initComponents() {
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		
		jTextField = new JTextField(10);
		jButton = new JButton();
		jScrollPane = new JScrollPane();
		jTextArea = new JTextArea();
		

		jPanel1.setBorder(BorderFactory.createTitledBorder("Server Info"));
		jPanel2.setBorder(BorderFactory.createTitledBorder("Online Users"));

		jTextField.setText("8443");
		
		jLabel1.setText("Server Status");
		jLabel2.setForeground(new Color(204, 0, 51));
		jLabel2.setText("Stop");
		jLabel3.setText("Port");
		jButton.setText("Start");
		jButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				int port = Integer.parseInt(jTextField.getText().trim());
				new ServerConnection(Server.this, port).start();
			}
			
		});
		
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel3);
		jPanel1.add(jTextField);
		jPanel1.add(jButton);

		jTextArea.setEditable(false); //User cannot edit online users list
		jTextArea.setRows(20);
		jTextArea.setColumns(30);
		jTextArea.setForeground(new Color(0, 51, 204));

		jScrollPane.setViewportView(jTextArea);
		jPanel2.add(jScrollPane);

		this.getContentPane().add(jPanel1, BorderLayout.NORTH);
		this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public void setRunningState() {
		this.jLabel2.setText("Run");
		this.jButton.setEnabled(false);
	}

	
	public void updateUserList(String str) {
		
		this.jTextArea.setText(str);
	}


	public static void main(String[] args) {
		new Server("Server");
	}
	
	
	
	
}