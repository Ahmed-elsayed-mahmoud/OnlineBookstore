package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mysql.jdbc.Connection;

import main.SignUp;
import main.User;

public class SignUpCustomer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SignUpCustomer frame = null;

	private JPanel contentPane;
	private JTextField usernametf;
	private JTextField firstnametf;
	private JTextField lastnametf;
	private JTextField emailtf;
	private JTextField telephonetf;
	private JTextField addresstf;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	private ArrayList<String> params ;
	private SignUp signup  ;
	/**
	 * Create the frame.
	 */



	public SignUpCustomer(final java.sql.Statement statment ,final Connection connection , final boolean edit , final User usr) {
		frame = this ;
		params = new ArrayList<>() ; 
		signup = new SignUp(statment);

		this.setTitle("New User Registeration");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sign up", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(153, 180, 209)));
		panel.setBounds(94, 26, 649, 530);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("user name  ");
		lblNewLabel.setBounds(6, 16, 98, 30);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblFirstName = new JLabel("first name ");
		lblFirstName.setBounds(6, 76, 98, 30);
		panel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblLastName = new JLabel("last name ");
		lblLastName.setBounds(6, 136, 98, 30);
		panel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblPassword = new JLabel("Email");
		lblPassword.setBounds(6, 196, 98, 30);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblTelephone = new JLabel("telephone");
		lblTelephone.setBounds(6, 256, 98, 30);
		panel.add(lblTelephone);
		lblTelephone.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(6, 316, 98, 30);
		panel.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblPassword_1 = new JLabel("password");
		lblPassword_1.setBounds(6, 376, 98, 30);
		panel.add(lblPassword_1);
		lblPassword_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblPassword_2 = new JLabel("password");
		lblPassword_2.setBounds(6, 436, 98, 30);
		panel.add(lblPassword_2);
		lblPassword_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		usernametf = new JTextField();
		usernametf.setBounds(146, 23, 497, 20);
		panel.add(usernametf);
		usernametf.setColumns(10);

		firstnametf = new JTextField();
		firstnametf.setBounds(146, 83, 497, 20);
		panel.add(firstnametf);
		firstnametf.setColumns(10);

		lastnametf = new JTextField();
		lastnametf.setBounds(146, 143, 497, 20);
		panel.add(lastnametf);
		lastnametf.setColumns(10);

		emailtf = new JTextField();
		emailtf.setBounds(146, 203, 497, 20);
		panel.add(emailtf);
		emailtf.setColumns(10);

		telephonetf = new JTextField();
		telephonetf.setBounds(146, 263, 497, 20);
		panel.add(telephonetf);
		telephonetf.setColumns(10);

		addresstf = new JTextField();
		addresstf.setBounds(146, 323, 497, 20);
		panel.add(addresstf);
		addresstf.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(146, 383, 497, 20);
		panel.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(146, 443, 497, 20);
		panel.add(passwordField_1);

		
		
		JButton btnSignUp = new JButton("save");
		btnSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String user_name = usernametf.getText().trim() ; 
				String first_name = firstnametf.getText().trim() ;
				String last_name = lastnametf.getText().trim() ; 
				String address = addresstf.getText().trim()  ; 
				String email = emailtf.getText().trim() ;
				String pass1 = String.valueOf(passwordField.getPassword()) ;
				String pass2 = String.valueOf(passwordField_1.getPassword()) ;
				String telephone = telephonetf.getText() ;

				if (telephone.isEmpty() || user_name.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || address.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "please fill all the fields !");
				}
				else 
				{
					if (pass1.equals(pass2))
					{
							params.add(user_name);
							params.add(first_name);
							params.add(last_name);
							params.add(email);
							params.add(pass1);
							params.add(telephone);
							params.add(address);
							params.add("0");
							params.add("1");
							
							try {
								int result = 1 ;
								if (edit)
									result = usr.editProfile(params) ? 1 : 2  ;
								else 
								 result = signup.addUser(params);
								if (result == 2) JOptionPane.showMessageDialog(frame, "Error :: Can't add user !");
								else
								{
									// open main UI
									JOptionPane.showMessageDialog(frame, "added " + user_name + " successfully J");
									
									frame.setVisible(false);
									frame.dispose();
									
									new LoginFrame() ;
									
								}
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(frame, "Error :: Can't add user !");
							}
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "password is not identical !");
					}
				}


			}
		});
		btnSignUp.setBounds(416, 492, 124, 31);
		panel.add(btnSignUp);
		btnSignUp.setIcon(new ImageIcon("pics\\signup.png"));

		JButton btnCancell = new JButton("cancell");
		btnCancell.setBounds(146, 492, 124, 31);
		panel.add(btnCancell);
		btnCancell.setIcon(new ImageIcon("pics\\cancell.png"));
		btnCancell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				frame.setVisible(false);
			    frame.dispose();
			     
			    new LoginFrame() ;
			}
		});
		
		
		if (edit)
		{
			try {
				ResultSet resultSet =  usr.showProfile() ;
				
				while (resultSet.next()) {
					usernametf.setText(resultSet.getString(1));
					firstnametf.setText(resultSet.getString(2));
					lastnametf.setText(resultSet.getString(3));
					emailtf.setText(resultSet.getString(4));
					telephonetf.setText(resultSet.getString(6));
					addresstf.setText(resultSet.getString(7));
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error !!");
			}
			
			
		}
	}

}
