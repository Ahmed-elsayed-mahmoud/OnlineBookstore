package gui ; 

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

@SuppressWarnings("deprecation")
public class Report {
	
	void prepareReports(Connection connection){
		

		JasperReportBuilder report1 = DynamicReports.report();//a new report
		report1
		  .columns(
		  	Columns.column("ISBN", "ISBN", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("Title", "Title", DataTypes.stringType()),
		  	Columns.column("Publisher", "Pub_Name", DataTypes.stringType()),
		    Columns.column("Quantity", "TotalQuantity", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)	
		  )
		  .title(//title of the report
				  Components.text("BookStore Report\n\nThe total sales for books in the previous month\n\n")
		  			.setHorizontalAlignment(HorizontalAlignment.CENTER)
		  )
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT b.ISBN, b.Title, b.Pub_Name, SUM(Quantity) AS TotalQuantity FROM shopping_cart s, book b WHERE b.ISBN = s.ISBN AND Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 1 MONTH) GROUP BY ISBN ORDER BY SUM(Quantity) DESC", connection);

		JasperReportBuilder report2 = DynamicReports.report();//a new report
		report2
		  .columns(
		  	Columns.column("Username", "Username", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("FirstName", "FName", DataTypes.stringType()),
		  	Columns.column("LastName", "LName", DataTypes.stringType()),
		    Columns.column("Quantity", "TotalQuantity", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)	
		  )
		  .title(//title of the report
				  Components.text("BookStore Report\n\nThe top 5 customers who purchase the most purchase amount in descending order for the last three months\n\n")
		  			.setHorizontalAlignment(HorizontalAlignment.CENTER)
		  )
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT c.Username, c.FName, c.LName, SUM(Quantity) AS TotalQuantity FROM shopping_cart s, customer c WHERE s.Username = c.Username AND Date IS NOT NULL AND Date >= date_sub(current_date, INTERVAL 3 MONTH) GROUP BY Username ORDER BY SUM(Quantity) DESC LIMIT 5", connection);

		JasperReportBuilder report3 = DynamicReports.report();//a new report
		report3
		  .columns(
		  	Columns.column("ISBN", "ISBN", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("Title", "Title", DataTypes.stringType()),
		  	Columns.column("Publisher", "Pub_Name", DataTypes.stringType()),
		    Columns.column("Quantity", "TotalQuantity", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)	
		  )
		  .title(//title of the report
				  Components.text("BookStore Report\n\nThe top 10 selling books for the last three months\n\n")
		  			.setHorizontalAlignment(HorizontalAlignment.CENTER)
		  )
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT b.ISBN, b.Title, b.Pub_Name, SUM(Quantity) AS TotalQuantity FROM shopping_cart s, book b WHERE s.Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 3 MONTH) AND s.ISBN = b.ISBN GROUP BY s.ISBN ORDER BY SUM(Quantity) DESC LIMIT 10", connection);

		
		showReport(report1, "report1");
		showReport(report2, "report2");
		showReport(report3, "report3");
	}
	private void showReport(JasperReportBuilder report, String reportName){
		try {
			report.show();//show the report
			report.toPdf(new FileOutputStream(reportName+".pdf"));//export the report to a pdf file
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
