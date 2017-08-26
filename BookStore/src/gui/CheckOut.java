package gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Connection;

import main.LogIn;
import main.Pair;
import main.User;

public class CheckOut extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CheckOut frame ; 
	private JTextField creditNoTf;
	private JTextField expiteDateTf;
	private JTable table;


	/*
	 * Create the frame.
	 */
	public CheckOut(final java.sql.Statement statement ,final Connection connection , final User usr) {
		frame = this ;

		this.setTitle("check Out");
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

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "card details", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel.setBounds(22, 21, 695, 65);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(6, 16, 88, 42);
		panel.add(lblCardNo);
		lblCardNo.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblExpireDate = new JLabel("Expire Date :");
		lblExpireDate.setBounds(376, 16, 94, 42);
		panel.add(lblExpireDate);
		lblExpireDate.setFont(new Font("Tahoma", Font.BOLD, 14));

		creditNoTf = new JTextField();
		creditNoTf.setBounds(97, 29, 182, 20);
		panel.add(creditNoTf);
		creditNoTf.setColumns(10);

		/*expiteDateTf = new JTextField();
		expiteDateTf.setBounds(490, 29, 199, 20);
		panel.add(expiteDateTf);
		expiteDateTf.setColumns(10);*/

		UtilDateModel model = new UtilDateModel();
		model.setDate(2019, 8, 24);
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		datePicker.setBounds(490, 29, 199, 25);
		panel.add(datePicker);


		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 97, 782, 376);
		contentPane.add(panel_1);
		panel_1.setLayout(null);


		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ISBN", "Tilte", "Quantity", "price"
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

		table.setBounds(0, 0,1500 , 1500);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane jScrollPane = new JScrollPane(table) ;
		jScrollPane.setBounds(6, 16, 770, 353);
		panel_1.add(jScrollPane);
		jScrollPane.setViewportBorder(new LineBorder(SystemColor.inactiveCaption, 2));

		JButton btnNewButton = new JButton("check out");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				String curDate = dtf.format(cal.getTime()) ;

				String cerditNo = creditNoTf.getText().trim() ;
				Date selectedDate = (Date) datePicker.getModel().getValue();
				String expireDate = dtf.format(selectedDate);

				try{
					Long.parseLong(cerditNo) ;
				}catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "card no must be digits !!");
					return ;
				}
				
				if (cerditNo.length()  > 16 && cerditNo.length() < 14 )
				{
					JOptionPane.showMessageDialog(frame, "card no must be withen the range from 14-16");
					return ;
				}
			
				
				if (cerditNo.isEmpty() || expireDate.isEmpty() || curDate.isEmpty()) JOptionPane.showMessageDialog(frame, "fill all Field !!");
				else
				{
					System.out.println(curDate + "\n" + cerditNo + "\n" + expireDate);
					Pair pair = usr.checkOutShoppingCart(curDate, cerditNo, expireDate) ;
					if (pair.getFirst())
					{
						JOptionPane.showMessageDialog(frame, "congratulations\nyou have paid " + pair.getSecond());
						frame.setVisible(false);
						frame.dispose();
						
						LogIn log = new LogIn(statement); 
						
						if (log.isManager(usr.getName()))
						{
							new MangerHome(statement, connection, usr.getName());
						}
						else
						{
							new CustomerHome(statement, connection, usr.getName());
						}
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Error :: 605"); 
					}
				}

			}
		});
		btnNewButton.setBounds(308, 484, 122, 55);
		contentPane.add(btnNewButton);

		JButton btnDeleteOrder = new JButton("delete order");
		btnDeleteOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int row_number = table.getSelectedRow() ;
				
				if (row_number == -1)
				{
					JOptionPane.showMessageDialog(frame, "first you must select a row from that table !");
				}
				else
				{
					String isbn = (String) table.getValueAt(row_number, 0) ;
					
					try {
						usr.removeItemFromShoppingCart(isbn) ;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame, "Error :: can't delete!");
						e.printStackTrace();
					}
					
					readShoppingCart(usr, table);
					
					
				}
				
			}
		});
		btnDeleteOrder.setBounds(605, 484, 122, 55);
		contentPane.add(btnDeleteOrder);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();

				LogIn log = new LogIn(statement) ; 
				if (log.isManager(usr.getName()))
				{
					new MangerHome(statement, connection, usr.getName()) ;
				}
				else
				{
					new CustomerHome(statement, connection, usr.getName()) ;
				}
			}
		});
		btnBack.setBounds(48, 484, 122, 55);
		contentPane.add(btnBack);

		readShoppingCart(usr , table) ; 
	}


	private void readShoppingCart(User usr, JTable table2) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		try {
			Map<String , ArrayList<String>> map = usr.showShoppingCart() ;

			java.util.Iterator<Entry<String, ArrayList<String>>> it = map.entrySet().iterator();

			while(it.hasNext())
			{
				String[]row = new String[4] ;
				Map.Entry pair = it.next();
				ArrayList<String> list = (ArrayList<String>) pair.getValue() ;
				row[0] = (String) pair.getKey() ; 
				row[1] = list.get(0) ;
				row[2] = list.get(1) ;
				row[3] = list.get(2) ;

				model.addRow(row);
				it.remove(); 
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			JOptionPane.showMessageDialog(frame, "Error :: oooops !!");
		}
	}





}
