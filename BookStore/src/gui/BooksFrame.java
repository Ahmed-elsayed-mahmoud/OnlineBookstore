package gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import main.LogIn;
import main.User;

public class BooksFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BooksFrame frame;
	private JTable table;
	private JTextField isbnTf;
	private JTextField titleTf;
	private JTextField catTf;
	private JTextField priceTf;
	private JTextField pub_yearTf;
	private JTextField pub_name_tf;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public BooksFrame(final java.sql.Statement statement ,final Connection connection , final User usr){
	
		frame = this ; 

		this.setTitle("Books");
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
		
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "Tilte", "Category", "price", "publication year", "publication name"
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
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.setBounds(0, 0,1500 , 1500);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	
		contentPane.setLayout(null);
		
		JScrollPane jScrollPane = new JScrollPane(table) ;
		jScrollPane.setViewportBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		jScrollPane.setBounds(10, 11, 770, 353);
		
		getContentPane().add(jScrollPane);

		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinner.setBounds(631, 483, 36, 38);
		contentPane.add(spinner);
		
		
		JButton btnAddToYour = new JButton("add shopping cart ");
		btnAddToYour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row_number = table.getSelectedRow() ;
				
				if (row_number == -1)
				{
					JOptionPane.showMessageDialog(frame, "first you must select a row from that table !");
				}
				else
				{
					String isbn = (String) table.getValueAt(row_number, 0) ;
					String price = String.valueOf(table.getValueAt(row_number, 3)) ;
					try {
						
						usr.addToShoppingCart(isbn, String.valueOf(spinner.getValue()), price) ;
						
						JOptionPane.showMessageDialog(frame, String.valueOf(spinner.getValue()) + " of " + (String) table.getValueAt(row_number,1) + " added to you shopping cart" ) ; 
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
						JOptionPane.showMessageDialog(frame, "sql Erorr !! ");
					} 
				}
				
			}
		});
		btnAddToYour.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAddToYour.setIcon(new ImageIcon("pics\\shopping.png"));
		btnAddToYour.setBounds(432, 469, 189, 70);
		contentPane.add(btnAddToYour);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose(); 
				
				LogIn log = new LogIn(statement); 
				
				if (log.isManager(usr.getName()))
					new MangerHome(statement, connection, usr.getName()); 
				else
					new CustomerHome(statement, connection, usr.getName()); 
			}
			
		});
		btnBack.setBounds(162, 469, 153, 70);
		contentPane.add(btnBack);
		
		isbnTf = new JTextField();
		isbnTf.setBounds(10, 410, 116, 20);
		contentPane.add(isbnTf);
		isbnTf.setColumns(10);
		
		titleTf = new JTextField();
		titleTf.setColumns(10);
		titleTf.setBounds(125, 410, 116, 20);
		contentPane.add(titleTf);
		
		catTf = new JTextField();
		catTf.setColumns(10);
		catTf.setBounds(242, 410, 116, 20);
		contentPane.add(catTf);
		
		priceTf = new JTextField();
		priceTf.setColumns(10);
		priceTf.setBounds(356, 410, 116, 20);
		contentPane.add(priceTf);
		
		pub_yearTf = new JTextField();
		pub_yearTf.setColumns(10);
		pub_yearTf.setBounds(472, 410, 116, 20);
		contentPane.add(pub_yearTf);
		
		pub_name_tf = new JTextField();
		pub_name_tf.setColumns(10);
		pub_name_tf.setBounds(586, 410, 116, 20);
		contentPane.add(pub_name_tf);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String isbn = isbnTf.getText().trim() ;
				String title = titleTf.getText().trim() ;
				String cat = catTf.getText().trim() ;
				String price = priceTf.getText().trim() ;
				String pub_year = pub_yearTf.getText().trim() ;
				String pub_name = pub_name_tf.getText().trim() ;
				
				if (isbn.isEmpty() && title.isEmpty() && cat.isEmpty() && price.isEmpty() && pub_year.isEmpty() && pub_name.isEmpty())
				{
					read(usr);
				}
				else 
				{
					Map<String , String> map = new HashMap<>() ;
					
					if (!isbn.isEmpty()) map.put("ISBN", isbn) ;
					if (!title.isEmpty()) map.put("Title", title) ;
					if (!cat.isEmpty()) map.put("Category", cat) ;
					if (!price.isEmpty()) map.put("Price", price) ;
					if (!pub_year.isEmpty()) map.put("Pub_Year", title) ;
					if (!pub_name.isEmpty()) map.put("Pub_Name", cat) ;
					
					search_book(usr , map) ;
				}
				
			}
		});
		button.setIcon(new ImageIcon("pics\\search.jpeg"));
		button.setBounds(725, 407, 18, 23);
		contentPane.add(button);
		
	
		
		read(usr) ;
	
		
	}

	protected void search_book(User usr, Map<String, String> map) {
		// TODO Auto-generated method stub
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		try {
			ResultSet resultSet = usr.searchBooks(map) ;
			while (resultSet.next())
			{
				ArrayList<String> list = new ArrayList<>() ;
				for (int i = 1 ; i < 7 ; i++){
					list.add(resultSet.getString(i)) ;
				}
				String[] table_items = new String[list.size()];
				table_items = list.toArray(table_items);
				
				model.addRow(table_items);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showConfirmDialog(frame, "Error 403 !") ;
		} 
		
		
	}

	private void read(final User usr ) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		try {
			ResultSet resultSet = usr.getBooks() ;
			while (resultSet.next())
			{
				ArrayList<String> list = new ArrayList<>() ;
				for (int i = 1 ; i < 7 ; i++){
					list.add(resultSet.getString(i)) ;
				}
				String[] table_items = new String[list.size()];
				table_items = list.toArray(table_items);
				
				model.addRow(table_items);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showConfirmDialog(frame, "Error 403 !") ;
		} 
		
	}
}
