package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import main.LogIn;
import main.Manager;
import main.User;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;

public class MangerHome extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private static MangerHome frame ;
	private User usr  ;

	/**
	 * Create the frame.
	 * @param connection 
	 * @param statment 
	 * @param user_name 
	 */
	public MangerHome(final Statement statement,final Connection connection, final String user_name) {
		frame = this ; 
		
		usr = new Manager(connection , statement , user_name) ;
		
		
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
		
		JMenuItem mntmReport = new JMenuItem("report");
		mntmReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Report report = new Report() ;
				
				report.prepareReports(connection);
				
				JOptionPane.showMessageDialog(frame, "you report hase been prepared J .");
			}
		});
		mnFile.add(mntmReport);
		
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
				
				new SignUpManager(statement, connection, true, usr);
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
		
		JButton btnPromote = new JButton("promote ");
		btnPromote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 String name = JOptionPane.showInputDialog(frame, "What's your Customer name?");
				 name = name.trim() ;
				 if (name == null || name.isEmpty()) return ;
				 boolean success = ((Manager) usr).promoteCustomers(name) ;
				 
				 if (success)
					 JOptionPane.showMessageDialog(frame, name + " has promoted ");
				 else
					 JOptionPane.showMessageDialog(frame, name + " can't promoted ");
			}
		});
		btnPromote.setFont(new Font("Tahoma", Font.ITALIC, 14));
		btnPromote.setIcon(new ImageIcon("pics\\promote customer.jpg"));
		btnPromote.setBounds(543, 32, 237, 160);
		contentPane.add(btnPromote);
		
		JButton btnAddNew = new JButton("add new ");
		btnAddNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				
				new NewBook(connection , statement , usr) ;
			}
		});
		btnAddNew.setFont(new Font("Tahoma", Font.ITALIC, 14));
		btnAddNew.setIcon(new ImageIcon("pics\\add_book.png"));
		btnAddNew.setHorizontalAlignment(SwingConstants.TRAILING);
		btnAddNew.setBounds(543, 221, 237, 137);
		contentPane.add(btnAddNew);
		
		JButton need = new JButton("");
		need.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				
				new Need(statement, connection, usr);
			}
		});
		need.setIcon(new ImageIcon("pics\\needs2.png"));
		need.setBounds(673, 401, 107, 123);
		contentPane.add(need);
		
		JButton order = new JButton("");
		order.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.dispose();
				
				new Orders(statement, connection, usr);
			}
		});
		order.setIcon(new ImageIcon("pics\\order2.png"));
		order.setBounds(543, 401, 112, 123);
		contentPane.add(order);
		
		JLabel lblOrder = new JLabel("order");
		lblOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOrder.setBounds(584, 387, 46, 14);
		contentPane.add(lblOrder);
		
		JLabel lblNeed = new JLabel("need");
		lblNeed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNeed.setBounds(708, 387, 46, 14);
		contentPane.add(lblNeed);
	}
}
