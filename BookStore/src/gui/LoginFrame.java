package gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.mysql.jdbc.Connection;

import main.DBConnect;
import main.LogIn;

public class LoginFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private int is_manager = 0 ; 
	private DBConnect dbConnect;
	private Connection conn;
	private Statement statement;
	private LogIn login; 



	/**
	 * Create the application.
	 */
	public LoginFrame() {
		
		/*connect to our data base */
		dbConnect = new DBConnect();
		dbConnect.connect();
		statement = dbConnect.getStatement();
		conn = (Connection) dbConnect.getConnection();
		login = new LogIn(statement);
		
		is_manager = 0 ;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.setBounds(100, 100, 796, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "choose type", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel_1.setBounds(202, 405, 409, 46);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JRadioButton rdbtnManager = new JRadioButton("manager ");
		buttonGroup.add(rdbtnManager);
		rdbtnManager.setBounds(222, 16, 141, 23);
		panel_1.add(rdbtnManager);

		JRadioButton rdbtnCustomer = new JRadioButton("customer ");
		rdbtnCustomer.setSelected(true);
		buttonGroup.add(rdbtnCustomer);
		rdbtnCustomer.setBounds(112, 16, 108, 23);
		panel_1.add(rdbtnCustomer);
		
		rdbtnCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				is_manager = 0 ; 
				
			}
		});
		
		rdbtnManager.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				is_manager = 1 ; 
				
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log in", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel.setBounds(200, 298, 411, 75);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(104, 16, 273, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(104, 47, 273, 20);
		panel.add(passwordField);

		JLabel lblName = new JLabel("name : ");
		lblName.setBounds(6, 16, 88, 20);
		panel.add(lblName);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblPassword = new JLabel("password :");
		lblPassword.setBounds(6, 48, 88, 20);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel label = new JLabel("");
		label.setBounds(387, 47, 18, 20);
		panel.add(label);
		label.setIcon(new ImageIcon("pics\\privacy_icon.jpg"));

		JLabel label_1 = new JLabel("");
		label_1.setBounds(387, 16, 18, 20);
		panel.add(label_1);
		label_1.setIcon(new ImageIcon("pics\\name.png"));

		JButton login_button = new JButton("login\r\n");
		login_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				String user_name = usernameField.getText().trim(); 
				String pass = String.valueOf(passwordField.getPassword()) ; 

				if (user_name.isEmpty() || pass.isEmpty())
				{
					JOptionPane.showMessageDialog(frame	, "please fill user name & passowrd fields");
				}
				else
				{
					
					boolean success = login.LoginIn(user_name, pass, is_manager) ;
					
					if (!success)
					{
						JOptionPane.showMessageDialog(frame, "you entered wrong user_name or password !\n\tto create new account please press SIGN UP");
					}
					else
					{
						/*open up main page for customer Or manager*/
						
						frame.dispose();
						frame.setVisible(false);
						if (is_manager == 1) // manager
						{
							new MangerHome(statement, conn, user_name) ;
						}
						else 
						{
							new CustomerHome(statement, conn, user_name) ;
						}
						
					}

				}
			}
		});
		login_button.setBounds(275, 502, 89, 23);
		frame.getContentPane().add(login_button);

		JButton signup_button = new JButton("sign up\r\n");
		signup_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 frame.setVisible(false);
			     frame.dispose();
			     // new sign for customer 
			     if (is_manager == 0)
			    	 new SignUpCustomer(statement, conn , false , null) ;
			     else 
			    	 new SignUpManager(statement, conn , false , null) ;
			}	
		});
		signup_button.setBounds(441, 502, 89, 23);
		frame.getContentPane().add(signup_button);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("pics\\book_store.jpg"));
		label_2.setBounds(0, 47, 891, 259);
		frame.getContentPane().add(label_2);
	}
}
