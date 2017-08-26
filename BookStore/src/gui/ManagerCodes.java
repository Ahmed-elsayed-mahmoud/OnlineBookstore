package gui;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ManagerCodes {
	
	private Set<String> manager_codes;
	public ManagerCodes()
	{
		manager_codes = new HashSet<>() ;
		read() ;
	}
	private void read() {
		// TODO Auto-generated method stub
		
		File file = new File("manager_codes.txt") ;
		
		
	    try {

	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
	            String str = sc.nextLine();
	            manager_codes.add(str) ;
	           
	        }
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean isManagerCode(String code)
	{
		return manager_codes.contains(code) ; 
	}
	
	
	
	
	

}
