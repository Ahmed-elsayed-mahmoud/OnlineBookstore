package gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import main.LogIn;
import main.Manager;
import main.User;

public class Need extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Need frame ;
	private JTable table;
	
	/*
	 * Create the frame.
	 */
	public Need(final java.sql.Statement statement ,final Connection connection , final User usr) {
		frame = this ;

		this.setTitle("Needs");
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

				logout.LogOut(usr.getName());

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
		
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "Quantity"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		table.setBounds(0, 0,1500 , 1500);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	
		contentPane.setLayout(null);
		
		JScrollPane jScrollPane = new JScrollPane(table) ;
		jScrollPane.setViewportBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		jScrollPane.setBounds(35, 11, 395, 528);
		
		getContentPane().add(jScrollPane);
		
		JButton confirmbtn = new JButton("confirm");
		confirmbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow() ;
				if (row == -1) JOptionPane.showMessageDialog(frame, "you must select one row !");
				else
				{
					String isbn = (String) table.getValueAt(row, 0) ; 
					
					try {
						((Manager)usr).needToOrder(isbn) ;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame, "can't order this !\nsql exception");
						e1.printStackTrace();
					}
					
					readNeeds(usr, table);
				}
				
				
				
				
			}
		});
		confirmbtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		confirmbtn.setIcon(new ImageIcon("pics\\needs.png"));
		confirmbtn.setBounds(500, 106, 224, 144);
		contentPane.add(confirmbtn);
		
		JButton backbtn = new JButton("back");
		backbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.dispose();
				
				new MangerHome(statement, connection, usr.getName()) ;
			}
		});
		backbtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backbtn.setIcon(new ImageIcon("pics\\back-mb-icon.png"));
		backbtn.setBounds(500, 346, 224, 129);
		contentPane.add(backbtn);

		readNeeds(usr , table) ;
		
	}
	
	private void readNeeds(User usr, JTable table2) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		
		try {
			ResultSet resultSet = ((Manager) usr).getNeeds() ;
			
			
			while (resultSet.next())
			{
				String row[] = new String[2] ;
				
				row[0] = resultSet.getString(1) ;
				row[1] = resultSet.getString(2) ;
				
				model.addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "Can't shows order. \n sql Exception");
			e.printStackTrace();
		}
		
	}

}
