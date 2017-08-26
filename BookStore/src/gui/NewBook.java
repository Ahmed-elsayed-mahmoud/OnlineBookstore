package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.mysql.jdbc.Connection;

import main.Authors;
import main.Book;
import main.Manager;
import main.Publisher;
import main.User;

public class NewBook extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NewBook frame ;
	private JTextField publisher_nameTf;
	private JTextField addressTf;
	private JTextField phoneTf;
	private JTextField isbnTf;
	private JTextField titleTf;
	private JTextField priceTf;
	private JTextField pub_yearTF;
	private JTextField author_name;
	private JTextField threshTF;

	/**
	 * Create the frame.
	 * @param statement 
	 * @param connection 
	 */
	@SuppressWarnings("unchecked")
	public NewBook(final Connection connection, final Statement statement, final User manger) {
		frame = this ; 
		this.setTitle("Home");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Publisher Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(33, 32, 238, 165);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("name ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(6, 19, 46, 14);
		panel.add(lblName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(6, 79, 79, 20);
		panel.add(lblAddress);
		
		JLabel lblPhone = new JLabel("phone");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhone.setBounds(6, 141, 46, 14);
		panel.add(lblPhone);
		
		publisher_nameTf = new JTextField();
		publisher_nameTf.setBounds(60, 16, 149, 20);
		panel.add(publisher_nameTf);
		publisher_nameTf.setColumns(10);
		
		addressTf = new JTextField();
		addressTf.setBounds(60, 79, 148, 20);
		panel.add(addressTf);
		addressTf.setColumns(10);
		
		phoneTf = new JTextField();
		phoneTf.setBounds(60, 138, 149, 20);
		panel.add(phoneTf);
		phoneTf.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Book Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(469, 32, 278, 374);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(6, 16, 66, 26);
		panel_1.add(lblIsbn);
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(6, 74, 66, 26);
		panel_1.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPrice = new JLabel("price");
		lblPrice.setBounds(6, 138, 66, 26);
		panel_1.add(lblPrice);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(127, 210, 145, 32);
		panel_1.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Geography", "History", "Science", "Art", "Religion"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPublicationYear = new JLabel("publication year");
		lblPublicationYear.setBounds(6, 271, 109, 32);
		panel_1.add(lblPublicationYear);
		lblPublicationYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		isbnTf = new JTextField();
		isbnTf.setBounds(127, 21, 145, 20);
		panel_1.add(isbnTf);
		isbnTf.setColumns(10);
		
		titleTf = new JTextField();
		titleTf.setBounds(127, 79, 145, 20);
		panel_1.add(titleTf);
		titleTf.setColumns(10);
		
		priceTf = new JTextField();
		priceTf.setBounds(127, 149, 145, 20);
		panel_1.add(priceTf);
		priceTf.setColumns(10);
		
		pub_yearTF = new JTextField();
		pub_yearTF.setBounds(127, 279, 145, 20);
		panel_1.add(pub_yearTF);
		pub_yearTF.setColumns(10);
		
		JLabel lblCategory = new JLabel("category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCategory.setBounds(6, 213, 66, 26);
		panel_1.add(lblCategory);
		
		JLabel lblThreshHold = new JLabel("thresh hold");
		lblThreshHold.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblThreshHold.setBounds(6, 335, 109, 28);
		panel_1.add(lblThreshHold);
		
		threshTF = new JTextField();
		threshTF.setColumns(10);
		threshTF.setBounds(127, 335, 145, 20);
		panel_1.add(threshTF);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Author info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(27, 270, 225, 119);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("name");
		lblNewLabel.setBounds(6, 16, 50, 38);
		panel_2.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		author_name = new JTextField();
		author_name.setBounds(70, 26, 149, 20);
		panel_2.add(author_name);
		author_name.setColumns(10);
		
		final DefaultListModel<String> model = new DefaultListModel<>();
		JList<String>list = new JList<>(model);
		
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBounds(33, 400, 220, 165);
		getContentPane().add(listScroller);
		
		JButton btnAddAuthor = new JButton("add Author");
		btnAddAuthor.setBounds(38, 74, 136, 38);
		panel_2.add(btnAddAuthor);
		btnAddAuthor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = author_name.getText().trim(); 
				if (name == null || name.isEmpty()) return ;
				model.addElement(name);
			}
		});
		btnAddAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		
		JButton btnNewButton = new JButton("add new book");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String pub_name = publisher_nameTf.getText().trim() ;
				String address = addressTf.getText().trim() ;
				String phone = phoneTf.getText().trim() ;  
				
				if (pub_name.isEmpty() || address.isEmpty() || phone.isEmpty()) 
				{
					JOptionPane.showMessageDialog(frame, "fill all fields"); 
					return ; 
				}
				
				Publisher p = new Publisher() ; 
				p.setAddress(address);
				p.setPhone(phone);
				p.setPubName(pub_name);
				
				
				String isbn = isbnTf.getText().trim() ;
				String title = titleTf.getText().trim() ;
				String category = String.valueOf(comboBox.getSelectedItem()) ;
				String price = priceTf.getText().trim() ;
				String pub_year = pub_yearTF.getText().trim() ;
				String thresh = threshTF.getText().trim() ;
				
				if (isbn.isEmpty() || title.isEmpty() || category.isEmpty() || price.isEmpty() || pub_year.isEmpty() || thresh.isEmpty()) 
				{
					JOptionPane.showMessageDialog(frame, "fill all fields"); 
					return ; 
				}
				Book b = new Book(); 
				b.setCatgory(category);
				b.setISBN(isbn);
				b.setPrice(price);
				b.setPubName(pub_name);
				b.setTitle(title);
				b.setPubYear(pub_year);
				
				if (model.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "fill all fields"); 
					return ;
				}
				ArrayList<Authors> authors = new ArrayList<>() ;
				for (int i = 0 ; i < model.getSize() ; i++)
				{
					Authors author = new Authors() ;
					author.setAuthorName(model.get(i));
					author.setISBN(isbn);
					authors.add(author);
				}
				
				((Manager) manger).addNewBook(b, p , authors , thresh) ;
				
				frame.setVisible(false);
				frame.dispose();
				
				new MangerHome(statement, connection , manger.getName());
				
			}
		});
		btnNewButton.setIcon(new ImageIcon("pics\\add_book.png"));
		btnNewButton.setBounds(345, 432, 248, 128);
		getContentPane().add(btnNewButton);
		
		JButton btnCancell = new JButton("cancel");
		btnCancell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
				
				new MangerHome(statement, connection , manger.getName());
			}
		});
		btnCancell.setBounds(658, 496, 89, 23);
		getContentPane().add(btnCancell);
	}
}
