package org.generation.italy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

import com.google.protobuf.TextFormat.ParseException;

public class Main {
	
	private final static String DB_URL = "jdbc:mysql://localhost:3306/dump_nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "Thewitchern1!";
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);
		
		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			Country country = selectCountryById(scanner, connection);
			
			if (country != null) {
				
				System.out.println("Hai scelto: " + country.getName() + " - Con Area: " + country.getArea() + " - Con giorno nazionale: " + country.getNationalDay());
				
				if (country.getNationalDay() == null && country.getCountryId() == 107) {
					
					System.out.println("National day: 17/03/1861");
					
				} else {
					System.out.println("National day: " + country.getNationalDay());
				}
			}
			
			
			}
		
		

	}

	private static Country selectCountryById(Scanner scanner, Connection connection) throws SQLException {
		
		Country country = null;
		System.out.println("Insert your ID: ");
		int id = Integer.parseInt(scanner.nextLine());
		String sql = "select * from countries c where country_id = ?;";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)){
			
			ps.setInt (1, id);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					
					country = new Country(rs.getInt(1), rs.getString(2), rs.getInt(3), null, rs.getString(5), rs.getString(6), rs.getInt(7));
					
				}
			}
			
		}
		
		
		return country;
		
	}
	
	private static void updateCountry(Scanner scanner, Connection connection) throws java.text.ParseException {
		
		String update = "";
		
		System.out.print("Inserisci il giorno di festa nazionale: ");
        int giorno = scanner.nextInt();
        System.out.print("Inserisci il mese: ");
        int mese = scanner.nextInt();
        System.out.print("Inserisci l'anno: ");
        int anno = scanner.nextInt();
        System.out.println("Data della festa nazionale: " + giorno + " - " + mese + " - " + anno);
	
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String nationalDayUpdate = scanner.nextLine();
        
        Date date = (Date) sdf.parse(nationalDayUpdate); 
   
           sdf = new SimpleDateFormat("EEE, d MMM yyyy");
           System.out.println("Date: " + sdf.format(date));
	
	
	
	}
	
	


}
