package chatroom.client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Client extends JFrame
{
	private JButton jButton1; 
	private JButton jButton2;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel;
	private JTextField usernameText;
	private JTextField hostText;
	private JTextField portText;

	
	public Client(String name) {
		super(name);
		initComponents(); // initialize UI
	}

	
	private void initComponents() {
		jPanel = new JPanel();

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();

		usernameText = new JTextField(15);
		hostText = new JTextField(15);
		portText = new JTextField(15);

		jButton1 = new JButton();
		jButton2 = new JButton();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);

		jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));

		jLabel1.setText("Username");
		jLabel2.setText("Server");
		jLabel3.setText("Port");

		jButton1.setText("Login");
		jButton2.setText("Reset");

		usernameText.setText("Alice");
		hostText.setText("localhost");
		portText.setText("8443");
		
		jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				usernameText.setText("");
				hostText.setText("");
				portText.setText("");
			}
		});
		
		jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameText.getText().trim();
				String host = hostText.getText().trim();
				int port = Integer.parseInt(portText.getText().trim());
				
				new ClientConnection(Client.this, username, host, port).start();
			}
		});
		
		jPanel.add(jLabel1);
		jPanel.add(usernameText);
		jPanel.add(jLabel2);
		jPanel.add(hostText);
		jPanel.add(jLabel3);
		jPanel.add(portText);
		jPanel.add(jButton1);
		jPanel.add(jButton2);

		this.getContentPane().add(jPanel);

		this.pack();
		this.setVisible(true);
	}
	
	

	public static void main(String[] args) {
		new Client("Client");
	}

}
