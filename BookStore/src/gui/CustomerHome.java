package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import main.Customer;
import main.LogIn;
import main.User;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;

public class CustomerHome extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private static CustomerHome frame ;
	
	private User usr ; 
	/**
	 * Create the frame.
	 * @param connection 
	 * @param statment 
	 * @param user_name 
	 */
	public CustomerHome(final Statement statement,final Connection connection, final String user_name) {
		frame = this ; 
		usr = new Customer(connection, statement, user_name) ;
		this.setTitle("Home");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("file");
		menuBar.add(mnFile);
		
		JMenuItem mntmSignOut = new JMenuItem("Sign Out");
		mntmSignOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogIn logout = new LogIn(statement) ;
				
				logout.LogOut(user_name);
				
				frame.setVisible(false);
				frame.dispose(); 
				
				new LoginFrame() ;
			}
		});
		mnFile.add(mntmSignOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		JMenu mnAbout = new JMenu("help");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("about");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutFrame(frame).setVisible(true); ;
			}
		});
		mnAbout.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				
				
				frame.setVisible(false);
				frame.dispose();
				
				new SignUpCustomer(statement, connection, true, usr);
			}
		});
		btnEditProfile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditProfile.setIcon(new ImageIcon("pics\\edit2.png"));
		btnEditProfile.setBounds(30, 32, 252, 160);
		contentPane.add(btnEditProfile);
		
		JButton btnShowBooks = new JButton("Show Books ");
		btnShowBooks.setIcon(new ImageIcon("pics\\books.png"));
		btnShowBooks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnShowBooks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.dispose();
				
				new BooksFrame(statement, connection, usr);
			
			}
		});
		btnShowBooks.setBounds(30, 221, 252, 137);
		contentPane.add(btnShowBooks);
		
		JButton btnCheckOut = new JButton("check out");
		btnCheckOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose(); 
				
				new CheckOut(statement, connection, usr) ;
			}
		});
		btnCheckOut.setIcon(new ImageIcon("pics\\checkout.png"));
		btnCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCheckOut.setBounds(30, 387, 252, 137);
		contentPane.add(btnCheckOut);
		
		JLabel lblWelecomHamada = new JLabel("welecom " + user_name);
		lblWelecomHamada.setForeground(SystemColor.activeCaption);
		lblWelecomHamada.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWelecomHamada.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelecomHamada.setBounds(338, 11, 183, 38);
		contentPane.add(lblWelecomHamada);
	}
}
